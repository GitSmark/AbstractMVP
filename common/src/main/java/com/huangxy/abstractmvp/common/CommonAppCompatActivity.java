package com.huangxy.abstractmvp.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huangxy on 2016/3/6.
 * https://github.com/GitSmark/AbstractMVP
 */
public abstract class CommonAppCompatActivity<T> extends AppCompatActivity implements CommonView.DataBinding<T> {

    private Unbinder mBinder;

    private boolean mIsVisible = false;

    private boolean mIsRefresh = false;

    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeContentViewSet();
        setContentView(getLayoutResId());
        mBinder = ButterKnife.bind(this);
        afterContentViewSet();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsVisible && mIsRefresh) {
            mIsRefresh = false;
            onReactivate();
        } else {
            onActivate();
        }
        mIsVisible = true;
    }

    protected void onActivate(){
        if (!mIsVisible) {
            initData();
        }
    }

    protected void onReactivate(){
        onActivate();
    }

    public final void setReactivate(boolean isRefresh){
        mIsRefresh = isRefresh;
    }

    public final void startActivity(Intent intent, boolean isRefresh) {
        startActivity(intent, null);
        setReactivate(isRefresh);
    }

    public final Context getContext() {
        return this;
    }

    public final AppCompatActivity getActivity() {
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
