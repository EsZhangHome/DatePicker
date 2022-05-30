package com.zes.datepicker.core;

import android.text.TextUtils;

import com.zes.datepicker.widget.PickString;

import java.util.List;

public class WheelPickerUtil {
    public static <T> String formString(T d) {
        if (d == null) {
            return null;
        }

        if (d instanceof String) {
            return (String) d;
        } else if (d instanceof PickString) {
            return ((PickString) d).pickDisplayName();
        } else {
            return d.toString();
        }
    }

    public static <T> int indexOf(T targetElement, List<T> data) {
        if (data == null || data.isEmpty()) {
            return -1;
        }

        String targetStr = WheelPickerUtil.formString(targetElement);
        for (int i = 0; i < data.size(); i++) {
            String str = WheelPickerUtil.formString(data.get(i));
            if (TextUtils.equals(targetStr, str)) {
                return i;
            }
        }

        return -1;
    }

    public static <T> String getStringVal(int index, List<T> data) {
        return WheelPickerUtil.formString(data.get(index));
    }
}
