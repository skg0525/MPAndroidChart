package com.xxmassdeveloper.mpchartexample.customrendererandformater;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
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

public class BarChartXAxisFontIconRenderer extends BarChartRenderer {

    private Context context;
    private ArrayList<String> imageFontList;

    public BarChartXAxisFontIconRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler, ArrayList<String> imageList, Context context, boolean mRoundedBars) {
        super(chart, animator, viewPortHandler,mRoundedBars);
        this.context = context;
        this.imageFontList = imageList;
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

                String font;
                try{
                    font = imageFontList.get(j % value);
                }catch(Exception e){
                    font = imageFontList.get(0);
                }

                if (font != null) {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "hnm.ttf");
                    Paint paint = new Paint();
                    paint.setTypeface(typeface);
                    paint.setTextSize(70f);
                    float valueTextHeight = Utils.calcTextHeight(paint, font);
                    float valueTextWidth = Utils.calcTextWidth(paint, font);
                    negOffset = valueTextHeight + valueOffsetPlus;

                    c.drawText(font,x-valueTextWidth/2f,(bottom + 0.5f * negOffset )+ valueTextHeight/2f ,paint);
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
