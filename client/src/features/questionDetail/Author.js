/* eslint-disable jsx-a11y/alt-text */
/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { url } from '../../url';

const LeftSpan = styled.span`
  color: gray;
  font-size: small;
`;
const EditA = styled.a`
  color: gray;
  font-size: small;
  text-decoration-line: none;
  pointer-events: ${(props) =>
    props.memberId === Number(sessionStorage.getItem('memberId'))
      ? 'all'
      : 'none'};
  :hover {
    background-color: ${(props) =>
      props.memberId === Number(sessionStorage.getItem('memberId'))
        ? '#D9EAF7'
        : 'none'};
  }
  cursor: pointer;
`;

const DeleteA = styled.a`
  color: gray;
  font-size: small;
  display: ${(props) =>
    props.memberId === Number(sessionStorage.getItem('memberId'))
      ? 'inline'
      : 'none'};
  :hover {
    background-color: ${(props) =>
      props.memberId === Number(sessionStorage.getItem('memberId'))
        ? '#D9EAF7'
        : 'none'};
  }
  cursor: pointer;
`;

const AuthorContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;
const AuthorLeftContainer = styled.div`
  display: flex;
  justify-content: left;
  padding: 0.5rem;
  gap: 0.4rem;
`;
const AuthorRightContainer = styled.div`
  width: 30%;
  background-color: #d9eaf7;
  display: flex;
  flex-direction: column;
  justify-content: left;
  padding: 0.5rem;
  gap: 0.4rem;
`;
const AuthorRightInnerContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  gap: 0.5rem;
`;
const AuthorRightInnerRightContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: left;
  gap: 0.2rem;
`;

export const timeCalc = (time) => {
  if (time === null) {
    alert('내용을 알 수 없습니다.');
  } else {
    const mDay = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    const date = new Date();
    const year = Number(date.getUTCFullYear()) - Number(time.slice(0, 4));
    const month = Number(date.getUTCMonth() + 1) - Number(time.slice(5, 7));
    const day = Number(date.getUTCDate()) - Number(time.slice(8, 10));
    const hour = Number(date.getUTCHours()) - Number(time.slice(11, 13));
    const min = Number(date.getUTCMinutes()) - Number(time.slice(14, 16));
    const sec = Number(date.getUTCSeconds()) - Number(time.slice(17, 19));

    if (year > 1) return `${year} years ago`;
    else if (year === 1) return `${12 + month} months ago`;
    if (month > 1) return `${month} months ago`;
    else if (month === 1)
      return `${mDay[Number(date.getUTCMonth())] + day} days ago`;

    if (day) return `${day} days ago`;
    if (hour) return `${hour} hours ago`;
    if (min) return `${min} mins ago`;
    if (sec) return `${sec} secs ago`;
    else return 'now';
  }
};

function Author({
  questionId,
  answerId,
  memberId,
  name,
  answered,
  avatar,
  time,
}) {
  const navigate = useNavigate();

  const deleteHandler = () => {
    const target = questionId ? 'question' : 'answer';
    const id = questionId || answerId;
    const accessToken = localStorage.getItem('Authorization');

    fetch(`${url}/${target}/${id}`, {
      credentials: 'include',
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        Authorization: accessToken,
      },
    })
      .then((res) => res.json())
      .then((data) => {
        navigate('/questions');
      })
      .catch((res) => {
        if (target === 'question') navigate('/questions');
        else window.location.reload();
      });
  };

  return (
    <AuthorContainer>
      <AuthorLeftContainer>
        <LeftSpan>Share</LeftSpan>
        <EditA
          href={questionId ? `/updateQ/${questionId}` : `/updateA/${answerId}`}
          memberId={memberId}
          answerId={answerId}
        >
          Edit
        </EditA>
        <LeftSpan>Follow</LeftSpan>
        <DeleteA memberId={memberId} onClick={deleteHandler}>
          Delete
        </DeleteA>
      </AuthorLeftContainer>
      <AuthorRightContainer>
        <LeftSpan>{timeCalc(time)}</LeftSpan>
        <AuthorRightInnerContainer>
          <img className="circle" src="http://placeimg.com/40/40" />
          <AuthorRightInnerRightContainer>
            <span>{name}</span>
            <div>{answered}</div>
          </AuthorRightInnerRightContainer>
        </AuthorRightInnerContainer>
      </AuthorRightContainer>
    </AuthorContainer>
  );
}

export default Author;
