// eslint-disable-next-line no-unused-vars
import React, { useState } from 'react';
import styled from 'styled-components';

import MyPTProfile from '../../Component/MyPages/MyP_Top_profile';
import MyPMenu from '../../Component/MyPages/MyP_menu';
import MyPSetNav from '../../Component/MyPages/myP_Set_nav';

const Container = styled.div`
  margin: 0px 65px 0px 65px;
`;

const H1 = styled.h1`
  font-weight: normal;
  margin: 0px;
  font-size: 2.07692308em;
  line-height: calc(15 / 13);
`;

const H3 = styled.h3`
  font-weight: 400;
  font-size: 1.61538462em;
  line-height: 1.3;
  margin: 0px 0px 8px 0px;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  padding: 24px;
  font-size: 0.8rem;
`;

const MainContainer = styled.div`
  display: flex;
`;

const Main = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
`;

const Title = styled.div`
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid #d6d9dc;
`;

const SubTitle = styled.div``;

const SetList = styled.div`
  margin-bottom: 48px;
  border: 1px solid #e3e6e8;
  border-radius: 5px;
`;

const SetListCompo = styled.div`
  padding: 24px;
  border-bottom: 1px solid #e3e6e8;
  &.last {
    border-bottom: none;
  }
`;

const SetBodyCompo = styled.div`
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;

  > label {
    display: flex;
    flex-direction: column;
    &:hover {
      cursor: pointer;
    }
    &.text-only-label {
      margin: 0 0 16px 0;
      &:hover {
        cursor: auto;
      }
    }
    &.to-exclude-input {
      flex-grow: 1;
      &:hover {
        cursor: pointer;
      }
    }
    &.download-data-hover {
      &:hover {
        cursor: auto;
      }
    }
    > div {
      font-weight: 600;
      font-size: 1.15384615em;
      &.to-exclude-input {
        margin: 2px 0 2px 0;
        padding: 0 2px 0 2px;
      }
    }
    > input {
      margin: 2px 0 2px 0;
      padding: 0.6em 0.7em;
      border: 1px solid #babfc4;
      border-radius: 3px;
    }
  }
`;

const SetItemCompo = styled.div`
  display: flex;
  &.theme-div {
    display: flex;
    justify-content: space-between;
  }
  &.toggle-div {
    margin: 8px;
  }
  &.download-btn {
    margin: 8px;
  }
`;

const DownloadBtn = styled.button`
  padding: 10.4px;
  &:hover {
    cursor: pointer;
  }
`;

function MySettings() {
  return (
    <React.Fragment>
      <Container>
        <Content>
          <MyPTProfile />
          <MyPMenu />
          <MainContainer>
            <MyPSetNav />
            <Main>
              <Title>
                <H1>Preferences</H1>
              </Title>
              <SubTitle>
                <H3>Interface</H3>
                <SetList>
                  <SetListCompo>
                    <SetBodyCompo>
                      <label htmlFor="theme" className="text-only-label">
                        <div id="theme">Theme</div>
                      </label>
                      <SetItemCompo className="theme-div"></SetItemCompo>
                    </SetBodyCompo>
                  </SetListCompo>
                  <SetListCompo>
                    <SetBodyCompo>
                      <label htmlFor="highcontrast-togle">
                        <div>High contrast</div>
                        <p>
                          Flipping this switch improves legibility by increasing
                          the contrast between text, background, and border
                          colors.
                        </p>
                      </label>
                      <SetItemCompo className="toggle-div"></SetItemCompo>
                    </SetBodyCompo>
                  </SetListCompo>
                  <SetListCompo>
                    <SetBodyCompo>
                      <label htmlFor="keyboard-shortcuts">
                        <div>Enable keyboard shortcuts</div>
                        <p>
                          When enabled, press <code>?</code> for help
                        </p>
                      </label>
                      <SetItemCompo className="toggle-div"></SetItemCompo>
                    </SetBodyCompo>
                  </SetListCompo>
                  <SetListCompo>
                    <SetBodyCompo>
                      <label htmlFor="hide-navigation">
                        <div>Hide left navigation</div>
                        <p>
                          When you flip this switch, the left navigation will no
                          longer be pinned to the left of the page on Q&A sites.
                        </p>
                      </label>
                      <SetItemCompo className="toggle-div"></SetItemCompo>
                    </SetBodyCompo>
                  </SetListCompo>
                  <SetListCompo className="last">
                    <SetBodyCompo>
                      <label htmlFor="hide-questions">
                        <div>Hide hot network questions</div>
                        <p>
                          When you flip this switch, you will no longer see Hot
                          Network Questions in the right sidebar on Q&A sites.
                        </p>
                      </label>
                      <SetItemCompo className="toggle-div"></SetItemCompo>
                    </SetBodyCompo>
                  </SetListCompo>
                </SetList>
                <H3>Advertisements</H3>
                <SetList>
                  <SetListCompo className="last">
                    <SetBodyCompo>
                      <label
                        htmlFor="Companies-to-exclude"
                        className="text-only-label to-exclude-input"
                      >
                        <div className="to-exclude-input">
                          Companies to exclude
                        </div>
                        <input
                          id="Companies-to-exclude"
                          type="text"
                          maxLength="140"
                          autoComplete="off"
                        />
                      </label>
                    </SetBodyCompo>
                  </SetListCompo>
                </SetList>
                <H3>Activity data</H3>
                <SetList>
                  <SetListCompo>
                    <SetBodyCompo>
                      <label
                        htmlFor="download-data"
                        className="download-data-hover"
                      >
                        <div>
                          Stack Overflow never sells or shares your activity
                          data with third parties.
                        </div>
                        <p>
                          We use your on-site activity to show you more relevant
                          content. For example, we might show you questions
                          based on the tags you usually browse, or show you job
                          listings in your current location.
                        </p>
                      </label>
                      <SetItemCompo className="download-btn">
                        <DownloadBtn>Download activity data</DownloadBtn>
                      </SetItemCompo>
                    </SetBodyCompo>
                  </SetListCompo>
                  <SetListCompo className="last">
                    <SetBodyCompo>
                      <label htmlFor="show-more-relevant">
                        <div>
                          Use my on-site activity to show more relevant content
                          (recommended)
                        </div>
                        <p>
                          If you opt-out, existing recommendations will be
                          discarded within 24 hours.
                        </p>
                      </label>
                      <SetItemCompo className="toggle-div"></SetItemCompo>
                    </SetBodyCompo>
                  </SetListCompo>
                </SetList>
              </SubTitle>
            </Main>
          </MainContainer>
        </Content>
      </Container>
    </React.Fragment>
  );
}

export default MySettings;
