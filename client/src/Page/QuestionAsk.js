import React from 'react';
import styled from 'styled-components';
// import Footer from '../Component/Footer';

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
`;

const H1 = styled.h1`
  margin: 24px 0px 27px;
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
  background-color: #ebf5ff;
  border: 1px solid #a0d1f7;
  border-radius: 3px;
  max-width: 70rem;
  padding: 16px;
`;

const NoticeTitle = styled.h3`
  font-size: 22px;
  font-weight: 370;
  margin-bottom: 12px;
`;

const NoticeDescription = styled.p`
  font-size: 14px;
  margin-bottom: 0px;
`;

const NoticeList = styled.h5`
  font-size: 16px;
  margin-bottom: 8px;
`;

const NoticeContent = styled.li`
  list-style-tpye: disc;
  font-size: 14px;
  margin-bottom: 0px;
  margin-left: 20px;
`;

const Maincontent = styled.main``;

const Button = styled.button`
  background-color: #0a95ff;
  color: white;
  border: none;
  padding: 10px 10px;
  border-radius: 5px;
  cursor: pointer;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2);
  position: relative;
  overflow: hidden;
  &:hover {
    background-color: #0A6ECD;
  }
  &:active {
    border-color #96C7ED;
    background-color: #0064CD;
    box-shadow: 0 0 0 0.3em 	#E0EBFF;
    }
`;

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
    border-color #96C7ED;
    box-shadow: 0 0 0 0.3em 	#E8F5FF;
    }
  }
`;

/*const Label = styled.label`
  font-size: 12px;
  font-weight: 400;
  margin-bottom: 8px;
`;*/

const LabelBox = styled.div``;

const InputBox = styled.div``;

const LabelAndDescription = styled.div``;

const MainLabel = styled.label``;

const MainDescription = styled.p`
  font-size: 12px;
  font-weight: 400;
  margin-bottom: 8px;
`;

const MainInput = styled.textarea`
width: 100%;
height:100px; 
margin: 0;
padding: 6px 12px 12px;
border: 1.5px solid #dcdcdc;
border-radius: 3px;
margin-bottom: 8px;
margin-top: 8px;
outline: none;
resize: vertical;
&:focus {
  border-color #96C7ED;
  box-shadow: 0 0 0 0.3em 	#E8F5FF;
  }
}`;

const SubmitAndClear = styled.div``;

const ClearButton = styled.button`
background-color: #ffffff;
color: #d22e32;
border: none;
padding: 10px 10px;
border-radius: 5px;
cursor: pointer;
box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2);
position: relative;
overflow: hidden;
&:hover {
  background-color: #0A6ECD;
}
&:active {
  border-color #96C7ED;
  background-color: #0064CD;
  box-shadow: 0 0 0 0.3em 	#E0EBFF;
  }
`;

function QuestionAsk() {
  return (
    <React.Fragment>
      <Container>
        <Content>
          <TitleContainer>
            <H1>Ask a public question</H1>
          </TitleContainer>
          <NoticeContainer>
            <NoticeTitle>Writing a good question</NoticeTitle>
            <NoticeDescription>
              You’re ready to ask a programming-related question and this form
              will help guide you through the process.
            </NoticeDescription>
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
          <Maincontent>
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
                  type="text"
                  placeholder="e.g. Is there an R function for finding the index of an element in a vector?"
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
                <MainInput></MainInput>
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
              <Button>Post your question</Button>
              <ClearButton>Discard draft</ClearButton>
            </SubmitAndClear>
          </Maincontent>
        </Content>
      </Container>
    </React.Fragment>
  );
}

export default QuestionAsk;
