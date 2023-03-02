import { useState } from 'react';
import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { url } from '../../url';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faThumbsUp as regularThumbsUp,
  faThumbsDown as regularThumbsDown,
} from '@fortawesome/free-regular-svg-icons';
import {
  faThumbsUp as solidThumbsUp,
  faThumbsDown as solidThumbsDown,
} from '@fortawesome/free-solid-svg-icons';

const QuestionLikeContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.7rem;
`;
const UpButton = styled.button`
  border: none;
  pointer-events: ${(props) => (props.bState === 'DOWN' ? 'none' : 'all')};
  background-color: white;
  color: ${(props) => (props.bState === 'UP' ? '#f48224' : 'black')};
`;

const DownButton = styled.button`
  border: none;
  pointer-events: ${(props) => (props.bState === 'UP' ? 'none' : 'all')};
  background-color: white;
  color: ${(props) => (props.bState === 'DOWN' ? '#f48224' : 'black')};
`;

// eslint-disable-next-line react/prop-types
function Like({ vote, questionvoteStatus }) {
  const { id } = useParams();
  const [bState, setBstate] = useState(questionvoteStatus);
  const [curVote, setCurVote] = useState(vote);
  const accessToken = localStorage.getItem('Authorization');

  const voteUpHandler = () => {
    fetch(`${url}/question-vote/${id}/up`, {
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
    fetch(`${url}/question-vote/${id}/down`, {
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
        {bState === 'UP' ? (
          <FontAwesomeIcon icon={solidThumbsUp} size="2x" />
        ) : (
          <FontAwesomeIcon icon={regularThumbsUp} size="2x" />
        )}
      </UpButton>
      <div>{curVote}</div>
      <DownButton bState={bState} onClick={voteDownHandler}>
        {bState === 'DOWN' ? (
          <FontAwesomeIcon icon={solidThumbsDown} size="2x" />
        ) : (
          <FontAwesomeIcon icon={regularThumbsDown} size="2x" />
        )}
      </DownButton>
    </QuestionLikeContainer>
  );
}

export default Like;
