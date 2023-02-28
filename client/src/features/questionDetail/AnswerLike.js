/* eslint-disable react/prop-types */
/* eslint-disable no-unused-vars */
import styled from 'styled-components';
import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { url } from '../../url';

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
const AdoptButton = styled.button`
  display: ${(props) =>
    props.memberId === Number(sessionStorage.getItem('memberId'))
      ? 'block'
      : 'none'};
  background-color: ${(props) =>
    props.adoptedId === props.answerId ? '#437b55' : 'none'};
  color: ${(props) => (props.adoptedId === props.answerId ? 'white' : 'none')};
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
  console.log(answerVoteStatus);

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
        上
      </UpButton>
      <div>{curVote}</div>
      <DownButton bState={bState} onClick={voteDownHandler}>
        下
      </DownButton>
      <AdoptButton
        answerId={answerId}
        adoptedId={adoptedId}
        memberId={memberId}
        onClick={adoptHandler}
      >
        Adopt
      </AdoptButton>
    </QuestionLikeContainer>
  );
}

export default AnswerLike;
