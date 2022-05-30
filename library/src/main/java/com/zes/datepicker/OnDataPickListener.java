package com.zes.datepicker;

public interface OnDataPickListener<T> {
    /**
     * @param index 选中项的index
     * @param val   选中项的显示字符串
     * @param data  选中项的数据
     */
    public void onDataPicked(int index, String val, T data);
}
