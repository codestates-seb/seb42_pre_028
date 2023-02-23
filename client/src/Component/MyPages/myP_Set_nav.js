// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Content = styled.div`
  display: flex;
  flex-direction: column;
  margin-right: 32px;
  min-width: 190px;
`;

const MyPageMenuUl = styled.ul`
  display: flex;
  flex-direction: column;
  list-style: none;
  margin: 0px;
  padding: 0px;

  > a {
    text-decoration: none;
    color: black;
  }
`;

const MyPageMenuLi = styled.li`
  display: flex;
  flex-direction: column;
  margin: 0px;
  padding: 6px 12px 6px 12px;

  &.title {
    margin-top: 16px;
    font-weight: bold;
    &.first {
      margin-top: 0px;
    }
  }
  &.item {
    font-weight: normal;
    &:hover {
      cursor: pointer;
    }
  }
`;

function MyPSetNav() {
  return (
    <React.Fragment>
      <Content>
        <MyPageMenuUl>
          <MyPageMenuLi className="title first">
            PERSONAL INFORMATION
          </MyPageMenuLi>
          <Link to="/mypage/useredit">
            <MyPageMenuLi className="item">Edit profile</MyPageMenuLi>
          </Link>
          <Link to="/mypage/userdelete">
            <MyPageMenuLi className="item">Delete profile</MyPageMenuLi>
          </Link>
          <MyPageMenuLi className="title">EMAIL SETTINGS</MyPageMenuLi>
          <MyPageMenuLi className="item">Edit email settings</MyPageMenuLi>
          <MyPageMenuLi className="item">Tag watching & ignoring</MyPageMenuLi>
          <MyPageMenuLi className="item">community digests</MyPageMenuLi>
          <MyPageMenuLi className="item">Question subscriptions</MyPageMenuLi>
          <MyPageMenuLi className="title">SITE SETTINGS</MyPageMenuLi>
          <Link to="/mypage/setting">
            <MyPageMenuLi className="item">Preferences</MyPageMenuLi>
          </Link>
          <MyPageMenuLi className="item">Flair</MyPageMenuLi>
          <MyPageMenuLi className="item">Hide communities</MyPageMenuLi>
          <MyPageMenuLi className="title">ACCESS</MyPageMenuLi>
          <MyPageMenuLi className="item">Your Collectives</MyPageMenuLi>
          <MyPageMenuLi className="item">Your logins</MyPageMenuLi>
          <MyPageMenuLi className="title">API</MyPageMenuLi>
          <MyPageMenuLi className="item">Authorized applications</MyPageMenuLi>
        </MyPageMenuUl>
      </Content>
    </React.Fragment>
  );
}

export default MyPSetNav;
