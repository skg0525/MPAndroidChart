package com.xxmassdeveloper.mpchartexample.custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class MyAxisArrayListValueFormatter implements IAxisValueFormatter
{

    private ArrayList<String> mFormat;

    public MyAxisArrayListValueFormatter(ArrayList list) {
        mFormat = list;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        try {
            return mFormat.get((int)value-1);
        } catch (Exception e){
            return mFormat.get(0);
        }

    }
}
