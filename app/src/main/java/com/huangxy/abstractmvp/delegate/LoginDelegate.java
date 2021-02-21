package com.huangxy.abstractmvp.delegate;

import android.content.Intent;
import android.text.TextUtils;

import com.huangxy.abstractmvp.activity.ListActivity;
import com.huangxy.abstractmvp.common.CommonActivity;
import com.huangxy.abstractmvp.contract.LoginContract;
import com.huangxy.abstractmvp.contract.RegisterContract;

public abstract class LoginDelegate extends CommonActivity implements LoginContract.Biz, RegisterContract.Biz { //一个View对应多个Presenter

    private LoginContract.View mLoginView = this;
    private RegisterContract.View mRegisterView = this;

    @Override
    public void userLogin(String userName, String password) {
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            mLoginView.showToast("账号密码不能为空！");
            return;
        }
        mLoginView.showToast("登录成功");
        startActivity(new Intent(getActivity(), ListActivity.class));
        getActivity().finish();
    }

    @Override
    public void userRegister() {
        mRegisterView.showToast("注册成功");
    }
}
