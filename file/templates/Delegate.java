#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")
#set($PREFIX = ${StringUtils.sub($NAME, "Delegate", "")})
public abstract class ${PREFIX}Delegate extends CommonActivity implements ${PREFIX}Contract.Biz {

    private ${PREFIX}Contract.View m${PREFIX}View = this;

    //这里实现Presenter层的接口，监听生命周期事件，执行业务逻辑

}