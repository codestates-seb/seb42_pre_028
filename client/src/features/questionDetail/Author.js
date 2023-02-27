/* eslint-disable react/prop-types */
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
const LeftSpan = styled.span`
  color: gray;
  font-size: small;
`;
const EditA = styled.a`
  color: gray;
  font-size: small;
  text-decoration-line: none;
  pointer-events: ${(props) =>
    !props.answerId &&
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

function Author({ questionId, answerId, memberId, name, answered, avatar }) {
  const navigate = useNavigate();

  const deleteHandler = () => {
    const target = questionId ? 'question' : 'answer';
    const id = questionId || answerId;
    const accessToken = localStorage.getItem('Authorization');

    fetch(`http://13.125.1.215:8080/${target}/${id}`, {
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
          href={`/update/${questionId}`}
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
        <LeftSpan>asked 2 days ago</LeftSpan>
        <AuthorRightInnerContainer>
          <span>{avatar}</span>
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
