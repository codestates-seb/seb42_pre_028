import { createSlice } from '@reduxjs/toolkit';

export const userDataSlice = createSlice({
  name: 'userData',
  initialState: {
    memberId: '',
  },
  reducers: {
    saveData: (state, data) => {
      console.log(data);
      state.memberId = '';
    },
    deleteData: (state) => {
      state.memberId = '';
    },
  },
});

export const { saveData, deleteData } = userDataSlice.actions;

export default userDataSlice.reducer;
