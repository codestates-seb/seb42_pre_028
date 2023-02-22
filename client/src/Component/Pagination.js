import styled from 'styled-components';

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
function Pagination({ size, pageCnt, currentPage, setCurrentPage }) {
  let totalPage = Math.ceil(size / pageCnt); // 전체 Page 개수
  let arr = [];
  for (let i = 1; i <= totalPage; i++) arr.push(i);

  const handlePrevPage = () => {
    if (currentPage > 1) {
      setCurrentPage(--currentPage);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPage) {
      setCurrentPage(++currentPage);
    }
  };

  const handleChangePage = (e) => {
    setCurrentPage(e.target.value);
  };

  return (
    <Ul>
      <Li>
        <button onClick={handlePrevPage}>{'< prev'}</button>
      </Li>

      {arr.map((el, index) => {
        return (
          <Li key={index}>
            <Button
              value={el}
              seleted={el === currentPage ? '#f38225' : null}
              onClick={handleChangePage}
            >
              {el}
            </Button>
          </Li>
        );
      })}
      <Li>
        <button onClick={handleNextPage}>{'next >'}</button>
      </Li>
    </Ul>
  );
}

export default Pagination;
