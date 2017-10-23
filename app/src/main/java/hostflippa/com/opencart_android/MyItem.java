package hostflippa.com.opencart_android;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class MyItem {
    private String itemId;
    private String itemTitle;
    private String itemPrice;
    private String itemImage;

    public MyItem(String itemId, String itemTitle, String itemPrice) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemPrice = itemPrice;
    }

    public MyItem(String itemId, String itemTitle, String itemPrice, String itemImage) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
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

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
