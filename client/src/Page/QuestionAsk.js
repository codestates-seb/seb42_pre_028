import React from 'react';
import styled from 'styled-components';
// import Footer from '../Component/Footer';

const Container = styled.div`
  display: flex;
  justify-content: column;
`;

const Content = styled.div`
  display: flex;
  justify-content: column;
`;

const TitleContainer = styled.div``;

const H1 = styled.h1``;

const Notice = styled.div``;

const H2 = styled.h2``;

function QuestionAsk() {
  return (
    <React.Fragment>
      <Container>
        <Content>
          <TitleContainer>
            <H1>Ask a public question</H1>
          </TitleContainer>
          <Notice>
            <H2>Writing a good question</H2>
            <p>공고1</p>
            <p>공고2</p>
          </Notice>
        </Content>
      </Container>
    </React.Fragment>
  );
}

export default QuestionAsk;
