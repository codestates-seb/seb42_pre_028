/* eslint-disable react/prop-types */
/* eslint-disable no-unused-vars */
import styled from 'styled-components';
import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { url } from '../../url';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faThumbsUp as regularThumbsUp,
  faThumbsDown as regularThumbsDown,
  faBookmark,
} from '@fortawesome/free-regular-svg-icons';
import {
  faThumbsUp as solidThumbsUp,
  faThumbsDown as solidThumbsDown,
  faCheck,
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

const AdoptButton = styled.button`
  border: none;
  display: ${(props) =>
    props.memberId === Number(sessionStorage.getItem('memberId'))
      ? 'block'
      : 'none'};
  background-color: white;
  color: ${(props) =>
    props.adoptedId === props.answerId ? '#437b55' : 'black'};
`;

function AnswerLike({
  vote,
  answerId,
  memberId,
  adoptedId,
  setAdoptedId,
  answerVoteStatus,
}) {
  const { id } = useParams();
  const voteStatus = answerVoteStatus.filter((el) => el.answerId === answerId);
  let tmp;
  if (!voteStatus.length) tmp = 'NONE';
  else tmp = voteStatus[0].voteStatus;

  const [bState, setBstate] = useState(tmp);

  const [curVote, setCurVote] = useState(vote);

  const accessToken = localStorage.getItem('Authorization');

  const voteUpHandler = () => {
    fetch(`${url}/answer-vote/${answerId}/up`, {
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
        setCurVote(data.data.answerVoteTotalCount);
        setBstate(data.data.voteStatus);
      });
  };

  const voteDownHandler = () => {
    fetch(`${url}/answer-vote/${answerId}/down`, {
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
        setCurVote(data.data.answerVoteTotalCount);
        setBstate(data.data.voteStatus);
      });
  };

  const adoptHandler = () => {
    fetch(`${url}/question/${id}/adopt-answer/${answerId}`, {
      credentials: 'include',
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: accessToken,
      },
    })
      .then((res) => res.json())
      .then((data) => {
        if (adoptedId === answerId) setAdoptedId(0);
        else setAdoptedId(answerId);
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
      <AdoptButton
        answerId={answerId}
        adoptedId={adoptedId}
        memberId={memberId}
        onClick={adoptHandler}
      >
        {adoptedId === answerId ? (
          <FontAwesomeIcon icon={faCheck} size="2x" />
        ) : (
          <FontAwesomeIcon icon={faBookmark} size="2x" />
        )}
      </AdoptButton>
    </QuestionLikeContainer>
  );
}

export default AnswerLike;
