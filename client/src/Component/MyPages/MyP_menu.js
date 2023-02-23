// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Container = styled.div`
  margin: 0px;
`;

const Content = styled.div`
  display: flex;
  margin-bottom: 16px;
`;

const MyPageMenuUl = styled.ul`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  width: 264.875px;
  height: 33px;
  padding: 2px 0px 2px 0px;
  margin: 0px;
  list-style: none;

  > a {
    text-decoration: none;
    color: black;
  }
`;

const MyPageMenuLi = styled.li`
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  border-radius: 1000px;
  padding: 6px 12px 6px 12px;
  height: 29px;

  .selected {
    background-color: #f48225;
    color: white;
  }
`;

function MyPMenu() {
  return (
    <React.Fragment>
      <Container>
        <Content>
          <MyPageMenuUl>
            <Link to="/mypage/profile">
              <MyPageMenuLi>Profile</MyPageMenuLi>
            </Link>
            <Link to="/mypage/activity">
              <MyPageMenuLi>activity</MyPageMenuLi>
            </Link>
            <MyPageMenuLi>Saves</MyPageMenuLi>
            <Link to="/mypage/setting">
              <MyPageMenuLi>Settings</MyPageMenuLi>
            </Link>
          </MyPageMenuUl>
        </Content>
      </Container>
    </React.Fragment>
  );
}

export default MyPMenu;
