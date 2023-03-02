import { createSlice } from '@reduxjs/toolkit';

export const myPageSlice = createSlice({
  name: 'myPage',
  initialState: {
    pageNumber: sessionStorage.getItem('pageNumber'),
  },
  reducers: {
    setPage: (state) => {
      sessionStorage.setItem('pageNumber', '0');
    },
    goPrev: (state) => {
      let buffer = Number(state.pageNumber) - 1;
      state.pageNumber = buffer;
      sessionStorage.setItem('pageNumber', buffer);
    },
    goNext: (state) => {
      let buffer = Number(state.pageNumber) + 1;
      state.pageNumber = buffer;
      sessionStorage.setItem('pageNumber', buffer);
    },
    goNumber: (state, action) => {
      let buffer = action.payload - 1;
      state.pageNumber = buffer;
      sessionStorage.setItem('pageNumber', buffer);
    },
    deletePage: (state) => {
      state.pageNumber = null;
      sessionStorage.removeItem('pageNumber');
    },
  },
});

export const { setPage, goPrev, goNext, goNumber, deletePage } =
  myPageSlice.actions;

export default myPageSlice.reducer;
