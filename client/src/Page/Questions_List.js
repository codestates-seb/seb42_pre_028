/* eslint-disable no-unused-vars */
import styled from 'styled-components';
import Question from '../Component/Question';
import { useState } from 'react';
import Pagination from '../Component/Pagination';
import { dummyData } from '../dummyData';
import { Link } from 'react-router-dom';
import Footer from '../Component/Footer';
import useGetFetch from '../Util/useGetFetch';

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
  const [data, isPending, error] = useGetFetch(
    `http://13.125.1.215:8080/question/?page=0&&size=5`
  );
  console.log(data);
  const sortArr = ['Newest', 'Active', 'Bountied', 'Unanswered', 'More'];

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
                <div>{data.pageInfo.totalElements} questions</div>
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
                />
                <RowDiv>
                  <Link to={`/questions/1/5`}>
                    <PagingButton>5</PagingButton>
                  </Link>
                  <Link to={`/questions/1/10`}>
                    <PagingButton>10</PagingButton>
                  </Link>
                  <Link to={`/questions/1/15`}>
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

export default Questions_List;
