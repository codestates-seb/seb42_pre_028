import {
  Container,
  LContainer,
  OauthButton,
  Label,
  Input,
  Form,
  InputContainer,
  P,
  SubmitButton,
  LastContainer,
  H1,
  TextContainer,
} from '../Component/styled_component';

const Sign_Up = () => {
  return (
    <Container>
      <LContainer align="start" padding="30px">
        <H1>Join the Stack Overflow community</H1>
        <TextContainer>
          <span>[Icon] </span>
          <div>Get unstuck — ask a question</div>
        </TextContainer>
        <TextContainer>
          <span>[Icon] </span>
          <div>Unlock new privileges like voting and commenting</div>
        </TextContainer>
        <TextContainer>
          <span>[Icon] </span>
          <div>Save your favorite tags, filters, and jobs</div>
        </TextContainer>
        <TextContainer>
          <span>[Icon] </span>
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
        <OauthButton>Sign up with Google</OauthButton>
        <OauthButton background_color="hsl(210,8%,20%);" color="white">
          Sign up with GitHub
        </OauthButton>
        <OauthButton background_color="#385499" color="white">
          Sign up with Facebook
        </OauthButton>
        <Form>
          <InputContainer>
            <Label for="display_name">Display name</Label>
            <Input id="display_name"></Input>
          </InputContainer>
          <InputContainer>
            <Label for="email">Email</Label>
            <Input id="email"></Input>
          </InputContainer>
          <InputContainer>
            <Label for="password">Password</Label>
            <Input id="password"></Input>
            <P>
              Passwords must contain at least eight characters, including at
              least 1 letter and 1 number.
            </P>
          </InputContainer>
          <InputContainer direction="row" width="290px">
            <input type="checkbox" id="check" />
            <Label for="check" font_weight="none" font_size="small">
              Opt-in to receive occasional product updates, user research
              invitations, company announcements, and digests.
            </Label>
            <div>Icon?</div>
          </InputContainer>
          <SubmitButton>Sign up</SubmitButton>
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
};

export default Sign_Up;
