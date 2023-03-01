import styled from 'styled-components';
import { Link, useNavigate } from 'react-router-dom';
import Menu from './Menu';
import { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { logout } from '../features/log/logSlice';
import { deleteData } from '../features/userData/userDataSlice';
import { deletePage } from '../features/myPageSlice/myPageSlice';

const Container = styled.header`
  padding: 4px;
  position: sticky; //상단바 고정 스크롤
  top: 0;
  left: 0;
  right: 0;
  background-color: #f8f9f9;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  border-top: 0.2rem solid #f48225;
  box-shadow: 0 0 5px #c8c8c8;
  z-index: 1;
`;

const Logo = styled.img`
  margin: 2px;
  width: 150px;
  height: 30px;
  vertical-align: middle;
`;

const MenuContainer = styled.div`
  position: relative;
`;

const Button = styled.button`
  margin: 2px;
  background-color: ${(props) => props.background_color || '#e1ecf4'};
  color: ${(props) => props.color || '#39739e'};
  border: 1px solid #7aa7c7;
  border-radius: 5px;
  cursor: pointer;

  padding: 10px;
`;

const Input = styled.input`
  margin: 2px;
  width: ${(props) => props.width || '290px'};
  height: ${(props) => props.height || '32.6px'};
  padding-left: 8px;
  border: 0.5px solid gray;
  border-radius: 5px;
`;

function Header() {
  const log = useSelector((state) => state.log.value);
  const [word, setWord] = useState('');
  const [menuflag, setMenuFlag] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const state = useSelector((state) => state.log);

  const menuToggle = () => {
    setMenuFlag(!menuflag);
  };

  const logoutHandler = () => {
    // 로그아웃은 프론트엔드에서만 처리하기로 함
    localStorage.removeItem('Authorization');
    // localStorage.removeItem('Refresh');
    dispatch(deleteData());
    dispatch(deletePage());
    dispatch(logout(state));
    alert('로그아웃 성공!');
    navigate('/');
  };

  return (
    <Container>
      <MenuContainer>
        <Button onClick={menuToggle}>Menu</Button>
        {menuflag ? <Menu setMenuFlag={setMenuFlag} /> : null}
        <Link to="/">
          <Logo img src="../images/Stack_Overflow_logo.svg.png" />
        </Link>
      </MenuContainer>
      <div>
        <Input
          width="30rem"
          height="40px"
          value={word}
          onChange={(e) => setWord(e.target.value)}
        />
        <Link to={`/search/${word}/1/5/Newest`}>
          <Button>Search</Button>
        </Link>
      </div>

      {log ? (
        <div>
          <Link to="/mypage/activity">
            <Button>MyPage</Button>
          </Link>
          <Button onClick={logoutHandler}>LogOut</Button>
        </div>
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
