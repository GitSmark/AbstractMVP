package com.huangxy.abstractmvp.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.annotation.Nullable;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huangxy on 2016/3/6.
 * https://github.com/GitSmark/AbstractMVP
 */
public abstract class CommonFragment<T> extends Fragment implements CommonView.DataBinding<T> {

    private Unbinder mBinder;

    protected final String TAG = getClass().getSimpleName();

    /**
     * 是否可见
     */
    private boolean mIsVisible = false;

    /**
     * 是否刷新
     */
    private boolean mIsRefresh = false;

    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        beforeContentViewSet();
        rootView = inflater.inflate(getLayoutResId(), container, false);
        mBinder = ButterKnife.bind(this, rootView);
        afterContentViewSet();
        initView(rootView); //不推荐使用，这是Fragment特有的方法，Activity/Fragment
        initView(); //推荐使用ButterKnife注入，有必要再直接使用rootView
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lazyLoad(getUserVisibleHint());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        lazyLoad(!hidden);
    }

    private void lazyLoad(boolean isVisibleToUser){
        mIsVisible = isVisibleToUser;
        if (isAdded() && isVisibleToUser) {
            onActivate();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mIsVisible && mIsRefresh){
            mIsRefresh = false;
            onReactivate();
        }
    }

    protected void onActivate(){
        initData();
    }

    protected void onReactivate(){
        onActivate();
    }

    public void setReactivate(boolean isRefresh){ //设置页面重新激活的时候是否刷新界面，比如点击列表跳转至详情页时使用，若为true，当关闭详情页返回当前页面时会回调onReactivate()方法
        mIsRefresh = isRefresh;
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

    public <T extends View> T findViewById(@IdRes int id) {
        if (rootView == null) {
            throw new IllegalStateException("Fragment " + this + " does not have a view");
        }
        return rootView.findViewById(id);
    }

    @Deprecated
    protected void initView(View root){}

    protected void initView(){}

    protected void initData(){}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBinder != null && mBinder != Unbinder.EMPTY) {
            mBinder.unbind();
            mBinder = null;
        }
    }
}
