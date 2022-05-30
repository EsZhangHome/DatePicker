package com.zes.datepicker.widget;

import com.zes.datepicker.core.ScrollWheelPicker;

import android.content.Context;
import android.util.AttributeSet;

public abstract class AbstractViewWheelPicker extends ScrollWheelPicker<ViewBaseAdapter> {

	public AbstractViewWheelPicker(Context context) {
		super(context);
	}

	public AbstractViewWheelPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AbstractViewWheelPicker(Context context, AttributeSet attrs,
                                   int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

}
