package inje.common.exception;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * 예외 코드 및 메시지 VO
 * @author  유기목
 * @since    2015. 10. 2.
 * @version 1.0
 * @see
 *  
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일          수정자                        수정내용
 *  ----------------     ----------      --------------------------------------------
 *  2015. 10. 2.       유기목        최초 생성
 *  </pre>
 */
public class ExceptionVO implements Serializable {

    private static final long serialVersionUID = 7488265703409663742L;

    /** 에러코드 **/
    private String errorCode;
    
    /** 에러메시지 **/
    private String errorMsg;

    public ExceptionVO() {
    }
    
    public ExceptionVO(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
