package com.huangxy.abstractmvp.delegate;

import androidx.viewbinding.ViewBinding;

import com.huangxy.abstractmvp.common.CommonActivity;
import com.huangxy.abstractmvp.model.DetailBean;

public abstract class DetailDelegate<T extends ViewBinding> extends CommonActivity<T> /*implements DetailContract.Biz*/ {

    private DetailBean detailBean = new DetailBean(TAG, "-1");

    @Override
    protected void beforeContentViewSet() {
        detailBean = (DetailBean) getIntent().getSerializableExtra("ARG_PARAM");
    }

    @Override
    public final DetailBean getBindData() {
        return detailBean;
    }
}
