package com.example.a99351.cgnoodlenote.ui.day;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.base.BaseFragment;
import com.example.a99351.cgnoodlenote.dialog.BubbleChartViewDialog;
import com.example.a99351.cgnoodlenote.dialog.NormalSelectPaymentDialog;
import com.example.a99351.cgnoodlenote.dialog.NormalSelectionDialog;
import com.example.a99351.cgnoodlenote.localdata.DBHelper;
import com.example.a99351.cgnoodlenote.localdata.DBHelperType;
import com.example.a99351.cgnoodlenote.localdata.DataMaker;
import com.example.a99351.cgnoodlenote.localdata.busdb.DayCharge;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;
import com.example.a99351.cgnoodlenote.ui.day.contract.DayContract;
import com.example.a99351.cgnoodlenote.ui.day.presenter.DayPresenter;
import com.example.a99351.cgnoodlenote.utils.DateTimeUtils;
import com.example.a99351.cgnoodlenote.utils.ToastUtil;
import com.example.a99351.cgnoodlenote.widget.BottomPopupwindow;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99351 on 2017/10/28.
 */

public class DayFragment extends BaseFragment<DayPresenter> implements DayContract.View{
    private static DayFragment instance;
    private RecyclerView mRecyclerView;
    private List<DayCharge> mData;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private ItemTouchHelper mItemTouchHelper;
    private DayDragAdapter mAdapter;

    //控件
    private TextView tvWacthDetail;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_day;
    }

    @Override
    protected void initView() {
        instance = this;
        //初始化列表
        mData = new ArrayList<>();

        tvWacthDetail = (TextView) mFragmentRootView.findViewById(R.id.tv_watch_detail);
        tvWacthDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BubbleChartViewDialog dialog = new BubbleChartViewDialog(mContext);
                dialog.show();
                dialog.setTitle("产品详细图");

            }
        });

        mFragmentRootView.findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbhelper = null;
                List<Product> products = null;
                try {
                    dbhelper = new DBHelper(mContext, DBHelperType.BUS_DBHELPER);
                    Dao<Product, Integer> userDao = dbhelper.getProductDao();
                    products = userDao.queryForAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                mPresenter.getChoosedProduct(mContext,products);
            }
        });


        mRecyclerView = (RecyclerView) mFragmentRootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setNestedScrollingEnabled(true);
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = (BaseViewHolder) viewHolder;
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                ToastUtil.showShortToast("move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                ToastUtil.showShortToast("move end: " );
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }
        };

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(20);
        paint.setColor(Color.BLACK);
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                holder.setTextColor(R.id.tv_name, Color.BLUE);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                ToastUtil.showShortToast("View reset: " + pos);
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
                holder.setTextColor(R.id.tv_name, Color.RED);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                ToastUtil.showShortToast("View Swiped: " + pos);
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ContextCompat.getColor(mContext, R.color.transparent));
                canvas.drawText("滑动会删除当前产品，请谨慎操作", 0, 40, paint);
            }
        };

        mAdapter = new DayDragAdapter(mData);
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        //mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
        /**
         * 这里是对item是否能够滑动的控制
         */
//        mAdapter.enableSwipeItem();//里面直接封装的是一个true值，设置不能滑动调用mAdapter.disableSwipeItem()方法
        mAdapter.setOnItemSwipeListener(onItemSwipeListener);
        mAdapter.enableDragItem(mItemTouchHelper);
        mAdapter.setOnItemDragListener(listener);
//        mRecyclerView.addItemDecoration(new GridItemDecoration(this ,R.drawable.list_divider));

        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
//                ToastUtils.showShortToast("点击了" + position);
//            }
//        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showPopFormBottom(view);
                ToastUtil.showShortToast("点击了" + position);
            }
        });


        initData();
    }

    private void initData() {
        mData = DataMaker.getDayCharges(mContext,DateTimeUtils.getCurrentDateTimeYMD());
        mAdapter.setNewData(mData);
        mAdapter.notifyDataSetChanged();
    }

    public static DayFragment getInstance(){
        if (instance == null){
            return new DayFragment();
        }
        return instance;
    }

    @Override
    public void reFreshRecyclerView(List<Product> products) {
        //这里开始进行数据当天数据库表的创建和更新
        try {
            DataMaker.createOrUndateDayCharge(mContext, products, new CallBack() {
                @Override
                public void callback() {
                    mData = DataMaker.getDayCharges(mContext, DateTimeUtils.getCurrentDateTimeYMD());
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.setNewData(mData);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPopFormBottom(View view) {
        NormalSelectPaymentDialog dialog = new NormalSelectPaymentDialog(new NormalSelectionDialog.Builder(mContext));
        dialog.show();
    }

}
