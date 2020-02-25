package inje.common.exception;

/**
 * 비즈니스 입셉션 처리
 * @author  유기목
 * @since    2015. 9. 7.
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *      수정일          수정자                        수정내용
 *  ----------------     ----------      --------------------------------------------
 *   2015. 9. 7.       유기목        최초 생성
 *  </pre>
 */
@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {
    protected String code;
    protected String defaultMessage;
    protected Object[] arguments;
    protected Exception exception;

    public BusinessException(String code) {
        this(code, "", null, null);
    }

    public BusinessException(String code, Exception ex) {
        this(code, "", null, ex);
    }

    public BusinessException(String code, Object[] arguments) {
        this(code, "", arguments, null);
    }

    public BusinessException(String code, Object[] arguments, Exception ex) {
        this(code, "", arguments, ex);
    }

    public BusinessException(String code, String defaultMessage,
            Object[] arguments) {
        this(code, defaultMessage, arguments, null);
    }

    public BusinessException(String code, String defaultMessage,
            Object[] arguments, Exception ex) {
        super(code, ex);

        this.code = code;
        this.defaultMessage = defaultMessage;
        
        // this.arguments = arguments;
        // secure coding
        if(arguments == null) {
            this.arguments = null;
        } else {
            this.arguments = new Object[arguments.length];
            for (int i = 0; i < arguments.length; ++i) {
                this.arguments[i] = arguments[i];
            }
        }
        
        this.exception = ex;
    }

    public String getCode() {
        return this.code;
    }

    public String getDefaultMessage() {
        return this.defaultMessage;
    }
    
/*    public Object[] getArguments() {
        return this.arguments;
    }*/

    //secure coding
    public Object[] getArguments() {
        Object[] retArguments = null;
        
        if (arguments != null) { 
            retArguments = new Object[arguments.length];
            for (int i = 0; i < arguments.length; i++) {
                retArguments[i] = this.arguments[i];
            }
        }
        
        return retArguments;
    }

    public Exception getException() {
        return this.exception;
    }
}
