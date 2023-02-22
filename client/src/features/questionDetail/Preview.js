/* eslint-disable react/prop-types */
import styled from 'styled-components';

const PreviewBox = styled.div`
  padding: 1rem;
  margin-bottom: 1rem;
  white-space: pre-line;
`;

const NomalBox = styled.div`
  margin: 0;
  // 개행을 인식시키는 속성
  white-space: pre-wrap;
`;

// eslint-disable-next-line no-unused-vars
const CodeBox = styled.div`
  margin: 0;
  background-color: #f6f6f6;
  padding: 0 1rem;
  white-space: pre-wrap;
`;

function Preview({ content }) {
  return (
    <PreviewBox>
      {content.split('\n').map((el, index) => {
        if (el.slice(0, 4) === '    ') {
          return <CodeBox key={index}>{el.slice(4)}</CodeBox>;
        }
        return <NomalBox key={index}>{el}</NomalBox>;
      })}
    </PreviewBox>
  );
}

export default Preview;
