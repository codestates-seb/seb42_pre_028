/* eslint-disable no-unused-vars */
import styled from 'styled-components';
import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Link, useNavigate, useParams } from 'react-router-dom';
import Like from '../features/questionDetail/Like';
import Author from '../features/questionDetail/Author';
import Preview from '../features/questionDetail/Preview';
import ContentRender from '../features/questionDetail/ContentRender';
import Footer from '../Component/Footer';
import AnswerLike from '../features/questionDetail/AnswerLike';
import { url } from '../url';
import Loading from '../Component/Loading';

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
  width: 100%;
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
  const [question, setData] = useState(null);
  const [isPending, setIsPending] = useState(true);
  const [error, setError] = useState(null);

  const log = useSelector((state) => state.log.value);
  const [content, setContent] = useState('');
  const [adoptedId, setAdoptedId] = useState(0);

  const accessToken = localStorage.getItem('Authorization');

  useEffect(() => {
    setTimeout(() => {
      fetch(`${url}/question/${id}`, {
        credentials: 'include',
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: accessToken,
        },
      })
        .then((res) => {
          return res.json();
        })
        .then((data) => {
          setIsPending(false);
          setData(data);
          setError(null);
          console.log(data);
          setAdoptedId(
            ...data.data.answers
              .filter((answer) => answer.adoptStatus === 'TRUE')
              .map((answer) => answer.answerId)
          );
        })
        .catch((err) => {
          setIsPending(false);
          setError(err.message);
        });
    }, 300);
  }, []);

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

  const answerPostHandler = () => {
    const splitContent = content.split('\n');
    fetch(`${url}/answer`, {
      credentials: 'include',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: accessToken,
      },
      body: JSON.stringify({
        questionId: question.data.questionId,
        content: splitContent,
      }),
    })
      .then((res) => res.json())
      .then((data) => {
        window.location.reload();
      });
  };

  return (
    <div>
      {isPending ? (
        <Loading />
      ) : (
        <>
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
                    <LeftSpan>Asked</LeftSpan>{' '}
                    <RightSpan>16 days ago</RightSpan>
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
                  <Like
                    vote={question.data.voteCount}
                    questionvoteStatus={
                      question.data.loginUserInfo.questionvoteStatus
                    }
                  />
                  <QuestionContentContainer>
                    {/* <span>Problem</span> */}
                    <ContentRender qContent={question.data.problemBody} />
                    {/* <span>Expecting</span> */}
                    <ContentRender qContent={question.data.expectingBody} />
                    <TagContainer>
                      {
                        //question.tags
                        question.data.tag.map((el, index) => {
                          return <TagSpan key={index}>{el}</TagSpan>;
                        })
                      }
                    </TagContainer>
                    <Author
                      questionId={id}
                      memberId={question.data.member.memberId}
                      name={question.data.member.displayName}
                      answered={345}
                      avatar={'img'}
                    />
                  </QuestionContentContainer>
                </QuestionContainer>
                <AnswerContainer>
                  <p>{question.data.answers.length} Answer</p>
                  {
                    // question.answer.map
                    question.data.answers.map((answer, index) => {
                      return (
                        <AnswerColumContainer key={index}>
                          <AnswerRowContainer>
                            <AnswerLike
                              adoptedId={adoptedId}
                              setAdoptedId={setAdoptedId}
                              memberId={question.data.member.memberId}
                              answerId={answer.answerId}
                              vote={answer.voteCount}
                              answerVoteStatus={
                                question.data.loginUserInfo.answerVoteStatus
                              }
                            />
                            <ContentRender qContent={answer.content} />
                          </AnswerRowContainer>
                          <Author
                            answerId={answer.answerId}
                            memberId={answer.member.memberId}
                            name={answer.member.displayName}
                            answered={345}
                            avatar={'img'}
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
                    <AskButton onClick={answerPostHandler}>
                      Post Your Answer
                    </AskButton>
                  </YourAnswerContainer>
                ) : null}
              </Mainbar>

              {/* <Sidebar>Sidebar</Sidebar> */}
            </Content>
          </Container>
          <Footer />
        </>
      )}
    </div>
  );
}
export default Question_Detail;
