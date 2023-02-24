import { createSlice } from '@reduxjs/toolkit';

export const logSlice = createSlice({
  name: 'log',
  initialState: {
    value: Number(localStorage.getItem('Login')),
  },
  reducers: {
    login: (state) => {
      state.value = 1;
    },
    logout: (state) => {
      state.value = 0;
    },
  },
});

export const { login, logout } = logSlice.actions;

export default logSlice.reducer;
