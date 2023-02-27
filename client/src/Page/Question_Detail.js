/* eslint-disable no-unused-vars */
import styled from 'styled-components';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import { Link, useParams } from 'react-router-dom';

import Like from '../features/questionDetail/Like';
import Author from '../features/questionDetail/Author';
import Preview from '../features/questionDetail/Preview';
import ContentRender from '../features/questionDetail/ContentRender';
import Footer from '../Component/Footer';
import AnswerLike from '../features/questionDetail/AnswerLike';
import useGetFetch from '../Util/useGetFetch';
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
  flex-direction: column;
  justify-content: left;
  gap: 2rem;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 0.5px solid gray;
`;

const AnswerRowContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  gap: 2rem;
`;

const AnswerColumContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: left;
  gap: 2rem;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
`;

const Textarea = styled.textarea`
  padding: 1rem;
  height: 18rem;
  margin-bottom: 1rem;
`;

const YourAnswerContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: left;
  gap: 2rem;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 0.5px solid gray;
`;

function Question_Detail() {
  const { id } = useParams(); // questionId
  const [question, isPending, error] = useGetFetch(
    `http://13.125.1.215:8080/question/${id}`
  );
  const tag = [123, 345, 5125];
  console.log(question);
  const log = useSelector((state) => state.log.value);
  const [content, setContent] = useState('');

  const keyDownHandler = (e) => {
    if (e.target.selectionStart === e.target.selectionEnd) return;
    let prevText = e.target.value.slice(0, e.target.selectionStart);
    let nextText = e.target.value.slice(e.target.selectionEnd);
    let selectedText = e.target.value.slice(
      e.target.selectionStart,
      e.target.selectionEnd
    );
    if (e.key === 'q' || e.key === 'Q') {
      let originData = selectedText.split('\n');
      let filterData = selectedText
        .split('\n')
        .filter((el) => el.slice(0, 4) === '    ');

      if (originData.length === filterData.length) {
        e.target.value =
          prevText +
          selectedText
            .split('\n')
            .map((el) => {
              return `${el.slice(4)}`;
            })
            .join('\n') +
          nextText;
        setContent(e.target.value);
      } else {
        e.target.value =
          prevText +
          selectedText
            .split('\n')
            .map((el) => {
              return `    ${el}`;
            })
            .join('\n') +
          nextText;
        setContent(e.target.value);
      }
    }
  };

  const postHandler = () => {
    const splitContent = content.split('\n');

    fetch('http://13.125.1.215:8080/answer', {
      credentials: 'include',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        answerId: question.answers.length + 1,
        content: splitContent,
        memberId: question.member.memberId,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
      });
  };
  return (
    <div>
      {isPending ? null : (
        <Container>
          <Content>
            <Mainbar>
              <TitleContainer>
                <H2>{question.data.title}</H2>
                <Link to="/create">
                  <AskButton>Ask Question</AskButton>
                </Link>
              </TitleContainer>
              <TitleStateContainer>
                <div>
                  <LeftSpan>Asked</LeftSpan> <RightSpan>16 days ago</RightSpan>
                </div>
                <div>
                  <LeftSpan>Modified</LeftSpan>{' '}
                  <RightSpan>9 days ago</RightSpan>
                </div>
                <div>
                  <LeftSpan>Viewed</LeftSpan>{' '}
                  <RightSpan>{question.data.viewCount} times</RightSpan>
                </div>
              </TitleStateContainer>
              <QuestionContainer>
                <Like vote={question.data.voteCount} />
                <QuestionContentContainer>
                  {/* qContent = question.data.problemBody */}
                  <ContentRender qContent={['problemBody']} />
                  {/* qContent = question.data.expectingBody */}
                  <ContentRender qContent={['expectingBody']} />
                  <TagContainer>
                    {
                      //question.tags
                      tag.map((el, index) => {
                        return <TagSpan key={index}>{el}</TagSpan>;
                      })
                    }
                  </TagContainer>
                  <Author
                    questionId={id}
                    //question.author
                    memberId={question.data.member.memberId}
                    name={question.data.member.name}
                    answered={345}
                    avatar={'img'}
                  />
                </QuestionContentContainer>
              </QuestionContainer>
              <AnswerContainer>
                <p>{question.data.answers.length} Answer</p>
                {
                  // question.answer.map
                  question.data.answers.map((el, index) => {
                    return (
                      <AnswerColumContainer key={index}>
                        <AnswerRowContainer>
                          <AnswerLike id={el.id} vote={el.vote} />
                          <ContentRender qContent={el.answerContent} />
                        </AnswerRowContainer>
                        <Author
                          name={el.author.name}
                          answered={el.author.answered}
                          avatar={el.author.avatar}
                        />
                      </AnswerColumContainer>
                    );
                  })
                }
              </AnswerContainer>
              {log ? (
                <YourAnswerContainer>
                  <p>Your Answer</p>
                  <Textarea
                    value={content}
                    onChange={(e) => {
                      setContent(e.target.value);
                    }}
                    onKeyDown={keyDownHandler}
                  />
                  <Preview content={content} />
                  <AskButton onClick={postHandler}>Post Your Answer</AskButton>
                </YourAnswerContainer>
              ) : null}
            </Mainbar>

            {/* <Sidebar>Sidebar</Sidebar> */}
          </Content>
        </Container>
      )}
      <Footer />
    </div>
  );
}
export default Question_Detail;
