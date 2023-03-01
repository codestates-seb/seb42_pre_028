import { configureStore } from '@reduxjs/toolkit';
import logReducer from '../features/log/logSlice';
import userDataReducer from '../features/userData/userDataSlice';
import myPageReducer from '../features/myPageSlice/myPageSlice';

export default configureStore({
  reducer: {
    log: logReducer,
    userData: userDataReducer,
    myPage: myPageReducer,
  },
});
