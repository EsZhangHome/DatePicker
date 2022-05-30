package com.zes.datepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.zes.datepicker.widget.IPickerView;
import com.zes.datepicker.widget.TextWheelPicker;

public class SingleTextWheelPicker extends TextWheelPicker implements IPickerView {
    public SingleTextWheelPicker(Context context) {
        super(context);
    }

    public SingleTextWheelPicker(Context context, int id) {
        super(context, id);
    }

    public SingleTextWheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SingleTextWheelPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setTextSize(int textSize) {
        super.setTextSize(textSize);
    }

    public void setTextColor(int textColor) {
        super.setTextColor(textColor);
    }

    public void setLineColor(int lineColor) {
        super.setLineColor(lineColor);
    }

    public void setLineWidth(int width) {
        super.setLineStorkeWidth(width);
    }

    public void setItemSpace(int space) {
        super.setItemSpace(space);
    }

    public void setVisibleItemCount(int itemCount) {
        super.setVisibleItemCount(itemCount);
    }

    public void setItemSize(int itemWidth, int itemHeight) {
        super.setItemSize(itemWidth, itemHeight);
    }

    @Override
    public void setShadow(int gravity, float factor) {
        super.setShadowGravity(gravity);
        super.setShadowFactor(factor);
    }

    @Override
    public void setScrollAnimFactor(float factor) {
        super.setFlingAnimFactor(factor);
    }

    @Override
    public void setScrollMoveFactor(float factor) {
        super.setFingerMoveFactor(factor);
    }

    @Override
    public void setScrollOverOffset(int offset) {
        super.setOverOffset(offset);
    }

    @Override
    public View asView() {
        return this;
    }

}
