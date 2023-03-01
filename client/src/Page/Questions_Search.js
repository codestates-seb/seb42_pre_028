/* eslint-disable no-unused-vars */
import styled from 'styled-components';
import Question from '../Component/Question';
import Pagination from '../Component/Pagination';
import { Link, useParams } from 'react-router-dom';
import Footer from '../Component/Footer';
import { useEffect, useState } from 'react';
import { url } from '../url';

const Container = styled.div`
  display: flex;
  justify-content: right;
`;

const Content = styled.div`
  width: 79.8%;
  display: flex;
  justify-content: left;
  padding: 1.5rem;
  border-left: 0.5px solid gray;
  gap: 1rem;
`;

const Mainbar = styled.div`
  width: 50rem;
  display: flex;
  flex-direction: column;
  align-items: center;
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
  height: 2.5rem;
  padding: 0 0.6rem;
  background-color: #1295ff;
  color: white;
  border: none;
  cursor: pointer;
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

function Questions_Search() {
  const { word, page, pageCnt, tap } = useParams();

  const [searchQuestion, setData] = useState(null);
  const [isPending, setIsPending] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setIsPending(true);
    setTimeout(() => {
      fetch(
        `${url}/question/search?title=${word}&filter=${tap}&page=${
          page - 1
        }&&size=${pageCnt}&&sorted=${tap}`
      )
        .then((res) => {
          if (!res.ok) {
            throw Error('could not fetch the data for that resource');
          }
          return res.json();
        })
        .then((data) => {
          setIsPending(false);
          setData(data);
          setError(null);
        })
        .catch((err) => {
          setIsPending(false);
          setError(err.message);
        });
    }, 300);
  }, [word, page, pageCnt, tap]);

  const sortArr = ['Relevance', 'Newest'];

  return (
    <div>
      {isPending ? null : (
        <Container>
          <Content>
            <Mainbar>
              <MainComponent>
                <H1>All Questions</H1>
                <Link to="/create">
                  <AskButton>Ask Question</AskButton>
                </Link>
              </MainComponent>
              <MainComponent>
                <div>{searchQuestion.pageInfo.totalElements} questions</div>
              </MainComponent>
              <MainComponent>
                <QuestionDiv>
                  {searchQuestion.data.map((obj, index) => {
                    return <Question key={index} question={obj}></Question>;
                  })}
                </QuestionDiv>
              </MainComponent>
              <RowWrapDiv>
                <Pagination
                  size={searchQuestion.pageInfo.totalElements}
                  pageCnt={searchQuestion.pageInfo.size}
                  currentPage={searchQuestion.pageInfo.page}
                  tap={tap}
                  word={word}
                />
                <RowDiv>
                  <Link to={`/search/${word}/1/5/${tap}`}>
                    <PagingButton>5</PagingButton>
                  </Link>
                  <Link to={`/search/${word}/1/10/${tap}`}>
                    <PagingButton>10</PagingButton>
                  </Link>
                  <Link to={`/search/${word}/1/15/${tap}`}>
                    <PagingButton>15</PagingButton>
                  </Link>
                  per page
                </RowDiv>
              </RowWrapDiv>
            </Mainbar>

            {/* <Sidebar>Sidebar</Sidebar> */}
          </Content>
        </Container>
      )}
      <Footer />
    </div>
  );
}

export default Questions_Search;
