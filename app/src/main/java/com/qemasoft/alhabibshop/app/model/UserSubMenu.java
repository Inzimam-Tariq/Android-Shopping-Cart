package com.qemasoft.alhabibshop.app.model;

/**
 * Created by Inzimam on 30-Oct-17.
 */

public class UserSubMenu {

    private String userSubMenuCode;
    private String userSubMenuTitle;
    private String userSubMenuSymbolLeft;
    private String userSubMenuSymbolRight;
    private String flagImage;

    public UserSubMenu() {
    }

    public UserSubMenu(String userSubMenuCode, String userSubMenuTitle,
                       String userSubMenuSymbolLeft, String userSubMenuSymbolRight,
                       String flagImage) {
        this.userSubMenuCode = userSubMenuCode;
        this.userSubMenuTitle = userSubMenuTitle;
        this.userSubMenuSymbolLeft = userSubMenuSymbolLeft;
        this.userSubMenuSymbolRight = userSubMenuSymbolRight;
        this.flagImage = flagImage;
    }

    public String getUserSubMenuCode() {
        return userSubMenuCode;
    }

    public String getUserSubMenuTitle() {
        return userSubMenuTitle;
    }

    public String getUserSubMenuSymbolLeft() {
        return userSubMenuSymbolLeft;
    }

    public String getUserSubMenuSymbolRight() {
        return userSubMenuSymbolRight;
    }

    public String getFlagImage() {
        return flagImage;
    }
}
