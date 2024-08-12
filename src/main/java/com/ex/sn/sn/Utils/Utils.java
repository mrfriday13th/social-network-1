package com.ex.sn.sn.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class Utils {
    public static <T> T getLast(List<T> list) {
        return list != null && !list.isEmpty() ? list.get(list.size() - 1) : null;
    }



    public static Date StringToDate(String s) {

        Date result = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = dateFormat.parse(s);
        }

        catch (ParseException e) {

        }
        return result;
    }
}


