import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faCakeCandles,
  faPen,
  faMessage,
} from '@fortawesome/free-solid-svg-icons';
import { faClock, faCalendar } from '@fortawesome/free-regular-svg-icons';

const Container = styled.div`
  margin: 0px;
  margin-bottom: 16px;
`;

const Content = styled.div`
  display: flex;
  align-items: center;
`;

const ProfileImg = styled.div`
  display: flex;
  margin: 8px;

  #my_profile_img {
    width: 128px;
    height: 128px;
    border-radius: 5px;
    box-shadow: 0 1px 2px hsla(0, 0%, 0%, 0.05), 0 1px 4px hsla(0, 0%, 0%, 0.05),
      0 2px 8px hsla(0, 0%, 0%, 0.05);
  }
`;

const UserData = styled.div`
  display: flex;
  flex-direction: column;
  margin: 8px;
`;

const UserName = styled.div`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  margin: -4px;
  max-width: calc(97.2307692rem / 3);

  > div {
    line-height: 1;
    font-size: 2.8em;
    margin: 4px;
    margin-bottom: 12px;
  }
`;

const UserLogUl = styled.ul`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  list-style: none;
  margin: 0;
  margin-left: -4px;
  padding: 0;
  color: #6a737c;
`;

const UserLogLi = styled.li`
  display: flex;
  align-items: center;
  margin: 4px;
  padding: 0;

  > div {
    margin: 0 2px 0 2px;
  }
`;

const Icon = styled.div`
  display: flex;
  align-items: center;
  margin: 0 2px 0 2px;
  > .icon-style {
    display: flex;
    align-items: center;
    width: 18px;
    height: 18px;
    margin: 0 2px 0 2px;
  }
`;

const BtnContainer = styled.div`
  position: absolute;
  top: 5.5em;
  right: 1.5em;
  display: flex;
  flex-wrap: wrap;
  margin-left: auto;
`;

const TopButton = styled.button`
  position: relative;
  margin: 3px;
  padding: 0.8em;
  border: 1px solid #9fa6ad;
  border-radius: 3px;
  background-color: white;
  color: #6a737c;

  &.cursor {
    cursor: pointer;
  }

  > .icon-style {
    width: 16px;
    height: 16px;
    margin-right: 5px;
    vertical-align: center;
  }
`;

function MyPTProfile() {
  const userDataState = useSelector((state) => state.userData);
  const logState = useSelector((state) => state.log);

  let elapsedDay = '?';
  if (logState.value === 1) {
    // 임시 방편...
    if (!userDataState.memberId) {
      window.location.reload();
    }
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
                src="../../logo192.png"
                alt="my profile img"
              />
            </Link>
          </ProfileImg>
          <UserData>
            {logState.value === 1 ? (
              <UserName className="user_name">
                <div>{userDataState.displayName}</div>
              </UserName>
            ) : (
              <UserName className="user_name">
                <div>user name</div>
              </UserName>
            )}
            <UserLogUl>
              {logState.value === 1 ? (
                <UserLogLi>
                  <Icon>
                    <FontAwesomeIcon
                      className="icon-style"
                      icon={faCakeCandles}
                    />
                  </Icon>
                  <div>Member for {elapsedDay} Days</div>
                </UserLogLi>
              ) : (
                <UserLogLi>
                  <Icon>
                    <FontAwesomeIcon
                      className="icon-style"
                      icon={faCakeCandles}
                    />
                  </Icon>
                  <div>Member for ? Days</div>
                </UserLogLi>
              )}
              <UserLogLi>
                <Icon>
                  <FontAwesomeIcon className="icon-style" icon={faClock} />
                </Icon>
                <div>Last seen this week</div>
              </UserLogLi>
              <UserLogLi>
                <Icon>
                  <FontAwesomeIcon className="icon-style" icon={faCalendar} />
                </Icon>
                <div>Visited 4 days, 1 consecutive</div>
              </UserLogLi>
            </UserLogUl>
          </UserData>
          <BtnContainer>
            <Link to="/mypage/useredit">
              <TopButton className="cursor">
                <FontAwesomeIcon className="icon-style" icon={faPen} />
                Edit profile
              </TopButton>
            </Link>
            <TopButton>
              <FontAwesomeIcon className="icon-style" icon={faMessage} />
              Network profile
            </TopButton>
          </BtnContainer>
        </Content>
      </Container>
    </React.Fragment>
  );
}

export default MyPTProfile;
