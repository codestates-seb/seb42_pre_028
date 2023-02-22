// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
// eslint-disable-next-line no-unused-vars
import styled from 'styled-components';

function MyPSetNav() {
  return (
    <React.Fragment>
      <div>
        세팅 페이지 좌측 네비
        <ul>
          <li>
            PERSONAL INFORMATION
            <ul>
              <li>Edit profile</li>
              <li>Delete profile</li>
            </ul>
          </li>
        </ul>
      </div>
    </React.Fragment>
  );
}

export default MyPSetNav;
