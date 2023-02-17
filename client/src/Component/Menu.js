/* eslint-disable react/prop-types */
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { Button } from './styled_component';

const Container = styled.nav`
  position: absolute;
  top: 90%;
  left: 0%;
`;

const NavContainer = styled.div`
  width: fit-content;
  height: fit-content;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: gray;
  padding: 10px 0px 0 0;
`;

const Menu = ({ setMenuFlag }) => {
  return (
    <Container>
      <NavContainer>
        <Link to="/">
          <Button onClick={() => setMenuFlag(false)}>Home💜</Button>
        </Link>
        <Link to="/questions">
          <Button onClick={() => setMenuFlag(false)}>Questions</Button>
        </Link>
        <Link to="/mypage">
          <Button onClick={() => setMenuFlag(false)}>My Page</Button>
        </Link>
      </NavContainer>
    </Container>
  );
};

export default Menu;
