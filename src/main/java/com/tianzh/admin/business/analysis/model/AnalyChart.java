package com.tianzh.admin.business.analysis.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pig on 2015-10-08.
 */
public class AnalyChart {
    private ArrayList<HashMap<String,Object>> series;
    private String[] categories;

    public ArrayList<HashMap<String, Object>> getSeries() {
        return series;
    }

    public void setSeries(ArrayList<HashMap<String, Object>> series) {
        this.series = series;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }
}
