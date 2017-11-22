package com.qemasoft.alhabibshop.app.model;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class MyPromotion {
    private String id;
    private String title;
    private String description;

    public MyPromotion(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public MyPromotion(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
