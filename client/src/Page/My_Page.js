import React from 'react';
import styled from 'styled-components';

const Content = styled.div`
  display: flex;
  padding: 24px 16px 24px 16px;
  font-size: 0.8rem;
`;

const ProfileImg = styled.div`
  margin: 8px;

  #my_profile_img {
    width: 96px;
    height: 96px;
  }
`;

function My_Page() {
  return (
    <React.Fragment>
      <Content>
        <div className="my_info">
          <ProfileImg>
            <img
              id="my_profile_img"
              src="../logo192.png"
              alt="my profile img"
            />
          </ProfileImg>
          <div className="userdata_container">
            <div className="user_data">user name</div>
            <ul className="user_log">
              <li>Member for 6 Days</li>
              <li>Last seen this week</li>
              <li>Visited 4 days, 1 consecutive</li>
            </ul>
          </div>
          <div className="profile_top_menu_bar">
            <button>Edit profile</button>
            <button>Network profile</button>
          </div>
        </div>
        <div className="my_menu">
          <ul className="my_menu_nav">
            <li>Profile</li>
            <li>activity</li>
            <li>Saves</li>
            <li>Settings</li>
          </ul>
        </div>
        <div className="my_questions_container">
          <div className="my_question_menubar">
            <div className="my_q_total">0 Questions</div>
            <div className="my_q_top_btn">
              <button>Score</button>
              <button>Activity</button>
              <button>Newest</button>
              <button>View</button>
            </div>
          </div>
          <div className="my_question_container">
            <div className="my_q_list">You have not asked any questions</div>
            <div className="my_q_delete">Deleted questions</div>
          </div>
        </div>
      </Content>
    </React.Fragment>
  );
}

export default My_Page;
