import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import Question from '../Question';
// import Pagination from '../Component/Pagination';
// import { Link } from 'react-router-dom';
// import useGetFetch from '../../Util/useGetFetch';
import { useSelector } from 'react-redux';

const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

const H2 = styled.h2`
  font-size: 1.61538462rem;
  margin: 0px;
  vertical-align: baseline;
  font-weight: normal;
`;

const Menubar = styled.div`
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin-bottom: 8px;
`;

const Main = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid #d6d9dc;
  border-radius: 5px;
`;

const NoQuestions = styled.div`
  max-width: calc(97.2307692rem / 12 * 4);
  padding: 48px;
  margin: 0 auto 0 auto;

  text-align: center;
  color: #6a737c;
`;

const Questions = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
`;

const DeletedQ = styled.div`
  padding: 16px;
  border-top: 1px solid #e3e6e8;
`;

function MyQuestionList() {
  const userDataState = useSelector((state) => state.userData);

  const useGetFetch = (url) => {
    const [data, setData] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
      const accessToken = localStorage.getItem('Authorization');
      setTimeout(() => {
        fetch(url, {
          credentials: 'include',
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: accessToken,
          },
        })
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
      }, 500);
    }, [url]);
    return [data, isPending, error];
  };

  const [data, isPending] = useGetFetch(
    `http://13.125.1.215:8080/members/${userDataState.memberId}/question?page=0&size=5`
  );
  console.log(data);

  const [data2] = useGetFetch(
    `http://13.125.1.215:8080/members/${userDataState.memberId}/answer?page=1&size=5`
  );
  console.log(data2);

  return (
    <React.Fragment>
      <Container>
        <Menubar>
          <div>
            <H2>0 Questions</H2>
          </div>
          <div>
            <button>Score</button>
            <button>Activity</button>
            <button>Newest</button>
            <button>View</button>
          </div>
        </Menubar>
      </Container>
      <Main>
        {isPending || !data ? (
          <NoQuestions>You have not asked any questions</NoQuestions>
        ) : (
          <Questions>
            {data.data.map((obj, index) => {
              return <Question key={index} question={obj}></Question>;
            })}
          </Questions>
        )}

        <DeletedQ>Deleted questions</DeletedQ>
      </Main>
    </React.Fragment>
  );
}

export default MyQuestionList;
