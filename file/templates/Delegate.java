#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import androidx.viewbinding.ViewBinding;

import com.huangxy.abstractmvp.common.CommonActivity;

#set($PREFIX = ${StringUtils.sub($NAME, "Delegate", "")})
public abstract class ${PREFIX}Delegate<T extends ViewBinding> extends CommonActivity<T> implements ${PREFIX}Contract.Biz {

	private ${PREFIX}Contract.View m${PREFIX}View = this;

	@Override
	protected void initData() {
		//初始化数据
	}

	//这里实现Presenter层的接口，监听生命周期事件，执行业务逻辑
}