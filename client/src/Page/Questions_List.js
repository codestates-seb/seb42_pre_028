import styled from 'styled-components';
import Question from '../Component/Question';
import { useState } from 'react';
import Pagination from '../Component/Pagination';
import { dummyData } from '../dummyData';
import { Link } from 'react-router-dom';

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
  display: flex;
  flex-direction: column;
  align-items: center;
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
  const size = dummyData.length;
  const [pageCnt, setPageCnt] = useState(5);
  const [currentPage, setCurrentPage] = useState(1);

  let start = (currentPage - 1) * pageCnt,
    end = currentPage * pageCnt;

  const renderData = dummyData.filter((el) => el.id >= start && el.id < end);

  const sortArr = ['Newest', 'Active', 'Bountied', 'Unanswered', 'More'];

  const pageHandler = (e) => {
    setPageCnt(e.target.value);
    setCurrentPage(1);
  };

  return (
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
            <div>{size} questions</div>
            <RowDiv>
              <ArrayDiv>
                {sortArr.map((el, index) => {
                  return <ArrayButton key={index}>{el}</ArrayButton>;
                })}
              </ArrayDiv>
              <ArrayButton>Filter</ArrayButton>
            </RowDiv>
          </MainComponent>
          <MainComponent>
            <QuestionDiv>
              {renderData.map((obj, index) => {
                return <Question key={index} question={obj}></Question>;
              })}
            </QuestionDiv>
          </MainComponent>
          <RowWrapDiv>
            <Pagination
              size={size}
              pageCnt={pageCnt}
              currentPage={currentPage}
              setCurrentPage={setCurrentPage}
            />
            <RowDiv>
              <PagingButton onClick={pageHandler} value={5}>
                5
              </PagingButton>
              <PagingButton onClick={pageHandler} value={10}>
                10
              </PagingButton>
              <PagingButton onClick={pageHandler} value={15}>
                15
              </PagingButton>
              per page
            </RowDiv>
          </RowWrapDiv>
        </Mainbar>

        {/* <Sidebar>Sidebar</Sidebar> */}
      </Content>
    </Container>
  );
}

export default Questions_List;
