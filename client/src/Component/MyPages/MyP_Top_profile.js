// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
// eslint-disable-next-line no-unused-vars
import styled from 'styled-components';

const Content = styled.div`
  display: flex;
  align-items: center;
`;

const ProfileImg = styled.div`
  display: flex;
  margin: 8px;

  #my_profile_img {
    width: 96px;
    height: 96px;
  }
`;

const UserData = styled.div`
  display: flex;
  flex-direction: column;
  margin: 8px;

  > div.user_name {
    font-size: 2.5em;
    margin-bottom: 8px;
  }
`;

const UserLogUl = styled.ul`
  display: flex;
  list-style: none;
  padding: 0;
`;

const UserLogLi = styled.li`
  display: flex;
`;

function MyPTProfile() {
  return (
    <React.Fragment>
      <Content>
        <ProfileImg>
          <Link to="/mypage/activity">
            <img
              id="my_profile_img"
              src="../logo192.png"
              alt="my profile img"
            />
          </Link>
        </ProfileImg>
        <UserData>
          <div className="user_name">user name</div>
          <UserLogUl>
            <UserLogLi>(icon) Member for 6 Days</UserLogLi>
            <UserLogLi>(icon) Last seen this week</UserLogLi>
            <UserLogLi>(icon) Visited 4 days, 1 consecutive</UserLogLi>
          </UserLogUl>
        </UserData>
        <div className="profile_top_menu_bar">
          <button>Edit profile</button>
          <button>Network profile</button>
        </div>
      </Content>
    </React.Fragment>
  );
}

export default MyPTProfile;
