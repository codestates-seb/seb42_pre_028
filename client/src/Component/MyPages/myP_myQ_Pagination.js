import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import {
  goPrev,
  goNext,
  goNumber,
} from '../../features/myPageSlice/myPageSlice';

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
function Pagination({ totalEle, size, currentPage, pageNumber }) {
  const dispatch = useDispatch();

  let totalPage = Math.ceil(totalEle / size); // 전체 Page 개수
  let arr = [];
  for (let i = 1; i <= totalPage; i++) arr.push(i);

  const prevHandler = () => {
    dispatch(goPrev());
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  const nextHandler = () => {
    dispatch(goNext());
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  const numberHandler = (e) => {
    dispatch(goNumber(Number(e.target.value)));
    window.scrollTo({
      top: 0,
      behavior: 'smooth',
    });
  };

  return (
    <Ul>
      <Li>
        {currentPage > 1 ? (
          <button onClick={prevHandler}>{'prev'}</button>
        ) : null}
      </Li>
      {arr.map((el, index) => {
        return (
          <Li key={index}>
            <Button
              value={el}
              seleted={el === Number(currentPage) ? '#f38225' : null}
              onClick={numberHandler}
            >
              {el}
            </Button>
          </Li>
        );
      })}
      <Li>
        {currentPage < totalPage ? (
          <button onClick={nextHandler}>{'next'}</button>
        ) : null}
      </Li>
    </Ul>
  );
}

export default Pagination;
