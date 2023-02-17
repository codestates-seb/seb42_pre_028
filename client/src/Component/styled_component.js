import styled from 'styled-components';

export const Container = styled.div`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f1f2f3;
`;

export const LContainer = styled.div`
  display: flex;
  background-color: ${(props) => props.background_color || 'none'};
  padding: ${(props) => props.padding || '0px'};
  flex-direction: column;
  justify-content: center;
  align-items: ${(props) => props.align || 'center'};
`;

export const OauthButton = styled.button`
  width: ${(props) => props.width || '324px'};
  height: 37.8px;
  background-color: ${(props) => props.background_color || 'white'};
  color: ${(props) => props.color || 'black'};
  margin-bottom: 6px;
  border: 0.5px solid gray;
  border-radius: 5px;
  cursor: pointer;
`;

export const Button = styled.button`
  width: ${(props) => props.width || '100px'};
  height: ${(props) => props.height || '50px'};

  background-color: ${(props) => props.background_color || 'gary'};
  color: ${(props) => props.color || 'black'};

  border-radius: 5px;
  cursor: pointer;

  padding: 10px;
`;

export const Label = styled.label`
  width: ${(props) => props.width || 'auto'};
  font-weight: ${(props) => props.font_weight || 'bold'};
  font-size: ${(props) => props.font_size || 'auto'};
  color: ${(props) => props.color || 'black'};
  cursor: pointer;
  margin-bottom: 5px;
  text-align: left;
`;

export const Input = styled.input`
  width: ${(props) => props.width || '290px'};
  height: ${(props) => props.height || '32.6px'};
  padding-left: 8px;
  border: 0.5px solid gray;
  border-radius: 5px;
`;

export const Form = styled.form`
  width: 324px;
  padding: 30px 20px;
  background-color: white;
  border-radius: 10px;
  margin-top: 10px;
`;

export const InputContainer = styled.div`
  width: ${(props) => props.width || 'auto'};
  display: flex;
  flex-direction: ${(props) => props.direction || 'column'};
  justify-content: center;
  align-items: start;
  margin-bottom: 19px;
`;

export const P = styled.p`
  width: ${(props) => props.width || '290px'};
  text-align: ${(props) => props.align || 'left'};
  font-size: small;
  color: ${(props) => props.color || 'gray'};
`;

export const SubmitButton = styled.button`
  width: 290px;
  height: 37.8px;
  background-color: hsl(206, 100%, 52%);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
`;

export const LastContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
`;

export const H1 = styled.h1`
  width: 430px;
  font-weight: 400;
  text-align: left;
  margin-bottom: 3rem;
`;

export const TextContainer = styled.div`
  display: flex;
  justify-content: left;
  height: 40px;
`;
