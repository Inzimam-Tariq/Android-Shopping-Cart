package com.qemasoft.alhabibshop.app.model;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class MyCategory {
    private String categoryId;
    private String categoryTitle;
    private String catImage;

    public MyCategory(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public MyCategory(String categoryTitle, String catImage) {
        this.categoryTitle = categoryTitle;
        this.catImage = catImage;
    }

    public MyCategory(String categoryId, String categoryTitle, String catImage) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.catImage = catImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public String getCatImage() {
        return catImage;
    }

}
