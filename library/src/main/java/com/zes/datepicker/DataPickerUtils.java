package com.zes.datepicker;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;

import com.zes.datepicker.core.LanguageType;
import com.zes.datepicker.option.PickOption;
import com.zes.datepicker.widget.IPickerView;

public class DataPickerUtils {

    /**
     * 是否是月底最后一天
     * 根据当前年月日判断
     *
     * @return
     */
    public static boolean isMonthEnd(int year, int month, int day) {
        month = month + 1;
        switch (month) {
            case 2:
                if (isLeapYear(year)) {
                    return day == 29;
                } else {
                    return day == 28;
                }
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return day == 31;
            default:
                return day == 30;
        }
    }

    /**
     * 是否是闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return true;
        }

        return false;
    }


    /**
     * @param context
     * @param option
     * @param mode
     * @return
     */
    public static DateTimePicker buildDateTimeWheelPicker(Context context, PickOption option,
                                                          @PickMode.Mode int mode, LanguageType languageType) {
        DateTimePicker pickerView = null;
        switch (mode) {
            case PickMode.MODE_BIRTHDAY:
                pickerView = new DateTimePicker(context, DateTimePicker.MODE_BIRTHDAY, languageType);
                pickerView.setWheelPickerVisibility(DateTimePicker.TYPE_HH_MM_SS, View.GONE);
                break;
            case PickMode.MODE_FUTURE_DATE:
                pickerView = new DateTimePicker(context, DateTimePicker.MODE_PENDING, languageType);
                pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                break;
            case PickMode.MODE_DATE:
                pickerView = new DateTimePicker(context);
                pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                break;
        }

        DataPickerUtils.setPickViewStyle(pickerView, option);
        return pickerView;
    }

    public static DateTimePicker buildDateTimeWheelPicker(Context context, PickOption option,
                                                          @PickMode.Mode int mode) {
        return buildDateTimeWheelPicker(context, option, mode, LanguageType.ZH);
    }


    public static DateTimePicker buildDateTimeWheelPicker(Context context, PickOption option,
                                                          @PickMode.Mode int mode, LanguageType languageType, boolean showFuture) {
        DateTimePicker pickerView = null;
        switch (mode) {
            case PickMode.MODE_BIRTHDAY:
                pickerView = new DateTimePicker(context, DateTimePicker.MODE_BIRTHDAY, languageType, showFuture);
                pickerView.setWheelPickerVisibility(DateTimePicker.TYPE_HH_MM_SS, View.GONE);
                break;
            case PickMode.MODE_FUTURE_DATE:
                pickerView = new DateTimePicker(context, DateTimePicker.MODE_PENDING, languageType, showFuture);
                pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                break;
            case PickMode.MODE_DATE:
                pickerView = new DateTimePicker(context, showFuture);
                pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                break;
        }

        DataPickerUtils.setPickViewStyle(pickerView, option);
        return pickerView;
    }

    public static DateTimePicker buildDateTimeWheelPicker(Context context, PickOption option, boolean showFuture) {
        DateTimePicker pickerView = new DateTimePicker(context, showFuture);
        pickerView.setWheelPickerVisibility(DateTimePicker.TYPE_HH_MM_SS, View.GONE);

        DataPickerUtils.setPickViewStyle(pickerView, option);
        return pickerView;
    }

    /**
     * @param context
     * @param option
     * @return
     */
    public static DateTimePicker buildDateTimeWheelPicker(Context context, PickOption option,
                                                          long from, long to,
                                                          @PickMode.Mode int pickMode, LanguageType languageType) {

        int mode;
        DateTimePicker pickerView = null;
        switch (pickMode) {
            case PickMode.MODE_BIRTHDAY:
                mode = DateTimePicker.MODE_BIRTHDAY;
                pickerView = new DateTimePicker(context, from, to, mode, languageType);
                pickerView.setWheelPickerVisibility(DateTimePicker.TYPE_HH_MM_SS, View.GONE);

                break;
            case PickMode.MODE_FUTURE_DATE:
                mode = DateTimePicker.MODE_PENDING;
                pickerView = new DateTimePicker(context, from, to, mode, languageType);
                pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                break;
            case PickMode.MODE_PERIOD_DATE:
                mode = DateTimePicker.MODE_PERIOD;
                pickerView = new DateTimePicker(context, from, to, mode, languageType);
                pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                break;
            case PickMode.MODE_DATE:
                mode = DateTimePicker.MODE_NORMAL;
                pickerView = new DateTimePicker(context, from, to, mode, languageType);
                pickerView.setWheelPickerVisibility(option.getDateWitchVisible(), View.VISIBLE);
                break;
        }
        DataPickerUtils.setPickViewStyle(pickerView, option);
        return pickerView;
    }

    /**
     * @param context
     * @param option
     * @return
     */
    public static DateTimePicker buildDateTimeWheelPicker(Context context, PickOption option, int fromYear, int toYear, LanguageType languageType) {
        DateTimePicker pickerView = new DateTimePicker(context, fromYear, toYear, DateTimePicker.MODE_BIRTHDAY, languageType);
        pickerView.setWheelPickerVisibility(DateTimePicker.TYPE_HH_MM_SS, View.GONE);
        DataPickerUtils.setPickViewStyle(pickerView, option);
        return pickerView;
    }


    /**
     * 设置滚轮样式
     *
     * @param pickerView
     * @param option
     */
    public static void setPickViewStyle(IPickerView pickerView, PickOption option) {
        pickerView.asView().setBackgroundColor(option.getBackgroundColor());
        pickerView.asView().setPadding(option.getLeftPadding(), option.getVerPadding(), option.getRightPadding(), option.getVerPadding());

        //设置Item样式
        pickerView.setTextColor(option.getItemTextColor());
        pickerView.setVisibleItemCount(option.getVisibleItemCount());
        pickerView.setTextSize(option.getItemTextSize());
        pickerView.setItemSpace(option.getItemSpace());
        pickerView.setLineColor(option.getItemLineColor());
        pickerView.setLineWidth(option.getItemLineWidth());

        pickerView.setShadow(option.getShadowGravity(), option.getShadowFactor());
        pickerView.setScrollMoveFactor(option.getFingerMoveFactor());
        pickerView.setScrollAnimFactor(option.getFlingAnimFactor());
        pickerView.setScrollOverOffset(option.getOverScrollOffset());
    }

    /**
     * 获取底部弹出框
     *
     * @param context
     * @param pickerView
     * @return
     */
    public static BottomSheet buildBottomSheet(Context context, @Nullable PickOption option, boolean clearFlag,
                                               IPickerView pickerView) {
        BottomSheet bottomSheet;
        if (clearFlag) {
            bottomSheet = new BottomSheet(context, true);
        } else {
            bottomSheet = new BottomSheet(context);
        }
        if (option != null) {
            bottomSheet.setMiddleText(option.getMiddleTitleText());
            bottomSheet.setMiddleTextColor(option.getMiddleTitleColor());
            bottomSheet.setTitleTextSize(option.getTitleTextSize());
            bottomSheet.setLineColor(option.getLineColor());
            bottomSheet.setTitleHeight(option.getTitleHeight());
        }
        bottomSheet.setContent(pickerView.asView());
        return bottomSheet;
    }

    public static PickOption checkOption(Context context, @Nullable PickOption option) {
        if (option != null) {
            return option;
        }

        return PickOption.getPickDefaultOptionBuilder(context).build();
    }
}
