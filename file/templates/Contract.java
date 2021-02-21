#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")
#set($PREFIX = ${StringUtils.sub($NAME, "Contract", "")})
public class ${PREFIX}Contract {

    public interface View {
        //这里写View层的业务接口
    }

    public interface Presenter {
        //这里写Presenter层的业务接口
    }

    public interface Biz extends View, Presenter {
        
    }
}
