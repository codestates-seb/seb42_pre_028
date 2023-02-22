import React from 'react';
import styled from 'styled-components';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';
import MyPActNav from '../../Component/MyPages/myP_Act_nav';

const Content = styled.div`
  display: flex;
  flex-direction: column;
  padding: 24px;
  font-size: 0.8rem;
`;

const MainContent = styled.div`
  display: flex;
`;

function My_Page() {
  return (
    <React.Fragment>
      <Content>
        <MyPTProfile />
        <MyPMenu />
        <MainContent>
          <MyPActNav />
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
          </div>
          <div className="my_q_list">You have not asked any questions</div>
          <div className="my_q_delete">Deleted questions</div>
        </MainContent>
      </Content>
    </React.Fragment>
  );
}

export default My_Page;
