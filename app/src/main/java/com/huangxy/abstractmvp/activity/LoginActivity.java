package com.huangxy.abstractmvp.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huangxy.abstractmvp.R;
import com.huangxy.abstractmvp.contract.LoginContract;
import com.huangxy.abstractmvp.delegate.LoginDelegate;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends LoginDelegate { //多个View使用同个Presenter

    @BindView(R.id.edit_username)
    EditText etUserName;
    @BindView(R.id.edit_password)
    EditText etPassword;

    private LoginContract.Presenter mPresenter = this;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login1;
    }

    @OnClick({R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                mPresenter.userLogin(username, password);
                break;
            default:
                break;
        }
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }
}
