import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { useSelector } from 'react-redux';

const Container = styled.div`
  margin: 0px;
`;

const Content = styled.div`
  display: flex;
  align-items: center;
`;

const ProfileImg = styled.div`
  display: flex;
  margin: 8px;

  #my_profile_img {
    width: 96px;
    height: 96px;
  }
`;

const UserData = styled.div`
  display: flex;
  flex-direction: column;
  margin: 8px;

  > div.user_name {
    font-size: 2.8em;
    margin-bottom: 8px;
  }
`;

const UserLogUl = styled.ul`
  display: flex;
  list-style: none;
  padding: 0;
`;

const UserLogLi = styled.li`
  display: flex;
`;

const BtnContainer = styled.div`
  display: flex;
  margin-left: auto;
`;

function MyPTProfile() {
  const userDataState = useSelector((state) => state.userData);
  const logState = useSelector((state) => state.log);

  let elapsedDay = '?';
  if (logState.value === 1) {
    console.log(userDataState);
    let today = new Date();
    let created = userDataState.createdAt.slice(0, 10);
    created = new Date(created);
    elapsedDay = Math.trunc(
      (today.getTime() - created.getTime()) / (1000 * 60 * 60 * 24)
    );
  }

  return (
    <React.Fragment>
      <Container>
        <Content>
          <ProfileImg>
            <Link to="/mypage/activity">
              <img
                id="my_profile_img"
                src="../logo192.png"
                alt="my profile img"
              />
            </Link>
          </ProfileImg>
          <UserData>
            {logState.value === 1 ? (
              <div className="user_name">{userDataState.displayName}</div>
            ) : (
              <div className="user_name">user name</div>
            )}
            <UserLogUl>
              {logState.value === 1 ? (
                <UserLogLi>(icon) Member for {elapsedDay} Days</UserLogLi>
              ) : (
                <UserLogLi>(icon) Member for ? Days</UserLogLi>
              )}
              <UserLogLi>(icon) Last seen this week</UserLogLi>
              <UserLogLi>(icon) Visited 4 days, 1 consecutive</UserLogLi>
            </UserLogUl>
          </UserData>
          <BtnContainer>
            <Link to="/mypage/useredit">
              <button>Edit profile</button>
            </Link>
            <button>Network profile</button>
          </BtnContainer>
        </Content>
      </Container>
    </React.Fragment>
  );
}

export default MyPTProfile;
