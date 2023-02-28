import styled from 'styled-components';
import { Link } from 'react-router-dom';
const Ul = styled.ul`
  display: flex;
  justify-content: center;
  gap: 0.3rem;
  padding: 0;
`;

const Li = styled.li`
  list-style: none;
`;

const Button = styled.button`
  background-color: ${(props) => props.seleted || null};
`;

// props type 에러 : props 타입을 검사하여 안전한 props 인지 확인하라는 오류
// eslint-disable-next-line react/prop-types
function Pagination({ size, pageCnt, currentPage, tap }) {
  let totalPage = Math.ceil(size / pageCnt); // 전체 Page 개수
  let arr = [];
  for (let i = 1; i <= totalPage; i++) arr.push(i);

  return (
    <Ul>
      <Link
        to={`/questions/${
          currentPage > 1 ? Number(currentPage) - 1 : currentPage
        }/${pageCnt}/${tap}`}
      >
        <Li>
          <button>{'< prev'}</button>
        </Li>
      </Link>

      {arr.map((el, index) => {
        return (
          <Link key={index} to={`/questions/${el}/${pageCnt}/${tap}`}>
            <Li>
              <Button
                value={el}
                seleted={el === Number(currentPage) ? '#f38225' : null}
              >
                {el}
              </Button>
            </Li>
          </Link>
        );
      })}
      <Link
        to={`/questions/${
          currentPage < totalPage ? Number(currentPage) + 1 : currentPage
        }/${pageCnt}/${tap}`}
      >
        <Li>
          <button>{'next >'}</button>
        </Li>
      </Link>
    </Ul>
  );
}

export default Pagination;
