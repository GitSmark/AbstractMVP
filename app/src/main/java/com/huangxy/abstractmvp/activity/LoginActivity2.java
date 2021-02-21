package com.huangxy.abstractmvp.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huangxy.abstractmvp.R;
import com.huangxy.abstractmvp.contract.LoginContract;
import com.huangxy.abstractmvp.contract.RegisterContract;
import com.huangxy.abstractmvp.delegate.LoginDelegate;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity2 extends LoginDelegate {

    @BindView(R.id.edit_username)
    EditText etUserName;
    @BindView(R.id.edit_password)
    EditText etPassword;

    private LoginContract.Presenter mLoginPresenter = this;
    private RegisterContract.Presenter mRegisterPresenter = this;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login2;
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String username = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                mLoginPresenter.userLogin(username, password);
                break;
            case R.id.btn_register:
                mRegisterPresenter.userRegister();
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
