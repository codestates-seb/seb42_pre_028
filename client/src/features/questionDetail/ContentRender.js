/* eslint-disable array-callback-return */
/* eslint-disable react/prop-types */
import styled from 'styled-components';

const PreviewBox = styled.div`
  padding: 1rem;
  margin-bottom: 1rem;
  white-space: pre-line;
`;

const NomalBox = styled.div`
  margin: 1rem 0;
  // 개행(enter)을 인식시키는 속성
  white-space: pre-wrap;
`;

const CodeBox = styled.div`
  padding: 1rem 1rem;
  background-color: #f6f6f6;
  padding-left: 1rem;
  padding-right: 1rem;
  white-space: pre-wrap;
`;

function ContentRender({ qContent }) {
  let codeArr = [];
  return (
    <PreviewBox>
      {qContent.map((el, index) => {
        if (el.slice(0, 4) === '    ') {
          codeArr.push(el.slice(4));
        } else {
          if (codeArr.length) {
            const sumCode = codeArr.join('\n');
            codeArr.length = 0;
            return (
              <div key={index}>
                <CodeBox>{sumCode}</CodeBox>
                <NomalBox>{el}</NomalBox>
              </div>
            );
          }
          return <NomalBox key={index}>{el}</NomalBox>;
        }
      })}
      {/* 남은 codeArr 렌더링 */}
      {codeArr.length ? <CodeBox>{codeArr.join('\n')}</CodeBox> : null}
    </PreviewBox>
  );
}

export default ContentRender;
