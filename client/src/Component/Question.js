/* eslint-disable react/prop-types */
import styled from 'styled-components';
import { Link } from 'react-router-dom';

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
  const timeCalc = (time) => {
    const mDay = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    const date = new Date();
    const year = Number(date.getUTCFullYear()) - Number(time.slice(0, 4));
    const month = Number(date.getUTCMonth() + 1) - Number(time.slice(5, 7));
    const day = Number(date.getUTCDate()) - Number(time.slice(8, 10));
    const hour = Number(date.getUTCHours()) - Number(time.slice(11, 13));
    const min = Number(date.getUTCMinutes()) - Number(time.slice(14, 16));
    const sec = Number(date.getUTCSeconds()) - Number(time.slice(17, 19));

    console.log(date);
    console.log(Number(date.getUTCHours()));
    console.log(time);
    console.log(hour);

    if (year > 1) return `${year} years ago`;
    else if (year === 1) return `${12 + month} months ago`;
    if (month > 1) return `${month} months ago`;
    else if (month === 1)
      return `${mDay[Number(date.getUTCMonth())] + day} days ago`;

    if (day) return `${day} days ago`;
    if (hour) return `${hour} hours ago`;
    if (min) return `${min} mins ago`;
    if (sec) return `${sec} secs ago`;
    else return 'now';
  };

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
