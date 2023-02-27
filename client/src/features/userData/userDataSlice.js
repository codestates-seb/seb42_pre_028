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
      // console.log(`action payload:${JSON.stringify(action.payload)}`);
      state.memberId = action.payload.memberId;
      state.displayName = action.payload.displayName;
      state.email = action.payload.email;
      state.createdAt = action.payload.createdAt;
      sessionStorage.setItem('memberId', state.memberId);
      sessionStorage.setItem('displayName', state.displayName);
      sessionStorage.setItem('email', state.email);
      sessionStorage.setItem('createdAt', state.createdAt);
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
