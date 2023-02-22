import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import SignUp from './Page/Sign_Up';
import Header from './Component/Header';
import LogIn from './Page/LogIn';
import QuestionsList from './Page/Questions_List';
import MyPage from './Page/My_Page';
import QuestionDetail from './Page/Question_Detail';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <div className="content">
        <Routes>
          {/* <Route path="/" element={<Home />}></Route> */}
          <Route path="/login" element={<LogIn />}></Route>
          <Route path="/signup" element={<SignUp />}></Route>
          <Route path="/questions" element={<QuestionsList />}></Route>
          <Route path="/questions/:id" element={<QuestionDetail />} />
          {/* <Route path="/create" element={<Create />}></Route> */}
          <Route path="/mypage" element={<MyPage />}></Route>
          {/* <Route path="/search" element={<Search />}></Route> */}
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
