import styled from 'styled-components';

const QuestionLikeContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.7rem;
`;

// eslint-disable-next-line react/prop-types
function Like({ size }) {
  return (
    <QuestionLikeContainer>
      <button>上</button>
      <div>{size}</div>
      <button>下</button>
      <button>B</button>
      <button>A</button>
    </QuestionLikeContainer>
  );
}

export default Like;
