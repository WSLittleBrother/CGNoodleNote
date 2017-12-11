package com.example.a99351.cgnoodlenote.ui.addvariety;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;

import java.io.File;
import java.util.List;

/**
 * Created by luoxw on 2016/6/20.
 */
public class AddVarietyDragAdapter extends BaseItemDraggableAdapter<Product, BaseViewHolder> {
    public AddVarietyDragAdapter(List data) {
        super(R.layout.item_addvariety_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Product item) {
        helper.setText(R.id.tv_first_letter,item.getProductname().substring(0,1));
        helper.setText(R.id.tv_product_name,item.getProductname());
        helper.setText(R.id.tv_product_remake,item.getRemake());
        if (!TextUtils.isEmpty(item.getImgurl())){
            Glide.with(mContext).load(new File(item.getImgurl())).error(R.mipmap.ic_launcher).into((ImageView) helper.getView(R.id.iv_img));
        }
    }
}
