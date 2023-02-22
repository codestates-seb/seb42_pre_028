// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
// eslint-disable-next-line no-unused-vars
import styled from 'styled-components';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';
import MyPSetNav from '../../Component/MyPages/myP_Set_nav';

const Content = styled.div`
  display: flex;
  padding: 24px 16px 24px 16px;
  font-size: 0.8rem;
`;

function MySettings() {
  return (
    <React.Fragment>
      <Content>
        <MyPTProfile />
        <MyPMenu />
        <MyPSetNav />
        <h2>Settings 페이지 입니다</h2>
        <div>회원 정보 수정 기능, 회원 탈퇴 기능이 들어갑니다.</div>
      </Content>
    </React.Fragment>
  );
}

export default MySettings;
