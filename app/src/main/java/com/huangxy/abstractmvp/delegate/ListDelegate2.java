package com.huangxy.abstractmvp.delegate;

import androidx.viewbinding.ViewBinding;

import com.huangxy.abstractmvp.common.CommonActivity;
import com.huangxy.abstractmvp.contract.ListContract;
import com.huangxy.abstractmvp.model.ListModel;
import com.huangxy.mcadapter.McEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class ListDelegate2<T extends ViewBinding> extends CommonActivity<T> implements ListContract.Biz {

    private ListContract.View mView = this;
    private List<McEntity> datalist = new ArrayList<>();

    @Override
    protected void initData() {

        //从数据库查询
        new ListModel().getDataByDatabase(new ListModel.Callback() {

            @Override
            public void success(List<McEntity> list) {
                //对数据进行处理
                datalist.clear();
                if (list != null && list.size() > 0) {
                    datalist.addAll(list);
                }
                notifyDataSetChanged();
            }

            @Override
            public void fail() {
                mView.showToast("请求失败");
            }
        });
    }

    @Override
    public List<McEntity> getBindListData() {
        return datalist; //返回绑定的列表数据
    }

    @Override
    public void onItemAction(int i) {
        mView.showToast("点击了项："+datalist.get(i).getItemEntity());
    }
}
