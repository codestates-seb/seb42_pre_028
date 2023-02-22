import styled from 'styled-components';
import { Link } from 'react-router-dom';
import Like from '../features/questionDetail/Like';
import Author from '../features/questionDetail/Author';

const Container = styled.div`
  display: flex;
  justify-content: right;
  background-color: #ffffff;
`;

const Content = styled.div`
  width: 80%;
  display: flex;
  justify-content: left;
  padding: 1.5rem;
  border-left: 0.5px solid gray;
  gap: 1rem;
`;

const Mainbar = styled.div`
  width: 50rem;
  display: flex;
  flex-direction: column;
  align-items: left;
`;

const TitleContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
`;

const H2 = styled.h2`
  margin-top: 0;
`;

const AskButton = styled.button`
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

const TitleStateContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  gap: 0.3rem;
  padding-bottom: 1rem;
  margin-bottom: 1rem;
  border-bottom: 0.5px solid gray;
`;

const LeftSpan = styled.span`
  color: gray;
  font-size: small;
`;
const RightSpan = styled.span`
  font-size: small;
`;

const QuestionContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  gap: 2rem;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 0.5px solid gray;
`;

const QuestionContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left;
`;

const TagContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  gap: 0.5rem;
`;

const TagSpan = styled.span`
  padding: 0.3rem;
  background-color: #e1ecf4;
  color: #467ba3;
  font-size: small;
`;

const AnswerContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: left;
  gap: 2rem;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 0.5px solid gray;
`;

function Question_Detail() {
  return (
    <Container>
      <Content>
        <Mainbar>
          <TitleContainer>
            <H2>how redirect user with stripe react component and django</H2>
            <Link to="/create">
              <AskButton>Ask Question</AskButton>
            </Link>
          </TitleContainer>
          <TitleStateContainer>
            <div>
              <LeftSpan>Asked</LeftSpan> <RightSpan>16 days ago</RightSpan>
            </div>
            <div>
              <LeftSpan>Modified</LeftSpan> <RightSpan>9 days ago</RightSpan>
            </div>
            <div>
              <LeftSpan>Viewed</LeftSpan> <RightSpan>6k times</RightSpan>
            </div>
          </TitleStateContainer>
          <QuestionContainer>
            <Like size={7} />
            <QuestionContentContainer>
              <p>
                I would like to redirect my user after he has made a payment
                (successful or failed) to a page automatically.
              </p>
              <p>
                Currently, the payment is going well, the update is a success on
                stripe and I manage to retrieve the necessary information with
                my django view.
              </p>
              <p>
                However, after successful payment, no redirection takes place.
                There are several documentation but I cant find a way to do it
                with the react component proposed by stripe themselves.
              </p>
              <p>How can I proceed?</p>
              <p>here is my work</p>
              <p>Offers.js : ReactComponent by Stripe</p>
              <div>---------------code1----------------</div>
              <p>
                When I click on the subscribe button, everything is fine. the
                payment is made on stripe and I retrieve the information in my
                webhook with django
              </p>
              <p>views.py</p>
              <div>---------------code2----------------</div>
              <p>
                And here are my affiliate url routes to my subscriptions app
              </p>
              <p>urls.py</p>
              <div>---------------code3----------------</div>
              <p>models.py</p>
              <div>---------------code4----------------</div>
              <p>
                after the payment I get a page with `successful payment` but no
                redirect to apply and no possibility to return to the
                application. the URL looks like this:
                <a href="https://checkout.stripe.com/c/pay/cs_test_b1m%5B...%5DERl#fid%5B...%5DICUl">
                  https://checkout.stripe.com/c/pay/cs_test_b1m[...]ERl#fid[...]ICUl
                </a>
              </p>
              <p>[...] = sequence of several numbers and letters</p>
              <p>what am I doing wrong? thanks for your help</p>
              <TagContainer>
                <TagSpan>python</TagSpan>
                <TagSpan>reactjs</TagSpan>
                <TagSpan>django</TagSpan>
              </TagContainer>
              <Author name="Bastien Angeloz" />
            </QuestionContentContainer>
          </QuestionContainer>
          <p>1 Answer</p>
          <AnswerContainer>
            <Like size={8} />
            <QuestionContentContainer>
              <p>
                To set up redirection to your application after successful
                payment, it can be done by setting in the pricing table page in
                Dashboard. You can select Dont show confirmation page in every
                price to disable showing Stripes confirmation page and set the
                return URL to direct to your website.
              </p>
              <p>Heres the screenshot of where you can set it up:</p>
              [Img]
              <Author name="yuting" />
            </QuestionContentContainer>
          </AnswerContainer>
        </Mainbar>

        {/* <Sidebar>Sidebar</Sidebar> */}
      </Content>
    </Container>
  );
}
export default Question_Detail;
