/* eslint-disable no-unused-vars */
import { useState } from 'react';
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { url } from '../url';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleQuestion } from '@fortawesome/free-solid-svg-icons';
import {
  faGoogle,
  faGithub,
  faFacebook,
} from '@fortawesome/free-brands-svg-icons';

const Container = styled.div`
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f1f2f3;
`;

const LContainer = styled.div`
  display: flex;
  background-color: ${(props) => props.background_color || 'none'};
  padding: ${(props) => props.padding || '0px'};
  flex-direction: column;
  justify-content: center;
  align-items: ${(props) => props.align || 'center'};
`;

const OauthButton = styled.button`
  width: ${(props) => props.width || '324px'};
  height: 37.8px;
  background-color: ${(props) => props.background_color || 'white'};
  color: ${(props) => props.color || 'black'};
  margin-bottom: 6px;
  border: 0.5px solid gray;
  border-radius: 5px;
  cursor: pointer;

  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
`;

const Label = styled.label`
  width: ${(props) => props.width || 'auto'};
  font-weight: ${(props) => props.font_weight || 'bold'};
  font-size: ${(props) => props.font_size || 'auto'};
  color: ${(props) => props.color || 'black'};
  cursor: pointer;
  margin-bottom: 5px;
  text-align: left;
`;

const Input = styled.input`
  width: ${(props) => props.width || '290px'};
  height: ${(props) => props.height || '32.6px'};
  padding-left: 8px;
  border: 0.5px solid gray;
  border-radius: 5px;
`;

const Form = styled.form`
  width: 324px;
  padding: 30px 20px;
  background-color: white;
  border-radius: 10px;
  margin-top: 10px;
`;

const InputContainer = styled.div`
  width: ${(props) => props.width || 'auto'};
  display: flex;
  flex-direction: ${(props) => props.direction || 'column'};
  justify-content: center;
  align-items: start;
  margin-bottom: 19px;
`;

const P = styled.p`
  width: ${(props) => props.width || '290px'};
  text-align: ${(props) => props.align || 'left'};
  font-size: small;
  color: ${(props) => props.color || 'gray'};
`;

const SubmitButton = styled.button`
  width: 290px;
  height: 37.8px;
  background-color: hsl(206, 100%, 52%);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
`;

const LastContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 30px;
`;

const H1 = styled.h1`
  width: 430px;
  font-weight: 400;
  text-align: left;
  margin-bottom: 3rem;
`;

const TextContainer = styled.div`
  display: flex;
  justify-content: left;
  height: 40px;
  gap: 0.5rem;
`;

function Sign_Up() {
  const [displayName, setDisplayName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const submitHandler = (e) => {
    e.preventDefault();
    if (displayName === '' || email === '' || password === '') {
      alert('빈 곳을 채워주세요');
    } else {
      fetch(`${url}/members`, {
        credentials: 'include',
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          displayName,
          email,
          password,
        }),
      })
        .then((res) => {
          if (!res.ok) {
            if (res.status === 500) {
              alert('회원가입 실패 : 이미 존재하는 계정입니다.');
            } else {
              alert('회원가입 실패');
            }
          } else {
            alert('회원가입 성공!');
            navigate('/login');
          }
        })
        .catch(() => alert('에러 발생'));
    }
  };
  return (
    <Container>
      <LContainer align="start" padding="30px">
        <H1>Join the Stack Overflow community</H1>
        <TextContainer>
          <span>
            <FontAwesomeIcon icon={faCircleQuestion} />
          </span>
          <div>Get unstuck — ask a question</div>
        </TextContainer>
        <TextContainer>
          <span>
            <FontAwesomeIcon icon={faCircleQuestion} />
          </span>
          <div>Unlock new privileges like voting and commenting</div>
        </TextContainer>
        <TextContainer>
          <span>
            <FontAwesomeIcon icon={faCircleQuestion} />
          </span>
          <div>Save your favorite tags, filters, and jobs</div>
        </TextContainer>
        <TextContainer>
          <span>
            <FontAwesomeIcon icon={faCircleQuestion} />{' '}
          </span>
          <div>Earn reputation and badges</div>
        </TextContainer>
        <P width="400px">
          Collaborate and share knowledge with a private group for FREE.{' '}
          <a href="https://stackoverflow.co/teams/?utm_source=so-owned&utm_medium=product&utm_campaign=free-50&utm_content=public-sign-up">
            Get Stack Overflow for Teams free for up to 50 users.
          </a>
        </P>
      </LContainer>
      <LContainer>
        <OauthButton>
          <FontAwesomeIcon icon={faGoogle} /> Sign up with Google
        </OauthButton>
        <OauthButton background_color="hsl(210,8%,20%);" color="white">
          <FontAwesomeIcon icon={faGithub} /> Sign up with GitHub
        </OauthButton>
        <OauthButton background_color="#385499" color="white">
          <FontAwesomeIcon icon={faFacebook} /> Sign up with Facebook
        </OauthButton>
        <Form onsubmit="return false;">
          <InputContainer>
            <Label htmlFor="display_name">Display name</Label>
            <Input
              id="display_name"
              value={displayName}
              onChange={(e) => setDisplayName(e.target.value)}
            ></Input>
          </InputContainer>
          <InputContainer>
            <Label htmlFor="email">Email</Label>
            <Input
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            ></Input>
          </InputContainer>
          <InputContainer>
            <Label htmlFor="password">Password</Label>
            <Input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            ></Input>
            <P>
              Passwords must contain at least eight characters, including at
              least 1 letter and 1 number.
            </P>
          </InputContainer>
          <InputContainer direction="row" width="290px">
            <input type="checkbox" id="check" />
            <Label htmlFor="check" font_weight="none" font_size="small">
              Opt-in to receive occasional product updates, user research
              invitations, company announcements, and digests.
            </Label>
            <div>
              <FontAwesomeIcon icon={faCircleQuestion} />
            </div>
          </InputContainer>
          <SubmitButton type="submit" onClick={submitHandler}>
            Sign up
          </SubmitButton>
          <P>
            By clicking “Sign up”, you agree to our{' '}
            <a href="https://stackoverflow.com/legal/terms-of-service/public">
              {' '}
              terms of service, privacy policy
            </a>{' '}
            and{' '}
            <a href="https://stackoverflow.com/legal/cookie-policy">
              cookie policy
            </a>
          </P>
        </Form>
        <LastContainer>
          <P color="black" align="center">
            Already have an account?{' '}
            <a href="https://stackoverflow.com/users/login?ssrc=head&returnurl=https%3a%2f%2fstackoverflow.com%2f">
              Log in
            </a>
          </P>
          <P color="black" align="center">
            Are you an employer?{' '}
            <a href="https://talent.stackoverflow.com/users/login">
              Sign up on Talent
            </a>{' '}
          </P>
        </LastContainer>
      </LContainer>
    </Container>
  );
}

export default Sign_Up;
