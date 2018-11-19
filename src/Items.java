public class Items {

    private String itemType;
    private String itemName;
    private int itemQuantity;
    private double itemCost;
    private String dateAdded;
    private double itemDiscount;
    private double itemNewCost;
    private int totalSold;

    public Items(String itemName, double itemCost, double itemDiscount, double itemNewCost) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemDiscount = itemDiscount;
        this.itemNewCost = itemNewCost;
    }

    public Items(String itemType, String itemName, int itemQuantity, double itemCost, double itemDiscount,
                 double itemNewCost, String dateAdded) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemCost = itemCost;
        this.itemDiscount = itemDiscount;
        this.itemNewCost = itemNewCost;
        this.dateAdded = dateAdded;
    }

    public Items(String itemName, double itemCost) {
        this.itemName = itemName;
        this.itemCost = itemCost;
    }

    public Items(String itemType, String itemName, int itemQuantity, double itemCost, String dateAdded) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemCost = itemCost;
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return itemType + "," + itemName + "," + itemQuantity + "," + itemCost + ","
                + itemDiscount + "," + itemNewCost + "," + dateAdded;
    }

    public double getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(double itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public double getItemNewCost() {
        return itemNewCost;
    }

    public void setItemNewCost(double itemNewCost) {
        this.itemNewCost = itemNewCost;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }
}
