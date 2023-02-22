// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
// eslint-disable-next-line no-unused-vars
import styled from 'styled-components';

const ProfileImg = styled.div`
  margin: 8px;

  #my_profile_img {
    width: 96px;
    height: 96px;
  }
`;

function MyPTProfile() {
  return (
    <React.Fragment>
      <ProfileImg>
        <img id="my_profile_img" src="../logo192.png" alt="my profile img" />
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
    </React.Fragment>
  );
}

export default MyPTProfile;
