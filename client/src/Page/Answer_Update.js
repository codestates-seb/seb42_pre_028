import { useState } from 'react';
import styled from 'styled-components';
import Preview from '../features/questionDetail/Preview';
import { useNavigate, useParams } from 'react-router-dom';

const UpdateContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1rem;
`;

const ColumDiv = styled.div`
  display: flex;
  flex-direction: column;
  align-items: left;
  gap: 0.7rem;
`;

const Textarea = styled.textarea`
  width: 30rem;
  padding: 1rem;
  height: 10rem;
  margin-bottom: 1rem;
`;

const UpdateButton = styled.button`
  width: fit-content;
  height: 2.5rem;
  padding: 0 0.6rem;
  background-color: #1295ff;
  color: white;
  border: none;
  cursor: pointer;
  :hover {
    background-color: #0088ff;
  }
`;

function Answer_Update() {
  const { id } = useParams();
  const [content, setContent] = useState(' ');
  const navigate = useNavigate();
  // const accessToken = localStorage.getItem('Authorization');

  const keyDownHandler = (e) => {
    if (e.target.selectionStart === e.target.selectionEnd) return;
    let prevText = e.target.value.slice(0, e.target.selectionStart);
    let nextText = e.target.value.slice(e.target.selectionEnd);
    let selectedText = e.target.value.slice(
      e.target.selectionStart,
      e.target.selectionEnd
    );
    if (e.key === 'q' || e.key === 'Q') {
      let originData = selectedText.split('\n');
      let filterData = selectedText
        .split('\n')
        .filter((el) => el.slice(0, 4) === '    ');

      if (originData.length === filterData.length) {
        e.target.value =
          prevText +
          selectedText
            .split('\n')
            .map((el) => {
              return `${el.slice(4)}`;
            })
            .join('\n') +
          nextText;
        setContent(e.target.value);
      } else {
        e.target.value =
          prevText +
          selectedText
            .split('\n')
            .map((el) => {
              return `    ${el}`;
            })
            .join('\n') +
          nextText;
        setContent(e.target.value);
      }
    }
  };

  const updateHandler = (e) => {
    e.preventDefault();
    const url = `http://13.125.1.215:8080/answer/${id}`;

    const splitPcontent = content.split('\n');

    const questionData = {
      answerId: id,
      content: splitPcontent,
    };

    fetch(url, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(questionData),
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        navigate(-1); // 서버로부터 받은 응답 데이터 출력
      })
      .catch((error) => {
        console.error(error); // 에러 처리
      });
  };

  return (
    <UpdateContainer>
      <form>
        <ColumDiv>
          <label htmlFor="problemContent">Content</label>
          <Textarea
            id="problemContent"
            value={content}
            onChange={(e) => {
              setContent(e.target.value);
            }}
            onKeyDown={keyDownHandler}
          />

          <Preview content={content}></Preview>

          <UpdateButton onClick={updateHandler}>Answer Update</UpdateButton>
        </ColumDiv>
      </form>
    </UpdateContainer>
  );
}
export default Answer_Update;
