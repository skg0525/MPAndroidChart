package com.xxmassdeveloper.mpchartexample.customrendererandformater;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

public class BarChartAxisArrayListValueFormatter implements IAxisValueFormatter
{

    private ArrayList<String> mFormat;

    public BarChartAxisArrayListValueFormatter(ArrayList list) {
        mFormat = list;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        try {

            //ADD logic here to add new line charater for text wrapping.
            return addNewLineCharacter(mFormat.get((int)value-1));
        } catch (Exception e){
            return addNewLineCharacter(mFormat.get(0));
        }

    }

    private String addNewLineCharacter(String s){
        if(s.length()>10){
            StringBuilder sb = new StringBuilder();
            String s2 = s.substring(9);
            sb.append(s.substring(0,9)).append("\n");
            if(s2.length()>10){
                sb.append(s2.substring(0,9)).append("\n").append(s2.substring(9));
            }else{
                sb.append(s2);
            }
            return sb.toString();
        }else{
            return s;
        }
    }
}
