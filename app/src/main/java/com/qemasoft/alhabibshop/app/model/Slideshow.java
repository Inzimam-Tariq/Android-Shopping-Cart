package com.qemasoft.alhabibshop.app.model;

public class Slideshow{
	private String image;
	private String id;
	private String bannerType;

    public Slideshow(String image) {
        this.image = image;
    }

    public Slideshow(String image, String id, String bannerType) {
        this.image = image;
        this.id = id;
        this.bannerType = bannerType;
    }


	public String getImage(){
		return image;
	}

	public String getId(){
		return id;
	}

	public String getBannerType(){
		return bannerType;
	}

	@Override
 	public String toString(){
		return 
			"Slideshow{" + 
			"image = '" + image + '\'' + 
			",id = '" + id + '\'' + 
			",bannerType = '" + bannerType + '\'' +
			"}";
		}
}
