package com.example.a99351.cgnoodlenote.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

/**
 * Created by 99351 on 2017/10/28.
 */

public class ColorFlipPagerTitleView extends android.support.v7.widget.AppCompatTextView implements IPagerTitleView{
    private int mNormalColor;
    private int mSelectedColor;
    private float mChangePercent = 0.45f;


    public ColorFlipPagerTitleView(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
        int padding = UIUtil.dip2px(context,10);
        setPadding(padding,0,padding,0);
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.END);
    }

    public ColorFlipPagerTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorFlipPagerTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onSelected(int i, int i1) {

    }

    @Override
    public void onDeselected(int i, int i1) {

    }

    @Override
    public void onLeave(int i, int i1, float v, boolean b) {
        if (v>=mChangePercent){
            setTextColor(mNormalColor);
        }else{
            setTextColor(mSelectedColor);
        }

    }

    @Override
    public void onEnter(int i, int i1, float v, boolean b) {

        if (v>=mChangePercent){
            setTextColor(mSelectedColor);
        }else {
            setTextColor(mNormalColor);
        }
    }
}
