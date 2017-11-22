package com.qemasoft.alhabibshop.app.model;

public class Slideshow{
	private String image;
	private String id;
	private String bannerType;


    public Slideshow(String image, String id, String bannerType) {
        this.image = image;
        this.id = id;
        this.bannerType = bannerType;
    }

    public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setBannerType(String bannerType){
		this.bannerType = bannerType;
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
