// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
// eslint-disable-next-line no-unused-vars
import styled from 'styled-components';

function MyPMenu() {
  return (
    <React.Fragment>
      <ul className="my_p_menu">
        <li>
          <Link to="/mypage/profile">Profile</Link>
        </li>
        <li>
          <Link to="/mypage/activity">activity</Link>
        </li>
        <li>Saves</li>
        <li>
          <Link to="/mypage/setting">Settings</Link>
        </li>
      </ul>
    </React.Fragment>
  );
}

export default MyPMenu;
