// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
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

    > .selected {
      background-color: #f48225;
      color: white;
    }
  }
`;

const MyPageMenuLi = styled.li`
  display: flex;
  flex-direction: column;
  margin: 0px;
  padding: 6px 48px 6px 12px;
  border-radius: 1000px;

  &:hover {
    background-color: #e3e6e8;
  }
`;

function MyPActNav() {
  let location = useLocation().pathname;
  location = location.slice(8);

  return (
    <React.Fragment>
      <Content>
        <MyPageMenuUl>
          <MyPageMenuLi>Answer</MyPageMenuLi>
          <MyPageMenuLi>Summary</MyPageMenuLi>
          <Link to="/mypage/activity">
            {location === 'activity' ? (
              <MyPageMenuLi className="selected">Questions</MyPageMenuLi>
            ) : (
              <MyPageMenuLi>Questions</MyPageMenuLi>
            )}
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
