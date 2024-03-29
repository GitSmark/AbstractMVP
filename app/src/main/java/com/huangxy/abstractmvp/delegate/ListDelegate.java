package com.huangxy.abstractmvp.delegate;

import android.content.Intent;
import android.util.Log;

import com.huangxy.abstractmvp.common.CommonActivity;
import com.huangxy.abstractmvp.contract.ListContract;
import com.huangxy.abstractmvp.model.ListModel;
import com.huangxy.mcadapter.McEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class ListDelegate extends CommonActivity<McEntity> implements ListContract.Biz {

    private ListContract.View mView = this;
    private List<McEntity> datalist = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        //可以在这里做一些不可描述的事情，比如...你懂的
    }

    @Override
    protected void onActivate() {
        Log.e(TAG, "界面初始化完毕，初始化数据");
        super.onActivate();
    }

    @Override
    protected void onReactivate() {
        Log.e(TAG, "页面重新激活，刷新数据");
        //refreshData();
    }

    @Override
    protected void initData() {

        //从后台接口获取数据
        new ListModel().getDataByRequestApi(new ListModel.Callback() {

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
        //Intent intent = new Intent(getActivity(), DetailActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent, true);
        //or setReactivate(true);
    }

    @Override
    protected void onDestroy() { //生命周期监听，可以在这里执行一些业务逻辑，比如释放界面资源
        super.onDestroy();
        //mView.recycle();
    }
}
