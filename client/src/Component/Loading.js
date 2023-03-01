/* eslint-disable import/no-unresolved */
import styled from 'styled-components';
import Spinner from './Spinner-1s-200px.gif';

const Background = styled.div`
  position: absolute;
  width: 100vw;
  height: 100vh;
  top: 0;
  left: 0;
  background: #ffffffb7;
  z-index: 999;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

function Loading() {
  return (
    <Background>
      <img src={Spinner} alt="로딩중" width="5%" />
    </Background>
  );
}

export default Loading;
