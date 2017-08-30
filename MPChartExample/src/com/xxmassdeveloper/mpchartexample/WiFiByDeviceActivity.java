
package com.xxmassdeveloper.mpchartexample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.xxmassdeveloper.mpchartexample.customrendererandformater.BarChartAxisArrayListValueFormatter;
import com.xxmassdeveloper.mpchartexample.customrendererandformater.BarChartXAxisFontIconRenderer;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;

public class WiFiByDeviceActivity extends DemoBase {

    private BarChart barChartDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wifibydevice);

        barChartDevices = (BarChart) findViewById(R.id.chart1);

        //PRE-STYLING of bar charts
        barChartPreDataStyling(barChartDevices);

        //SET THE DATA
        setData(barChartDevices, 20, 20);

        //POST styling
        barChartPostDataStyling(barChartDevices);

    }

    /*
    * Author: SKG
    * This method is used to style the bar before setting data
    */
    private void barChartPreDataStyling(BarChart chart) {
        chart.getDescription().setEnabled(false);
        //chart.getLegend().setEnabled(false);
        LegendEntry l = new LegendEntry("", Legend.LegendForm.NONE, Float.NaN, Float.NaN, null, Color.BLACK);
        LegendEntry[] array = new LegendEntry[1];
        array[0] = l;
        chart.getLegend().setCustom(array);

        // if more than 80 entries are displayed in the chart, no values will be
        chart.setMaxVisibleValueCount(80);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);
        chart.setDoubleTapToZoomEnabled(false);

        chart.setDrawBarShadow(false);
        //mChart.setDescription(new Description());
        //mChart.setBackgroundColor(Color.rgb(0, 0, 0));//Set as a black
        chart.setDrawGridBackground(false);//set this to true to draw the grid background, false if not

    }

    /*
    * Author: SKG
    * This method is used to style the bar after setting data
    */
    private void barChartPostDataStyling(BarChart chart) {

        int visibleGraphCount = 5;

        // below is simply styling decisions on code that I have)
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        //this is the space between x-axis and bars
        chart.getAxisLeft().setAxisMinimum(-5.0f);
        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        // xAxis.setDrawLabels(false);
        xAxis.setAxisLineColor(Color.GRAY);
        xAxis.setAxisLineWidth(1.0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        //set the typrface for text
        // xAxis.setTypeface(mTfLight);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        ArrayList lables = new ArrayList();
        lables.add("abcdefghijklmnopqrstuvwxyz");
        lables.add("device2");
        lables.add("device3");
        lables.add("device4");
        lables.add("device5");

        IAxisValueFormatter xAxisFormatter = new BarChartAxisArrayListValueFormatter(lables);

        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1.0f); // only intervals of 1 day
        xAxis.setLabelCount(visibleGraphCount);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setXOffset(-30f);
        xAxis.setmMultiLineLabel(true);

        // now modify viewport
        chart.setVisibleXRangeMaximum(visibleGraphCount); // allow 5 values to be displayed at once on the x-axis, not more
        // mChart.moveViewToX(0); // set the left edge of the chart to x-index 0
        // moveViewToX(...) also calls invalidate()

        //mChart.fitScreen();
        // add a nice and smooth animation
        chart.animateY(1500);
    }

    private void setData(BarChart chart, int count, float range) {

        int start = 0;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);

            yVals1.add(new BarEntry(i, val));

        }

        BarDataSet set1;

        set1 = new BarDataSet(yVals1, "");
        set1.setDrawIcons(false);
        set1.setColor(Color.BLUE);
        set1.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setBarWidth(0.6f);

        chart.setData(data);
        //mChart.setFitBars(true);

        //modify renderer to add icons.
        ArrayList<String> imageList = new ArrayList<>();
        imageList.add("\ue938");
        imageList.add("\ue938");
        imageList.add("\ue938");
        imageList.add("\ue938");
        imageList.add("\ue938");
        chart.setScaleEnabled(false);
        chart.setRenderer(new BarChartXAxisFontIconRenderer(chart, chart.getAnimator(), chart.getViewPortHandler(), imageList, getApplicationContext()));
        chart.setExtraOffsets(0, 0, 0, 30);
        //mChart.setFitBars(true);


        chart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {

                for (IDataSet set : barChartDevices.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                barChartDevices.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {

                if (barChartDevices.getData() != null) {
                    barChartDevices.getData().setHighlightEnabled(!barChartDevices.getData().isHighlightEnabled());
                    barChartDevices.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (barChartDevices.isPinchZoomEnabled())
                    barChartDevices.setPinchZoom(false);
                else
                    barChartDevices.setPinchZoom(true);

                barChartDevices.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                barChartDevices.setAutoScaleMinMaxEnabled(!barChartDevices.isAutoScaleMinMaxEnabled());
                barChartDevices.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : barChartDevices.getData().getDataSets())
                    ((BarDataSet) set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                barChartDevices.invalidate();
                break;
            }
            case R.id.animateX: {
                barChartDevices.animateX(3000);
                break;
            }
            case R.id.animateY: {
                barChartDevices.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                barChartDevices.animateXY(3000, 3000);
                break;
            }
            case R.id.actionSave: {
                if (barChartDevices.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }
        }
        return true;
    }
}
