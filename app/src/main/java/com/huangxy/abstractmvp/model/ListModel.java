package com.huangxy.abstractmvp.model;

import com.huangxy.mcadapter.McEntity;

import java.util.ArrayList;
import java.util.List;

public class ListModel {

    //从后台接口获取数据
    public void getDataByRequestApi(final Callback callback) {

        //这里模拟请求接口获取数据
        List<McEntity> list = new ArrayList<>();
        for(int i=0; i<100; i++) {
            list.add(new McEntity(""+i, 0));
        }
        if (list != null) {
            callback.success(list);
        } else {
            callback.fail();
        }
    }

    //从数据库查询
    public void getDataByDatabase(final Callback callback) {

        //这里模拟查询数据库获取数据
        List<McEntity> list = new ArrayList<>();
        for(int i=0; i<50; i++) {
            list.add(new McEntity(""+i, i%2));
        }
        if (list != null) {
            callback.success(list);
        } else {
            callback.fail();
        }
    }

    /**
     * 用于回传请求的数据的回调接口
     */
    public interface Callback {

        void success(List<McEntity> list);

        void fail();
    }
}
