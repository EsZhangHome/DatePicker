package com.zes.datepicker;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zes.datepicker.core.DateFormatUtils;
import com.zes.datepicker.core.LanguageType;
import com.zes.datepicker.core.WheelPickerUtil;
import com.zes.datepicker.option.PickOption;
import com.zes.datepicker.widget.IPickerView;
import com.zes.datepicker.widget.TextWheelPickerAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataPicker {
    /**
     * 获取日期
     *
     * @param context
     * @param initDate 初始化时选择的日期
     * @param mode     获取哪一种数据
     * @param option
     * @param listener
     */
    public static void pickDate(Context context, @Nullable Date initDate, int mode,
                                @Nullable PickOption option,
                                final OnDatePickListener listener) {
        pickDate(context, initDate, mode, option, LanguageType.ZH, false, listener);
    }

    public static void pickDate(Context context, @Nullable Date initDate,
                                @Nullable PickOption option, boolean showFuture,
                                final OnDatePickListener listener) {
        pickDate(context, initDate, PickMode.MODE_BIRTHDAY, option, LanguageType.ZH, showFuture, listener);
    }

    public static void pickDate(Context context, @Nullable Date initDate,
                                @Nullable PickOption option,
                                final OnDatePickListener listener) {
        pickDate(context, initDate, PickMode.MODE_BIRTHDAY, option, LanguageType.ZH, false, listener);
    }

    public static void pickDate(Context context, @Nullable Date initDate, int mode,
                                @Nullable PickOption option, boolean showFuture,
                                final OnDatePickListener listener) {
        pickDate(context, initDate, mode, option, LanguageType.ZH, showFuture, listener);
    }

    public static void pickDate(Context context, @Nullable Date initDate, int mode,
                                @Nullable PickOption option, LanguageType languageType,
                                final OnDatePickListener listener) {
        pickDate(context, initDate, mode, option, languageType, false, listener);
    }

    public static void pickDate(Context context, @Nullable Date initDate, int mode,
                                @Nullable PickOption option, LanguageType languageType, boolean showFuture,
                                final OnDatePickListener listener) {
        option = DataPickerUtils.checkOption(context, option);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate != null ? initDate : new Date());
        final DateTimePicker picker = DataPickerUtils.buildDateTimeWheelPicker(context, option, mode, languageType, showFuture);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        if (mode != PickMode.MODE_BIRTHDAY) {
            picker.setDefaultSelectedTime(calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        }

        showPicker(context, option, false, picker, listener);
    }

    public static void pickDate(Context context, @Nullable String dateStr, int mode,
                                @Nullable PickOption option, LanguageType languageType, boolean showFuture,
                                final OnDatePickListener listener) {
        String year;
        String month;
        String day;
        if (dateStr == null || "null".equals(dateStr) || "".equals(dateStr)) {
            year = "1987";
            month = "1";
            day = "1";
        } else {
            long timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(languageType, dateStr, "yyyyMMdd");
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(languageType, dateStr, "yyyy-MM-dd");
            }
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(languageType, dateStr, "yyyy年MM月dd日");
            }

            year = DateFormatUtils.changeTimeStampToFormatTime(languageType, timestamp, "yyyy");
            month = DateFormatUtils.changeTimeStampToFormatTime(languageType, timestamp, "MM");
            day = DateFormatUtils.changeTimeStampToFormatTime(languageType, timestamp, "dd");
        }
        if (Integer.parseInt(month) <= 0) {
            month = "0";
        } else {
            month = (Integer.parseInt(month) - 1) + "";
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        final DateTimePicker picker = DataPickerUtils.buildDateTimeWheelPicker(context, option, mode, languageType, showFuture);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        if (mode != PickMode.MODE_BIRTHDAY) {
            picker.setDefaultSelectedTime(calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        }

        showPicker(context, option, false, picker, listener);
    }

    public static void pickDate(Context context, @Nullable String dateStr, int mode, boolean clearFlag,
                                @Nullable PickOption option, LanguageType languageType, boolean showFuture,
                                final OnDatePickListener listener) {
        String year;
        String month;
        String day;
        if (dateStr == null || "null".equals(dateStr) || "".equals(dateStr)) {
            year = "1987";
            month = "1";
            day = "1";
        } else {
            long timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(languageType, dateStr, "yyyyMMdd");
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(languageType, dateStr, "yyyy-MM-dd");
            }
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(languageType, dateStr, "yyyy年MM月dd日");
            }

            year = DateFormatUtils.changeTimeStampToFormatTime(languageType, timestamp, "yyyy");
            month = DateFormatUtils.changeTimeStampToFormatTime(languageType, timestamp, "MM");
            day = DateFormatUtils.changeTimeStampToFormatTime(languageType, timestamp, "dd");
        }
        if (Integer.parseInt(month) <= 0) {
            month = "0";
        } else {
            month = (Integer.parseInt(month) - 1) + "";
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        final DateTimePicker picker = DataPickerUtils.buildDateTimeWheelPicker(context, option, mode, languageType, showFuture);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        if (mode != PickMode.MODE_BIRTHDAY) {
            picker.setDefaultSelectedTime(calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        }

        showPicker(context, option, clearFlag, picker, listener);
    }

    public static void pickDate(Context context, @Nullable String dateStr, boolean clearFlag, int mode,
                                @Nullable PickOption option, boolean showFuture,
                                final OnDatePickListener listener) {
        String year;
        String month;
        String day;
        if (dateStr == null || "null".equals(dateStr) || "".equals(dateStr)) {
            year = "1987";
            month = "1";
            day = "1";
        } else {
            long timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyyMMdd");
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyy-MM-dd");
            }
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyy年MM月dd日");
            }

            year = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "yyyy");
            month = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "MM");
            day = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "dd");
        }

        if (Integer.parseInt(month) <= 0) {
            month = "0";
        } else {
            month = (Integer.parseInt(month) - 1) + "";
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        final DateTimePicker picker = DataPickerUtils.buildDateTimeWheelPicker(context, option, mode, LanguageType.ZH, showFuture);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        if (mode != PickMode.MODE_BIRTHDAY) {
            picker.setDefaultSelectedTime(calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        }

        getaVoid(context, clearFlag, option, listener, picker);
    }

    public static void pickDate(Context context, @Nullable String dateStr, int mode,
                                @Nullable PickOption option, boolean showFuture,
                                final OnDatePickListener listener) {
        String year;
        String month;
        String day;
        if (dateStr == null || "null".equals(dateStr) || "".equals(dateStr)) {
            year = "1987";
            month = "1";
            day = "1";
        } else {
            long timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyyMMdd");
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyy-MM-dd");
            }
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyy年MM月dd日");
            }

            year = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "yyyy");
            month = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "MM");
            day = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "dd");
        }

        if (Integer.parseInt(month) <= 0) {
            month = "0";
        } else {
            month = (Integer.parseInt(month) - 1) + "";
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        final DateTimePicker picker = DataPickerUtils.buildDateTimeWheelPicker(context, option, mode, LanguageType.ZH, showFuture);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        if (mode != PickMode.MODE_BIRTHDAY) {
            picker.setDefaultSelectedTime(calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        }

        getaVoid(context, false, option, listener, picker);
    }


    private static void getaVoid(Context context, boolean clearFlag, @Nullable PickOption option, OnDatePickListener listener, DateTimePicker picker) {
        showPicker(context, option, clearFlag, picker, listener);
    }

    public static void pickDate(Context context, @Nullable String dateStr, int mode,
                                @Nullable PickOption option,
                                final OnDatePickListener listener) {
        String year;
        String month;
        String day;
        if (dateStr == null || "null".equals(dateStr) || "".equals(dateStr)) {
            year = "1987";
            month = "0";
            day = "1";
        } else {
            long timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyyMMdd");
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyy-MM-dd");
            }
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyy年MM月dd日");
            }

            year = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "yyyy");
            month = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "MM");
            day = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "dd");
        }
        if (Integer.parseInt(month) <= 0) {
            month = "0";
        } else {
            month = (Integer.parseInt(month) - 1) + "";
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        final DateTimePicker picker = DataPickerUtils.buildDateTimeWheelPicker(context, option, mode, LanguageType.ZH, false);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        if (mode != PickMode.MODE_BIRTHDAY) {
            picker.setDefaultSelectedTime(calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        }

        showPicker(context, option, false, picker, listener);
    }

    public static void pickDate(Context context, @Nullable String dateStr, int mode, boolean clearFlag,
                                @Nullable PickOption option,
                                final OnDatePickListener listener) {
        String year;
        String month;
        String day;
        if (dateStr == null || "null".equals(dateStr) || "".equals(dateStr)) {
            year = "1987";
            month = "0";
            day = "1";
        } else {
            long timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyyMMdd");
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyy-MM-dd");
            }
            if (timestamp == 0) {
                timestamp = DateFormatUtils.changeFormatTimeToTimeStamp(LanguageType.ZH, dateStr, "yyyy年MM月dd日");
            }

            year = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "yyyy");
            month = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "MM");
            day = DateFormatUtils.changeTimeStampToFormatTime(LanguageType.ZH, timestamp, "dd");
        }
        if (Integer.parseInt(month) <= 0) {
            month = "0";
        } else {
            month = (Integer.parseInt(month) - 1) + "";
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        final DateTimePicker picker = DataPickerUtils.buildDateTimeWheelPicker(context, option, mode, LanguageType.ZH, false);
        picker.setDefaultSelectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        if (mode != PickMode.MODE_BIRTHDAY) {
            picker.setDefaultSelectedTime(calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        }

        showPicker(context, option, clearFlag, picker, listener);
    }


    /**
     * 显示 Time Picker
     *
     * @param context
     * @param option
     * @param picker
     * @param listener
     */
    private static void showPicker(Context context, @NonNull PickOption option, boolean clearFlag,
                                   final IPickerView picker,
                                   final OnDatePickListener listener) {
        BottomSheet bottomSheet = DataPickerUtils.buildBottomSheet(context, option, clearFlag, picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDatePicked((IDateTimePicker) picker);
                }
            }
        });
    }

    /**
     * 获取单行数据
     *
     * @param context
     * @param initData
     * @param srcData
     * @param listener
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable T initData, @NonNull final List<T> srcData,
                                    @Nullable PickOption option, final OnDataPickListener listener) {
        option = DataPickerUtils.checkOption(context, option);
        final SingleTextWheelPicker picker = new SingleTextWheelPicker(context);
        DataPickerUtils.setPickViewStyle(picker, option);

        TextWheelPickerAdapter adapter = new TextWheelPickerAdapter(srcData);
        picker.setAdapter(adapter);
        int index = WheelPickerUtil.indexOf(initData, srcData);
        picker.setCurrentItem(index < 0 ? 0 : index);

        BottomSheet bottomSheet = DataPickerUtils.buildBottomSheet(context, option, false, picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int index = picker.getPickedIndex();
                    listener.onDataPicked(index, picker.getPickedData(), srcData.get(index));
                }
            }
        });
    }

    /**
     * 多行数据选择
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex,
                                    @NonNull List<List<?>> srcData, @Nullable PickOption option,
                                    final OnMultiDataPickListener listener) {
        pickData(context, initIndex, srcData, option, false, listener, null);
    }

    /**
     * 多行数据选择
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex,
                                    @NonNull List<List<?>> srcData, @Nullable PickOption option,
                                    final OnMultiDataPickListener listener,
                                    final OnCascadeWheelListener cascadeListener) {
        pickData(context, initIndex, srcData, option, false, listener, cascadeListener);
    }

    /**
     * 多行数据选择
     *
     * @param context
     * @param initIndex
     * @param srcData
     * @param listener
     * @param <T>
     */
    public static <T> void pickData(Context context, @Nullable List<Integer> initIndex,
                                    @NonNull List<List<?>> srcData, @Nullable PickOption option,
                                    boolean wrapper, final OnMultiDataPickListener listener,
                                    final OnCascadeWheelListener cascadeListener) {
        option = DataPickerUtils.checkOption(context, option);
        //List<WheelPickerData> pickerData = WheelPickerData.wrapper(initData, srcData);
        //WheelPickerData.disScrollable(0, pickerData);
        //WheelPickerData.placeHold(1, pickerData);
        final MultipleTextWheelPicker picker = wrapper ?
                new MultipleTextWheelPicker(context, WheelPickerData.wrapper(initIndex, srcData)) :
                new MultipleTextWheelPicker(context, initIndex, srcData);

        picker.setOnCascadeWheelListener(cascadeListener);
        DataPickerUtils.setPickViewStyle(picker, option);

        BottomSheet bottomSheet = DataPickerUtils.buildBottomSheet(context, option, false, picker);
        bottomSheet.show();
        bottomSheet.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    List<String> pickedVal = picker.getPickedVal();
                    List<Integer> pickedIndex = picker.getPickedIndex();
                    List<T> pickedData = picker.getPickedData();
                    listener.onDataPicked(pickedIndex, pickedVal, pickedData);
                }
            }
        });
    }
}
