package com.huangxy.abstractmvp.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.huangxy.abstractmvp.R;
import com.huangxy.abstractmvp.contract.ListContract;
import com.huangxy.abstractmvp.delegate.ListDelegate; //替换业务逻辑，如本例中由接口改为从数据库获取数据
import com.huangxy.abstractmvp.delegate.ListDelegate2; //extends ListDelegate → ListDelegate2
import com.huangxy.abstractmvp.view.McAdapterItem1;
import com.huangxy.abstractmvp.view.McAdapterItem2;
import com.huangxy.mcadapter.McAdapter;

import butterknife.BindView;

public class ListActivity extends ListDelegate implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview)
    ListView listView;

    private ListContract.Presenter mPresenter = this;
    private McAdapter mcAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        mcAdapter = new McAdapter(getBindListData(), McAdapterItem1.class, McAdapterItem2.class); //配合使用McAdapter，实现可拔插多布局列表，支持item多处复用
        listView.setOnItemClickListener(this);
        listView.setAdapter(mcAdapter);
    }

    @Override
    public void notifyDataSetChanged() {
        mcAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mPresenter.onItemAction(i);
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }
}