import React from 'react';
import styled from 'styled-components';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';
import MyPActNav from '../../Component/MyPages/myP_Act_nav';
import MyQuestionList from '../../Component/MyPages/myP_myQ_list';
import Footer from '../../Component/Footer';

const Container = styled.div`
  margin: 0px 65px 0px 65px;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  padding: 24px;
  font-size: 0.8rem;
`;

const MainContent = styled.div`
  display: flex;
  margin-bottom: 48px;
`;

const Section = styled.div`
  width: 100%;
`;

function My_Page() {
  return (
    <React.Fragment>
      <Container>
        <Content>
          <MyPTProfile />
          <MyPMenu />
          <MainContent>
            <MyPActNav />
            <Section>
              <MyQuestionList />
            </Section>
          </MainContent>
        </Content>
      </Container>
      <Footer />
    </React.Fragment>
  );
}

export default My_Page;
