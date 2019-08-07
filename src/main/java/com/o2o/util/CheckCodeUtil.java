package com.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author He
 * @Date 2019/7/30
 * @Time 19:14
 * @Description TODO
 **/

public class CheckCodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest req){
        //从session中拿到验证码图片中的字符串
        String verifycode = (String) req.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(req, "verifyCodeActual");
        //System.out.println(verifycode+"==="+verifyCodeActual);
        return verifyCodeActual != null && verifyCodeActual.equals(verifycode);
    }
}
