import React, { useState } from 'react';
import styled from 'styled-components';
import { useSelector, useDispatch } from 'react-redux';
import { saveData } from '../../features/userData/userDataSlice';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';
import MyPSetNav from '../../Component/MyPages/myP_Set_nav';
import Footer from '../../Component/Footer';
import Nav from '../../Component/Nav';

import { url } from '../../url';

const MainDiv = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  max-width: 1264px;
  margin: 0 auto;
`;

const Container = styled.div`
  /* margin: 0px 65px 0px 65px; */
  width: 100%;
`;

const H1 = styled.h1`
  font-weight: normal;
  margin: 0px;
  font-size: 2.07692308em;
  line-height: calc(15 / 13);
`;

const H3 = styled.h3`
  font-weight: 400;
  font-size: 1.61538462em;
  line-height: 1.3;
  margin: 0px 0px 8px 0px;
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

const SubTitle = styled.div``;

const SetList = styled.div`
  padding: 24px;
  margin-bottom: 48px;
  border: 1px solid #e3e6e8;
  border-radius: 5px;
  display: flex;
  flex-direction: column;
`;

const ListCompoDiv = styled.div`
  display: flex;
  flex-direction: column;
  margin: 12px 0px 12px 0px;
`;

const ListCompoTitle = styled.div`
  font-weight: bold;
`;

const Label = styled.label`
  > div {
    margin: 2px 0 2px 0;
  }
  > input {
    width: 100%;
    max-width: 421.33px;
    min-height: 32.59px;
    padding: 7.8px 9.1px 7.8px 9.1px;
  }
`;

const BtnDiv = styled.div`
  margin-bottom: 48px;
  padding: 10px 0 15px 0;
`;

const SubmitBtn = styled.button`
  margin: 4px;
  padding: 10.4px;
  border: 1px solid transparent;
  border-radius: 3px;
  background-color: #0a95ff;
  color: white;
  box-shadow: hsla(0, 0%, 100%, 0.4);
  width: 7rem;
  height: 2.5rem;
  &:hover {
    cursor: pointer;
    background-color: #0074cc;
  }
`;

const PlsLoginDiv = styled.div`
  padding: 24px;
  height: calc(100vh - 23.4rem);
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
`;

function EditProfile() {
  const userDataState = useSelector((state) => state.userData);
  const dispatch = useDispatch();
  const [displayName, setDisplayName] = useState(userDataState.displayName);
  const [password, setPassword] = useState(null);
  const state = useSelector((state) => state.log);

  if (
    !(
      state.value === 1 ||
      state.value === '1' ||
      state.value === 0 ||
      state.value === '0'
    )
  ) {
    window.location.reload();
  }

  const submitHandler = () => {
    const accessToken = localStorage.getItem('Authorization');
    const editData = {};
    // displayName 또는 password 데이터가 입력되지 않은 경우 editData 객체에 포함시키지 않음
    if (displayName) {
      editData.displayName = displayName;
    } else if (password) {
      editData.password = password;
    }

    fetch(`${url}/members/${userDataState.memberId}`, {
      credentials: 'include',
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Authorization: accessToken,
      },
      body: JSON.stringify(editData),
    })
      .then((res) => {
        if (res.ok) {
          return res.json();
        } else {
          alert('회원정보 변경 실패');
        }
      })
      .then((data) => {
        dispatch(saveData(data.data));
        alert('회원정보를 변경하였습니다.');
        window.location.reload('/mypage/useredit');
      })
      .catch(() => alert('에러 발생'));
  };

  return (
    <React.Fragment>
      <MainDiv>
        <Nav />
        <Container>
          {state.value === 1 || state.value === '1' ? (
            <Content>
              <MyPTProfile />
              <MyPMenu />
              <MainContainer>
                <MyPSetNav />
                <Main>
                  <Title>
                    <H1>Edit your Profile</H1>
                  </Title>
                  <SubTitle>
                    <H3>Public information</H3>
                    <SetList>
                      <ListCompoDiv>
                        <Label htmlFor="change-picture">
                          <ListCompoTitle>Profile image</ListCompoTitle>
                          <div>
                            <img
                              id="my_profile_img"
                              src="../../logo192.png"
                              alt="my profile img"
                            />
                          </div>
                        </Label>
                      </ListCompoDiv>
                      <ListCompoDiv>
                        <Label>
                          <ListCompoTitle>Display name</ListCompoTitle>
                          <input
                            onChange={(e) => setDisplayName(e.target.value)}
                            value={displayName}
                          />
                        </Label>
                      </ListCompoDiv>
                      <ListCompoDiv>
                        <Label>
                          <ListCompoTitle>Password</ListCompoTitle>
                          <input
                            onChange={(e) => setPassword(e.target.value)}
                          />
                        </Label>
                      </ListCompoDiv>
                    </SetList>
                  </SubTitle>
                  <BtnDiv>
                    <SubmitBtn onClick={submitHandler}>Save Profile</SubmitBtn>
                  </BtnDiv>
                </Main>
              </MainContainer>
            </Content>
          ) : (
            <PlsLoginDiv>로그인이 필요한 페이지입니다.</PlsLoginDiv>
          )}
        </Container>
      </MainDiv>
      <Footer />
    </React.Fragment>
  );
}

export default EditProfile;
