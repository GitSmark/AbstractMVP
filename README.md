[![](https://jitpack.io/v/GitSmark/AbstractMVP.svg)](https://jitpack.io/#GitSmark/AbstractMVP)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# AbstractMVP简介
AbstractMVP是一个为MVP架构实现提供抽象组件的库，且不存在传统androidMVP中的生命周期等问题。

MVP是模型(model)－视图(view)－呈现器(presenter)的缩写。  
`Model`：业务逻辑和数据处理(数据库存储操作，网络数据请求，复杂算法，耗时操作)。  
`View` : 对应于Activity，负责View的绘制以及与用户交互。  
`Presenter`：负责完成View于Model间的数据交互。

缘起为何
-----

最近在两家新的公司看到项目还在用着传统androidMVP的开发模式，在实际使用中存在着因Activity异常重启Presenter层异步任务回调导致OOM、界面复用等问题，查阅了大量资料后发现，除了使用传统androidMVP把`Activity/Fragment`作为View层并包含一个独立类作为Presenter层的，更多的是使用`Activity/Fragment`作为Presenter层，更有甚者使用`Adapter`作为Presenter层的，针对传统androidMVP模式存在的问题，各路大神各显神通，网上的解决方案也是五花八门。对MVP模式有了更加深层的理解后，我决定对原先的实现方案进行重新的设计并封装，于是就有了现在的2.0版本。

**与传统androidMVP不同，AbstractMVP使用`Activity/Fragment`作为Presenter层来处理代码逻辑，通过让`ViewController`类继承自一个关联了业务逻辑层接口协议的`ViewDelegate`抽象类，实现View层跟Presenter层可以通过相互持有的对方层接口协议的引用对象双向调用，从而做到完全解耦。我们将`Activity/Fragment`作为Presenter层，并使用多态继承的办法派生出View层，这里面有点像iOS开发里的 `ViewController` 和 `APPDelegate` 的对应关系。** 

使用`Activity/Fragment`作为Presenter层的优点就在于，可以原封不动的使用`Activity/Fragment`本身的生命周期去处理业务逻辑，而不需要强加给另一个包含类，甚至记忆额外自定义的生命周期。对于一个开发团队，完全可以在定义好业务逻辑层接口协议后，将View层的东西交给一个人编写，而将业务实现交给另一个人编写。而随着需求变化，改动界面只需要修改对应的`ViewController`，业务逻辑变动也只需要修改对应的`ViewDelegate`。

AbstractMVP本身定位就是一种借鉴于iOS的创新型Android MVP开发模式，在查阅资料过程中也有看到同样使用了`Activity/Fragment`作为Presenter层并应用到了`ViewDelegate`的优秀项目[TheMVP](https://github.com/kymjs/TheMVP)，但两者的实现原理有所差异，最大的不同之处在于：在AbstractMVP中，直接使用`ViewDelegate`作为Presenter层，见下图。

![AbstractMVP原理示意图](file/AbstractMVP-SketchMap.png)

从实现原理上看，AbstractMVP本质还是MVC模式，只是把`Activity/Fragment`的业务逻辑独立拆分成`ViewController`和`ViewDelegate`，并实现了View层跟Presenter层的代码隔离，开发使用就 **跟经典MVC模式一样简单易用（无需进行繁琐的绑定解绑，额外处理生命周期），又兼具MVP模式的优点！** 正是由于存在这种特性，AbstractMVP既能用于新项目开发，也适用于使用经典MVC模式开发的旧项目！

实现原理
-----
现在让我们通过 *用户登录* 的例子来进一步的说明，重点展示View层和Presenter层的设计。

1. 以Activity为例，我们首先封装一个通用的基类 `BaseActivity`，以实现View层跟Presenter层的逻辑分离。代码很简单，重点在于把跟UI相关的逻辑都放在基类，不用污染Presenter层，让View层只关注视图呈现，Presenter层只关注业务逻辑。
  
  ```java
  public abstract class CommonActivity extends Activity {

      private Unbinder mBinder;

      @Override
      protected void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(getLayoutResId());                   //把这部分跟UI相关的逻辑放在基类，不用污染Presenter层
          mBinder = ButterKnife.bind(this);                   //使用ButterKnife注入，保持代码整洁，增强可读性
          //initView();                                       //View层调用，跟Presenter层逻辑分离
          //initData();                                       //Presenter层调用，初始化数据
      }

      protected abstract int getLayoutResId();                //View层必须实现的抽象方法，onCreateView

      @Override
      protected void onDestroy() {
          super.onDestroy();
          if (mBinder != null && mBinder != Unbinder.EMPTY) {
              mBinder.unbind();
              mBinder = null;
          }
      }
  }
  ``` 
2. 定义 `BizContract` ，这部分跟传统androidMVP差不多，分别定义好View层跟Presenter层的业务逻辑接口，然后合并为一个业务逻辑层接口协议。
  
  ```java
  public class LoginContract {

      public interface View {
          void showToast(String str);
      }

      public interface Presenter {
          void userLogin(String username, String password);
      }

      public interface Biz extends View, Presenter { //View层跟Presenter层合并为业务逻辑层

      }
  }
  ```
3. 新建 `ViewDelegate` ，Presenter层接口方法跟业务逻辑（包括**生命周期监听事件**）在这里实现，在这里可以通过持有View层接口协议的引用对象 `ViewContract` 去调用相应的实现方法。
  
  注意：**`ViewDelegate`必须为抽象类**，以实现View层的代码隔离，根据业务需求选择继承自  `CommonActivity`, `CommonFragment`, `CommonFragmentActivity`, `CommonAppCompatActivity` 并关联对应的业务接口协议
  
  ```java
  public abstract class LoginDelegate extends CommonActivity implements LoginContract.Biz { //关联业务逻辑层的接口协议

      private LoginContract.View mLoginView = this; //ViewContract的引用对象

      @Override
      public void userLogin(String username, String password) {
          if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
              mLoginView.showToast("账号密码不能为空！"); //回调View层的方法
              return;
          }
          if (username.equals("admin") && password.equals("123456")) {
              mLoginView.showToast("登录成功");
              return;
          }
          mLoginView.showToast("登录失败");
      }
  }
  ```
4. 新建 `ViewController` ，View层接口方法跟界面控制（包括**用户交互监听事件**）在这里实现，在这里可以通过持有Presenter层接口协议的引用对象 `PresenterContract` 去调用相应的实现方法。
  
  最后：别忘了实现getLayoutResId()方法！
  
  ```java
  public class LoginView extends LoginDelegate { //继承自对应的业务逻辑代理类

      @BindView(R.id.edit_username) EditText etUserName;
      @BindView(R.id.edit_password) EditText etPassword;

      private LoginContract.Presenter mLoginPresenter = this; //PresenterContract的引用对象

      @Override
      protected int getLayoutResId() {
          return R.layout.activity_login;
      }

      @OnClick({R.id.btn_login})
      public void onViewClicked(View view) { //用户点击登录按钮
          String username = etUserName.getText().toString().trim();
          String password = etPassword.getText().toString().trim();
          mLoginPresenter.userLogin(username, password); //调用Presenter层的方法
      }

      @Override
      public void showToast(String str) {
          Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
      }
  }
  ```
  运行的效果就是，当用户点击登录按钮， `ViewController` 会调用Presenter层的userLogin()方法，验证账号密码，然后 `ViewDelegate` 再回调View层的showToast()方法弹出提示结果。这个例子虽然简单，关键在于理解AbstractMVP的实现原理！你品，你细品！
  
授人予鱼
-----
  
1. 结合实际开发中的经验，我把这个项目重新封装成依赖库，也分享给大家。

  首先，在项目的build.gradle文件中添加以下配置
  ```
  repositories {
      maven {
          url "https://jitpack.io"
      }
  }
  ```
  然后在app的build.gradle文件中添加以下依赖
  ```
  implementation 'com.github.GitSmark:AbstractMVP:2.0'
  ```
  
  **使用说明**： `Activity` 跟 `Fragment` 视图呈现方式不太一样，因此Fragment基类中多了一个 `rootView` ，还加入了懒加载，具体实现请查看 `CommonFragment` 。针对界面复用的问题（比如业务需要把Fragment换成Activity，这可不仅仅是改改类名的问题，更多的是一大堆生命周期需要去修改），**为了弱化`ViewController`的界面属性，让View层只关注视图呈现部分**，我给 `CommonActivity` 新增了以下两个常用方法，对标Fragment：
 - `getActivity()` 
 - `getContext()`
 
  如上方例子中的showToast()方法可以改成这样，
  ```java
      @Override
      public void showToast(String str) {
          Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
      }
  }
  ```
  此时， `ViewDelegate` 是`Activity` 还是`Fragment` ，都与 `ViewController` 无关。这里也推荐使用[ButterKnife](https://github.com/JakeWharton/butterknife)，简化代码，减少工作量的同时可以让View层更独立（体现在 Activity 跟 Fragment 中 findViewById() 的差异），在业务逻辑变更的时候无需改动View层代码。
  
  **特别说明**：AbstractMVP结构把数据存放在Presenter层，把`Adapter`放在View层，针对多场景下View层和Presenter层的数据交互问题，封装了 `CommonView` DataBinding 通用接口，并搭配使用[McAdapter](https://github.com/GitSmark/McAdapter)，实现可拔插多布局列表，支持item多处复用。
  
  AbstractMVP支持 `ViewController` 和 `ViewDelegate` 一对一，一对多，多对一，~~多对多~~，**降低耦合，代码复用，层级职责更明显，易于单元测试**，更多使用方法详见示例。

2. 使用 `Live Template` 快速生成对应文件，提高生产力，相关模板文件已封装好放在file/templates里面，欢迎下载使用，使用方法请自行百度。

Contact
--------
  Have problem? Just [tweet me](https://twitter.com/huangxy) or [send me an email](mailto:huangxy8023@foxmail.com).

License
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

