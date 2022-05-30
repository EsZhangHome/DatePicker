package com.zes.datepicker;

import com.zes.datepicker.widget.TextWheelPicker;

public class DateTimeItem {
    public final static int TYPE_YEAR = 1 << 1;
    public final static int TYPE_MONTH = 1 << 2;
    public final static int TYPE_DAY = 1 << 3;
    public final static int TYPE_HOUR = 1 << 4;
    public final static int TYPE_MINUTE = 1 << 5;
    public final static int TYPE_SECOND = 1 << 6;

    private int type;
    private String label;
    private TextWheelPicker wheelPicker;

    public DateTimeItem(int type, String label, TextWheelPicker picker) {
        this.type = type;
        this.label = label;
        this.wheelPicker = picker;
    }

    public int getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public TextWheelPicker getWheelPicker() {
        return wheelPicker;
    }
}
