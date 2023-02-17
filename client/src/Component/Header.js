import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { Button, Input } from './styled_component';
import Menu from './Menu';
import { useState } from 'react';

const Container = styled.nav`
  padding: 0 1rem;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
`;

const MenuContainer = styled.div`
  position: relative;
`;

const Header = () => {
  const [menuflag, setMenuFlag] = useState(false);
  const [logflag, setLogFlag] = useState(false);

  const menuToggle = () => {
    setMenuFlag(!menuflag);
  };

  // eslint-disable-next-line no-unused-vars
  const logToggle = () => {
    setLogFlag(!logflag);
  };

  return (
    <Container>
      <MenuContainer>
        <Button onClick={menuToggle}>Menu</Button>
        {menuflag ? <Menu setMenuFlag={setMenuFlag} /> : null}
        <Link to="/">
          <Button>Logo</Button>
        </Link>
      </MenuContainer>
      <div>
        <Input width="290px" height="40px" />
        <Link to="/search">
          <Button>Search</Button>
        </Link>
      </div>

      {logflag ? (
        <Link to="/mypage">
          <Button>MyPage</Button>
        </Link>
      ) : (
        <div>
          <Link to="/login">
            <Button>Log In!</Button>
          </Link>
          <Link to="/signup">
            <Button>Sign Up!</Button>
          </Link>
        </div>
      )}
    </Container>
  );
};

export default Header;
