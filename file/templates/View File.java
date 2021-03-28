#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")
#set($PREFIX = ${StringUtils.sub($NAME, "View", "")})
#set($PREFIX = ${StringUtils.sub($PREFIX, "Activity", "")})
#set($PREFIX = ${StringUtils.sub($PREFIX, "Fragment", "")})
public class ${NAME} extends ${PREFIX}Delegate<Activity${PREFIX}Binding> {

	private ${PREFIX}Contract.Presenter m${PREFIX}Presenter = this;

	@Override
	protected void initView() {
		//初始化界面
	}

	//这里实现View层的接口，监听用户交互事件，控制界面展示
}