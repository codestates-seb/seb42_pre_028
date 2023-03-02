import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Preview from '../features/questionDetail/Preview';
import { useNavigate, useParams } from 'react-router-dom';
import { url } from '../url';
import { Autocomplete } from '../Component/Autocomplete';

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
  gap: 2rem;
`;

const Input = styled.input`
  width: 30rem;
  padding: 0.3rem;
  margin-bottom: 1rem;
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

function Question_Update() {
  const { id } = useParams();
  const [title, setTitle] = useState('');
  const [problemContent, setProblemContent] = useState(' ');
  const [expectingContent, setExpectingContent] = useState(' ');
  const [tag, setTag] = useState([]);
  const navigate = useNavigate();
  const accessToken = localStorage.getItem('Authorization');

  useEffect(() => {
    setTimeout(() => {
      fetch(`${url}/question/${id}`, {
        credentials: 'include',
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: accessToken,
        },
      })
        .then((res) => {
          if (!res.ok) {
            throw Error('could not fetch the data for that resource');
          }
          return res.json();
        })
        .then((data) => {
          console.log(data);
          setTitle(data.data.title);
          setProblemContent(data.data.problemBody.join('\n'));
          setExpectingContent(data.data.expectingBody.join('\n'));
          setTag(data.data.tag);
        });
    }, 300);
  }, []);

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
        e.target.id === 'problemContent'
          ? setProblemContent(e.target.value)
          : setExpectingContent(e.target.value);
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
        e.target.id === 'problemContent'
          ? setProblemContent(e.target.value)
          : setExpectingContent(e.target.value);
      }
    }
  };

  const updateHandler = (e) => {
    e.preventDefault();

    const splitPcontent = problemContent.split('\n');
    const splitEcontent = expectingContent.split('\n');

    const questionData = {
      questionId: id,
      title: title,
      problemBody: splitPcontent,
      expectingBody: splitEcontent,
      tag: tag,
    };

    fetch(`${url}/question/${id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        Authorization: accessToken,
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
      <ColumDiv>
        <label htmlFor="title">Title</label>
        <Input
          id="title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />

        <label htmlFor="problemContent">Problem</label>
        <Textarea
          id="problemContent"
          value={problemContent}
          onChange={(e) => {
            setProblemContent(e.target.value);
          }}
          onKeyDown={keyDownHandler}
        />

        <label htmlFor="expectingContent">Expecting</label>
        <Textarea
          id="expectingContent"
          value={expectingContent}
          onChange={(e) => {
            setExpectingContent(e.target.value);
          }}
          onKeyDown={keyDownHandler}
        />

        <Preview content={problemContent}></Preview>
        <Preview content={expectingContent}></Preview>

        <label htmlFor="tags">Tags</label>
        <Autocomplete tags={tag} setTags={setTag} />
        <UpdateButton onClick={updateHandler}>Question Update</UpdateButton>
      </ColumDiv>
    </UpdateContainer>
  );
}
export default Question_Update;
