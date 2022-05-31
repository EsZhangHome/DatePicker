package com.zes.datepicker;

import static com.zes.datepicker.DateTimeItem.TYPE_DAY;
import static com.zes.datepicker.DateTimeItem.TYPE_HOUR;
import static com.zes.datepicker.DateTimeItem.TYPE_MINUTE;
import static com.zes.datepicker.DateTimeItem.TYPE_MONTH;
import static com.zes.datepicker.DateTimeItem.TYPE_SECOND;
import static com.zes.datepicker.DateTimeItem.TYPE_YEAR;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.zes.datepicker.core.AbstractWheelPicker;
import com.zes.datepicker.core.LanguageType;
import com.zes.datepicker.core.OnWheelPickedListener;
import com.zes.datepicker.widget.IPickerView;
import com.zes.datepicker.widget.TextWheelPicker;
import com.zes.datepicker.widget.TextWheelPickerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DateTimePicker extends LinearLayout implements OnWheelPickedListener<String>, IPickerView, IDateTimePicker {
    //所有
    public final static int TYPE_ALL = TYPE_YEAR | TYPE_MONTH | TYPE_DAY |
            TYPE_HOUR | TYPE_MINUTE | TYPE_SECOND;
    //年月日
    public final static int TYPE_YY_MM_DD = TYPE_YEAR | TYPE_MONTH | TYPE_DAY;
    //时分秒
    public final static int TYPE_HH_MM_SS = TYPE_HOUR | TYPE_SECOND | TYPE_MINUTE;

    /**
     * 一天时长的毫秒数
     */
    public final static long ONE_DAY_TIME_LENGTH = 24 * 60 * 60 * 1000L;
    /**
     * 一年时长的毫秒数（默认按365天算，不考虑闰年）
     */
    public final static long ONE_YEAR_TIME_LENGTH = 365 * ONE_DAY_TIME_LENGTH;
    /**
     * 未来模式，时间下限为当前时间, 默认是100年后
     */
    public final static int MODE_PENDING = 0;
    /**
     * 生日模式，时间上限为当前时间，默认100年
     */
    public final static int MODE_BIRTHDAY = 1;
    /**
     * 正常模式，前后100年
     */
    public final static int MODE_NORMAL = 2;
    /**
     * 时间段，给定开始时间和结束时间
     */
    public final static int MODE_PERIOD = 3;

    /**
     * 时间字符串文本 '年' '月' '日' '时' '分' '秒'
     */
    private String mYearStr;
    private String mMonthStr;
    private String mDayStr;
    private String mHourStr;
    private String mMinuteStr;
    private String mSecondStr;

    private int mMode = MODE_PERIOD;

    private List<DateTimeItem> mDateTimeItems;
    private HashMap<Integer, List<String>> mData =
            new HashMap<Integer, List<String>>(6);

    /**
     * 当前时间的年月日时分秒
     */
    private int mCurrYear;
    private int mCurrMonth;
    private int mCurrDay;
    private int mCurrHour;
    private int mCurrMinute;
    private int mCurrSecond;

    /**
     * 被选中的的年月日时分秒
     */
    private int mSelectedYear;
    private int mSelectedMonth;
    private int mSelectedDay;
    private int mSelectedHour;
    private int mSelectedMinute;
    private int mSelectedSecond;

    /**
     * 开始-结束的时间段
     */
    private long mFrom = 0;
    private long mTo = 0;


    /**
     * 时间段开始年月日时分秒
     */
    private int mFromYear;
    private int mFromMonth;
    private int mFromDay;
    private int mFromHour;
    private int mFromMinute;
    private int mFromSecond;

    /**
     * 时间段结束年月日时分秒
     */
    private int mToYear;
    private int mToMonth;
    private int mToDay;
    private int mToHour;
    private int mToMinute;
    private int mToSecond;

    //语言
    private LanguageType mLanguageType;

    //是否显示未来的时间
    private boolean showFuture = false;

    //============================= constructor ==============================
    public DateTimePicker(Context context) {
        super(context);
        init();
    }

    public DateTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DateTimePicker(Context context, boolean showFuture) {
        super(context);
        this.showFuture = showFuture;
        init(MODE_BIRTHDAY, LanguageType.ZH, showFuture);
    }


    public DateTimePicker(Context context, int mode, boolean showFuture) {
        super(context);
        this.showFuture = showFuture;
        init(mode, LanguageType.ZH, showFuture);
    }

    public DateTimePicker(Context context, int mode, LanguageType languageType, boolean showFuture) {
        super(context);
        this.showFuture = showFuture;
        init(mode, languageType, showFuture);
    }

    public DateTimePicker(Context context, int mode, LanguageType languageType) {
        super(context);
        init(mode, languageType, false);
    }

    public DateTimePicker(Context context, long from, long to, int mode, LanguageType languageType) {
        super(context);
        init(from, to, mode, languageType);
    }

    public DateTimePicker(Context context, int fromYear, int toYear, int mode, LanguageType languageType) {
        super(context);
        init(fromYear, toYear, mode, languageType);
    }

    public DateTimePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //============================= init ==============================
    private void init() {
        init(MODE_BIRTHDAY, LanguageType.ZH, false);
    }

    private void init(int mode, LanguageType languageType, boolean showFuture) {
        if (mode == MODE_PERIOD) {
            throw new IllegalArgumentException("from & to must be setting!!!");
        }

        long curr = System.currentTimeMillis();
        long from = curr - ONE_YEAR_TIME_LENGTH * 100 - 25 * ONE_DAY_TIME_LENGTH;
        long to = curr + ONE_YEAR_TIME_LENGTH * 100 + 25 * ONE_DAY_TIME_LENGTH;
        switch (mode) {
            case MODE_PENDING:
                from = curr;
                break;
            case MODE_BIRTHDAY:
                to = curr;
                break;
        }
        this.showFuture = showFuture;
        init(from, to, mode, languageType);
    }

    private void init(int mode, LanguageType languageType) {
        if (mode == MODE_PERIOD) {
            throw new IllegalArgumentException("from & to must be setting!!!");
        }

        long curr = System.currentTimeMillis();
        long from = curr - ONE_YEAR_TIME_LENGTH * 100 - 25 * ONE_DAY_TIME_LENGTH;
        long to = curr + ONE_YEAR_TIME_LENGTH * 100 + 25 * ONE_DAY_TIME_LENGTH;
        switch (mode) {
            case MODE_PENDING:
                from = curr;
                break;
            case MODE_BIRTHDAY:
                to = curr;
                break;
        }
        init(from, to, mode, languageType);
    }

    private void init(long from, long to, int mode, LanguageType languageType) {
        mMode = mode;
        mFrom = from;
        mTo = to;
        mLanguageType = languageType;

        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        initPickerLabelStr(languageType);
        initCurrDateTime();
        initTextWheelPicker(languageType);
        addPickers();

        initData();
    }

    private void init(int fromYear, int toYear, int mode, LanguageType languageType) {
        mFromYear = fromYear;
        mToYear = toYear;
        if (mode == MODE_PERIOD) {
            throw new IllegalArgumentException("from & to must be setting!!!");
        }

        long curr = System.currentTimeMillis();
        long from = curr - ONE_YEAR_TIME_LENGTH * 100 - 25 * ONE_DAY_TIME_LENGTH;
        long to = curr + ONE_YEAR_TIME_LENGTH * 100 + 25 * ONE_DAY_TIME_LENGTH;
        switch (mode) {
            case MODE_PENDING:
                from = curr;
                break;
            case MODE_BIRTHDAY:
                to = curr;
                break;
        }
        init(from, to, mode, languageType);
    }

    /**
     * 初始化picker 标签字符串
     * 年、月、日、时、分、秒
     */
    private void initPickerLabelStr(LanguageType languageType) {
        switch (languageType) {
            // 英文不显示
            case EN:
                mYearStr = getResources().getString(R.string._year_en);
                mMonthStr = getResources().getString(R.string._month_en);
                mDayStr = getResources().getString(R.string._day_en);
                mHourStr = getResources().getString(R.string._hour_en);
                mMinuteStr = getResources().getString(R.string._minute_en);
                mSecondStr = getResources().getString(R.string._second_en);
                break;
            case ZH:
                mYearStr = getResources().getString(R.string._year_zh);
                mMonthStr = getResources().getString(R.string._month_zh);
                mDayStr = getResources().getString(R.string._day_zh);
                mHourStr = getResources().getString(R.string._hour_zh);
                mMinuteStr = getResources().getString(R.string._minute_zh);
                mSecondStr = getResources().getString(R.string._second_zh);
                break;
            case RU:
                mYearStr = getResources().getString(R.string._year_ru);
                mMonthStr = getResources().getString(R.string._month_ru);
                mDayStr = getResources().getString(R.string._day_ru);
                mHourStr = getResources().getString(R.string._hour_ru);
                mMinuteStr = getResources().getString(R.string._minute_ru);
                mSecondStr = getResources().getString(R.string._second_ru);
                break;
            case KO:
                mYearStr = getResources().getString(R.string._year_ko);
                mMonthStr = getResources().getString(R.string._month_ko);
                mDayStr = getResources().getString(R.string._day_ko);
                mHourStr = getResources().getString(R.string._hour_ko);
                mMinuteStr = getResources().getString(R.string._minute_ko);
                mSecondStr = getResources().getString(R.string._second_ko);
                break;
            case JA:
                mYearStr = getResources().getString(R.string._year_ja);
                mMonthStr = getResources().getString(R.string._month_ja);
                mDayStr = getResources().getString(R.string._day_ja);
                mHourStr = getResources().getString(R.string._hour_ja);
                mMinuteStr = getResources().getString(R.string._minute_ja);
                mSecondStr = getResources().getString(R.string._second_ja);
                break;
            case IT:
                mYearStr = getResources().getString(R.string._year_it);
                mMonthStr = getResources().getString(R.string._month_it);
                mDayStr = getResources().getString(R.string._day_it);
                mHourStr = getResources().getString(R.string._hour_it);
                mMinuteStr = getResources().getString(R.string._minute_it);
                mSecondStr = getResources().getString(R.string._second_it);
                break;
            case DE:
                mYearStr = getResources().getString(R.string._year_de);
                mMonthStr = getResources().getString(R.string._month_de);
                mDayStr = getResources().getString(R.string._day_de);
                mHourStr = getResources().getString(R.string._hour_de);
                mMinuteStr = getResources().getString(R.string._minute_de);
                mSecondStr = getResources().getString(R.string._second_de);
                break;
            case ES:
                mYearStr = getResources().getString(R.string._year_es);
                mMonthStr = getResources().getString(R.string._month_es);
                mDayStr = getResources().getString(R.string._day_es);
                mHourStr = getResources().getString(R.string._hour_es);
                mMinuteStr = getResources().getString(R.string._minute_es);
                mSecondStr = getResources().getString(R.string._second_es);
                break;
            case FR:
                mYearStr = getResources().getString(R.string._year_fr);
                mMonthStr = getResources().getString(R.string._month_fr);
                mDayStr = getResources().getString(R.string._day_fr);
                mHourStr = getResources().getString(R.string._hour_fr);
                mMinuteStr = getResources().getString(R.string._minute_fr);
                mSecondStr = getResources().getString(R.string._second_fr);
                break;
            case RO:
                mYearStr = getResources().getString(R.string._year_ro);
                mMonthStr = getResources().getString(R.string._month_ro);
                mDayStr = getResources().getString(R.string._day_ro);
                mHourStr = getResources().getString(R.string._hour_ro);
                mMinuteStr = getResources().getString(R.string._minute_ro);
                mSecondStr = getResources().getString(R.string._second_ro);
                break;
            case TR:
                mYearStr = getResources().getString(R.string._year_tr);
                mMonthStr = getResources().getString(R.string._month_tr);
                mDayStr = getResources().getString(R.string._day_tr);
                mHourStr = getResources().getString(R.string._hour_tr);
                mMinuteStr = getResources().getString(R.string._minute_tr);
                mSecondStr = getResources().getString(R.string._second_tr);
                break;
            case UK:
                mYearStr = getResources().getString(R.string._year_uk);
                mMonthStr = getResources().getString(R.string._month_uk);
                mDayStr = getResources().getString(R.string._day_uk);
                mHourStr = getResources().getString(R.string._hour_uk);
                mMinuteStr = getResources().getString(R.string._minute_uk);
                mSecondStr = getResources().getString(R.string._second_uk);
                break;
            case PT:
                mYearStr = getResources().getString(R.string._year_pt);
                mMonthStr = getResources().getString(R.string._year_pt);
                mDayStr = getResources().getString(R.string._year_pt);
                mHourStr = getResources().getString(R.string._year_pt);
                mMinuteStr = getResources().getString(R.string._year_pt);
                mSecondStr = getResources().getString(R.string._year_pt);
                break;
            case VI:
                mYearStr = getResources().getString(R.string._year_vi);
                mMonthStr = getResources().getString(R.string._month_vi);
                mDayStr = getResources().getString(R.string._day_vi);
                mHourStr = getResources().getString(R.string._hour_vi);
                mMinuteStr = getResources().getString(R.string._minute_vi);
                mSecondStr = getResources().getString(R.string._second_vi);
                break;
            default:
                mYearStr = getResources().getString(R.string._year);
                mMonthStr = getResources().getString(R.string._month);
                mDayStr = getResources().getString(R.string._day);
                mHourStr = getResources().getString(R.string._hour);
                mMinuteStr = getResources().getString(R.string._minute);
                mSecondStr = getResources().getString(R.string._second);
                break;
        }
    }

    /**
     * 初始化当前日期
     * 年月日时分秒
     */
    private void initCurrDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        mCurrYear = calendar.get(Calendar.YEAR);
        mCurrMonth = calendar.get(Calendar.MONTH);
        mCurrDay = calendar.get(Calendar.DATE);
        mCurrHour = calendar.get(Calendar.HOUR_OF_DAY);
        mCurrMinute = calendar.get(Calendar.MINUTE);
        mCurrSecond = calendar.get(Calendar.SECOND);
    }

    /**
     * 初始化wheelPicker
     */
    private void initTextWheelPicker(LanguageType languageType) {
        if (mDateTimeItems == null) {
            mDateTimeItems = new ArrayList<DateTimeItem>(6);
        }

        Context context = getContext();
        DateTimeItem yearPicker = new DateTimeItem(TYPE_YEAR, mYearStr,
                new TextWheelPicker(context, TYPE_YEAR));
        DateTimeItem monthPicker = new DateTimeItem(TYPE_MONTH, mMonthStr,
                new TextWheelPicker(context, TYPE_MONTH));
        DateTimeItem dayPicker = new DateTimeItem(TYPE_DAY, mDayStr,
                new TextWheelPicker(context, TYPE_DAY));

        DateTimeItem hourPicker = new DateTimeItem(TYPE_HOUR, mHourStr,
                new TextWheelPicker(context, TYPE_HOUR));
        DateTimeItem minutePicker = new DateTimeItem(TYPE_MINUTE, mMonthStr,
                new TextWheelPicker(context, TYPE_MINUTE));
        DateTimeItem secondPicker = new DateTimeItem(TYPE_SECOND, mSecondStr,
                new TextWheelPicker(context, TYPE_SECOND));

        switch (languageType) {
            // 英文不显示
            case EN:
            case KO:
            case OTHER:
                mDateTimeItems.add(monthPicker);
                mDateTimeItems.add(dayPicker);
                mDateTimeItems.add(yearPicker);
                mDateTimeItems.add(hourPicker);
                mDateTimeItems.add(minutePicker);
                mDateTimeItems.add(secondPicker);
                break;
            case RU:
            case IT:
            case DE:
            case ES:
            case FR:
            case UK:
            case PT:
            case VI:
                mDateTimeItems.add(dayPicker);
                mDateTimeItems.add(monthPicker);
                mDateTimeItems.add(yearPicker);
                mDateTimeItems.add(hourPicker);
                mDateTimeItems.add(minutePicker);
                mDateTimeItems.add(secondPicker);
                break;
            default:// zh--中国 ja--日本 tr--土耳其
                mDateTimeItems.add(yearPicker);
                mDateTimeItems.add(monthPicker);
                mDateTimeItems.add(dayPicker);
                mDateTimeItems.add(hourPicker);
                mDateTimeItems.add(minutePicker);
                mDateTimeItems.add(secondPicker);
                break;
        }

    }

    /**
     * 将pickers添加到父布局中
     */
    private void addPickers() {
        LayoutParams llParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        llParams.weight = 1;
        for (DateTimeItem itemPicker : mDateTimeItems) {
            TextWheelPicker picker = itemPicker.getWheelPicker();
            picker.setOnWheelPickedListener(this);
            addView(picker, llParams);
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (DateTimeItem item : mDateTimeItems) {
            //给具体的wheelPicker 设置adapter
            TextWheelPicker picker = item.getWheelPicker();
            picker.setAdapter(new TextWheelPickerAdapter(new ArrayList<String>()));
            List<String> data = ((TextWheelPickerAdapter) picker.getAdapter()).getData();
            mData.put(item.getType(), data);
        }

        fillData(mData);
        notifyDataSetChanged();
    }

    /**
     * 设置那些picker 显示或隐藏
     * 1.若当前设置visibility 设置为VISIBLE,
     * wheelType匹配的picker显示，没匹配的就隐藏
     * <p>
     * 2.若当前设置visibility 设置为GONE
     * wheelType匹配的picker隐藏，没匹配的就显示
     *
     * @param wheelType  哪些picker
     * @param visibility 显示或隐藏, 不能取值为INVISIBLE
     */
    @Override
    public void setWheelPickerVisibility(int wheelType, int visibility) {
        if (visibility == INVISIBLE) {
            throw new IllegalArgumentException("INVISIBLE can't be setting!");
        }

        int antiVisibility = VISIBLE;
        if (visibility == VISIBLE) {
            antiVisibility = GONE;
        } else if (visibility == GONE) {
            antiVisibility = VISIBLE;
        }

        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            int visible = (wheelType & item.getType()) != 0 ? visibility : antiVisibility;
            picker.setVisibility(visible);
        }
    }

    /**
     * 设置默认选择日期：年月日
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    @Override
    public void setDefaultSelectedDate(int year, int month, int day) {
        List<String> years = mData.get(TYPE_YEAR);
        List<String> months = mData.get(TYPE_MONTH);
        List<String> days = mData.get(TYPE_DAY);

        if (years == null || months == null || days == null ||
                years.isEmpty() || months.isEmpty() || days.isEmpty()) {
            return;
        }

        mSelectedYear = year;
        mSelectedMonth = month;
        mSelectedDay = day;

        int yearIndex = Math.max(0, years.indexOf(year + mYearStr));
        int monthIndex = Math.max(0, months.indexOf((month + 1) + mMonthStr));
        int dayIndex = Math.max(0, days.indexOf(year + mDayStr));

        setDateItemIndex(yearIndex, monthIndex, dayIndex);
        notifyDataSetChanged();
    }

    /**
     * 设置默认选中的时间：时分秒
     *
     * @param hour   时
     * @param minute 分
     * @param second 秒
     */
    @Override
    public void setDefaultSelectedTime(int hour, int minute, int second) {
        List<String> hours = mData.get(TYPE_HOUR);
        List<String> minutes = mData.get(TYPE_MINUTE);
        List<String> seconds = mData.get(TYPE_SECOND);

        if (hours == null || minutes == null || seconds == null ||
                hours.isEmpty() || minutes.isEmpty() || seconds.isEmpty()) {
            return;
        }

        mSelectedHour = hour;
        mSelectedMinute = minute;
        mSelectedSecond = second;

        int hourIndex = Math.max(0, hours.indexOf(hour + mYearStr));
        int minuteIndex = Math.max(0, minutes.indexOf(minute + mMonthStr));
        int secondIndex = Math.max(0, seconds.indexOf(second + mDayStr));

        setTimeItemIndex(hourIndex, minuteIndex, secondIndex);
        notifyDataSetChanged();
    }

    private void setDateItemIndex(int yearIndex, int monthIndex, int dayIndex) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            int type = item.getType();
            switch (type) {
                case TYPE_YEAR:
                    picker.setCurrentItem(yearIndex);
                    break;
                case TYPE_MONTH:
                    picker.setCurrentItem(monthIndex);
                    break;
                case TYPE_DAY:
                    picker.setCurrentItem(dayIndex);
                    break;
            }
        }
    }

    private void setTimeItemIndex(int hourIndex, int minuteIndex, int secondIndex) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            int type = item.getType();
            switch (type) {
                case TYPE_HOUR:
                    picker.setCurrentItem(hourIndex);
                    break;
                case TYPE_MINUTE:
                    picker.setCurrentItem(minuteIndex);
                    break;
                case TYPE_SECOND:
                    picker.setCurrentItem(secondIndex);
                    break;
            }
        }
    }

    public void setTextSize(int textSize) {
        if (textSize < 0) {
            return;
        }

        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setTextSize(textSize);
        }
    }

    public void setTextColor(int textColor) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setTextColor(textColor);
        }
    }

    public void setLineColor(int lineColor) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setLineColor(lineColor);
        }
    }

    public void setLineWidth(int width) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setLineStorkeWidth(width);
        }
    }

    public void setItemSpace(int space) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setItemSpace(space);
        }
    }

    public void setVisibleItemCount(int itemCount) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setVisibleItemCount(itemCount);
        }
    }

    public void setItemSize(int itemWidth, int itemHeight) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setItemSize(itemWidth, itemHeight);
        }
    }

    @Override
    public void setShadow(int gravity, float factor) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setShadowGravity(gravity);
            picker.setShadowFactor(factor);
        }
    }

    @Override
    public void setScrollAnimFactor(float factor) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setFlingAnimFactor(factor);
        }
    }

    @Override
    public void setScrollMoveFactor(float factor) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setFingerMoveFactor(factor);
        }
    }

    @Override
    public void setScrollOverOffset(int offset) {
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            picker.setOverOffset(offset);
        }
    }

    @Override
    public View asView() {
        return this;
    }

    public int getDateMode() {
        return mMode;
    }

    private void updateYears(int from, int to) {
        List<String> years = mData.get(TYPE_YEAR);
        if (years == null) {
            return;
        }

        years.clear();
        int size = to - from;
        for (int i = from; i <= from + size; i++) {
            years.add(i + mYearStr);
        }
    }

    private void updateMonth(int minMonth, int maxMonth) {
        if (minMonth > maxMonth) {
            return;
        }

        List<String> months = mData.get(TYPE_MONTH);
        if (months == null) {
            return;
        }
        months.clear();

        for (int i = minMonth; i <= maxMonth; i++) {
            if (mLanguageType == LanguageType.ZH) {
                months.add((i + 1) + mMonthStr);
            } else if (mLanguageType == LanguageType.KO) {
                months.add((i + 1) + mMonthStr);
            } else if (mLanguageType == LanguageType.OTHER) {
                months.add((i + 1) + mMonthStr);
            } else {
                String date = changeOldTimeStringToNewTimeString((i + 1) + "", "MM", "MMM");
                months.add(date + mMonthStr);
            }
        }
    }

    private void updateDay(int minDay, int maxDay) {
        if (minDay > maxDay) {
            return;
        }

        List<String> days = mData.get(TYPE_DAY);
        if (days == null) {
            return;
        }
        days.clear();

        for (int i = minDay; i <= maxDay; i++) {
            days.add(i + mDayStr);
        }
    }

    private void updateHour(int minHour, int maxHour) {
        if (minHour > maxHour) {
            return;
        }

        List<String> hours = mData.get(TYPE_HOUR);
        if (hours == null) {
            return;
        }
        hours.clear();

        for (int i = minHour; i <= maxHour; i++) {
            hours.add(i + mHourStr);
        }
    }

    private void updateMinute(int minMinute, int maxMinute) {
        if (minMinute > maxMinute) {
            return;
        }

        List<String> minutes = mData.get(TYPE_MINUTE);
        if (minutes == null) {
            return;
        }
        minutes.clear();

        for (int i = minMinute; i <= maxMinute; i++) {
            minutes.add(i + mMinuteStr);
        }
    }

    private void updateSecond(int minSecond, int maxSecond) {
        if (minSecond > maxSecond) {
            return;
        }

        List<String> seconds = mData.get(TYPE_SECOND);
        if (seconds == null) {
            return;
        }
        seconds.clear();

        for (int i = minSecond; i <= maxSecond; i++) {
            seconds.add(i + mSecondStr);
        }
    }

    /**
     * 校正当前选择月份的天数
     */
    private void correctMaxDays() {
        correctDays(mSelectedMonth + 1, 1);
    }

    /**
     * 校正某月的天数，
     *
     * @param month    校正的哪一个月
     * @param startDay 当前最小开始天数
     */
    private void correctDays(int month, int startDay) {
        switch (month) {
            case 2:
                if (DataPickerUtils.isLeapYear(mSelectedYear)) {
                    updateDay(startDay, 29);
                } else {
                    updateDay(startDay, 28);
                }
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                updateDay(startDay, 31);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                updateDay(startDay, 30);
                break;
        }
    }

    /**
     * 校正某月的天数，
     *
     * @param month    校正的哪一个月
     * @param startDay 当前月最小开始天数
     * @param endDay   当前月最大结束天数
     */
    private void correctDays(int month, int startDay, int endDay) {
        switch (month) {
            case 2:
                if (DataPickerUtils.isLeapYear(mSelectedYear)) {
                    updateDay(startDay, Math.min(29, endDay));
                } else {
                    updateDay(startDay, Math.min(28, endDay));
                }
                break;
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                updateDay(startDay, Math.min(31, endDay));
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                updateDay(startDay, Math.min(30, endDay));
                break;
        }
    }

    private TextWheelPicker getPicker(int type) {
        for (DateTimeItem item : mDateTimeItems) {
            if (item.getType() == type) {
                return item.getWheelPicker();
            }
        }

        return mDateTimeItems.get(0).getWheelPicker();
    }

    @Override
    public long getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(mSelectedYear, mSelectedMonth, mSelectedDay, mSelectedHour, mSelectedMinute,
                mSelectedSecond);
        return calendar.getTimeInMillis();
    }

    public int getSelectedYear() {
        return mSelectedYear;
    }

    public int getSelectedMonth() {
        return mSelectedMonth;
    }

    public int getSelectedDay() {
        return mSelectedDay;
    }

    public int getSelectedHour() {
        return mSelectedHour;
    }

    public int getSelectedMinute() {
        return mSelectedMinute;
    }

    public int getSelectedSecond() {
        return mSelectedSecond;
    }

    /**
     * 填充数据
     *
     * @param data
     */
    private void fillData(HashMap<Integer, List<String>> data) {
        if (mFrom == mTo || mFrom > mTo) {
            throw new IllegalArgumentException("please set legal period of time");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mFrom);
        mFromYear = calendar.get(Calendar.YEAR);
        mFromMonth = 0;
        mFromDay = 1;
        mFromHour = calendar.get(Calendar.HOUR_OF_DAY);
        mFromMinute = calendar.get(Calendar.MINUTE);
        mFromSecond = calendar.get(Calendar.SECOND);

        calendar.setTimeInMillis(mTo);
        if (showFuture) {
            mToYear = calendar.get(Calendar.YEAR) + 100;
            mToMonth = 11;
            mToDay = 31;
        } else {
            mToYear = calendar.get(Calendar.YEAR);
            mToMonth = calendar.get(Calendar.MONTH);
            mToDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        mToHour = calendar.get(Calendar.HOUR_OF_DAY);
        mToMinute = calendar.get(Calendar.MINUTE);
        mToSecond = calendar.get(Calendar.SECOND);

        //默认的选择的日期时间是起始日期时间
        mSelectedYear = mFromYear;
        mSelectedMonth = mFromMonth;
        mSelectedDay = mFromDay;
        mSelectedHour = mFromHour;
        mSelectedMinute = mFromMinute;
        mSelectedSecond = mFromSecond;

        Iterator<Map.Entry<Integer, List<String>>> it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<String>> entry = it.next();
            int type = entry.getKey();
            switch (type) {
                case TYPE_YEAR:
                    //通过 onWheelSelected的年的picker，关联其他picker数据联动
                    updateYears(mFromYear, mToYear);
                    break;
//                case TYPE_MONTH:
//                    updateMonth(mFromMonth, mToMonth);
//                    break;
//                case TYPE_DAY:
//                    updateDay(mFromDay, mToDay);
//                    break;
//                case TYPE_HOUR:
//                    updateHour(fromCalendar.get(Calendar.HOUR), toCalendar.get(Calendar.HOUR));
//                    break;
//                case TYPE_MINUTE:
//                    updateMinute(fromCalendar.get(Calendar.MINUTE), toCalendar.get(Calendar.MINUTE));
//                    break;
//                case TYPE_SECOND:
//                    updateSecond(fromCalendar.get(Calendar.SECOND), toCalendar.get(Calendar.SECOND));
//                    break;
            }
        }
    }

    @Override
    public void onWheelSelected(AbstractWheelPicker wheelPicker, int index, String data, boolean touch) {
        int type = wheelPicker.getId();
        switch (type) {
            case TYPE_YEAR:
                int year = getCurrentDate(data, mYearStr);
                mSelectedYear = year > 0 ? year : mCurrYear;
                notifyMonthChange(mSelectedMonth);
                break;
            case TYPE_MONTH:
                if (mLanguageType == LanguageType.ZH) {
                    int month = getCurrentMonth(data, mMonthStr);
                    mSelectedMonth = month >= 0 ? month : mCurrMonth;
                } else if (mLanguageType == LanguageType.KO) {
                    int month = getCurrentMonth(data, mMonthStr);
                    mSelectedMonth = month >= 0 ? month : mCurrMonth;
                } else if (mLanguageType == LanguageType.OTHER) {
                    int month = getCurrentMonth(data, mMonthStr);
                    mSelectedMonth = month >= 0 ? month : mCurrMonth;
                } else {
                    String date = changeOldTimeStringToNewTimeString(data, "MMM", "MM");
                    int month = getCurrentMonth(date, mMonthStr);
                    mSelectedMonth = month >= 0 ? month : mCurrMonth;
                }
                notifyDayChange(mSelectedDay);
                break;
            case TYPE_DAY:
                int day = getCurrentDate(data, mDayStr);
                mSelectedDay = day > 0 ? day : mCurrDay;
                notifyHourChange(mSelectedHour);
                break;
            case TYPE_HOUR:
                int hour = getCurrentDate(data, mHourStr);
                mSelectedHour = hour >= 0 ? hour : mCurrHour;
                notifyMinuteChange(mSelectedMinute);
                break;
            case TYPE_MINUTE:
                int minute = getCurrentDate(data, mMinuteStr);
                mSelectedMinute = minute >= 0 ? minute : mCurrMinute;
                notifySecondChange();
                break;
            case TYPE_SECOND:
                int second = getCurrentDate(data, mSecondStr);
                mSelectedSecond = second >= 0 ? second : mCurrSecond;
                break;
        }
    }

    /**
     * 通知更新月份数据
     */
    private void notifyMonthChange(int selectedMonth) {
        boolean changed = false;
        List<String> months = mData.get(TYPE_MONTH);
        if (mSelectedYear == mFromYear) {
            updateMonth(mFromMonth, mFromYear == mToYear ? mToMonth : 11);
        } else if (mSelectedYear == mToYear) {
            updateMonth(0, mToMonth);
        } else {
            changed = months.size() != 12;
            if (changed) {
                updateMonth(0, 11);
            }
        }

        //update month index
        if (mLanguageType == LanguageType.ZH) {
            int monthIndex = Math.max(0, months.indexOf((selectedMonth + 1) + mMonthStr));
            TextWheelPicker picker = getPicker(TYPE_MONTH);
            TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
            picker.setCurrentItemWithoutReLayout(monthIndex);
            //update month
            adapter.setData(months);
        } else if (mLanguageType == LanguageType.KO) {
            int monthIndex = Math.max(0, months.indexOf((selectedMonth + 1) + mMonthStr));
            TextWheelPicker picker = getPicker(TYPE_MONTH);
            TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
            picker.setCurrentItemWithoutReLayout(monthIndex);
            //update month
            adapter.setData(months);
        } else if (mLanguageType == LanguageType.OTHER) {
            int monthIndex = Math.max(0, months.indexOf((selectedMonth + 1) + mMonthStr));
            TextWheelPicker picker = getPicker(TYPE_MONTH);
            TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
            picker.setCurrentItemWithoutReLayout(monthIndex);
            //update month
            adapter.setData(months);
        } else {
            String date = changeOldTimeStringToNewTimeString((selectedMonth + 1) + "", "MM", "MMM");
            int monthIndex = Math.max(0, months.indexOf(date + mMonthStr));
            TextWheelPicker picker = getPicker(TYPE_MONTH);
            TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
            picker.setCurrentItemWithoutReLayout(monthIndex);
            //update month
            adapter.setData(months);
        }
    }


    /**
     * 将一个日期，按照格式转换秤另外一个日期字符串
     *
     * @param time
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public String changeOldTimeStringToNewTimeString(String time, String oldFormat, String newFormat) {
        SimpleDateFormat format = getDateFormat(oldFormat);
        SimpleDateFormat format2 = getDateFormat(newFormat);
        try {
            if (time != null) {
                return format2.format(format.parse(time));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public SimpleDateFormat getDateFormat(String pattern) {
        Locale locale = Locale.CHINESE;
        switch (mLanguageType) {
            case ZH:
                locale = Locale.CHINESE;
                break;
            case EN:
                locale = Locale.ENGLISH;
                break;
            case KO:
                locale = Locale.KOREAN;
                break;
            case IT:
                locale = Locale.ITALY;
                break;
            case DE:
                locale = Locale.GERMAN;
                break;
            case ES:
                locale = new Locale("es", "ES");
                break;
            case FR:
                locale = Locale.FRENCH;
                break;
            case RU:
                locale = new Locale("RUS", "ru", "");
                break;
            case RO:
                locale = new Locale("ro", "RO");
                break;
            case JA:
                locale = new Locale("ja", "JA");
                break;
            case TR:
                locale = new Locale("tr", "TR");
                break;
            case UK:
                locale = new Locale("uk", "UK");
                break;
            case PT:
                locale = new Locale("pt", "PT");
                break;
            case VI:
                locale = new Locale("vi", "VI");
                break;
        }
        return new SimpleDateFormat(pattern, locale);
    }

    private void notifyDayChange(int selectedDay) {
        TextWheelPicker picker = getPicker(TYPE_DAY);
        if (picker.getVisibility() != VISIBLE) {
            return;
        }

        List<String> days = mData.get(TYPE_DAY);
        if (mSelectedYear == mFromYear && mSelectedMonth == mFromMonth) {
            if (mFromYear == mToYear && mFromMonth == mToMonth) {
                correctDays(mSelectedMonth + 1, mFromDay, mToDay);
            } else {
                correctDays(mSelectedMonth + 1, mFromDay);
            }
        } else if (mSelectedYear == mToYear && mSelectedMonth == mToMonth) {
            updateDay(1, mToDay);
        } else {
            correctMaxDays();
        }

        //update day index
        int dayIndex = Math.max(0, days.indexOf(selectedDay + mDayStr));
        TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
        picker.setCurrentItemWithoutReLayout(dayIndex);
        //update day
        adapter.setData(days);
    }

    private void notifyHourChange(int selectedHour) {
        TextWheelPicker picker = getPicker(TYPE_HOUR);
        if (picker.getVisibility() != VISIBLE) {
            return;
        }

        List<String> hours = mData.get(TYPE_HOUR);
        if (mSelectedYear == mFromYear && mSelectedMonth == mFromMonth && mSelectedDay == mFromDay) {
            if (mFromYear == mToYear && mFromMonth == mToMonth && mFromDay == mToDay) {
                updateHour(mFromHour, Math.min(mToHour, 23));
            } else {
                updateHour(mFromHour, 23);
            }
        } else if (mSelectedYear == mToYear && mSelectedMonth == mToMonth && mSelectedDay == mToDay) {
            updateHour(0, mToHour);
        } else {
            updateHour(0, 23);
        }

        //update hour index
        int hourIndex = Math.max(0, hours.indexOf(selectedHour + mHourStr));
        TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
        picker.setCurrentItemWithoutReLayout(hourIndex);
        //update hours
        adapter.setData(hours);
    }

    private void notifyMinuteChange(int selectedMinute) {
        TextWheelPicker picker = getPicker(TYPE_MINUTE);
        if (picker.getVisibility() != VISIBLE) {
            return;
        }
        List<String> minutes = mData.get(TYPE_MINUTE);

        if (mSelectedYear == mFromYear && mSelectedMonth == mFromMonth &&
                mSelectedDay == mFromDay && mSelectedHour == mFromHour) {
            if (mFromYear == mToYear && mFromMonth == mToMonth && mFromDay == mToDay &&
                    mFromHour == mToHour) {
                updateMinute(mFromMinute, Math.min(mToMinute, 59));
            } else {
                updateMinute(mFromMinute, 59);
            }
        } else if (mSelectedYear == mToYear && mSelectedMonth == mToMonth &&
                mSelectedDay == mToDay && mSelectedHour == mToHour) {
            updateMinute(0, mToMinute);
        } else {
            updateMinute(0, 59);
        }

        //update minute index
        int minuteIndex = Math.max(0, minutes.indexOf(selectedMinute + mMinuteStr));
        TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
        picker.setCurrentItemWithoutReLayout(minuteIndex);
        //update minute
        adapter.setData(minutes);
    }

    private void notifySecondChange() {
        TextWheelPicker picker = getPicker(TYPE_SECOND);
        if (picker.getVisibility() != VISIBLE) {
            return;
        }

        List<String> seconds = mData.get(TYPE_SECOND);

        if (mSelectedYear == mFromYear && mSelectedMonth == mFromMonth &&
                mSelectedDay == mFromDay && mSelectedHour == mFromHour &&
                mSelectedMinute == mFromMinute) {
            updateSecond(mFromSecond, 59);
        } else if (mSelectedYear == mToYear && mSelectedMonth == mToMonth &&
                mSelectedDay == mToDay && mSelectedHour == mToHour &&
                mSelectedMinute == mToMinute) {
            updateSecond(0, mToSecond);
        } else {
            updateSecond(0, 59);
        }

        TextWheelPickerAdapter adapter = (TextWheelPickerAdapter) picker.getAdapter();
        picker.setCurrentItemWithoutReLayout(0);
        //update minute
        adapter.setData(seconds);
    }

    public void notifyDataSetChanged() {
        //年月日市时分秒是联动的，所以只需要通知年的数据变化(默认年是第一个)
        for (DateTimeItem item : mDateTimeItems) {
            TextWheelPicker picker = item.getWheelPicker();
            if (item.getType() == TYPE_YEAR) {
                picker.getAdapter().notifyDataSetChanged();
                break;
            }
        }
    }


    /**
     * 获取具体的日期数字部分 如'2021年' 返回 2021
     *
     * @param data   显示的数据 xxxx年  xx月  xx日 xx时 xx分 xx秒
     * @param suffix 年 月 日 时 分 秒
     * @return 具体的年月日时分秒的数字
     */
    private static int getCurrentDate(String data, String suffix) {
        int suffixLeg = suffix == null ? 0 : suffix.length();
        String temp = data;
        return Integer.parseInt(temp.substring(0, temp.length() - suffixLeg));

    }

    /**
     * 获取具体的月份数字部分 如'10月' 返回 10
     *
     * @param data   显示的数据 xx月
     * @param suffix 月
     * @return 具体月的数字
     */
    private static int getCurrentMonth(String data, String suffix) {
        return getCurrentDate(data, suffix) - 1;
    }

}
