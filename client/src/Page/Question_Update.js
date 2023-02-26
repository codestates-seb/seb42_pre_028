import { useState } from 'react';
import styled from 'styled-components';
import Preview from '../features/questionDetail/Preview';
import { useNavigate } from 'react-router-dom';

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

const Input = styled.input`
  width: 30rem;
  padding: 0.3rem;
  margin-bottom: 1rem;
`;
const Textarea = styled.textarea`
  width: 30rem;
  padding: 1rem;
  height: 18rem;
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

function Question_Update() {
  const [title, setTitle] = useState('question.title');
  const [content, setContent] = useState('question.content');
  const navigate = useNavigate();

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

  const updateHandler = () => {
    /*useEffect(() => {
      if (state === false) {
        navigate('/login');
      }
    });*/
    const url = 'http://13.125.1.215:8080/update';

    const questionData = {
      title: title,
      problemBody: content,
      expectingBody: content,
      memberId: 1, // 쿠키에서 멤버 아이디 가져오기   쿠키.memberId
    };

    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(questionData),
    })
      .then((res) => res.json())
      .then((data) => {
        console.log(data);
        navigate(`/update/${data.questionId}`); // 서버로부터 받은 응답 데이터 출력
      })
      .catch((error) => {
        console.error(error); // 에러 처리
      });
  };
  return (
    <UpdateContainer>
      <form>
        <ColumDiv>
          <label htmlFor="title">Title</label>
          <Input
            id="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />

          <label htmlFor="content">Content</label>
          <Textarea
            id="content"
            value={content}
            onChange={(e) => {
              setContent(e.target.value);
            }}
            onKeyDown={keyDownHandler}
          />

          <Preview content={content}></Preview>

          <label htmlFor="tags">Tags(구현예정)</label>

          <UpdateButton onClick={updateHandler}>Question Update</UpdateButton>
        </ColumDiv>
      </form>
    </UpdateContainer>
  );
}
export default Question_Update;
