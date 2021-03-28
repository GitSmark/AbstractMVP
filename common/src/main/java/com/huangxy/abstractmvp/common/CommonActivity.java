package com.huangxy.abstractmvp.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huangxy on 2016/3/6.
 * https://github.com/GitSmark/AbstractMVP
 */
public abstract class CommonActivity<T> extends Activity implements CommonView.DataBinding<T> {

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

    protected void onActivate(){ //当Activity可见且获得用户焦点能交互时触发
        if (!mIsVisible) {
            initData();
        }
    }

    protected void onReactivate(){ //页面重新激活时触发，需要在startNewActivity后设置setReactivate(true);
        onActivate();
    }

    public final void setReactivate(boolean isRefresh){ //设置页面重新激活的时候是否刷新界面，常用于点击列表跳转至详情页时使用，若为true，当关闭详情页返回当前页面时会回调onReactivate()方法
        mIsRefresh = isRefresh;
    }

    public final void startActivity(Intent intent, boolean isRefresh) {
        startActivity(intent, null);
        setReactivate(isRefresh);
    }

    public final Context getContext() {
        return this;
    }

    public final Activity getActivity() {
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
