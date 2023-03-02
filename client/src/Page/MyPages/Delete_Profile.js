// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { logout } from '../../features/log/logSlice';
import { deleteData } from '../../features/userData/userDataSlice';
import { deletePage } from '../../features/myPageSlice/myPageSlice';

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

const DeleteBtn = styled.button`
  &.disabled {
    opacity: 0.5;
    pointer-events: none;
    text-decoration: none;
  }
`;

function DeleteProfile() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [boxChecked, setBoxChecked] = useState(false);
  const userDataState = useSelector((state) => state.userData);
  const state = useSelector((state) => state.log);
  let deleteQuery = window.location.search;

  useEffect(() => {
    if (deleteQuery === '?delete-agree=on') {
      alert('회원정보를 삭제하고 로그아웃하였습니다.');
      navigate('/', { replace: true });
    }
  }, [deleteQuery, navigate]);

  const CheckedHandler = () => {
    setBoxChecked(!boxChecked);
  };

  const deleteHandler = () => {
    // 테스트 데이터가 들어가 있는 김민호 계정 삭제 방지 코드
    if (userDataState.memberId !== '1') {
      const accessToken = localStorage.getItem('Authorization');
      // const refreshToken = localStorage.getItem('Refresh');

      fetch(`${url}/members/${userDataState.memberId}`, {
        credentials: 'include',
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          Authorization: accessToken,
          // Refresh: refreshToken,
        },
      })
        .then((res) => {
          // 확인하기 : 삭제 후 쿼리 GET 요청이 자동으로 이루어지며 페이지가 해당 주소로 이동됨; 왜?
          if (!res.ok) {
            alert('회원정보 삭제 실패');
          } else {
            let data = res.json();
            console.log(data);
            localStorage.removeItem('Authorization');
            // localStorage.removeItem('Refresh');
            dispatch(deleteData());
            dispatch(deletePage());
            dispatch(logout(state));
          }
        })
        .catch(() => alert('에러 발생'));
    } else {
      alert(
        '해당 계정은 테스트용 계정으로, 삭제하실 수 없습니다. 임의의 계정으로 회원가입을 하신 후 계정 삭제 테스트를 진행해 주세요.'
      );
    }
  };
  return (
    <React.Fragment>
      <MainDiv>
        <Nav />
        <Container>
          <Content>
            <MyPTProfile />
            <MyPMenu />
            <MainContainer>
              <MyPSetNav />
              <Main>
                <Title>
                  <H1>Delete Profile</H1>
                </Title>
                <div>
                  <p>
                    {`Before confirming that you would like your profile deleted,
                  we'd like to take a moment to explain the implications of
                  deletion:`}
                  </p>
                  <ul>
                    <li>
                      Deletion is irreversible, and you will have no way to
                      regain any of your original content, should this deletion
                      be carried out and you change your mind later on.
                    </li>
                    <li>
                      {`Your questions and answers will remain on the site, but will
                    be disassociated and anonymized (the author will be listed
                    as "유저번호 또는 이메일아이디") and will not indicate your
                    authorship even if you later return to the site.`}
                    </li>
                  </ul>
                  <p>
                    {`Confirming deletion will only delete your profile on Stack
                  Overflow - it will not affect any of your other profiles on
                  the Stack Exchange network. If you want to delete multiple
                  profiles, you'll need to visit each site separately and
                  request deletion of those individual profiles.`}
                  </p>
                  <form>
                    <fieldset>
                      <div>
                        <label>
                          <div>
                            <input
                              type="checkbox"
                              name="delete-agree"
                              checked={boxChecked}
                              onChange={CheckedHandler}
                            />
                          </div>
                          <div>
                            I have read the information stated above and
                            understand the implications of having my profile
                            deleted. I wish to proceed with the deletion of my
                            profile.
                          </div>
                        </label>
                      </div>
                    </fieldset>
                    {boxChecked ? (
                      <DeleteBtn onClick={deleteHandler}>
                        Delete Profile
                      </DeleteBtn>
                    ) : (
                      <DeleteBtn className="disabled">Delete Profile</DeleteBtn>
                    )}
                  </form>
                </div>
              </Main>
            </MainContainer>
          </Content>
        </Container>
      </MainDiv>
      <Footer />
    </React.Fragment>
  );
}

export default DeleteProfile;
