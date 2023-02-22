import styled from 'styled-components';
import { Link, useParams } from 'react-router-dom';
import Like from '../features/questionDetail/Like';
import Author from '../features/questionDetail/Author';
import { useState } from 'react';
import Preview from '../features/questionDetail/Preview';
import QuestionContent from '../features/questionDetail/QuestionContent';
import { dummyData } from '../dummyData';

const Container = styled.div`
  display: flex;
  justify-content: right;
  background-color: #ffffff;
`;

const Content = styled.div`
  width: 80%;
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
  align-items: left;
`;

const TitleContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const H2 = styled.h2`
  width: 80%;
  margin-top: 0;
`;

const AskButton = styled.button`
  width: fit-content;
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

const TitleStateContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  gap: 0.3rem;
  padding-bottom: 1rem;
  margin-bottom: 1rem;
  border-bottom: 0.5px solid gray;
`;

const LeftSpan = styled.span`
  color: gray;
  font-size: small;
`;
const RightSpan = styled.span`
  font-size: small;
`;

const QuestionContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  gap: 2rem;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 0.5px solid gray;
`;

const QuestionContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left;
`;

const TagContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  gap: 0.5rem;
`;

const TagSpan = styled.span`
  padding: 0.3rem;
  background-color: #e1ecf4;
  color: #467ba3;
  font-size: small;
`;

const AnswerContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  gap: 2rem;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 0.5px solid gray;
`;

const Textarea = styled.textarea`
  padding: 1rem;
  height: 18rem;
  margin-bottom: 1rem;
`;

function Question_Detail() {
  const { id } = useParams();
  const [content, setContent] = useState('');

  return (
    <Container>
      <Content>
        <Mainbar>
          <TitleContainer>
            <H2>how redirect user with stripe react component and django</H2>
            <Link to="/create">
              <AskButton>Ask Question</AskButton>
            </Link>
          </TitleContainer>
          <TitleStateContainer>
            <div>
              <LeftSpan>Asked</LeftSpan> <RightSpan>16 days ago</RightSpan>
            </div>
            <div>
              <LeftSpan>Modified</LeftSpan> <RightSpan>9 days ago</RightSpan>
            </div>
            <div>
              <LeftSpan>Viewed</LeftSpan> <RightSpan>6k times</RightSpan>
            </div>
          </TitleStateContainer>
          <QuestionContainer>
            <Like size={7} />
            <QuestionContentContainer>
              <QuestionContent qContent={dummyData[id].content} />
              <TagContainer>
                <TagSpan>python</TagSpan>
                <TagSpan>reactjs</TagSpan>
                <TagSpan>django</TagSpan>
              </TagContainer>
              <Author name="Bastien Angeloz" />
            </QuestionContentContainer>
          </QuestionContainer>
          <p>1 Answer</p>
          <AnswerContainer>
            <Like size={8} />
            <QuestionContentContainer>
              <p>
                To set up redirection to your application after successful
                payment, it can be done by setting in the pricing table page in
                Dashboard. You can select Dont show confirmation page in every
                price to disable showing Stripes confirmation page and set the
                return URL to direct to your website.
              </p>
              <p>Heres the screenshot of where you can set it up:</p>
              [Img]
              <Author name="yuting" />
            </QuestionContentContainer>
          </AnswerContainer>
          <p>Your Answer</p>
          <Textarea
            value={content}
            onChange={(e) => {
              let text = e.target.value;
              setContent(text);
            }}
          />
          <Preview content={content} />
          <AskButton>Post Your Answer</AskButton>
        </Mainbar>

        {/* <Sidebar>Sidebar</Sidebar> */}
      </Content>
    </Container>
  );
}
export default Question_Detail;
