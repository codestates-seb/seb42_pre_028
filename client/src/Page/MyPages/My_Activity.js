// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
// eslint-disable-next-line no-unused-vars
import styled from 'styled-components';

function MyActivity() {
  return (
    <React.Fragment>
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
    </React.Fragment>
  );
}

export default MyActivity;
