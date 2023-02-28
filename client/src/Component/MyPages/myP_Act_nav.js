// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const Content = styled.div`
  display: flex;
  flex-direction: column;
  margin-right: 32px;
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
  padding: 6px 48px 6px 12px;
`;

function MyPActNav() {
  return (
    <React.Fragment>
      <Content>
        <MyPageMenuUl>
          <MyPageMenuLi>Answer</MyPageMenuLi>
          <MyPageMenuLi>Summary</MyPageMenuLi>
          <Link to="/mypage/activity">
            <MyPageMenuLi>Questions</MyPageMenuLi>
          </Link>
          <MyPageMenuLi>Tags</MyPageMenuLi>
          <MyPageMenuLi>Articles</MyPageMenuLi>
          <MyPageMenuLi>Badges</MyPageMenuLi>
          <MyPageMenuLi>Following</MyPageMenuLi>
          <MyPageMenuLi>Bounties</MyPageMenuLi>
          <MyPageMenuLi>Reputataion</MyPageMenuLi>
          <MyPageMenuLi>All actions</MyPageMenuLi>
          <MyPageMenuLi>Responses</MyPageMenuLi>
          <MyPageMenuLi>Votes</MyPageMenuLi>
        </MyPageMenuUl>
      </Content>
    </React.Fragment>
  );
}

export default MyPActNav;
