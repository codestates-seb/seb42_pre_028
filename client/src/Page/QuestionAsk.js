import React, { useState /*, useEffect*/ } from 'react';
import styled from 'styled-components';
import Footer from '../Component/Footer';
import { useNavigate } from 'react-router-dom';
//import { useSelector /*, useDispatch*/ } from 'react-redux';
// import Editor from '../Component/Editor';

const Container = styled.div`
  padding: 0px 24px 24px;
  margin: 0;
  display: flex;
  justify-content: space-around;
`;

const Content = styled.div`
  width: 100%;
  max-width: 1264px;
  border-left: 0;
  border-right: 0;
`;

const TitleContainer = styled.div`
  margin: 0px 0px 12px;
  height: 125px;
  display: flex;
  align-items: center;
`;

const H1 = styled.h1`
  margin: 24px 0px 27px;
  font-size: 27px;
`;

const CreatBox = styled.div`
  padding: 24px;
  max-width: 70rem;
  border: 1px solid #dcdcdc;
  border-radius: 3px;
  margin: 12px 0px;
`;

const NoticeContainer = styled.div`
  padding: 24px;
  background-color: #ebf4fb;
  border: 1px solid #a0d1f7;
  border-radius: 3px;
  max-width: 70rem;
`;

const NoticeTitle = styled.h2`
  margin: 0px 0px 8px;
  font-size: 15px;
  margin-bottom: 12px;
`;
const NoticeDescriptionTop = styled.p`
  font-size: 15px;
  margin: 0px;
`;

const NoticeDescription = styled.p`
  font-size: 15px;
  margin: 0;
  padding: 0;
`;

const NoticeList = styled.h5`
  font-size: 13px;
  margin: 0px 0px 8px;
`;

const NoticeContent = styled.li`
  list-style-tpye: disc;
  font-size: 14px;
  margin-bottom: 0px;
  margin-left: 20px;
`;

const MainContent = styled.main``;

const Input = styled.input`
  width: 100%;
  margin: 0;
  padding: 0.6em 0.7em;
  border: 1.5px solid #dcdcdc;
  border-radius: 3px;
  margin-bottom: 8px;
  margin-top: 8px;
  outline: none;
  &:focus {
    border-color: #96c7ed;
    box-shadow: 0 0 0 0.3em #e8f5ff;
  }
`;

const LabelBox = styled.div``;

const InputBox = styled.div``;

const LabelAndDescription = styled.div``;

const MainLabel = styled.label`
  padding: 0px 2px;
  font-weight: bold;
`;

const MainDescription = styled.p`
  font-size: 12px;
  font-weight: 400;
  margin-bottom: 8px;
`;

const MainInput = styled.textarea`
  width: 100%;
  height: 170px;
  margin: 0;
  padding: 6px 12px 12px;
  border: 1.5px solid #dcdcdc;
  border-radius: 3px;
  margin-bottom: 8px;
  margin-top: 8px;
  outline: none;
  resize: vertical;
  &:focus {
    border-color: #96c7ed;
    box-shadow: 0 0 0 0.3em #e8f5ff;
  }
`;

const SubmitAndClear = styled.div`
  display: flex;
`;

const Button = styled.button`
  background-color: #0a95ff;
  margin: 0px 8px;
  color: white;
  border: none;
  padding: 10.4px;
  border-radius: 5px;
  cursor: pointer;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  &:hover {
    background-color: #0a6ecd;
  }
  &:active {
    border-color: #96c7ed;
    background-color: #0064cd;
    box-shadow: 0 0 0 0.3em #e0ebff;
  }
`;

const ClearButton = styled.button`
  background-color: #ffffff;
  color: #d22e32;
  border: none;
  padding: 10.4px;
  border-radius: 5px;
  position: relative;
  overflow: hidden;
  cursor: pointer;
  &:hover {
    background-color: #fff0f5;
  }
  &:active {
    border-color: #c22e32;
    background-color: #f9d2d3;
    box-shadow: 0 0 0 0.3em #ffdfdc;
  }
`;

function QuestionAsk() {
  const [title, setTitle] = useState('');
  const [problemBody, setProblemBody] = useState('');
  const [expectingBody, setExpectingBody] = useState('');
  const navigate = useNavigate();
  //const dispatch = useDispatch();
  //const state = useSelector((state) => state.log);

  const questionHandler = () => {
    /*useEffect(() => {
      if (state === false) {
        navigate('/login');
      }
    });*/
    const url = 'http://13.125.1.215:8080/question';

    const questionData = {
      title: title,
      problemBody: problemBody,
      expectingBody: expectingBody,
      memberId: 1, // 쿠키에서 멤버 아이디 가져오기   쿠키.memberId
    };

    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(questionData),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        navigate(`/questions/${data.questionId}`); // 서버로부터 받은 응답 데이터 출력
      })
      .catch((error) => {
        console.error(error); // 에러 처리
      });
  };

  return (
    <React.Fragment>
      <Container>
        <Content>
          <TitleContainer>
            <H1>Ask a public question</H1>
          </TitleContainer>
          <NoticeContainer>
            <NoticeTitle>Writing a good question</NoticeTitle>
            <NoticeDescriptionTop>
              You’re ready to ask a programming-related question and this form
              will help guide you through the process.
            </NoticeDescriptionTop>
            <NoticeDescription>
              Looking to ask a non-programming question? See the topics here to
              find a relevant site.
            </NoticeDescription>
            <NoticeList>Steps</NoticeList>
            <NoticeContent>
              Summarize your problem in a one-line title.
            </NoticeContent>
            <NoticeContent>Describe your problem in more detail.</NoticeContent>
            <NoticeContent>
              Describe what you tried and what you expected to happen.
            </NoticeContent>
            <NoticeContent>
              Add “tags” which help surface your question to members of the
              community
            </NoticeContent>
            <NoticeContent>
              Review your question and post it to the site.
            </NoticeContent>
          </NoticeContainer>
          <MainContent>
            <CreatBox>
              <InputBox>
                <LabelBox>
                  <LabelAndDescription>
                    <MainLabel>Title</MainLabel>
                    <MainDescription>
                      Be specific and imagine you’re asking a question to
                      another person.
                    </MainDescription>
                  </LabelAndDescription>
                </LabelBox>
                <Input
                  id="title-create"
                  type="string"
                  placeholder="e.g. Is there an R function for finding the index of an element in a vector?"
                  value={title}
                  onChange={(e) => setTitle(e.target.value)}
                ></Input>
              </InputBox>
              <Button>Next</Button>
            </CreatBox>

            <CreatBox>
              <InputBox>
                <LabelBox>
                  <LabelAndDescription>
                    <MainLabel>What are the details of your problem?</MainLabel>
                    <MainDescription>
                      Introduce the problem and expand on what you put in the
                      title. Minimum 20 characters.
                    </MainDescription>
                  </LabelAndDescription>
                </LabelBox>
                <MainInput
                  id="problemBody-create"
                  type="string"
                  value={problemBody}
                  onChange={(e) => setProblemBody(e.target.value)}
                ></MainInput>
              </InputBox>
              <Button>Next</Button>
            </CreatBox>

            <CreatBox>
              <InputBox>
                <LabelBox>
                  <LabelAndDescription>
                    <MainLabel>
                      What did you try and what were you expecting?
                    </MainLabel>
                    <MainDescription>
                      Describe what you tried, what you expected to happen, and
                      what actually resulted. Minimum 20 characters.
                    </MainDescription>
                  </LabelAndDescription>
                </LabelBox>
                <MainInput
                  id="expectingBody-create"
                  type="string"
                  value={expectingBody}
                  onChange={(e) => setExpectingBody(e.target.value)}
                ></MainInput>
              </InputBox>
              <Button>Next</Button>
            </CreatBox>

            <CreatBox>
              <InputBox>
                <LabelBox>
                  <LabelAndDescription>
                    <MainLabel>Tags</MainLabel>
                    <MainDescription>
                      Add up to 5 tags to describe what your question is about.
                      Start typing to see suggestions.
                    </MainDescription>
                  </LabelAndDescription>
                </LabelBox>
                <Input
                  type="text"
                  placeholder="e.g. (json node.js python)"
                ></Input>
              </InputBox>
            </CreatBox>

            <SubmitAndClear>
              <Button onClick={questionHandler}>Post your question</Button>
              <ClearButton>Discard draft</ClearButton>
            </SubmitAndClear>
          </MainContent>
        </Content>
      </Container>
      <Footer />
    </React.Fragment>
  );
}

export default QuestionAsk;
