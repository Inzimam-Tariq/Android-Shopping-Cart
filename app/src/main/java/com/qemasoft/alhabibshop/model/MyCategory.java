package com.qemasoft.alhabibshop.model;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class MyCategory {
    private String categoryId;
    private String categoryTitle;
    private int catImage;

    public MyCategory( String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public MyCategory(String categoryTitle, int catImage) {
        this.categoryTitle = categoryTitle;
        this.catImage = catImage;
    }

    public MyCategory(String categoryId, String categoryTitle, int catImage) {
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

    public int getCatImage() {
        return catImage;
    }

}
