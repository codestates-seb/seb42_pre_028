import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Sign_Up from './Page/Sign_Up';
import Header from './Component/Header';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <div className="content">
        <Routes>
          {/* <Route path="/" element={<Home />}></Route> */}
          {/* <Route path="/login" element={<Log_In />}></Route> */}
          <Route path="/signup" element={<Sign_Up />}></Route>
          {/* <Route path="/questions" element={<Questions />}></Route> */}
          {/* <Route path="/mypage" element={<My_Page />}></Route> */}
          {/* <Route path="/search" element={<Search />}></Route> */}
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
