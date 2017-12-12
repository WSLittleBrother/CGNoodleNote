package com.example.a99351.cgnoodlenote.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.utils.DensityUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/11/7.
 */

public class NormalSelectPaymentDialog {
    private Dialog mDialog;
    private View dialogView;
    private TextView title;
    private TextView bt_sure;
    private NormalSelectionDialog.Builder mBuilder;
    private List<String> datas;

    public NormalSelectPaymentDialog(NormalSelectionDialog.Builder builder) {
        this.mBuilder=builder;

        mDialog=new Dialog(mBuilder.getContext(), R.style.DialogStyle);
        dialogView= View.inflate(builder.getContext(), R.layout.popupwindow_bottom,null);
        mDialog.setContentView(dialogView);

        Window window=mDialog.getWindow();
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.width = (int) (DensityUtil.getScreenWidth(builder.getContext()) *
                builder.getItemWidth());
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity= Gravity.BOTTOM;
        window.setAttributes(lp);

        bt_sure = (TextView) dialogView.findViewById(R.id.tv_positive);
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.setCanceledOnTouchOutside(mBuilder.isTouchOutside());
    }

    public void show() {

        mDialog.show();

    }

}
