package com.teamnova.ej.realreview.adapter;

import android.graphics.Bitmap;

/**
 * Created by ej on 2017-09-28.
 */

public class ShopAdd3_SetData {
    public String shopAdd3Tag;
    public String shopAdd3Title;
    public String shopAdd3Description;
    public Bitmap shopAdd3LvImage;

    public ShopAdd3_SetData() {

    }

    public ShopAdd3_SetData(Bitmap shopAdd3LvImage, String shopAdd3Tag, String shopAdd3Title, String shopAdd3Description) {
        this.shopAdd3LvImage = shopAdd3LvImage;
        this.shopAdd3Tag = shopAdd3Tag;
        this.shopAdd3Title = shopAdd3Title;
        this.shopAdd3Description = shopAdd3Description;
    }

    public void setShopAdd3LvImage(Bitmap shopAdd3LvImage) {
        this.shopAdd3LvImage = shopAdd3LvImage;
    }

    public void setShopAdd3Tag(String shopAdd3Tag) {
        this.shopAdd3Tag = shopAdd3Tag;
    }

    public void setShopAdd3Title(String shopAdd3Title) {
        this.shopAdd3Title = shopAdd3Title;
    }

    public void setShopAdd3Description(String shopAdd3Description) {
        this.shopAdd3Description = shopAdd3Description;
    }

    public Bitmap getShopAdd3LvImage() {
        return shopAdd3LvImage;

    }

    public String getShopAdd3Tag() {
        if (shopAdd3Title.isEmpty()) {
            return "";
        } else {
            return shopAdd3Tag;
        }

    }

    public String getShopAdd3Title() {
        if (shopAdd3Title.isEmpty()) {
            return "";
        } else {
            return shopAdd3Title;
        }


    }

    public String getShopAdd3Description() {
        return shopAdd3Description;
    }


}
