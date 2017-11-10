package com.teamnova.ej.realreview.adapter;

/**
 * Created by ej on 2017-11-10.
 */

public class ShopDetail_Answer_Viewing_Set {

    private String type;
    private String idx;
    private String nick_imagepath;
    private String regdate;
    private String answer;
    private String question_idx;
    private String nick;
    private String id_shop;
    private String id_user;
    private String replycount;


    public ShopDetail_Answer_Viewing_Set() {
    }

    public ShopDetail_Answer_Viewing_Set(String type, String idx, String nick_imagepath, String regdate, String question_idx, String nick, String id_shop, String id_user, String answer, String replycount) {
        this.type = type;
        this.idx = idx;
        this.nick_imagepath = nick_imagepath;
        this.regdate = regdate;
        this.question_idx = question_idx;
        this.nick = nick;
        this.id_shop = id_shop;
        this.id_user = id_user;
        this.answer = answer;
        this.replycount = replycount;
    }

    public String getQuestion_idx() {
        return question_idx;
    }

    public void setQuestion_idx(String question_idx) {
        this.question_idx = question_idx;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop) {
        this.id_shop = id_shop;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getNick_imagepath() {
        return nick_imagepath;
    }

    public void setNick_imagepath(String nick_imagepath) {
        this.nick_imagepath = nick_imagepath;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getReplycount() {
        return replycount;
    }

    public void setReplycount(String replycount) {
        this.replycount = replycount;
    }
}
