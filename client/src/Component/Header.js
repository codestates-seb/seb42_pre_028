import styled from 'styled-components';
import { Link } from 'react-router-dom';
import Menu from './Menu';
// import { useEffect, useState } from 'react';
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

const Button = styled.button`
  background-color: ${(props) => props.background_color || '#e1ecf4'};
  color: ${(props) => props.color || '#39739e'};
  border: 1px solid #7aa7c7;
  border-radius: 5px;
  cursor: pointer;

  padding: 10px;
`;

const Input = styled.input`
  width: ${(props) => props.width || '290px'};
  height: ${(props) => props.height || '32.6px'};
  padding-left: 8px;
  border: 0.5px solid gray;
  border-radius: 5px;
`;

const Header = () => {
  const [menuflag, setMenuFlag] = useState(false);
  const [logflag, setLogFlag] = useState(false);

  // useEffect(() => {
  //   logToggle();
  // }, []);

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
        <Input width="30rem" height="40px" />
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
            <Button background_color="#1295ff" color="white">
              Sign Up!
            </Button>
          </Link>
        </div>
      )}
    </Container>
  );
};

export default Header;
