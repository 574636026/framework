package com.injedu.easycode.maintain.context;



/**
 * 代码生成工具异常类
 * 
 * User: liyd
 * Date: 13-11-28
 * Time: 下午5:55
 */
public class EasyCodeException extends RuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = -6599206504765759300L;

    /** Exception code */
    protected String          resultCode       = "UN_KNOWN_EXCEPTION";

    /** Exception message */
    protected String          resultMsg        = "未知异常";

    /**
     * Constructor
     */
    public EasyCodeException() {
        super();
    }

    /**
     * Constructor
     *
     * @param message the message
     */
    public EasyCodeException(String message) {
        super(message);
        this.resultMsg = message;
    }

    /**
     * Constructor
     * 
     * @param e
     */
    public EasyCodeException(Throwable e) {
        super(e);
        this.resultMsg = e.getMessage();
    }

    /**
     * Constructor
     *
     * @param code the code
     * @param message the message
     */
    public EasyCodeException(String code, String message) {
        super();
        this.resultCode = code;
        this.resultMsg = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
