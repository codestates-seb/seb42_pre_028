import React from 'react';
import styled from 'styled-components';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';
import MyPActNav from '../../Component/MyPages/myP_Act_nav';
import Footer from '../../Component/Footer';

const Container = styled.div`
  margin: 0px 65px 0px 65px;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  padding: 24px;
  font-size: 0.8rem;
`;

const H2 = styled.h2`
  font-size: 1.61538462rem;
  margin: 0px;
  vertical-align: baseline;
  font-weight: normal;
`;

const MainContent = styled.div`
  display: flex;
  margin-bottom: 48px;
`;

const Section = styled.div`
  width: 100%;
`;

const MQContainer = styled.div`
  display: flex;
  flex-direction: column;
`;

const MQMenubar = styled.div`
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  margin-bottom: 8px;
`;

const MQMain = styled.div`
  display: flex;
  flex-direction: column;
  border: 1px solid #d6d9dc;
  border-radius: 5px;
`;

const Questions = styled.div`
  max-width: calc(97.2307692rem / 12 * 4);
  padding: 48px;
  margin: 0 auto 0 auto;

  text-align: center;
  color: #6a737c;
`;

const DeletedQ = styled.div`
  padding: 16px;
  border-top: 1px solid #e3e6e8;
`;

function My_Page() {
  return (
    <React.Fragment>
      <Container>
        <Content>
          <MyPTProfile />
          <MyPMenu />
          <MainContent>
            <MyPActNav />
            <Section>
              <MQContainer>
                <MQMenubar>
                  <div>
                    <H2>0 Questions</H2>
                  </div>
                  <div>
                    <button>Score</button>
                    <button>Activity</button>
                    <button>Newest</button>
                    <button>View</button>
                  </div>
                </MQMenubar>
              </MQContainer>
              <MQMain>
                <Questions>You have not asked any questions</Questions>
                <DeletedQ>Deleted questions</DeletedQ>
              </MQMain>
            </Section>
          </MainContent>
        </Content>
      </Container>
      <Footer />
    </React.Fragment>
  );
}

export default My_Page;
