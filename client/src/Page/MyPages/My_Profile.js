// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
// eslint-disable-next-line no-unused-vars
import styled from 'styled-components';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';

const Content = styled.div`
  display: flex;
  padding: 24px 16px 24px 16px;
  font-size: 0.8rem;
`;

function MyProfile() {
  return (
    <React.Fragment>
      <Content>
        <MyPTProfile />
        <MyPMenu />
        <h2>Profile 페이지 입니다</h2>
        <div>About, Badges, Posts 정보를 볼 수 있습니다.</div>
      </Content>
    </React.Fragment>
  );
}

export default MyProfile;
