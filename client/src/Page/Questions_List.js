/* eslint-disable no-unused-vars */
import styled from 'styled-components';
import Question from '../Component/Question';
import Pagination from '../Component/Pagination';
import { Link } from 'react-router-dom';
import Footer from '../Component/Footer';
import useGetFetch from '../Util/useGetFetch';
import { url } from '../url';
import Nav from '../Component/Nav';
import Loading from '../Component/Loading';
import { useSelector } from 'react-redux';

const MainDiv = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  max-width: 1264px;
  margin: 0 auto;
`;

const Container = styled.div`
  display: flex;
  justify-content: right;
  width: 100%;
`;

const Content = styled.div`
  display: flex;
  justify-content: left;
  padding: 1.5rem;
  gap: 1rem;
  width: 100%;
`;

const Mainbar = styled.div`
  width: 50rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;

// eslint-disable-next-line no-unused-vars
const Sidebar = styled.div`
  width: 300px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid red;
  margin-left: 1rem;
`;

const MainComponent = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
`;

const RowDiv = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
`;

const RowWrapDiv = styled.div`
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
`;

const H1 = styled.h1`
  margin-top: 0;
`;

const AskButton = styled.button`
  pointer-events: ${(props) => (!props.log ? 'none' : 'all')};
  height: 2.5rem;
  padding: 0 0.6rem;
  background-color: #1295ff;
  a {
    color: white;
    border: none;
    cursor: pointer;
    text-decoration: none;
  }
  :hover {
    background-color: #0088ff;
  }
`;

const QuestionDiv = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: left;
  align-items: left;
`;

const ArrayDiv = styled.div`
  display: flex;
  justify-content: center;
`;

const ArrayButton = styled.button`
  padding: 0.5rem;
  color: #738089;
  background-color: white;
  border: 1px solid #738089;
  cursor: pointer;
  :hover {
    background-color: #ffdfcf;
  }
`;

const PagingButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;

  height: 1.8rem;
  background-color: ${(props) => props.backColor || 'none'};
  padding: 0.6rem;
  border: 1px solid #738089;

  cursor: pointer;
`;

function Questions_List() {
  const [data, isPending, error] = useGetFetch(
    `${url}/question/?page=0&&size=5`
  );
  console.log(data);
  const sortArr = ['createdAt', 'viewCount', 'voteCount'];
  const log = useSelector((state) => state.log.value);
  console.log(log);
  return (
    <div>
      {isPending ? (
        <Loading />
      ) : (
        <>
          <MainDiv>
            <Nav />
            <Container>
              <Content>
                <Mainbar>
                  <MainComponent>
                    <H1>All Questions</H1>
                    <AskButton log={log}>
                      <a href="/create">Ask Question</a>
                    </AskButton>
                  </MainComponent>
                  <MainComponent>
                    <div>{data.pageInfo.totalElements} questions</div>
                    <RowDiv>
                      <ArrayDiv>
                        {sortArr.map((el, index) => {
                          return (
                            <Link key={index} to={`/questions/1/5/${el}`}>
                              <ArrayButton>{el}</ArrayButton>
                            </Link>
                          );
                        })}
                      </ArrayDiv>
                      <ArrayButton>Filter</ArrayButton>
                    </RowDiv>
                  </MainComponent>
                  <MainComponent>
                    <QuestionDiv>
                      {data.data.map((obj, index) => {
                        return <Question key={index} question={obj}></Question>;
                      })}
                    </QuestionDiv>
                  </MainComponent>
                  <RowWrapDiv>
                    <Pagination
                      size={data.pageInfo.totalElements}
                      pageCnt={data.pageInfo.size}
                      currentPage={data.pageInfo.page}
                      tap={'createdAt'}
                    />
                    <RowDiv>
                      <Link to={`/questions/1/5/createdAt`}>
                        <PagingButton>5</PagingButton>
                      </Link>
                      <Link to={`/questions/1/10/createdAt`}>
                        <PagingButton>10</PagingButton>
                      </Link>
                      <Link to={`/questions/1/15/createdAt`}>
                        <PagingButton>15</PagingButton>
                      </Link>
                      per page
                    </RowDiv>
                  </RowWrapDiv>
                </Mainbar>

                {/* <Sidebar>Sidebar</Sidebar> */}
              </Content>
            </Container>
          </MainDiv>
          <Footer />
        </>
      )}
    </div>
  );
}

export default Questions_List;
