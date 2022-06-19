package com.zes.datepickerdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zes.datepicker.DataPicker;
import com.zes.datepicker.IDateTimePicker;
import com.zes.datepicker.OnDataPickListener;
import com.zes.datepicker.OnDatePickListener;
import com.zes.datepicker.PickMode;
import com.zes.datepicker.core.LanguageType;
import com.zes.datepicker.option.PickOption;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PickerActivity extends Activity {
    public static final String TIME_YYYY_MM_DD = "yyyy-MM-dd";

    private Date mInitBirthday = new Date();

    private String mInitData = null;

    private Context mContext;

    private PickOption.Builder getPickDefaultOptionBuilder(Context context) {
        return PickOption.getPickDefaultOptionBuilder(context)
                .setMiddleTitleColor(0xFF333333)
                .setLineColor(0XFFF1F1F1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker_new);
        mContext = this;

        //选择生日
        findViewById(R.id.picker_future_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("日期选择器器（包含未来时间）")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, option, true,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_zh_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("中文日期选择器")
                        .build();
                DataPicker.pickDate(mContext, "19931223", PickMode.MODE_BIRTHDAY, false, option,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        //选择生日
        findViewById(R.id.picker_en_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("英文日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.EN,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_start_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("开始时间+结束时间日期选择器")
                        .build();
                DataPicker.pickDate(mContext, "20220619", "20200619", "20221231", PickMode.MODE_BIRTHDAY, false, option, LanguageType.KO,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("开始时间+结束时间日期选择器")
                        .build();
                DataPicker.pickDate(mContext, "20220618", "20200619", PickMode.MODE_BIRTHDAY, false, option, LanguageType.KO,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_ru_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("俄语日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.RU,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_ko_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("韩文日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.KO,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_ja_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("小日子过的不错日期时间器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.JA,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_it_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("意大利日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.IT,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_de_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("德国日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.DE,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_es_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("西班牙日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.ES,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_fr_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("法国日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.FR,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_ro_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("罗马尼亚日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.RO,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_tr_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("土耳其日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.TR,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_uk_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("英国日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.UK,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_vi_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("越南日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.VI,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_pt_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("葡萄牙日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.PT,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_other_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("其他日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.OTHER,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_other_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("其他日期选择器")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.OTHER,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_long_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleText("测试一个长标题文案的日期选择器试试显示布局是什么样子的因为设置了最小的高度，不知道超过最小高度后啥样子")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择生日
        findViewById(R.id.picker_size_color_birthday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setLeftPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setRightPadding(mContext.getResources().getDimensionPixelSize(R.dimen.px150))
                        .setMiddleTitleColor(0XFFFF0000)
                        .setTitleTextSize(28)
                        .setMiddleTitleText("修改标题颜色+字体大小")
                        .build();
                DataPicker.pickDate(mContext, mInitBirthday, PickMode.MODE_BIRTHDAY, option, LanguageType.ZH,
                        new OnDatePickListener() {
                            @Override
                            public void onDatePicked(IDateTimePicker dateTimePicker) {
                                mInitBirthday.setTime(dateTimePicker.getTime());
                                Toast.makeText(mContext, formatDate(dateTimePicker.getTime(), TIME_YYYY_MM_DD),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //选择单个文本
        findViewById(R.id.picker_sex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickOption option = getPickDefaultOptionBuilder(mContext)
                        .setMiddleTitleText("性别(修改每个选项的颜色+分割线颜色)")
                        .setItemTextColor(0XFFFF0000)
                        .setItemLineColor(0xFF00FF00)
                        .setItemTextSize(mContext.getResources().getDimensionPixelSize(R.dimen.font_40px))
                        .setItemSpace(mContext.getResources().getDimensionPixelSize(R.dimen.px36))
                        .build();
                DataPicker.pickData(PickerActivity.this, mInitData, getSex(1), option, new OnDataPickListener<String>() {
                    @Override
                    public void onDataPicked(int index, String val, String data) {
                        mInitData = data;
                        Toast.makeText(PickerActivity.this, val, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private List<String> getSex(int c) {
        List<String> data = new ArrayList<>();
        data.add("男");
        data.add("女");
        return data;
    }

    /**
     * 格式化时间
     *
     * @param date   需要被处理的日期,距离1970的long
     * @param format 最终返回的日期字符串的格式串
     * @return
     */
    public static String formatDate(long date, String format) {
        DateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
