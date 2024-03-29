package com.zes.datepicker;

import com.zes.datepicker.widget.PickString;

import java.util.List;

public class AdministrativeMap {
    public int year;
    public List<Province> provinces;

    /**
     * 省
     */
    public static class Province implements PickString {
        public String name;
        public String code;
        public List<City> city;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }

    /**
     * 市
     */
    public static class City implements PickString{
        public String name;
        public String code;
        public List<Area> areas;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }

    /**
     * 区县
     */
    public static class Area implements PickString{
        public String name;
        public String code;
        public List<Country> countries;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }

    /**
     * 乡镇
     */
    public static class Country  implements PickString{
        public String name;
        public String code;

        @Override
        public String pickDisplayName() {
            return name;
        }
    }
}
