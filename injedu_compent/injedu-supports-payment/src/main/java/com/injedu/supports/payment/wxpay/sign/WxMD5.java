package com.injedu.supports.payment.wxpay.sign;


import java.security.MessageDigest;

/**
 * User: rizenguo
 * Date: 2014/10/23
 * Time: 15:43
 */
public class WxMD5 {
	
	private final static String CHARSET = "utf-8";
	
    private final static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        int j = b.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = b[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
        return new String(str);
    }

    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes(CHARSET)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
    
}
