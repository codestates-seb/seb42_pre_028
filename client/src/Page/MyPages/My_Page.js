import React from 'react';
import styled from 'styled-components';
import { useSelector } from 'react-redux';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';
import MyPActNav from '../../Component/MyPages/myP_Act_nav';
import MyQuestionList from '../../Component/MyPages/myP_myQ_list';
import Footer from '../../Component/Footer';
import Nav from '../../Component/Nav';

const MainDiv = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  max-width: 1264px;
  margin: 0 auto;
`;

const Container = styled.div`
  /* margin: 0px 65px 0px 65px; */
  width: 100%;
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

const PlsLoginDiv = styled.div`
  padding: 24px;
  height: calc(100vh - 23.4rem);
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
`;

function My_Page() {
  const state = useSelector((state) => state.log);
  if (
    !(
      state.value === 1 ||
      state.value === '1' ||
      state.value === 0 ||
      state.value === '0'
    )
  ) {
    window.location.reload();
  }

  return (
    <React.Fragment>
      <MainDiv>
        <Nav />
        <Container>
          {state.value === 1 || state.value === '1' ? (
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
          ) : (
            <PlsLoginDiv>로그인이 필요한 페이지입니다.</PlsLoginDiv>
          )}
        </Container>
      </MainDiv>
      <Footer />
    </React.Fragment>
  );
}

export default My_Page;
