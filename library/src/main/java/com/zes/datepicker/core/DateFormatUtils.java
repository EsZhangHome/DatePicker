package com.zes.datepicker.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 时间格式转换工具
 */
public class DateFormatUtils {
    public static SimpleDateFormat getDateFormat(LanguageType languageType, String pattern) {
        Locale locale = Locale.CHINESE;
        switch (languageType) {
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

    public static String changeTimeStampToFormatTime(LanguageType languageType, long timestamp, String format) {
        if (timestamp + "".length() < 13) {
            timestamp = timestamp * 1000;
        }
        Date date = new Date(timestamp);
        SimpleDateFormat f = getDateFormat(languageType, format);
        return f.format(date);
    }

    public static long changeFormatTimeToTimeStamp(LanguageType languageType, String time, String format) {
        SimpleDateFormat f = getDateFormat(languageType, format);
        try {
            return f.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
