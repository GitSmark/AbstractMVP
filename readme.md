# AbstractMVP
一种Android轻量级的完全分布式MVP开发模式      [[English documents]](https://github.com/GitSmark/AbstractMVP/blob/master/README.md)

特点
-----
* 简单方便实用
* 帮助您分离您的逻辑，并保持您的类清楚和明确
* 一种借鉴于iOS的创新型Android MVP开发模式

特别声明
---------
*  `com.ttt.common.mvp` 类包里面的文件为[本人](https://github.com/GitSmark/)原创.
* `com.ttt.common.adapter` 类包里面的文件引用自 [tianzhijiexian/CommonAdapter](https://github.com/tianzhijiexian/CommonAdapter)，深表感谢.

工程
----
* 工程结构采用非典型MVP模式，如下图

![](20160306221534.png)

使用说明
--------
1. 首先把下图相关文件加入到你的工程.

  ![](20160306221638.png)
  
 
2. 创建你自己的` ViewDelegate`  和 ` ViewInterface` .
  
根据项目需要独立继承抽象类 `CommonActivity`, `CommonFragment`, `CommonFragmentActivity` ，实现相关方法，然后添加你的接口方法并实现，方便回调.
  ```java
  public abstract class MainFragmentDelegate extends CommonFragment {
    // implements your ViewInterface and realization

  	@Override
  	public int inflateView() {
  		// TODO Auto-generated method stub
  		// return R.layout.fragment_main;
  	}
  
  	@Override
  	public void initView(View view) {
  		// TODO Auto-generated method stub
  		// initView view.findViewbyId(R.id.xx);
  	}
  
  	@Override
  	public void setListener() {
  		// TODO Auto-generated method stub
  		// setListener
  	}
  
  }
  ```
3. 根据情况需要可重写相应的方法.
 - `BeforeCreate()` 
 - `AfterCreate()`
 - `getData()`
 - `Other()`

4. 添加监听事件，在实现类继承并实现相应监听回调方法，处理事件，你还可以通过实现 `CommonViewAdapterInterface` 接口去传递数据.

   ```java
  public abstract class MainDelegate extends CommonActivity implements OnClickListener, OnItemClickListener, CommonViewAdapterInterface, MainInterface {
  
  	TextView tv;
  	ListView lv;
  	
  	@Override
  	public void initView() {
  		// TODO Auto-generated method stub
  		// setContentView(R.layout.activity_main);
  		// tv = (TextView) findViewById(R.id.textview);
  		// lv = (ListView) findViewById(R.id.listview);
  		// lv.setAdapter((BaseAdapter)getAdapter());
  	}
  	
  	@Override
  	public void setListener() {
  		// TODO Auto-generated method stub
  		// tv.setOnClickListener(this);
  		// lv.setOnItemClickListener(this);
  	}
  
  	@Override
  	public void setText() {
  		// TODO Auto-generated method stub
  		// tv.setText("Hello, AbstractMVP!");
  	}
  
  }
  ```
  ```java
  public class MainActivity extends MainDelegate {

  	private List<MainListEntity> datas = new ArrayList<>();
  	private MainListAdapter adapter = new MainListAdapter(datas);
  	
  	@Override
  	public Adapter getAdapter() {
  		// TODO Auto-generated method stub
  		// return adapter;
  	}
  	
  	@Override
  	public void onClick(View v) {
  		// TODO Auto-generated method stub
  		// setText();
  	}
  
  	@Override
  	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
  		// TODO Auto-generated method stub
  		// Toast.makeText(this, datas.get(arg2).getName(), Toast.LENGTH_LONG).show();
  	}
  
  }
  ```

5.更多关于`CommonAdapter`的使用方法，[请点这里](https://github.com/tianzhijiexian/CommonAdapter)

自定义
-------------------
  定制你专属的 `Delegate` 和`Interface`，去实现你想要的效果.
  
参考实例
----------
  这里有一个相关例子，[点击下载](https://github.com/GitSmark/AbstractMVP/blob/master/AbstractMVPSample.zip) .

联系方式
--------
  有问题，请[推特我](https://twitter.com/huangxy) 或者 [给我发邮件](mailto:huangxy8023@foxmail.com).

许可证
----------

    Copyright 2016 huangxy@GitSmark

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

