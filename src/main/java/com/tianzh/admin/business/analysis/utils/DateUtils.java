package com.tianzh.admin.business.analysis.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pig on 2015-06-10.
 */
public class DateUtils {

    public static String specifyDate(int ago){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - ago);
            return format.format(calendar.getTime());
    }

    public static Date formatStr(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(date);
    }

    public static String getFirstDay(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();

        calendar.setTime(theDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return df.format(calendar.getTime());
    }


    public static void main(String[] args){
        System.out.println(getFirstDay());
    }

    public static String date2Str(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        return format.format(date);
    }
}
