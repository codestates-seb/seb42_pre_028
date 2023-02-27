import { createSlice } from '@reduxjs/toolkit';

export const userDataSlice = createSlice({
  name: 'userData',
  initialState: {
    memberId: sessionStorage.getItem('memberId'),
    displayName: sessionStorage.getItem('displayName'),
    email: sessionStorage.getItem('email'),
    createdAt: sessionStorage.getItem('createdAt'),
  },
  reducers: {
    saveData: (state, action) => {
      sessionStorage.setItem('memberId', action.payload.memberId);
      sessionStorage.setItem('displayName', action.payload.displayName);
      sessionStorage.setItem('email', action.payload.email);
      sessionStorage.setItem('createdAt', action.payload.createdAt);
    },
    deleteData: () => {
      sessionStorage.removeItem('memberId');
      sessionStorage.removeItem('displayName');
      sessionStorage.removeItem('email');
      sessionStorage.removeItem('createdAt');
    },
  },
});

export const { saveData, deleteData } = userDataSlice.actions;

export default userDataSlice.reducer;
