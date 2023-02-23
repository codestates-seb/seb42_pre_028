/* eslint-disable react/prop-types */
import styled from 'styled-components';
import { Link } from 'react-router-dom';

const Container = styled.div`
  display: flex;
  justify-content: center;
  padding: 1rem;
  border-top: 0.5px solid gray;
`;

const RowDiv = styled.div`
  width: 100px;
  display: flex;
  justify-content: right;
  margin-right: 1rem;
  margin-bottom: 0.5rem;
  gap: 0.3rem;
`;

const ColumDiv = styled.div`
  display: flex;
  flex-direction: column;
  align-items: left;
`;

const H3 = styled.h3`
  margin-top: 0rem;
  margin-bottom: 0.3rem;
`;

const TagDiv = styled.div`
  display: flex;
  justify-content: left;
  margin-right: 1rem;
  margin-bottom: 0.5rem;
  margin-top: 1rem;
  gap: 0.3rem;
`;

const AuthorDiv = styled.div`
  display: flex;
  justify-content: right;
  margin-bottom: 1rem;
  gap: 0.3rem;
`;

function Tag({ tag }) {
  return <button>{tag}</button>;
}

function Question({ question }) {
  const date = new Date();

  return (
    <Container>
      <ColumDiv>
        <RowDiv>
          <span>{question.vote}</span>
          <span>vote</span>
        </RowDiv>

        <RowDiv>
          <span>{question.answer.length}</span>
          <span>answer</span>
        </RowDiv>
        <RowDiv>
          <span>{question.views}</span>
          <span>views</span>
        </RowDiv>
      </ColumDiv>

      <ColumDiv>
        <H3>
          <Link to={`/questions/${question.id}`}>{question.title}</Link>
        </H3>
        <div>{question.body}</div>
        <ColumDiv>
          <TagDiv>
            {question.tags.map((tag, index) => {
              return <Tag key={index} tag={tag} />;
            })}
          </TagDiv>
          <AuthorDiv>
            <span>[{question.author.avatar}]</span>
            <span>{question.author.name}</span>
            <span>{question.author.answered} answered</span>
            {Number(date.getSeconds() - Number(question.time.getSeconds()))}
            <span>sec ago</span>
          </AuthorDiv>
        </ColumDiv>
      </ColumDiv>
    </Container>
  );
}

export default Question;
