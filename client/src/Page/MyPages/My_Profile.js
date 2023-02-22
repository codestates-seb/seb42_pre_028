// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';

const Content = styled.div`
  display: flex;
  flex-direction: column;
  padding: 24px;
  font-size: 0.8rem;
`;

const ProfileContent = styled.div`
  display: flex;
  flex-wrap: nowrap;
`;

const LeftDiv = styled.div`
  display: flex;
  flex-direction: column;
`;

const LeftMainDiv = styled.div`
  padding: 12px;
  border: 1px solid #d6d9dc;
  border-radius: 5px;
  background-color: white;
`;

const RightDiv = styled.div`
  display: flex;
  flex-direction: column;
`;

const RightMainDiv = styled.div`
  padding: 32px;
  border: 1px solid #e3e6e8;
  border-radius: 5px;
  background-color: #f8f9f9;
  text-align: center;
`;

function MyProfile() {
  return (
    <React.Fragment>
      <Content>
        <MyPTProfile />
        <MyPMenu />
        <ProfileContent>
          <LeftDiv>
            <div className="stats">
              <div className="stats-title">Stats</div>
              <LeftMainDiv>
                <div className="stats-main-1">
                  <div className="stats-main-1-num">1</div>
                  reputation
                </div>
                <div className="stats-main-2">
                  <div className="stats-main-2-num">0</div>
                  reached
                </div>
                <div className="stats-main-3">
                  <div className="stats-main-3-num">0</div>answers
                </div>
                <div className="stats-main-4">
                  <div className="stats-main-4-num">0</div>questions
                </div>
              </LeftMainDiv>
            </div>
            <div className="communities">
              <div className="communities-title">Communities</div>
              <div className="communities-edit">Edit</div>
              <LeftMainDiv>
                <div className="communities-main-list">
                  <Link to="/">Stack Overflow</Link>
                </div>
                <div className="communities-main-total">1</div>
              </LeftMainDiv>
            </div>
          </LeftDiv>
          <RightDiv>
            <div className="about">
              <div className="about-title">About</div>
              <RightMainDiv>
                <p>
                  Your about me section is currently blank. Would you like to
                  add one? Edit profile
                </p>
              </RightMainDiv>
            </div>
            <div className="badges">
              <div className="badges-title">Badges</div>
              <RightMainDiv>
                <p>You have not earned any badges.</p>
              </RightMainDiv>
            </div>
            <div className="posts">
              <div className="posts-title">Posts</div>
              <RightMainDiv>
                <p>이미지</p>
                <p>Just getting started? Try answering a question!</p>
                <p>
                  Your most helpful questions, answers and tags will appear
                  here. Start by answering a question or selecting tags that
                  match topics you’re interested in.
                </p>
              </RightMainDiv>
            </div>
          </RightDiv>
        </ProfileContent>
      </Content>
    </React.Fragment>
  );
}

export default MyProfile;
