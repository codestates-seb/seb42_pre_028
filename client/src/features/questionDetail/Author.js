/* eslint-disable react/prop-types */
import styled from 'styled-components';

const LeftSpan = styled.span`
  color: gray;
  font-size: small;
`;

const AuthorContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
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

function Author({ name, answered, avatar }) {
  return (
    <AuthorContainer>
      <LeftSpan>Share Edit Follow</LeftSpan>
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
