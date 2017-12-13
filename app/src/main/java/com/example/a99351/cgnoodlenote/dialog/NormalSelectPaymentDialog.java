package com.example.a99351.cgnoodlenote.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.localdata.DataMaker;
import com.example.a99351.cgnoodlenote.localdata.busdb.DayCharge;
import com.example.a99351.cgnoodlenote.ui.day.CallBack;
import com.example.a99351.cgnoodlenote.utils.DensityUtil;
import com.example.a99351.cgnoodlenote.utils.ToastUtil;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/11/7.
 */

public class NormalSelectPaymentDialog {
    private Dialog mDialog;
    private View dialogView;
    private TextView tvName;
    private ImageView ivImg;
    private EditText etPrice;
    private EditText etCount;
    private EditText etUnit;
    private EditText etRemake;
    private TextView bt_sure;
    private NormalSelectionDialog.Builder mBuilder;
    private Context mContext;
    private DayCharge dayCharge;

    public NormalSelectPaymentDialog(NormalSelectionDialog.Builder builder) {
        this.mBuilder=builder;
        mContext = mBuilder.getContext();
        mDialog=new Dialog(mBuilder.getContext(), R.style.DialogStyle);
        dialogView= View.inflate(builder.getContext(), R.layout.dialog_day_bottom,null);
        mDialog.setContentView(dialogView);

        Window window=mDialog.getWindow();
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.width = (int) (DensityUtil.getScreenWidth(builder.getContext()) *
                builder.getItemWidth());
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity= Gravity.BOTTOM;
        window.setAttributes(lp);

        bt_sure = (TextView) dialogView.findViewById(R.id.tv_positive);
        tvName = (TextView) dialogView.findViewById(R.id.tv_name);
        ivImg = (ImageView) dialogView.findViewById(R.id.iv_img);
        etPrice = (EditText) dialogView.findViewById(R.id.et_input_price);
        etCount = (EditText) dialogView.findViewById(R.id.et_input_count);
        etUnit = (EditText) dialogView.findViewById(R.id.et_input_unit);
        etRemake = (EditText) dialogView.findViewById(R.id.et_input_remake);
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPrice.getText().toString())||TextUtils.isEmpty(etCount.getText().toString())||TextUtils.isEmpty(etUnit.getText().toString())){
                    ToastUtil.showShortToast("重要信息输入不完整");
                    return;
                }
                dayCharge.setProductprice(etPrice.getText().toString().trim());
                dayCharge.setProductunit(etUnit.getText().toString().trim());
                dayCharge.setRemake(etRemake.getText().toString().trim());
                dayCharge.setProductweight(etCount.getText().toString().trim());
                DataMaker.updateDayCharge(mContext,dayCharge);

                dialogCallBack.dialogCallBack();
                mDialog.dismiss();
            }
        });
        mDialog.setCanceledOnTouchOutside(mBuilder.isTouchOutside());
    }

    public void show() {
        initView();
        mDialog.show();
    }

    private void initView() {
        tvName.setText(dayCharge.getProductname());
        if (!TextUtils.isEmpty(dayCharge.getProductimg())){
            Glide.with(mContext).load(new File(dayCharge.getProductimg())).error(R.mipmap.ic_launcher).into(ivImg);
        }else{
            ivImg.setImageResource(R.mipmap.ic_launcher);
        }
    }

    public void setData(DayCharge data){
        dayCharge = data;
    }

    public interface DialogCallBack{
        void dialogCallBack();
    }
    private DialogCallBack dialogCallBack;
    public void setListener(DialogCallBack dialogCallBack){
        this.dialogCallBack = dialogCallBack;
    }

}
