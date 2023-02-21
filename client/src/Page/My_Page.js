import React from 'react';
import styled from 'styled-components';

// eslint-disable-next-line no-unused-vars
import MyProfile from './MyPages/My_Profile';
import MyActivity from './MyPages/My_Activity';
// eslint-disable-next-line no-unused-vars
import MySettings from './MyPages/My_Settings';

const Content = styled.div`
  display: flex;
  padding: 24px 16px 24px 16px;
  font-size: 0.8rem;
`;

const ProfileImg = styled.div`
  margin: 8px;

  #my_profile_img {
    width: 96px;
    height: 96px;
  }
`;

function My_Page() {
  return (
    <React.Fragment>
      <Content>
        <div className="my_info">
          <ProfileImg>
            <img
              id="my_profile_img"
              src="../logo192.png"
              alt="my profile img"
            />
          </ProfileImg>
          <div className="userdata_container">
            <div className="user_data">user name</div>
            <ul className="user_log">
              <li>Member for 6 Days</li>
              <li>Last seen this week</li>
              <li>Visited 4 days, 1 consecutive</li>
            </ul>
          </div>
          <div className="profile_top_menu_bar">
            <button>Edit profile</button>
            <button>Network profile</button>
          </div>
        </div>
        <div className="my_menu">
          <ul className="my_menu_nav">
            <li>Profile</li>
            <li>activity</li>
            <li>Saves</li>
            <li>Settings</li>
          </ul>
        </div>
        <MyActivity />
      </Content>
    </React.Fragment>
  );
}

export default My_Page;
