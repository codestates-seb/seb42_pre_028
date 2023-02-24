import React, { useState } from 'react';
import styled from 'styled-components';
import { useSelector } from 'react-redux';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';
import MyPSetNav from '../../Component/MyPages/myP_Set_nav';
import Footer from '../../Component/Footer';

const Container = styled.div`
  margin: 0px 65px 0px 65px;
`;

const H1 = styled.h1`
  font-weight: normal;
  margin: 0px;
  font-size: 2.07692308em;
  line-height: calc(15 / 13);
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  padding: 24px;
  font-size: 0.8rem;
`;

const MainContainer = styled.div`
  display: flex;
`;

const Main = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
`;

const Title = styled.div`
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid #d6d9dc;
`;

function EditProfile() {
  const [displayName, setDisplayName] = useState(null);
  const [password, setPassword] = useState(null);
  const memberIdState = useSelector((state) => state.userData.memberId);

  const submitHandler = () => {
    const editData = {
      displayName: displayName,
      password: password,
    };

    console.log(editData);
    console.log(memberIdState.memberId);

    fetch(
      // 질문 2 : editData의 일부 key의 값이 null로 들어간다면 서버에는 데이터가 어떻게 저장되는가?
      // ㄴ 테스트 결과 : 빈 데이터는 edit 객체에 포함시키지 않도록 구현해야함
      `http://13.125.1.215:8080/members/${memberIdState.memberId}`,
      {
        credentials: 'include',
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(editData),
      }
    )
      .then((res) => {
        if (res.ok) {
          alert('회원정보를 변경하였습니다.');
        } else {
          alert('회원정보 변경 실패');
        }
      })
      .catch(() => alert('에러 발생'));
  };

  return (
    <React.Fragment>
      <Container>
        <Content>
          <MyPTProfile />
          <MyPMenu />
          <MainContainer>
            <MyPSetNav />
            <Main>
              <Title>
                <H1>Edit your Profile</H1>
              </Title>
              <div>
                <label>
                  <div>Display name</div>
                  <input onChange={(e) => setDisplayName(e.target.value)} />
                </label>
              </div>
              <div>
                <label>
                  <div>Password</div>
                  <input onChange={(e) => setPassword(e.target.value)} />
                </label>
              </div>
              <div>
                <button onClick={submitHandler}>Submit</button>
              </div>
            </Main>
          </MainContainer>
        </Content>
      </Container>
      <Footer />
    </React.Fragment>
  );
}

export default EditProfile;
