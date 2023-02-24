import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { useState } from 'react';

const QuestionLikeContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.7rem;
`;

// eslint-disable-next-line react/prop-types
function Like({ vote }) {
  const { id } = useParams();

  const [curVote, setCurVote] = useState(vote);
  const accessToken = localStorage.getItem('Authorization');

  const voteUpHandler = () => {
    fetch(`https://8b90-112-156-175-230.jp.ngrok.io/question-vote/${id}/up`, {
      credentials: 'include',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: accessToken,
      },
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        // window.location.reload();
        setCurVote(data.questionVoteTotalCount);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const voteDownHandler = () => {
    fetch(`https://8b90-112-156-175-230.jp.ngrok.io/question-vote/${id}/down`, {
      credentials: 'include',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: accessToken,
      },
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        setCurVote(data.questionVoteTotalCount);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <QuestionLikeContainer>
      <button onClick={voteUpHandler}>上</button>
      <div>{curVote}</div>
      <button onClick={voteDownHandler}>下</button>
      <button>B</button>
      <button>A</button>
    </QuestionLikeContainer>
  );
}

export default Like;
