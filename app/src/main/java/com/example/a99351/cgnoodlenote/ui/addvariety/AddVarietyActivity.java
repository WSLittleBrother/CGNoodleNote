package com.example.a99351.cgnoodlenote.ui.addvariety;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.base.BaseActivity;
import com.example.a99351.cgnoodlenote.dialog.DialogInterface;
import com.example.a99351.cgnoodlenote.dialog.LoadingDialog;
import com.example.a99351.cgnoodlenote.dialog.NormalAlertDialog;
import com.example.a99351.cgnoodlenote.dialog.NormalSelectionDialog;
import com.example.a99351.cgnoodlenote.localdata.DBHelper;
import com.example.a99351.cgnoodlenote.localdata.DBHelperType;
import com.example.a99351.cgnoodlenote.localdata.busdb.Product;
import com.example.a99351.cgnoodlenote.utils.DateTimeUtils;
import com.example.a99351.cgnoodlenote.utils.PhotoUtils;
import com.example.a99351.cgnoodlenote.utils.ToastUtil;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddVarietyActivity extends BaseActivity implements AddVarietyDragAdapter.setImgClick {
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar_rightbutton)
    TextView toolbarRightbutton;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.toolbar_leftbutton)
    TextView toolbarLeftbutton;
    @Bind(R.id.iv_img)
    CircleImageView ivImg;
    @Bind(R.id.llayout_img)
    LinearLayout llayoutImg;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_price)
    EditText etPrice;
    @Bind(R.id.et_remake)
    EditText etRemake;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.tv_product_count)
    TextView tvProductCount;

    /**
     * 常量
     */
    public static final int OPEN_CAMERA = 101;
    public static final int OPEN_ALBUM = 102;
    public static final int CODE_RESULT_REQUEST = 103;
    public static final int ITEM_OPEN_CAMERA = 104;
    public static final int ITEM_OPEN_ALBUM = 105;
    public static final int ITEM_CODE_RESULT_REQUEST = 106;
    private Product addProduct;

    private File fileUri ;
    private File fileCropUri ;
    private Uri imageUri;
    private Uri cropImageUri;
    private String imgUrl;
    private String cropImgUrl;
    private String itemImgUrl;
    private String itemCropImgUrl;

    /**
     * RecyclerView相关
     */
    private RecyclerView mRecyclerView;
    private List<Product> mData;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private ItemTouchHelper mItemTouchHelper;
    private AddVarietyDragAdapter mAdapter;

    /**
     * 数据里辅助类
     */
    private AddVatietyDataMaker mDataMaker;

    private LoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataMaker = new AddVatietyDataMaker(mContext);
        mLoadingDialog = new LoadingDialog(AddVarietyActivity.this, "数据加载中...", false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_variety;
    }

    @Override
    protected void initToolBar() {
        toolbarTitle.setText("增加种类");
    }

    @Override
    protected void initView() {
        toolbarRightbutton.setVisibility(View.VISIBLE);
        toolbarRightbutton.setText("保存");

        /**
         * 初始化RecyclerView相关
         */
        initRecyclerVIew();
    }

    private void initRecyclerVIew() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setNestedScrollingEnabled(true);
        mData = initData();
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
                ToastUtil.showShortToast("move end: ");
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

        mAdapter = new AddVarietyDragAdapter(mData);
        mAdapter.setListener(AddVarietyActivity.this);
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
                ToastUtil.showShortToast("点击了" + position);
            }
        });

    }

    private List<Product> initData() {
        DBHelper dbhelper = null;
        List<Product> products = null;
        try {
            dbhelper = new DBHelper(mContext, DBHelperType.BUS_DBHELPER);
            Dao<Product, Integer> userDao = dbhelper.getProductDao();
            products = userDao.queryForAll();
            //更新产品数量
            tvProductCount.setText("数量  "+products.size());
            //这里是更新数据的操作
            if (mAdapter != null) {
                mAdapter.setNewData(products);
                mAdapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @OnClick({R.id.back, R.id.toolbar_rightbutton, R.id.llayout_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish_Activity(AddVarietyActivity.this);
                break;
            case R.id.toolbar_rightbutton:
                if (TextUtils.isEmpty(etName.getText().toString().trim()) || TextUtils.isEmpty(etPrice.getText().toString().trim()) || TextUtils.isEmpty(etRemake.getText().toString().trim())) {
                    ToastUtil.showShortToast("请填写完整产品信息");
                    return;
                }
                NormalAlertDialog dialog = new NormalAlertDialog.Builder(mContext).setBoolTitle(true).setTitleText("温馨提示")
                        .setContentText("是否添加【" + etName.getText().toString().trim() + "】到产品列表")
//                            .setSingleModel(false)
                        .setRightText("继续")
                        .setLeftText("取消")
//                            .setRightTextColor(CourseDetailActivity.this.getResources().getColor(R.color.blue))
                        .setHeight(0.23f)
                        .setWidth(0.65f)
                        .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {

                            @Override
                            public void clickLeftButton(NormalAlertDialog dialog, View view) {
                                dialog.dismiss();
                            }

                            @Override
                            public void clickRightButton(NormalAlertDialog dialog, View view) {
                                mLoadingDialog.show();
                                addProduct = new Product();
                                addProduct.setProductname(etName.getText().toString().trim());
                                addProduct.setPrice(etPrice.getText().toString().trim());
                                addProduct.setRemake(etRemake.getText().toString().trim());
                                addProduct.setCreatedate(DateTimeUtils.getCurrentDateTime());
                                addProduct.setCreateday(DateTimeUtils.getCurrentDateTimeYMD());
                                addProduct.setImgurl(cropImgUrl);
                                mDataMaker.addVatietyPrduct(addProduct);
                                dialog.dismiss();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mLoadingDialog != null) {
                                            etName.setText("");
                                            etPrice.setText("");
                                            etRemake.setText("");
                                            ivImg.setImageResource(R.mipmap.default_photo);
                                            mLoadingDialog.cancelDialog();
                                            ToastUtil.showShortToast("产品添加成功");
                                        }
                                        initData();

                                    }
                                }, 2000);

                            }
                        }).setTouchOutside(false)
                        .build();
                dialog.show();
                break;
            case R.id.llayout_img:
                List<String> headData = new ArrayList<>();
                headData.add("相机");
                headData.add("相册");
                NormalSelectionDialog chaHeaddialog = new NormalSelectionDialog.Builder(AddVarietyActivity.this)
                        .setItemHeight(45)
                        .setItemTextColor(R.color.blue)
                        .setItemTextSize(14)
                        .setItemWidth(0.7f)
                        .setCancleButtonText("取消")
                        .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {
                            @Override
                            public void onItemClick(NormalSelectionDialog dialog, View button, int position) {
                                switch (position) {
                                    case 0:
                                        performCodeWithPermission("拍照需要您提供浏览存储的权限", new PermissionCallback() {
                                            @Override
                                            public void hasPermission() {
                                                String[] str = PhotoUtils.getPhotoPath();
                                                imgUrl = str[0];
                                                cropImgUrl = str[1];
                                                fileUri = new File(imgUrl);
                                                fileCropUri = new File(cropImgUrl);
                                                imageUri = Uri.fromFile(fileUri);
                                                //通过FileProvider创建一个content类型的Uri
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                    imageUri = FileProvider.getUriForFile(AddVarietyActivity.this, "com.example.a99351.cgnoodlenote.fileprovider", fileUri);
                                                }
                                                PhotoUtils.takePicture(AddVarietyActivity.this, imageUri, OPEN_CAMERA);
                                            }

                                            @Override
                                            public void noPermission() {
                                            }
                                        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                                        dialog.dismiss();
                                        break;
                                    case 1:
                                        performCodeWithPermission("打开相册需要您提供浏览存储的权限", new PermissionCallback() {
                                            @Override
                                            public void hasPermission() {
                                                PhotoUtils.openPic(AddVarietyActivity.this, OPEN_ALBUM);
                                            }

                                            @Override
                                            public void noPermission() {
                                            }
                                        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        dialog.dismiss();
                                        break;
                                }

                            }
                        }).setTouchOutside(true)
                        .build();

                chaHeaddialog.setData(headData);
                chaHeaddialog.show();
                break;
        }
    }
    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==RESULT_OK){
            switch (requestCode){
                case OPEN_CAMERA:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;
                case OPEN_ALBUM:
                    cropImgUrl = PhotoUtils.getPhotoPath()[1];
                    fileCropUri = new File(cropImgUrl);
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(this, "com.example.a99351.cgnoodlenote.fileprovider", new File(newUri.getPath()));
                    }
                    PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        ivImg.setImageBitmap(bitmap);
                    }
                    break;
                case ITEM_OPEN_CAMERA:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, ITEM_CODE_RESULT_REQUEST);
                    break;

                case ITEM_OPEN_ALBUM:
                    cropImgUrl = PhotoUtils.getPhotoPath()[1];
                    fileCropUri = new File(cropImgUrl);
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri itemnewUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        itemnewUri = FileProvider.getUriForFile(this, "com.example.a99351.cgnoodlenote.fileprovider", new File(itemnewUri.getPath()));
                    }
                    PhotoUtils.cropImageUri(this, itemnewUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, ITEM_CODE_RESULT_REQUEST);
                    break;
                case ITEM_CODE_RESULT_REQUEST:
                    itemProduct.setImgurl(cropImgUrl);
                    mDataMaker.savePrductImgUrl(itemProduct);
                   initData();
                    break;
            }
        }else{
            cropImgUrl = "";
            imgUrl = "";
        }

    }

    private Product itemProduct;
    @Override
    public void imgClick(Product item) {
        itemProduct = item;
        List<String> headData = new ArrayList<>();
        headData.add("相机");
        headData.add("相册");
        NormalSelectionDialog chaHeaddialog = new NormalSelectionDialog.Builder(AddVarietyActivity.this)
                .setItemHeight(45)
                .setItemTextColor(R.color.blue)
                .setItemTextSize(14)
                .setItemWidth(0.7f)
                .setCancleButtonText("取消")
                .setOnItemListener(new DialogInterface.OnItemClickListener<NormalSelectionDialog>() {
                    @Override
                    public void onItemClick(NormalSelectionDialog dialog, View button, int position) {
                        switch (position) {
                            case 0:
                                String[] str = PhotoUtils.getPhotoPath();
                                imgUrl = str[0];
                                cropImgUrl = str[1];
                                fileUri = new File(imgUrl);
                                fileCropUri = new File(cropImgUrl);
                                imageUri = Uri.fromFile(fileUri);
                                //通过FileProvider创建一个content类型的Uri
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    imageUri = FileProvider.getUriForFile(AddVarietyActivity.this, "com.example.a99351.cgnoodlenote.fileprovider", fileUri);
                                }
                                PhotoUtils.takePicture(AddVarietyActivity.this, imageUri, ITEM_OPEN_CAMERA);
                                dialog.dismiss();
                                break;
                            case 1:
                                PhotoUtils.openPic(AddVarietyActivity.this, ITEM_OPEN_ALBUM);
                                dialog.dismiss();
                                break;
                        }

                    }
                }).setTouchOutside(true)
                .build();

        chaHeaddialog.setData(headData);
        chaHeaddialog.show();
    }
}
