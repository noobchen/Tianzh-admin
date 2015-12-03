package com.tianzh.admin.business.analysis.utils;

import com.tianzh.admin.business.analysis.model.ThPayProvince;

import java.util.List;

/**
 * Created by pig on 2015-09-15.
 */
public class ProvinceUtils {

    public static String getProvinceStr(List<ThPayProvince> provinces) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ThPayProvince province : provinces) {
            stringBuilder.append(province.getProvince()).append(",");
        }

        return stringBuilder.toString();
    }
}
