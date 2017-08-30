package com.xxmassdeveloper.mpchartexample.customrendererandformater;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skg on 8/30/17.
 */

public class BarChartXAxisBitmapRenderer extends BarChartRenderer {

    private Context context;
    private ArrayList<Bitmap> imageList;

    public BarChartXAxisBitmapRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler, ArrayList<Bitmap> imageList, Context context, boolean mRoundedBars) {
        super(chart, animator, viewPortHandler,mRoundedBars);
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public void drawValues(Canvas c) {
        int value = 4;
        List<IBarDataSet> dataSets = mChart.getBarData().getDataSets();
        final float valueOffsetPlus = Utils.convertDpToPixel(22f);
        float negOffset;

        for (int i = 0; i < mChart.getBarData().getDataSetCount(); i++) {

            IBarDataSet dataSet = dataSets.get(i);
            applyValueTextStyle(dataSet);
            float valueTextHeight = Utils.calcTextHeight(mValuePaint, "8");
            negOffset = valueTextHeight + valueOffsetPlus;

            BarBuffer buffer = mBarBuffers[i];

            float left, right, top, bottom;

            for (int j = 0; j < buffer.buffer.length * mAnimator.getPhaseX(); j += value) {

                left = buffer.buffer[j];
                right = buffer.buffer[j + 2];
                top = buffer.buffer[j + 1];
                bottom = buffer.buffer[j + 3];

                float x = (left + right) / 2f;

                if (!mViewPortHandler.isInBoundsRight(x))
                    break;

                if (!mViewPortHandler.isInBoundsY(top) || !mViewPortHandler.isInBoundsLeft(x))
                    continue;

                //BarEntry entry = dataSet.getEntryForIndex(j / visibleGraphCount);
                //float val = entry.getY();
                mValuePaint.setTextAlign(Paint.Align.CENTER);
                /*if (val > 0) {

                    drawValue(c, dataSet.getValueFormatter(), val, entry, i, x, (bottom + negOffset), dataSet.getValueTextColor(j / 4));
                }*/

                Bitmap bitmap;
                try{
                    bitmap = imageList.get(j % value);
                }catch(Exception e){
                    bitmap = imageList.get(0);
                }

                if (bitmap != null) {
                    Bitmap scaledBitmap = getScaledBitmap(bitmap);
                    c.drawBitmap(scaledBitmap, x - scaledBitmap.getWidth() / 2f, (bottom + 0.5f * negOffset) - scaledBitmap.getWidth() / 7f, null);
                    //c.drawText("hello", x-scaledBitmap.getWidth()/2f,(bottom + 0.5f * negOffset) - scaledBitmap.getWidth() / 16f,mDrawPaint);
                }
            }
        }
    }


    private Bitmap getScaledBitmap(Bitmap bitmap) {
        int width = (int) 70;
        int height = (int) 70;
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }
}
