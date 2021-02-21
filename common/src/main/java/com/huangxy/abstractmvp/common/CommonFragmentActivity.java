package com.huangxy.abstractmvp.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huangxy on 2016/3/6.
 * https://github.com/GitSmark/AbstractMVP
 */
public abstract class CommonFragmentActivity<T> extends FragmentActivity implements CommonView.DataBinding<T> {

    private Unbinder mBinder;

    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeContentViewSet();
        setContentView(getLayoutResId());
        mBinder = ButterKnife.bind(this);
        afterContentViewSet();
        initView();
        initData();
    }

    protected Context getContext() {
        return this;
    }

    protected Activity getActivity() {
        return this;
    }

    @Override
    public T getBindData() {
        return null;
    }

    @Override
    public List<T> getBindListData() {
        return null;
    }

    @Override
    public void notifyDataSetChanged() {}

    protected abstract int getLayoutResId();

    protected void beforeContentViewSet(){}

    protected void afterContentViewSet(){}

    protected void initView(){}

    protected void initData(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinder != null && mBinder != Unbinder.EMPTY) {
            mBinder.unbind();
            mBinder = null;
        }
    }
}
