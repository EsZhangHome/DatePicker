package com.zes.datepicker.core;

public interface OnWheelPickedListener<T> {

	public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, T data, boolean touch);
}
