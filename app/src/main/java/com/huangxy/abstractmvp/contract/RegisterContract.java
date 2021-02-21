package com.huangxy.abstractmvp.contract;

public class RegisterContract {

    public interface View {
        void showToast(String str);
    }

    public interface Presenter {
        void userRegister();
    }

    public interface Biz extends View, Presenter {

    }
}
