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
  border: 1px solid transparent;
  border-radius: 3px;
  background-color: #d0393e;
  color: white;
  box-shadow: hsla(0, 0%, 100%, 0.4);
  width: 7rem;
  height: 2.5rem;
  padding: 10.4px;
  &:hover {
    cursor: pointer;
    background-color: #ab262a;
  }

  &.disabled {
    opacity: 0.5;
    pointer-events: none;
    text-decoration: none;
  }
`;

const P = styled.p`
  margin-bottom: 1.1em;
`;

const UL = styled.ul`
  margin-bottom: 1.1em;
  margin-left: 1em;
`;

const Fieldset = styled.fieldset`
  padding: 0.4em;
  margin-bottom: 24px;
`;

const ChectDiv = styled.div`
  display: flex;

  > label {
    display: flex;

    > div {
      margin: 4px;
    }
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

function DeleteProfile() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [boxChecked, setBoxChecked] = useState(false);
  const userDataState = useSelector((state) => state.userData);
  const state = useSelector((state) => state.log);
  let deleteQuery = window.location.search;

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

  useEffect(() => {
    if (deleteQuery === '?delete-agree=on') {
      alert('??????????????? ???????????? ???????????????????????????.');
      navigate('/', { replace: true });
    }
  }, [deleteQuery, navigate]);

  const CheckedHandler = () => {
    setBoxChecked(!boxChecked);
  };

  const deleteHandler = () => {
    // ????????? ???????????? ????????? ?????? ????????? ?????? ?????? ?????? ??????
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
          // ???????????? : ?????? ??? ?????? GET ????????? ???????????? ??????????????? ???????????? ?????? ????????? ?????????; ????
          if (!res.ok) {
            alert('???????????? ?????? ??????');
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
        .catch(() => alert('?????? ??????'));
    } else {
      alert(
        '?????? ????????? ???????????? ????????????, ???????????? ??? ????????????. ????????? ???????????? ??????????????? ?????? ??? ?????? ?????? ???????????? ????????? ?????????.'
      );
    }
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
                    <H1>Delete Profile</H1>
                  </Title>
                  <div>
                    <P>
                      {`Before confirming that you would like your profile deleted,
                we'd like to take a moment to explain the implications of
                deletion:`}
                    </P>
                    <UL>
                      <li>
                        Deletion is irreversible, and you will have no way to
                        regain any of your original content, should this
                        deletion be carried out and you change your mind later
                        on.
                      </li>
                      <li>
                        {`Your questions and answers will remain on the site, but will
                  be disassociated and anonymized (the author will be listed
                  as "${userDataState.email}") and will not indicate your
                  authorship even if you later return to the site.`}
                      </li>
                    </UL>
                    <P>
                      {`Confirming deletion will only delete your profile on Stack
                Overflow - it will not affect any of your other profiles on
                the Stack Exchange network. If you want to delete multiple
                profiles, you'll need to visit each site separately and
                request deletion of those individual profiles.`}
                    </P>
                    <form>
                      <Fieldset>
                        <ChectDiv>
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
                        </ChectDiv>
                      </Fieldset>
                      {boxChecked ? (
                        <DeleteBtn onClick={deleteHandler}>
                          Delete Profile
                        </DeleteBtn>
                      ) : (
                        <DeleteBtn className="disabled">
                          Delete Profile
                        </DeleteBtn>
                      )}
                    </form>
                  </div>
                </Main>
              </MainContainer>
            </Content>
          ) : (
            <PlsLoginDiv>???????????? ????????? ??????????????????.</PlsLoginDiv>
          )}
        </Container>
      </MainDiv>
      <Footer />
    </React.Fragment>
  );
}

export default DeleteProfile;
