
package com.xxmassdeveloper.mpchartexample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xxmassdeveloper.mpchartexample.custom.DayAxisValueFormatter;
import com.xxmassdeveloper.mpchartexample.custom.MyAxisArrayListValueFormatter;
import com.xxmassdeveloper.mpchartexample.custom.MyAxisValueFormatter;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.List;

public class WiFiByDeviceActivity extends DemoBase  {

    private BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wifibydevice);


        mChart = (BarChart) findViewById(R.id.chart1);

        mChart.getDescription().setEnabled(false);
        mChart.getLegend().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDoubleTapToZoomEnabled(false);

        mChart.setDrawBarShadow(false);
        //mChart.setDescription(new Description());
        //mChart.setBackgroundColor(Color.rgb(0, 0, 0));//Set as a black
        mChart.setDrawGridBackground(false);//set this to true to draw the grid background, false if not


        setData(12,50);
        //initialize();
        //init2();


        // below is simply styling decisions on code that I have)
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getAxisLeft().setAxisMinimum(-1.5f);
        mChart.getAxisRight().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        // xAxis.setDrawLabels(false);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setAxisLineWidth(1.0f);
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTextSize(15f);
        xAxis.setTypeface(mTfLight);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        ArrayList lables = new ArrayList();
        lables.add("device1");
        lables.add("device2");
        lables.add("device3");
        lables.add("device4");
        lables.add("device5");

        IAxisValueFormatter xAxisFormatter = new MyAxisArrayListValueFormatter(lables);

        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1.0f); // only intervals of 1 day
        xAxis.setLabelCount(4);
        xAxis.setValueFormatter(xAxisFormatter);

        // now modify viewport
        mChart.setVisibleXRangeMaximum(4); // allow 5 values to be displayed at once on the x-axis, not more
        // mChart.moveViewToX(0); // set the left edge of the chart to x-index 0
// moveViewToX(...) also calls invalidate()

        //mChart.fitScreen();
        // add a nice and smooth animation
        mChart.animateY(1500);

    }

    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = (int) start; i < start + count + 1; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);

            if (Math.random() * 100 < 25) {
                yVals1.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                yVals1.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The");
            set1.setDrawIcons(false);
            set1.setColors(Color.BLUE);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setBarWidth(0.7f);

            mChart.setData(data);
            //mChart.setFitBars(true);
        }

        mChart.invalidate();
    }
    private void initialize(){
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        int count = 25;
        for (int i = 0; i < count + 1; i++) {
            float mult = (count + 1);
            float val = (float) (Math.random() * mult) + mult / 3;
            yVals1.add(new BarEntry(i, val));
        }

        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Data Set");
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            set1.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            mChart.setData(data);
            mChart.setFitBars(true);
        }

        mChart.invalidate();
    }


    private void init2(){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry (1, 5));
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        entries2.add(new BarEntry (3, 2));
        ArrayList<BarEntry> entries3 = new ArrayList<>();
        entries3.add(new BarEntry (5, 7));
        ArrayList<BarEntry> entries4 = new ArrayList<>();
        entries4.add(new BarEntry (7, 7));
        ArrayList<BarEntry> entries5 = new ArrayList<>();
        entries5.add(new BarEntry (9, 1));
        List<IBarDataSet> bars = new ArrayList<IBarDataSet>();
        BarDataSet dataset = new BarDataSet(entries, "First");
        dataset.setColor(Color.RED);
        dataset.setDrawValues(false);
        bars.add(dataset);
        BarDataSet dataset2 = new BarDataSet(entries2, "Second");
        dataset2.setColor(Color.BLUE);
        dataset2.setDrawValues(false);
        bars.add(dataset2);
        BarDataSet dataset3 = new BarDataSet(entries3, "Third");
        dataset3.setColor(Color.GREEN);
        dataset3.setDrawValues(false);
        bars.add(dataset3);
        BarDataSet dataset4 = new BarDataSet(entries4, "Fourth");
        dataset4.setColor(Color.GRAY);
        dataset4.setDrawValues(false);
        bars.add(dataset4);
        BarDataSet dataset5 = new BarDataSet(entries5, "Fifth");
        dataset5.setColor(Color.BLACK);
        dataset5.setDrawValues(false);
        bars.add(dataset5);
        BarData data = new BarData(bars);
        mChart.setData(data);

        // below is simply styling decisions on code that I have)
        YAxis left = mChart.getAxisLeft();
        left.setDrawLabels(false);
        mChart.getAxisRight().setEnabled(false);
        XAxis bottomAxis = mChart.getXAxis();
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
       // bottomAxis.setDrawLabels(false);
        bottomAxis.setDrawGridLines(false);


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

                for (IDataSet set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {

                if(mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                mChart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : mChart.getData().getDataSets())
                    ((BarDataSet)set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(3000, 3000);
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
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
