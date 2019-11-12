package com.drs.mpchartdemo;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

/**
 * @author: drs
 * @time: 2018/7/18 11:03
 * @des:
 */
public class LineChartActivity extends Activity implements OnChartValueSelectedListener {
    private LineChart mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);
        init();
    }

    private void init() {
        mChart = findViewById(R.id.chart1);
//        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        addinit();

        setData(10, 200);

        mChart.animateX(2500);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);

        // mChart.invalidate();
    }

    private void addinit() {
        //获取此图表的x轴
        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        //        xAxis.setLabelRotationAngle(-50);//设置x轴字体显示角度
        //        xAxis.enableGridDashedLine(10f, 10f, 0f);//删掉竖线虚线
        //        xAxis.setAxisMinimum(0f);//设置x轴的最小值
        //        xAxis.setAxisMaximum(10f);//设置最大值
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setLabelRotationAngle(10f);//设置x轴标签的旋转角度
        //        设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
        xAxis.setLabelCount(10);
        //        xAxis.setTextColor(Color.BLUE);//设置轴标签的颜色
        //        xAxis.setTextSize(24f);//设置轴标签的大小
        xAxis.setGridColor(Color.RED);//设置竖线颜色
        //        xAxis.setAxisLineColor(Color.GREEN);//设置x轴线颜色
        //        xAxis.setAxisLineWidth(5f);//设置x轴线宽度
        //        xAxis.setValueFormatter();//格式化x轴标签显示字符
        /**
         * Y轴默认显示左右两个轴线
         */
        //获取右边的轴线
        YAxis rightAxis = mChart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        //获取左边的轴线
        YAxis leftAxis = mChart.getAxisLeft();
        //设置网格线为虚线效果
        //        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //是否绘制0所在的网格线
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(true);

        mChart.setTouchEnabled(true); // 设置是否可以触摸
        mChart.setDragEnabled(true);// 是否可以拖拽
        mChart.setScaleEnabled(false);// 是否可以缩放 x和y轴, 默认是true
        mChart.setScaleXEnabled(true); //是否可以缩放 仅x轴
        mChart.setScaleYEnabled(true); //是否可以缩放 仅y轴
        mChart.setPinchZoom(true);  //设置x轴和y轴能否同时缩放。默认是否
        mChart.setDoubleTapToZoomEnabled(true);//设置是否可以通过双击屏幕放大图表。默认是true
        mChart.setHighlightPerDragEnabled(true);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        mChart.setDragDecelerationEnabled(true);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        mChart.setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。
        mChart.setExtraBottomOffset(20);
    }


    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

            float val = (float) (Math.random() * range);
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.star)));
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "折线图demo");
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            //            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            //            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
        }
    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        float x = e.getX();
        float y = e.getY();
        Log.i("drs", "e.getX() = " + x + "     e.getY() = " + y);
    }
    @Override
    public void onNothingSelected() {

    }
}
