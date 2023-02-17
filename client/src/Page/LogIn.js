import React from 'react';
import styled from 'styled-components';
// 지워도 작동하길래 지웠습니다...

const Content = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f1f2f3;
  font-size: 0.8rem;
`;

const Items = styled.div`
  display: flex;
  flex-direction: column;
  width: 278px;
`;

const Logo = styled.div`
  margin-bottom: 24px;
  #logo {
    width: 32px;
    height: 37px;
  }
`;

const OpenIdDiv = styled.div`
  display: flex;
  flex-direction: column;
  margin: -4px 0 16px;
`;

const OpenIdBtn = styled.button`
  margin: 4px 0 4px;
  border: 1px solid;
  padding: 10.4px;
  border-radius: 5px;

  &:hover {
    cursor: pointer;
  }

  &#Google {
    background-color: white;
    border-color: #d6d9dc;
    &:hover {
      background-color: #f8f9f9;
    }
    &:active {
      background-color: #f1f2f3;
    }
  }

  &#GitHub {
    background-color: #2f3337;
    color: white;
    border-color: transparent;
    &:hover {
      background-color: #232629;
    }
    &:active {
      background-color: black;
    }
  }

  &#Facebook {
    background-color: #385499;
    color: white;
    border-color: transparent;
    &:hover {
      background-color: #314a86;
    }
    &:active {
      background-color: #2a4074;
    }
  }
`;

const LogInForm = styled.div`
  display: flex;
  flex-direction: column;
  background-color: white;
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.05), 0 20px 48px rgba(0, 0, 0, 0.05),
    0 1px 4px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
  padding: 24px;
  margin-bottom: 24px;
`;

const InputEmail = styled.div`
  display: flex;
  flex-direction: column;
  margin: 6px 0 6px;

  > label {
    text-align: left;
    margin: 2px 0 2px;
    padding: 0 2px;
    font-size: 1rem;
    font-weight: bold;
  }

  > input {
    margin: 2px 0 2px;
    border: 1px solid #babfc4;
    border-radius: 3px;
    padding: 0.6em 0.7em;
    color: #0c0d0e;
  }
`;

const InputPW = styled.div`
  display: flex;
  flex-direction: column;
  margin: 6px 0 6px;

  > div.input-password-label {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    > label {
      text-align: left;
      margin: 2px 0 2px;
      padding: 0 2px;
      font-size: 1rem;
      font-weight: bold;
    }
  }

  > input {
    margin: 2px 0 2px;
    border: 1px solid #babfc4;
    border-radius: 3px;
    padding: 0.6em 0.7em;
    color: #0c0d0e;
  }
`;

const LoginBtnDiv = styled.div`
  display: flex;
  flex-direction: column;
  margin: 6px 0 6px;

  > button {
    margin: 2px 0 2px;
    padding: 10.4px;
    border: 1px solid rgba(0, 0, 0, 0);
    border-radius: 3px;
    background-color: #0a95ff;
    color: white;

    &:hover {
      cursor: pointer;
      background-color: #0074cc;
    }
    &:active {
      background-color: #0063bf;
    }
  }
`;

const Message = styled.div`
  padding: 16px;
  margin-bottom: 24px;

  div.mt12 {
    margin-top: 12px;
  }
`;

function LogIn() {
  return (
    <React.Fragment>
      <Content>
        <Items>
          <Logo>
            <img id="logo" src="../images/apple-touch-icon.png" alt="logo" />
          </Logo>
          <OpenIdDiv>
            <OpenIdBtn id="Google">Log in with Google</OpenIdBtn>
            <OpenIdBtn id="GitHub">Log in with GitHub</OpenIdBtn>
            <OpenIdBtn id="Facebook">Log in with Facebook</OpenIdBtn>
          </OpenIdDiv>
          <LogInForm>
            <InputEmail>
              <label htmlFor="email">Email</label>
              <input
                id="email"
                type="email"
                size="30"
                maxLength="100"
                name="email"
              />
            </InputEmail>
            <InputPW>
              <div className="input-password-label">
                <label htmlFor="password">Password</label>
                <span>Forgot password?</span>
              </div>
              <input
                id="password"
                type="password"
                name="password"
                autoComplete="off"
              />
            </InputPW>
            <LoginBtnDiv>
              <button>Log in</button>
            </LoginBtnDiv>
          </LogInForm>
          <Message>
            <div>Don`t have an accout? Sign up</div>
            <div className="mt12">Are you an employer? Sign up on Talent</div>
          </Message>
        </Items>
      </Content>
    </React.Fragment>
  );
}

export default LogIn;