package com.zes.datepicker;

import java.util.List;

public interface OnCascadeWheelListener<T> {

	/**
	 *
	 * @param wheelIndex 滚轮的索引位置
	 * @param itemIndex  所有滚轮对应数据的索引位置数组
	 * @return T 级联数据
	 */
	public T onCascade(int wheelIndex, List<Integer> itemIndex);
}
