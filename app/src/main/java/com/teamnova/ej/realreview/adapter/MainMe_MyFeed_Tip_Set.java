package com.teamnova.ej.realreview.adapter;

/**
 * Created by ej on 2017-12-01.
 */

public class MainMe_MyFeed_Tip_Set {


    /**
     * Tip Var
     */

    String type;
    String idx;
    String shop_id; // Glide -> Convert URL & Viewing on getView
    String user_id;
    String tip;
    String regdate;
    String nearby;
    String nick;
    String locality;
    String shopTipCount;
    String shopidCheck;
    String shopImagePath;
    String shopTipRowNum;
    String shopName;

    public MainMe_MyFeed_Tip_Set() {
    }

    public MainMe_MyFeed_Tip_Set(

            String type,
            String idx,
            String shop_id,
            String user_id,
            String tip,
            String regdate,
            String nearby,
            String nick,
            String locality,
            String shopTipCount,
            String shopidCheck,
            String shopImagePath,
            String shopTipRowNum,
            String shopName
    ) {
        this.type= type;
        this.idx= idx;
        this.shop_id= shop_id;
        this.user_id= user_id;
        this.tip= tip;
        this.regdate= regdate;
        this.nearby= nearby;
        this.nick= nick;
        this.locality= locality;
        this.shopTipCount= shopTipCount;
        this.shopidCheck= shopidCheck;
        this.shopImagePath= shopImagePath;
        this.shopTipRowNum= shopTipRowNum;
        this.shopName= shopName;

    }

}
