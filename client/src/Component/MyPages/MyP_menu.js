// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
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

    > .selected {
      background-color: #f48225;
      color: white;
    }
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

  &:hover {
    background-color: #e3e6e8;
  }
`;

function MyPMenu() {
  let location = useLocation().pathname;
  location = location.slice(8);

  return (
    <React.Fragment>
      <Container>
        <Content>
          <MyPageMenuUl>
            <Link to="/mypage/profile">
              {location === 'profile' ? (
                <MyPageMenuLi className="selected">Profile</MyPageMenuLi>
              ) : (
                <MyPageMenuLi>Profile</MyPageMenuLi>
              )}
            </Link>
            <Link to="/mypage/activity">
              {location === 'activity' ? (
                <MyPageMenuLi className="selected">activity</MyPageMenuLi>
              ) : (
                <MyPageMenuLi>activity</MyPageMenuLi>
              )}
            </Link>
            <MyPageMenuLi>Saves</MyPageMenuLi>
            <Link to="/mypage/setting">
              {location === 'setting' ||
              location === 'useredit' ||
              location === 'userdelete' ? (
                <MyPageMenuLi className="selected">Settings</MyPageMenuLi>
              ) : (
                <MyPageMenuLi>Settings</MyPageMenuLi>
              )}
            </Link>
          </MyPageMenuUl>
        </Content>
      </Container>
    </React.Fragment>
  );
}

export default MyPMenu;
