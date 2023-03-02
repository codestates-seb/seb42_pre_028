import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import SignUp from './Page/Sign_Up';
import Header from './Component/Header';
// import Home from './Page/Home';
import LogIn from './Page/LogIn';
import QuestionsList from './Page/Questions_List';
import QuestionAsk from './Page/QuestionAsk';
import MyPage from './Page/MyPages/My_Page';
import MyProfile from './Page/MyPages/My_Profile';
import MySettings from './Page/MyPages/My_Settings';
import QuestionDetail from './Page/Question_Detail';
import DeleteProfile from './Page/MyPages/Delete_Profile';
import EditProfile from './Page/MyPages/Edit_Profile';
import QuestionsPage from './Page/Questions_Page';
import QuestionUpdate from './Page/Question_Update';
import AnswerUpdate from './Page/Answer_Update';
import QuestionsSearch from './Page/Questions_Search';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <div className="content">
        <Routes>
          <Route path="/" element={<QuestionsList />}></Route>
          <Route path="/login" element={<LogIn />}></Route>
          <Route path="/signup" element={<SignUp />}></Route>
          <Route path="/questions" element={<QuestionsList />}></Route>
          <Route path="/create" element={<QuestionAsk />}></Route>
          <Route path="/questions/:id" element={<QuestionDetail />} />
          <Route path="/mypage/activity" element={<MyPage />}></Route>
          <Route path="/mypage/profile" element={<MyProfile />}></Route>
          <Route path="/mypage/setting" element={<MySettings />}></Route>
          <Route path="/mypage/userdelete" element={<DeleteProfile />}></Route>
          <Route path="/mypage/useredit" element={<EditProfile />}></Route>
          <Route
            path="/questions/:page/:pageCnt/:tap"
            element={<QuestionsPage />}
          ></Route>
          <Route path="/updateQ/:id" element={<QuestionUpdate />} />
          <Route path="/updateA/:id" element={<AnswerUpdate />} />
          <Route
            path="/search/:word/:page/:pageCnt/:tap"
            element={<QuestionsSearch />}
          ></Route>
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
