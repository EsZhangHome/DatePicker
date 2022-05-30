package com.zes.datepicker.widget;

import android.view.View;

public interface IPickerView {
    /**
     * 设置Item的文本大小
     * @param textSize
     */
    public void setTextSize(int textSize);

    /**
     * 设置item文本的颜色
     * @param textColor
     */
    public void setTextColor(int textColor);

    /**
     * 设置item分割线的颜色
     * @param lineColor
     */
    public void setLineColor(int lineColor);

    /**
     * 设置item分割线的宽度
     * @param width
     */
    public void setLineWidth(int width);

    /**
     * 设置item的space
     * @param
     */
    public void setItemSpace(int space);

    /**
     * 设置显示的item counte
     * @param itemCount
     */
    public void setVisibleItemCount(int itemCount);

    /**
     * 设置item的宽高
     * @param itemWidth
     * @param itemHeight
     * @deprecated pickerView自动计算
     */
    public void setItemSize(int itemWidth, int itemHeight);

    /**
     * 设置滚轮偏向，及其偏向因子
     * @param gravity
     * @param factor [0, 1]
     */
    public void setShadow(int gravity, float factor);

    /**
     * 设置滚动阻尼因子
     * @param factor
     */
    public void setScrollAnimFactor(float factor);

    /**
     * 设置回手指滑动因子
     * @param factor
     */
    public void setScrollMoveFactor(float factor);

    /**
     * 设置回滚偏移量
     * @param offset
     */
    public void setScrollOverOffset(int offset);

    public View asView();
}
