import styled from 'styled-components';

const QuestionLikeContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.7rem;
`;

// eslint-disable-next-line react/prop-types
function Like({ vote }) {
  const voteUpHandler = (e) => {
    fetch('URL/question/up', {
      credentials: 'include',
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
      });
  };

  const voteDownHandler = (e) => {
    fetch('URL/question/down', {
      credentials: 'include',
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
      });
  };

  return (
    <QuestionLikeContainer>
      <button onClick={voteUpHandler}>上</button>
      <div>{vote}</div>
      <button onClick={voteDownHandler}>下</button>
      <button>B</button>
      <button>A</button>
    </QuestionLikeContainer>
  );
}

export default Like;
