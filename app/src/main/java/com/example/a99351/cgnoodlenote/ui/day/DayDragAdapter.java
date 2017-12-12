package com.example.a99351.cgnoodlenote.ui.day;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.localdata.busdb.DayCharge;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;

import java.io.File;
import java.util.List;

/**
 * Created by luoxw on 2016/6/20.
 */
public class DayDragAdapter extends BaseItemDraggableAdapter<DayCharge, BaseViewHolder> {
    public DayDragAdapter(List data) {
        super(R.layout.item_draggable_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DayCharge item) {
        if (!TextUtils.isEmpty(item.getProductimg())){
            Glide.with(mContext).load(new File(item.getProductimg())).error(R.mipmap.ic_launcher).into((ImageView) helper.getView(R.id.iv_photo));
        }else{
            ((ImageView) helper.getView(R.id.iv_photo)).setImageResource(R.mipmap.ic_launcher_dog);
        }
        helper.setText(R.id.tv_name, item.getProductname());
        helper.setText(R.id.tv_info, item.getCreatedate());
    }
}
