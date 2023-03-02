// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';
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
  flex-wrap: nowrap;

  & a {
    color: #0074cc;
    text-decoration: none;
  }
`;

const LeftDiv = styled.div`
  display: flex;
  flex-direction: column;
  margin: 12px;
  width: 200px;
  min-width: 200px;
  max-width: 240px;
`;

const LeftContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  row-gap: 24px;
  min-height: 270.344px;
`;

const LeftTitle = styled.div`
  font-size: 1.61538462rem;
  margin-bottom: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  > div.communities-edit {
    font-size: 0.8rem;
  }
`;

const LeftMainDiv = styled.div`
  padding: 12px;
  border: 1px solid #d6d9dc;
  border-radius: 5px;
  background-color: white;
  padding: 12px;
  display: flex;
  flex-wrap: wrap;
`;

const StatsBox = styled.div`
  display: flex;
  flex-direction: column;
  flex-basis: calc(50% - 16px);
  margin: 8px;
`;

const UL = styled.div`
  list-style: none;
  width: 100%;

  > li {
    margin: 6px 0 6px 0;
  }
`;

const LinkDiv = styled.div`
  display: flex;
  justify-content: space-between;

  > div {
    display: flex;

    &.communities-main-total {
      color: black;
    }
  }
`;

const RightDiv = styled.div`
  display: flex;
  flex-direction: column;
  margin: 12px;
  row-gap: 24px;
`;

const RightMainDiv = styled.div`
  display: flex;
  flex-direction: column;
  padding: 32px;
  border: 1px solid #e3e6e8;
  border-radius: 5px;
  background-color: #f8f9f9;
  text-align: center;
`;

const RightTitle = styled.div`
  display: flex;
  margin-bottom: 8px;
  font-size: 1.61538462rem;
`;

const P = styled.p`
  display: flex;
  margin: 0 auto;
  max-width: calc(97.2307692rem * 3);
`;

const PlsLoginDiv = styled.div`
  padding: 24px;
  height: calc(100vh - 23.4rem);
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
`;

function MyProfile() {
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
                <LeftDiv>
                  <LeftContent>
                    <div className="stats">
                      <LeftTitle>Stats</LeftTitle>
                      <LeftMainDiv>
                        <StatsBox>
                          <div className="stats-main-1-num">1</div>
                          <div>reputation</div>
                        </StatsBox>
                        <StatsBox>
                          <div className="stats-main-2-num">0</div>
                          <div>reached</div>
                        </StatsBox>
                        <StatsBox>
                          <div className="stats-main-3-num">0</div>
                          <div>answers</div>
                        </StatsBox>
                        <StatsBox>
                          <div className="stats-main-4-num">0</div>
                          <div>questions</div>
                        </StatsBox>
                      </LeftMainDiv>
                    </div>
                    <div className="communities">
                      <LeftTitle>
                        <div className="communities-title">Communities</div>
                        <div className="communities-edit">Edit</div>
                      </LeftTitle>
                      <LeftMainDiv>
                        <UL>
                          <li>
                            <Link to="/">
                              <LinkDiv>
                                <div>Stack Overflow</div>
                                <div className="communities-main-total">1</div>
                              </LinkDiv>
                            </Link>
                          </li>
                        </UL>
                      </LeftMainDiv>
                    </div>
                  </LeftContent>
                </LeftDiv>
                <RightDiv>
                  <div className="about">
                    <RightTitle>About</RightTitle>
                    <RightMainDiv>
                      <P>
                        Your about me section is currently blank. Would you like
                        to add one? Edit profile
                      </P>
                    </RightMainDiv>
                  </div>
                  <div className="badges">
                    <RightTitle>Badges</RightTitle>
                    <RightMainDiv>
                      <P>You have not earned any badges.</P>
                    </RightMainDiv>
                  </div>
                  <div className="posts">
                    <RightTitle>Posts</RightTitle>
                    <RightMainDiv>
                      <P>Just getting started? Try answering a question!</P>
                      <P>
                        Your most helpful questions, answers and tags will
                        appear here. Start by answering a question or selecting
                        tags that match topics you’re interested in.
                      </P>
                    </RightMainDiv>
                  </div>
                </RightDiv>
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

export default MyProfile;
