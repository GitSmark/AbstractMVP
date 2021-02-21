package com.huangxy.abstractmvp.contract;

public class LoginContract {

    public interface View {
        void showToast(String str);
    }

    public interface Presenter {
        void userLogin(String userName, String password);
    }

    public interface Biz extends View, Presenter {

    }
}
