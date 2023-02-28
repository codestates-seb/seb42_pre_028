import React, { useState } from 'react';
import styled from 'styled-components';
import { useSelector, useDispatch } from 'react-redux';
import { saveData } from '../../features/userData/userDataSlice';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';
import MyPSetNav from '../../Component/MyPages/myP_Set_nav';
import Footer from '../../Component/Footer';

import { url } from '../../url';

const Container = styled.div`
  margin: 0px 65px 0px 65px;
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
  margin-bottom: 48px;
  border: 1px solid #e3e6e8;
  border-radius: 5px;
`;

function EditProfile() {
  const [displayName, setDisplayName] = useState(null);
  const [password, setPassword] = useState(null);
  const userDataState = useSelector((state) => state.userData);
  const dispatch = useDispatch();

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
              <SubTitle>
                <H3>Public information</H3>
                <SetList>
                  <div>
                    <label htmlFor="change-picture">Profile image</label>
                    <img
                      id="my_profile_img"
                      src="../logo192.png"
                      alt="my profile img"
                    />
                    <button id="change-picture">Change picture</button>
                  </div>
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
                </SetList>
              </SubTitle>
              <SubTitle>
                <H3>Links</H3>
                <SetList>
                  <div>
                    <label>
                      <div>Website link</div>
                      <input />
                    </label>
                  </div>
                  <div>
                    <label>
                      <div>Twitter link or username</div>
                      <input />
                    </label>
                  </div>
                  <div>
                    <label>
                      <div>GitHub link or username</div>
                      <input />
                    </label>
                  </div>
                </SetList>
              </SubTitle>
              <SubTitle>
                <H3>Private information</H3>
                <div>Not shown publicly</div>
                <SetList>
                  <div>
                    <label>
                      <div>Full name</div>
                      <input />
                    </label>
                  </div>
                </SetList>
              </SubTitle>
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
