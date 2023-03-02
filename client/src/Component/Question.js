/* eslint-disable react/prop-types */
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { timeCalc } from '../features/questionDetail/Author';

const Container = styled.div`
  display: flex;
  justify-content: left;
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

const TagSpan = styled.button`
  border: none;
  padding: 0.3rem;
  background-color: #e1ecf4;
  color: #467ba3;
  font-size: small;
  cursor: pointer;
  :hover {
    background-color: #e1e5f1;
  }
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
  width: 100%;
  display: flex;
  justify-content: left;
  margin-bottom: 1rem;
  gap: 0.3rem;
`;

const AnswerBox = styled.div`
  padding: 0.2rem;
  border: ${(props) => (props.adopted ? '1px solid green' : 'none')};
  color: ${(props) => (props.adopted ? 'green' : 'black')};
  gap: 0.5rem;
`;

const BoldSpan = styled.span`
  font-weight: bold;
`;

const ColorSpan = styled.span`
  color: #488fd6;
`;

function Tag({ tag }) {
  return <TagSpan>{tag}</TagSpan>;
}

function Question({ question }) {
  return (
    <Container>
      <ColumDiv>
        <RowDiv>
          <span>{question.voteCount} vote</span>
        </RowDiv>

        <RowDiv>
          <AnswerBox adopted={question.adoptAnswerId}>
            <span>{question.answerCount} answer</span>
          </AnswerBox>
        </RowDiv>
        <RowDiv>
          <span>{question.viewCount} views</span>
        </RowDiv>
      </ColumDiv>

      <ColumDiv>
        <H3>
          <Link to={`/questions/${question.questionId}`}>{question.title}</Link>
        </H3>
        <div>{question.problemBody[0]}</div>
        <ColumDiv>
          <TagDiv>
            {question.tag.map((el, index) => {
              return <Tag key={index} tag={el} />;
            })}
          </TagDiv>
          <AuthorDiv>
            <span>{question.member.iconImageUrl}</span>
            <ColorSpan>{question.member.displayName}</ColorSpan>
            <BoldSpan>
              {question.member.myQuestionCount + question.member.myAnswerCount}
            </BoldSpan>
            <span>asked</span>
            <span>{timeCalc(question.createdAt)}</span>
          </AuthorDiv>
        </ColumDiv>
      </ColumDiv>
    </Container>
  );
}

export default Question;
