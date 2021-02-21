package com.huangxy.abstractmvp.contract;

public class ListContract {

    public interface View {
        void showToast(String str);
    }

    public interface Presenter {
        void onItemAction(int i);
    }

    public interface Biz extends View, Presenter {

    }
}
