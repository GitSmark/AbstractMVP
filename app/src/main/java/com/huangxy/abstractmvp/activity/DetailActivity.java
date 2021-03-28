package com.huangxy.abstractmvp.activity;

import android.view.View;

import com.huangxy.abstractmvp.BR;
import com.huangxy.abstractmvp.databinding.ActivityDetailBinding;
import com.huangxy.abstractmvp.delegate.DetailDelegate;

public class DetailActivity extends DetailDelegate {

    private ActivityDetailBinding bindingView;

    @Override
    protected View getLayoutView() {
        bindingView = ActivityDetailBinding.inflate(getLayoutInflater()); //推荐使用主动绑定rootView，无需泛型入参
        return bindingView.getRoot();
    }

    @Override
    protected void initView() {
        if (bindingView != null) {
            bindingView.setVariable(BR.detailBean, getBindData());
            //bindingView.setDetailBean(getBindData());
        }
    }
}
