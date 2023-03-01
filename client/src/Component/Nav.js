import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import styled from 'styled-components';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faEarthAmericas,
  faCertificate,
  faCircleInfo,
} from '@fortawesome/free-solid-svg-icons';

const Content = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  margin-right: 8px;
  width: 220px;
  font-size: 0.8rem;
  color: #6a737c;
  border-right: 1px solid #d6d9dc;

  > div {
    position: sticky;
    top: 54.94px;
    max-height: calc(100vh - 50px);
    padding-top: 24px;
    margin-bottom: 8px;
  }
`;

const MyPageMenuUl = styled.ul`
  display: flex;
  flex-direction: column;
  list-style: none;
  margin: 0px;
  padding: 0px;

  > a {
    text-decoration: none;
    color: #6a737c;

    > #selected {
      background-color: #e3e6e8;
      font-weight: Bold;
      border-right: 3px solid #f48225;
      color: black;

      &.earth {
        padding-left: 1em;
      }
      > .earth {
        width: 19px;
        height: 18px;
        margin: 0 2px 0 2px;
      }
      &.certificate {
        padding-left: 1em;
      }
      > .certificate {
        width: 16px;
        height: 16px;
      }
      > .info {
        width: 12px;
        height: 12px;
      }
    }
  }
`;

const MyPageMenuLi = styled.li`
  display: flex;
  flex-direction: row;
  margin: 0px;
  padding: 6px 12px 6px 12px;

  &.title {
    margin-top: 16px;
    font-weight: normal;
    &.first {
      margin-top: 0px;
    }
  }
  &.item {
    font-weight: normal;
    padding-left: 3em;

    &.earth {
      padding-left: 1em;
    }

    &.certificate {
      padding-left: 1em;
    }

    &:hover {
      color: black;
    }
    &.first {
      padding: 6px 12px 6px 12px;
    }
  }
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

  > .info {
    width: 12px;
    height: 12px;
  }

  > .certificate {
    filter: invert(59%) sepia(58%) saturate(1810%) hue-rotate(344deg)
      brightness(98%) contrast(95%);
  }
`;

function Nav() {
  let location = useLocation().pathname;
  if (location.length > 10) {
    location = location.slice(0, 10);
    if (location !== '/questions') {
      location = location.slice(0, 7);
    }
  }

  return (
    <React.Fragment>
      <Content>
        <div>
          <MyPageMenuUl>
            <MyPageMenuLi className="title first"></MyPageMenuLi>
            <Link to="/">
              {location === '/' ? (
                <MyPageMenuLi id="selected" className="item first">
                  Home
                </MyPageMenuLi>
              ) : (
                <MyPageMenuLi className="item first">Home</MyPageMenuLi>
              )}
            </Link>
            <MyPageMenuLi className="title">PUBLIC</MyPageMenuLi>
            <Link to="/questions">
              {location === '/questions' ? (
                <MyPageMenuLi id="selected" className="item earth">
                  <FontAwesomeIcon
                    className="icon-style earth"
                    icon={faEarthAmericas}
                  />
                  <div>Questions</div>
                </MyPageMenuLi>
              ) : (
                <MyPageMenuLi className="item earth">
                  <Icon>
                    <FontAwesomeIcon
                      className="icon-style earth"
                      icon={faEarthAmericas}
                    />
                  </Icon>
                  <div>Questions</div>
                </MyPageMenuLi>
              )}
            </Link>
            <MyPageMenuLi className="item">Tags</MyPageMenuLi>
            <Link to="/mypage/activity">
              {location === '/mypage' ? (
                <MyPageMenuLi id="selected" className="item">
                  Users
                </MyPageMenuLi>
              ) : (
                <MyPageMenuLi className="item">Users</MyPageMenuLi>
              )}
            </Link>
            <MyPageMenuLi className="item">Companies</MyPageMenuLi>
            <MyPageMenuLi className="title">
              <div>COLLECTIVES</div>
              <Icon>
                <FontAwesomeIcon
                  className="icon-style info"
                  icon={faCircleInfo}
                />
              </Icon>
            </MyPageMenuLi>
            <MyPageMenuLi className="item certificate">
              <Icon>
                <FontAwesomeIcon
                  className="icon-style certificate"
                  icon={faCertificate}
                />
              </Icon>
              <div>Explore Collectives</div>
            </MyPageMenuLi>
          </MyPageMenuUl>
        </div>
      </Content>
    </React.Fragment>
  );
}

export default Nav;
