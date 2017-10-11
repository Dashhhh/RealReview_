package com.teamnova.ej.realreview.util;

/**
 * Created by user on 2015-12-01.
 */
public class sql_control {


    public static void pwUpdate(String id, String pw) { //changing pw
        Signup_idchk pwchange = new Signup_idchk(id, pw, 0);
        Signup_idchk.active = true;
        pwchange.start();
    }

    public static void sendingPW(String pw, String mail) { //changing pw
        Signup_idchk sendingPW = new Signup_idchk(pw, mail);
        Signup_idchk.active = true;
        sendingPW.start();
    }

    public static void findingPW(String id, String Name, String Mail) { //changing pw
        Signup_idchk findingpw = new Signup_idchk(id, Name, Mail);
        Signup_idchk.active = true;
        findingpw.start();
    }

    static public void get_userInfo(String id, int type) { //getting information
        Signup_idchk getinfo = new Signup_idchk(id, type);
        Signup_idchk.active = true;
        getinfo.start();
    }

    static public void userUpdate(String id, String name, String age, String phone, String mail, String address) {   //changing information
        Signup_idchk updateinfo = new Signup_idchk(id, name, age, phone, mail, address);
        Signup_idchk.active = true;
        updateinfo.start();
    }

    static public void userRegist(String id, String name, String age, String phone, String mail, String address, String pw) {    //sign up
        Signup_idchk registinfo = new Signup_idchk(id, name, age, phone, mail, address, pw);
        Signup_idchk.active = true;
        registinfo.start();
    }


    static public void idChk(String id) {    //id check
        Signup_idchk idchk = new Signup_idchk(id, 2);
        Signup_idchk.active = true;
        idchk.start();
    }


}
