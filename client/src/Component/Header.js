import styled from 'styled-components';
import { Link } from 'react-router-dom';
import Menu from './Menu';
import { useState } from 'react';
import { useSelector } from 'react-redux';

const Container = styled.nav`
  padding: 0.5rem 1rem;
  background-color: #f8f9f9;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  border-top: 0.2rem solid #f48225;
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

function Header() {
  const log = useSelector((state) => state.log.value);
  const [menuflag, setMenuFlag] = useState(false);

  const menuToggle = () => {
    setMenuFlag(!menuflag);
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

      {log ? (
        <Link to="/mypage/activity">
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
}

export default Header;
