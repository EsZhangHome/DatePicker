package com.zes.datepicker.widget;

import com.zes.datepicker.core.WheelPickerUtil;

import java.util.List;

public class TextWheelPickerAdapter<T> extends TextBaseAdapter {
	private List<T> mData;
	
	public TextWheelPickerAdapter() {
		//empty constructor
	}
	
	public TextWheelPickerAdapter(List<T> data) {
		mData = data;
	}

	public List<T> getData() {
		return mData;
	}
	
	public void setData(List<T> data) {
		mData = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}

	@Override
	public String getItemText(int position) {
		T d = mData == null ? null : mData.get(position);
		return WheelPickerUtil.formString(d);
	}

	@Override
	public String getItem(int position) {
		return getItemText(position);
	}
}
