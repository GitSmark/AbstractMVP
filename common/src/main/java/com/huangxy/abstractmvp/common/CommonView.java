package com.huangxy.abstractmvp.common;

import java.util.List;

/**
 * Created by huangxy on 2016/3/6.
 * https://github.com/GitSmark/AbstractMVP
 */
public class CommonView {

    //用于数据改变时通知刷新界面
    public interface DataSetBinding {
        void notifyDataSetChanged();
    }

    //适用于详情页，获取视图绑定detailData
    public interface DataBindType1<T> extends DataSetBinding {
        T getBindData();
    }

    //适用于列表页，获取视图绑定listData
    public interface DataBindType2<T> extends DataSetBinding {
        List<T> getBindListData();
    }

    public interface DataBinding<T> extends DataBindType1, DataBindType2 {

    }
}
