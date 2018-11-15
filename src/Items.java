public class Items {

    private String itemType;
    private String itemName;
    private int itemQuantity;
    private double itemCost;
    private String dateAdded;

    public Items(String itemType, String itemName, int itemQuantity, double itemCost, String dateAdded) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemCost = itemCost;
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return itemType + "," + itemName + "," + itemQuantity + "," + itemCost
                + "," + dateAdded;
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
