package com.example.a99351.cgnoodlenote.ui.loginandregister;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a99351.cgnoodlenote.MainActivity;
import com.example.a99351.cgnoodlenote.R;
import com.example.a99351.cgnoodlenote.base.ActivityManager;
import com.example.a99351.cgnoodlenote.base.BaseActivity;
import com.example.a99351.cgnoodlenote.dialog.LoadingDialog;
import com.example.a99351.cgnoodlenote.localdata.DBHelper;
import com.example.a99351.cgnoodlenote.localdata.DBHelperType;
import com.example.a99351.cgnoodlenote.localdata.sysdb.SysConfig;
import com.example.a99351.cgnoodlenote.localdata.sysdb.User;
import com.example.a99351.cgnoodlenote.ui.loginandregister.contract.LoginContract;
import com.example.a99351.cgnoodlenote.ui.loginandregister.presenter.LoginPresenter;
import com.example.a99351.cgnoodlenote.utils.CheckUtils;
import com.example.a99351.cgnoodlenote.utils.ToastUtil;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginAndRegisterActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.line2)
    View line2;
    @Bind(R.id.tv_commit)
    TextView tvCommit;
    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_center)
    TextView tvTopCenter;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.ll_layout_forget_password_and_goto_register)
    LinearLayout llLayoutForgetPasswordAndGotoRegister;
    @Bind(R.id.et_activation_code)
    EditText etActivationCode;
    @Bind(R.id.line3)
    View line3;
    @Bind(R.id.ll_layout_input_activation_code)
    LinearLayout llLayoutInputActivationCode;
    @Bind(R.id.tv_get_activation_code)
    TextView tvGetActivationCode;
    @Bind(R.id.ll_layout_get_activation_code)
    LinearLayout llLayoutGetActivationCode;
    @Bind(R.id.tv_goto_register)
    TextView tvGotoRegister;


    //枚举类型，用来区分是否是登录，注册还是忘记密码界面
    private LoginType loginType;

    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_and_register;
    }

    @Override
    protected void initToolBar() {
    }

    @Override
    protected void initView() {
        ivTopBack.setVisibility(View.GONE);
        loginType = LoginType.LOGIN;

    }

    @OnClick({R.id.iv_top_back, R.id.tv_commit, R.id.tv_forget_password, R.id.tv_get_activation_code, R.id.tv_goto_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                refreshView(LoginType.LOGIN);
                break;
            case R.id.tv_goto_register:
                refreshView(LoginType.REGISTER);
                break;
            case R.id.tv_forget_password:
                refreshView(LoginType.FORGET_PASSWORD);
                break;
            case R.id.tv_get_activation_code:
                mPresenter.getRegisterCode();
                break;
            case R.id.tv_commit:
                mPresenter.getActivation(etActivationCode.getText().toString().trim());
                String username = etUsername.getText().toString().trim();
                if (username.equals("")) {
                    ToastUtil.showShortToast("请输入用户名");
                    return;
                }
                if (etPassword.getText().toString().trim().equals("")) {
                    ToastUtil.showShortToast("请输入密码");
                    return;
                }
                if (etPassword.getText().toString().trim().length() < 6) {
                    ToastUtil.showShortToast("密码必须大于6位数");
                    return;
                }
                Map<String, String> map2 = new HashMap<>();
                map2.put("username", username);
                map2.put("password", CheckUtils.md5(etPassword.getText().toString().trim()));
                if (loginType == LoginType.LOGIN) {
                    //登录请求
                    mPresenter.loginByPassword(map2);
                }else if (loginType ==LoginType.REGISTER){
                    mPresenter.register(map2);
                }else{
                    mPresenter.forgetPassword(map2);
                }
                break;
        }
    }

    private void refreshView(LoginType type) {
        //这里监听按钮点击后应该执行的事件
        loginType = type;
        etActivationCode.setText("");
        switch (type) {
            case LOGIN:
                llLayoutForgetPasswordAndGotoRegister.setVisibility(View.VISIBLE);
                llLayoutGetActivationCode.setVisibility(View.GONE);
                llLayoutInputActivationCode.setVisibility(View.GONE);
                tvTopCenter.setText("登录");
                tvCommit.setText("登录");
                ivTopBack.setVisibility(View.GONE);
                break;
            case REGISTER:
                llLayoutForgetPasswordAndGotoRegister.setVisibility(View.GONE);
                llLayoutGetActivationCode.setVisibility(View.VISIBLE);
                llLayoutInputActivationCode.setVisibility(View.VISIBLE);
                tvTopCenter.setText("注册");
                tvCommit.setText("注册");
                ivTopBack.setVisibility(View.VISIBLE);
                break;
            case FORGET_PASSWORD:
                llLayoutForgetPasswordAndGotoRegister.setVisibility(View.GONE);
                llLayoutGetActivationCode.setVisibility(View.VISIBLE);
                llLayoutInputActivationCode.setVisibility(View.VISIBLE);
                tvTopCenter.setText("忘记密码");
                tvCommit.setText("确定");
                ivTopBack.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 双击返回键退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (loginType ==LoginType.REGISTER||loginType ==LoginType.FORGET_PASSWORD){
            refreshView(LoginType.LOGIN);
            return true;//这里返回true时他的父Activity如果重写了这个方法那么他的父Activity不会执行，否则则执行
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                ActivityManager.getActivityMar().exitApp(LoginAndRegisterActivity.this);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void getRegisterCodeSuccess(String info) {

    }

    @Override
    public void registerSuccess(String model) {
        LoginUtil.initUserFileAndFolder();
        refreshView(LoginType.LOGIN);
        ToastUtil.showShortToast(model);
    }

    @Override
    public void loginByPassword(String model) {
        String username = etUsername.getText().toString().trim();
        String password = CheckUtils.md5(etPassword.getText().toString().trim());
        LoginUtil.saveLoginState(mContext,username,password);
        startActivity(MainActivity.class);
        ToastUtil.showShortToast("已登录");
    }

    @Override
    public void loadFail(String msg) {

    }

    @Override
    public void showLoading() {
        mLoadingDialog = new LoadingDialog(LoginAndRegisterActivity.this, "加载中...", false);
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancelDialog();
        }
    }
}

