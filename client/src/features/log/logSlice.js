import { createSlice } from '@reduxjs/toolkit';

export const logSlice = createSlice({
  name: 'log',
  initialState: {
    value: false,
  },
  reducers: {
    login: (state) => {
      state.value = true;
    },
    logout: (state) => {
      state.value = false;
    },
  },
});

export const { login, logout } = logSlice.actions;

export default logSlice.reducer;
