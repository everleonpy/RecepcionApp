package py.com.softpoint.pojos;

import java.io.Serializable;
import java.util.Date;

public class PoPurchOrdersProdsVw implements Serializable {

    private double amount;
    private double approvedQty;
    private String awaitedDate;
    private String barCode;
    private String cancelledBy;
    private String cancelledOn; //Fecha
    private String description;
    private String expirationDate;
    private long identifier;
    private String itemBarCode;
    private String itemDescription;
    private long itemId;
    private long itemNumber;
    private String itemStatus;
    private String itemType;
    private long itemUomId;
    private long orderId;
    private long orgId;
    private String productName;
    private double qtyReceivedTolPct;
    private double quantity;
    private double receivedQty;
    private String rejectExceedingsFlag;
    private long unitId;
    private String uomName;

    public PoPurchOrdersProdsVw(){}

    public PoPurchOrdersProdsVw(double amount, double approvedQty, String awaitedDate, String barCode, String cancelledBy, String cancelledOn,
                                String description, String expirationDate, long identifier, String itemBarCode, String itemDescription, long itemId,
                                long itemNumber, String itemStatus, String itemType, long itemUomId, long orderId, long orgId, String productName,
                                double qtyReceivedTolPct, double quantity, double receivedQty, String rejectExceedingsFlag, long unitId, String uomName) {

        this.amount = amount;
        this.approvedQty = approvedQty;
        this.awaitedDate = awaitedDate;
        this.barCode = barCode;
        this.cancelledBy = cancelledBy;
        this.cancelledOn = cancelledOn;
        this.description = description;
        this.expirationDate = expirationDate;
        this.identifier = identifier;
        this.itemBarCode = itemBarCode;
        this.itemDescription = itemDescription;
        this.itemId = itemId;
        this.itemNumber = itemNumber;
        this.itemStatus = itemStatus;
        this.itemType = itemType;
        this.itemUomId = itemUomId;
        this.orderId = orderId;
        this.orgId = orgId;
        this.productName = productName;
        this.qtyReceivedTolPct = qtyReceivedTolPct;
        this.quantity = quantity;
        this.receivedQty = receivedQty;
        this.rejectExceedingsFlag = rejectExceedingsFlag;
        this.unitId = unitId;
        this.uomName = uomName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getApprovedQty() {
        return approvedQty;
    }

    public void setApprovedQty(double approvedQty) {
        this.approvedQty = approvedQty;
    }

    public String getAwaitedDate() {
        return awaitedDate;
    }

    public void setAwaitedDate(String awaitedDate) {
        this.awaitedDate = awaitedDate;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public String getCancelledOn() {
        return cancelledOn;
    }

    public void setCancelledOn(String cancelledOn) {
        this.cancelledOn = cancelledOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public String getItemBarCode() {
        return itemBarCode;
    }

    public void setItemBarCode(String itemBarCode) {
        this.itemBarCode = itemBarCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(long itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public long getItemUomId() {
        return itemUomId;
    }

    public void setItemUomId(long itemUomId) {
        this.itemUomId = itemUomId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getQtyReceivedTolPct() {
        return qtyReceivedTolPct;
    }

    public void setQtyReceivedTolPct(double qtyReceivedTolPct) {
        this.qtyReceivedTolPct = qtyReceivedTolPct;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(double receivedQty) {
        this.receivedQty = receivedQty;
    }

    public String getRejectExceedingsFlag() {
        return rejectExceedingsFlag;
    }

    public void setRejectExceedingsFlag(String rejectExceedingsFlag) {
        this.rejectExceedingsFlag = rejectExceedingsFlag;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public String getUomName() {
        return uomName;
    }

    public void setUomName(String uomName) {
        this.uomName = uomName;
    }
}
