package com.zes.datepicker;

import java.util.List;

public interface OnMultiDataPickListener<T> {
    /**
     * @param indexArr 选中项的index数组
     * @param val      选中项的显示字符串数组
     * @param data     选中项的数据数组
     */
    public void onDataPicked(List<Integer> indexArr, List<String> val, List<T> data);
}
