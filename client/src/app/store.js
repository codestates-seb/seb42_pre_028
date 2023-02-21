import { configureStore } from '@reduxjs/toolkit';
import logReducer from '../features/log/logSlice';

export default configureStore({
  reducer: {
    log: logReducer,
  },
});
