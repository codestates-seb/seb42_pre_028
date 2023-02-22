import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { Outlet } from 'react-router';

function Footer() {
  if (window.location.pathname === '/login') return null;
  if (window.location.pathname === '/signup') return null;
  return (
    <FooterContainer>
      <LogoContainer>
        <Link to="/" style={{ textDecoration: 'none' }}>
          <Logo img src="../images/Stack_Overflow_icon.svg" />
        </Link>
      </LogoContainer>
      <Category>
        <Link to="/" style={{ textDecoration: 'none' }}>
          <p className="categoryTitle">STACK OVERFLOW</p>
        </Link>
        <Link to="/questions" style={{ textDecoration: 'none' }}>
          <Menu>Questions</Menu>
        </Link>
        <Menu>Help</Menu>
      </Category>
      <Category>
        <p className="categoryTitle">PRODUCTS</p>
        <Menu>Teams</Menu>
        <Menu>Advertising</Menu>
        <Menu>Collectives</Menu>
        <Menu>Talent</Menu>
      </Category>
      <Category>
        <p className="categoryTitle">COMPANY</p>
        <Menu>About</Menu>
        <Menu>Press</Menu>
        <Menu>Work Here</Menu>
        <Menu>Legal</Menu>
        <Menu>Privacy Policy</Menu>
        <Menu>Terms of Service</Menu>
        <Menu>Contact Us</Menu>
        <Menu>Cookie Settings</Menu>
        <Menu>Cookie Policy</Menu>
      </Category>
      <Category>
        <p className="categoryTitle">STACK EXCHANGE NETWORK</p>
        <Menu>Technology</Menu>
        <Menu>Culture & recreation</Menu>
        <Menu>Life & arts</Menu>
        <Menu>Science</Menu>
        <Menu>Professional</Menu>
        <Menu>Business</Menu>
        <br />
        <Menu>API</Menu>
        <Menu>Data</Menu>
      </Category>
      <SnsAndCopyright>
        <SnsContainer>
          <p className="sns">Blog</p>
          <p className="sns">Facebook</p>
          <p className="sns">Twitter</p>
          <p className="sns">LinkedIn</p>
          <p className="sns">Instagram</p>
        </SnsContainer>
        <p>
          Site design / logo © 2022 Stack Exchange Inc; user contributions
          licensed under CC BY-SA. rev 2022.12.19.43125
        </p>
      </SnsAndCopyright>
      <Outlet />
    </FooterContainer>
  );
}

const FooterContainer = styled.div`
  padding: 20px;
  height: 20rem;
  background-color: rgb(35 38 41);
  color: #9199a1;
  display: flex;
  justify-content: center;
  bottom: 0px;
`;

const LogoContainer = styled.div`
  flex: 0 0 64px;
  margin: 0 1 40 1px;
`;

const Logo = styled.img`
  width: 50px;
  height: 50px;
`;

const Category = styled.div`
  height: 260px;
  padding: 0 12 24 0px;
  margin: 0 30px;
  .categoryTitle {
    height: 1.5rem;
    padding: 0px ;
    margin : 0px
    font-size: 15px;
    font-weight: 700;
    color: #babfc4;
  }
`;

const Menu = styled.p`
  font-size: 0.8rem;
  padding-bottom: 6px;
  margin: 0px;
  color: #babfc4;
`;

const SnsAndCopyright = styled.div`
  width: 15rem;
  height: 260px;
  padding: 0 12 24 0px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  font-size: 11px;
`;

const SnsContainer = styled.div`
  width: 10rem;
  display: flex;
  justify-content: space-between;
  border-radius: 2rem;
  padding-top: 10px;
  .sns {
    border-radius: 2rem;
    padding-right: 10px;
  }
`;

export default Footer;
