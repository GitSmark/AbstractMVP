package com.huangxy.abstractmvp.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huangxy on 2016/3/6.
 * https://github.com/GitSmark/AbstractMVP
 */
public abstract class CommonActivity<T extends ViewBinding> extends Activity implements CommonView.DataBinding {

    private Unbinder mBinder;

    protected T $, bindingView;

    private boolean mIsVisible = false;

    private boolean mIsRefresh = false;

    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeContentViewSet();
        try {
            Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Method method = ((Class) type).getDeclaredMethod("inflate", LayoutInflater.class);
            $ = bindingView = (T) method.invoke(null, getLayoutInflater());
            setContentView(bindingView.getRoot());
        } catch (Exception e) {
            View rootView = getLayoutView();
            if (rootView != null) {
                setContentView(rootView);
            } else
                setContentView(getLayoutResId());
        }
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
    public Object getBindData() {
        return null;
    }

    @Override
    public List getBindListData() {
        return null;
    }

    @Override
    public void notifyDataSetChanged() {}

    protected View getLayoutView() {
        return null;
    }

    @LayoutRes
    protected int getLayoutResId() {
        return 0;
    }

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
