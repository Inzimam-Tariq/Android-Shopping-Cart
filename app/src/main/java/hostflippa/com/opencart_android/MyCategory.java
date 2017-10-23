package hostflippa.com.opencart_android;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class MyCategory {
    private String itemId;
    private String itemTitle;
    private int catImage;

    public MyCategory( String itemTitle) {
        this.itemTitle = itemTitle;
    }
    public MyCategory( String itemTitle, int image) {
        this.itemTitle = itemTitle;
        this.catImage = image;
    }

    public MyCategory(String itemId, String itemTitle, String itemPrice, int catImage) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.catImage = catImage;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getCatImage() {
        return catImage;
    }

    public void setCatImage(int catImage) {
        this.catImage = catImage;
    }
}
