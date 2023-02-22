import styled from 'styled-components';
import { Link } from 'react-router-dom';

function Home() {
  return (
    <HomeContainer>
      <Link to="/questions" style={{ textDecoration: 'none' }}>
        <HomeCategory>
          <p className="category">Questions</p>
        </HomeCategory>
      </Link>
      <Link to="/Tags" style={{ textDecoration: 'none' }}>
        <HomeCategory>
          <p className="category">Tags</p>
        </HomeCategory>
      </Link>
      <Link to="Users" style={{ textDecoration: 'none' }}>
        <HomeCategory>
          <p className="category">Users</p>
        </HomeCategory>
      </Link>
    </HomeContainer>
  );
}

const HomeContainer = styled.div`
  margin: 90px 30px 30px;
  height: 30rem;
  color: #9199a1;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #5a5a5a;
  border-radius: 5px;
`;

const HomeCategory = styled.button`
  margin: 2rem;
  height: 3rem;
  width: 10rem;
  display: flex;
  color: black;
  justify-content: center;
  border: 1px solid #fdf5e6;
  background-color: #fddcaa;
  border-radius: 5px;

  &:hover {
    background-color: #ffaa28;
  }

  &:active {
    background-color: #ff9614;
  }
`;

export default Home;
