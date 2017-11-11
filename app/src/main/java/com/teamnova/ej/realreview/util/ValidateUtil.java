package com.teamnova.ej.realreview.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ej on 2017-09-15.
 */

public class ValidateUtil {
    // 이메일정규식
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    //비밀번호정규식
    public static final Pattern VALID_PASSWORD_REGEX_ALPHA_NUM = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$"); // 4자리 ~ 16자리까지 가능

    public static boolean validatePassword(String pwStr) {
        Matcher matcher = VALID_PASSWORD_REGEX_ALPHA_NUM.matcher(pwStr);
        return matcher.matches();
    }

    //닉네임정규식
    public static final Pattern VALID_NICK_REGEX_ALPHA_NUM = Pattern.compile("^[A-Za-z0-9_-]{5,12}$"); // 4자리 ~ 16자리까지 가능
    public static boolean validateNick(String nickStr) {
        Matcher matcher = VALID_NICK_REGEX_ALPHA_NUM.matcher(nickStr);
        return matcher.matches();
    }

    //HTTP 정규식
//    public static final Pattern VALID_HTTP_REGEX = Pattern.compile("/^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w_.-]*)*/?$/"); // 4자리 ~ 16자리까지 가능
    public static final Pattern VALID_HTTP_REGEX = Pattern.compile("/^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w_\\.-]*)*\\/?$/"); // 4자리 ~ 16자리까지 가능
    public static boolean validateHttp(String httpStr) {
        Matcher matcher = VALID_HTTP_REGEX.matcher(httpStr);
        return matcher.matches();
    }





}