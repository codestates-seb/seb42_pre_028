import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { useState } from 'react';

const QuestionLikeContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.7rem;
`;
const UpButton = styled.button`
  pointer-events: ${(props) => (props.bState === 'DOWN' ? 'none' : 'all')};
  background-color: ${(props) => (props.bState === 'UP' ? '#f48224' : 'none')};
`;

const DownButton = styled.button`
  pointer-events: ${(props) => (props.bState === 'UP' ? 'none' : 'all')};
  background-color: ${(props) =>
    props.bState === 'DOWN' ? '#f48224' : 'none'};
`;

// eslint-disable-next-line react/prop-types
function Like({ vote }) {
  const { id } = useParams();
  const [bState, setBstate] = useState('NONE');
  const [curVote, setCurVote] = useState(vote);
  const accessToken = localStorage.getItem('Authorization');

  const voteUpHandler = () => {
    fetch(`http://13.125.1.215:8080/question-vote/${id}/up`, {
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
        setCurVote(data.data.questionVoteTotalCount);
        setBstate(data.data.voteStatus);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const voteDownHandler = () => {
    fetch(`http://13.125.1.215:8080/question-vote/${id}/down`, {
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
        setCurVote(data.data.questionVoteTotalCount);
        setBstate(data.data.voteStatus);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <QuestionLikeContainer>
      <UpButton bState={bState} onClick={voteUpHandler}>
        上
      </UpButton>
      <div>{curVote}</div>
      <DownButton bState={bState} onClick={voteDownHandler}>
        下
      </DownButton>
    </QuestionLikeContainer>
  );
}

export default Like;
