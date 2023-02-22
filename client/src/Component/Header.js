import styled from 'styled-components';
import { Link, useNavigate } from 'react-router-dom';
import Menu from './Menu';
import { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { logout } from '../features/log/logSlice';

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
  const [menuflag, setMenuFlag] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const state = useSelector((state) => state.log);

  const menuToggle = () => {
    setMenuFlag(!menuflag);
  };

  // 로그아웃 기능 추가
  const logoutHandler = () => {
    // const logoutData = {
    // req.body에 담을 데이터가 들어갈 자리
    // };

    // 테스트용 코드
    // 기능 : 로그아웃 버튼 클릭 시 로그인 상태를 false로 변경 후 홈으로 이동
    dispatch(logout(state));
    navigate('/');

    // 서버 통신용 코드 (임의로 작성. 로그아웃 api 필요)
    // fetch(`https://991b-112-156-175-230.jp.ngrok.io/auth/logout`, {
    //   credentials: 'include',
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify(logoutData),
    // })
    //   .then((res) => {
    //     if (res.ok) {
    //       dispatch(logout(state));
    //     }
    //     return res.json();
    //   })
    //   .then((data) => {
    //     console.log(data);
    //     if (state.value === false) {
    //       navigate('/');
    //   })
    //   .catch(() => alert('에러 발생'));
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
        <Input width="30rem" height="40px" />
        <Link to="/search">
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
