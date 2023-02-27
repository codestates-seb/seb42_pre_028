import { createSlice } from '@reduxjs/toolkit';

export const logSlice = createSlice({
  name: 'log',
  initialState: {
    value: Number(sessionStorage.getItem('Login')),
  },
  reducers: {
    login: (state) => {
      state.value = 1;
      sessionStorage.setItem('Login', 1);
    },
    logout: (state) => {
      state.value = 0;
      sessionStorage.removeItem('Login');
    },
  },
});

export const { login, logout } = logSlice.actions;

export default logSlice.reducer;
