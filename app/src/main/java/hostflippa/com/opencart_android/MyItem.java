package hostflippa.com.opencart_android;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class MyItem {
    private String itemId;
    private String itemTitle;
    private String itemPriceFull;
    private String itemPriceDisc;
    private String itemImage;


    public MyItem(String itemId, String itemTitle, String itemPriceFull) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemPriceFull = itemPriceFull;
    }

    public MyItem(String itemId, String itemTitle, String itemPriceDisc,String itemPriceFull) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemPriceFull = itemPriceFull;
        this.itemPriceDisc = itemPriceDisc;
    }
    public MyItem(String itemId, String itemTitle, String itemPriceDisc,String itemPriceFull, String itemImage) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemPriceFull = itemPriceFull;
        this.itemPriceDisc = itemPriceDisc;
        this.itemImage = itemImage;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemPriceDisc() {
        return itemPriceDisc;
    }

    public String getItemPriceFull() {
        return itemPriceFull;
    }

    public String getItemImage() {
        return itemImage;
    }

}
