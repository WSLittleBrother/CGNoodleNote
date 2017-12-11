package com.example.a99351.cgnoodlenote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.localdata.DBHelper;
import com.example.a99351.cgnoodlenote.localdata.DBHelperType;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;
import com.example.a99351.cgnoodlenote.utils.ToastUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.BubbleChartView;

/**
 * Created by 99351 on 2017/12/7.
 */

public class BubbleChartViewDialog extends Dialog {
    private Context mContext;
    private TextView tvTitle;
    private TextView tvPositive;
    private TextView tvNoData;

    private BubbleChartView chart;
    private BubbleChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private ValueShape shape = ValueShape.CIRCLE;//泡沫图为圆形
    private boolean hasLabels = true;
    private boolean hasLabelForSelected = false;

    public BubbleChartViewDialog(@NonNull Context context) {
        super(context, R.style.FragmnetDiaog);
        mContext = context;
    }

    public BubbleChartViewDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BubbleChartViewDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bubble_chart_view);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        tvPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              BubbleChartViewDialog.this.dismiss();
            }
        });
    }

    private void initView() {
        chart = (BubbleChartView) findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvPositive = (TextView) findViewById(R.id.tv_positive);
        tvNoData = (TextView) findViewById(R.id.tv_no_data);

    }

    private void initData() {
        DBHelper dbhelper = null;
        try {
            dbhelper = new DBHelper(mContext, DBHelperType.BUS_DBHELPER);
            Dao<Product, Integer> userDao = dbhelper.getProductDao();
            List<Product> products = userDao.queryForAll();
            if (products.size() ==0){
                tvNoData.setVisibility(View.VISIBLE);
                chart.setVisibility(View.GONE);
            }else{
                tvNoData.setVisibility(View.GONE);
                generateData(products);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateData(List<Product> products) {

        List<BubbleValue> values = new ArrayList<BubbleValue>();
        for (int i = 1; i <= products.size(); i++){
            Product product = products.get(i-1);
            BubbleValue value = null;
            value = new BubbleValue(i, Float.parseFloat(product.getPrice()), Float.parseFloat(product.getPrice()));
            value.setLabel(product.getProductname());
            value.setColor(ChartUtils.pickColor());
            value.setShape(shape);
            values.add(value);
        }

        data = new BubbleChartData(values);
        data.setHasLabels(hasLabels);
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);

        if (hasAxes) {
            Axis axisX = new Axis();
            Axis axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
                axisX.setName("编号");
                axisY.setName("价格");
            }
            data.setAxisXBottom(axisX);
            data.setAxisYLeft(axisY);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }

        chart.setBubbleChartData(data);

    }

    private class ValueTouchListener implements BubbleChartOnValueSelectListener {

        @Override
        public void onValueSelected(int bubbleIndex, BubbleValue value) {
            Snackbar.make(tvPositive,"品名："+new String(value.getLabel())+"    价格："+value.getY()+" 元",Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onValueDeselected() {
            // TODO Auto-generated method stub

        }
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

}
