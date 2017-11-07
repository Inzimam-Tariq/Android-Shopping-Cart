package com.qemasoft.alhabibshop.app.model;

/**
 * Created by Inzimam on 30-Oct-17.
 */

public class MenuSubCategory {

    private String menuSubCategoryId;
    private String menuSubCategoryName;

    public MenuSubCategory(String menuSubCategoryId, String menuSubCategoryName) {
        this.menuSubCategoryId = menuSubCategoryId;
        this.menuSubCategoryName = menuSubCategoryName;
    }

    public String getMenuSubCategoryId() {
        return menuSubCategoryId;
    }

    public void setMenuSubCategoryId(String menuSubCategoryId) {
        this.menuSubCategoryId = menuSubCategoryId;
    }

    public String getMenuSubCategoryName() {
        return menuSubCategoryName;
    }

    public void setMenuSubCategoryName(String menuSubCategoryName) {
        this.menuSubCategoryName = menuSubCategoryName;
    }
}
