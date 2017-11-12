package com.qemasoft.alhabibshop.app.model;

import java.util.List;

/**
 * Created by Inzimam on 30-Oct-17.
 */

public class MenuCategory {

    private String menuCategoryId;
    private String menuCategoryName;
    private String menuCategoryImage;
    private List<MenuSubCategory> menuSubCategory;

    public MenuCategory(String menuCategoryId, String menuCategoryName,
                        String menuCategoryImage, List<MenuSubCategory> menuSubCategory) {
        this.menuCategoryId = menuCategoryId;
        this.menuCategoryName = menuCategoryName;
        this.menuCategoryImage = menuCategoryImage;
        this.menuSubCategory = menuSubCategory;
    }

    public String getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(String menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public String getMenuCategoryName() {
        return menuCategoryName;
    }

    public void setMenuCategoryName(String menuCategoryName) {
        this.menuCategoryName = menuCategoryName;
    }

    public String getMenuCategoryImage() {
        return menuCategoryImage;
    }

    public void setMenuCategoryImage(String menuCategoryImage) {
        this.menuCategoryImage = menuCategoryImage;
    }

    public List<MenuSubCategory> getMenuSubCategory() {
        return menuSubCategory;
    }

    public void setMenuSubCategory(List<MenuSubCategory> menuSubCategory) {
        this.menuSubCategory = menuSubCategory;
    }
}

