package com.qemasoft.alhabibshop.app.model;

/**
 * Created by Inzimam Tariq on 17-Oct-17.
 */

public class MyCategory {
    private String categoryId;
    private String categoryTitle;
    private String catImage;
    private String description;

    public MyCategory(String categoryId, String categoryTitle) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
    }

    public MyCategory(String categoryId, String categoryTitle, String catImage) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.catImage = catImage;
    }

    public MyCategory(String categoryId, String categoryTitle, String catImage,
                      String description) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.catImage = catImage;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

}
