package py.com.softpoint.pojos;

import java.io.Serializable;
import java.util.Date;

/**
* 
* @author eleon
*/
public class InvPoReceivingItem  implements Serializable {
    
    private Long identifier;
    private Long receivingId;
    private Long purchOrderId;
    private Long orgId;
    private Long unitId;
    private String itemType;
    private Long itemNumber;
    private Double quantity;
    private String description;
    private String createdBy;
    private Date createOn;
    private String modifiedBy;
    private Date modifiedOn;
    private Long invMaterialId;
    private String invMtlBarCode;
    private Long itemUomId;
    private String lotNumber;
    private String serialNumber;
    private Date expiryDate;
    private Date manufactDate;
    private Long serviceId;
    private Long poLineId;
    private Long locationId;
    private Double bonusQty;
    private String comments;
    private String itemClass;
    private Long deviceId;

    public InvPoReceivingItem() {
    }

    public InvPoReceivingItem(Long identifier, Long receivingId, Long purchOrderId, Long orgId, Long unitId, String itemType,
            Long itemNumber, Double quantity, String description, String createdBy, Date createOn, String modifiedBy, Date modifiedOn, 
            Long invMaterialId, String invMtlBarCode, Long itemUomId, String lotNumber, String serialNumber, Date expiryDate, Date manufactDate, 
            Long serviceId, Long poLineId, Long locationId, Double bonusQty, String comments, String itemClass, Long deviceId) {
        
        this.identifier = identifier;
        this.receivingId = receivingId;
        this.purchOrderId = purchOrderId;
        this.orgId = orgId;
        this.unitId = unitId;
        this.itemType = itemType;
        this.itemNumber = itemNumber;
        this.quantity = quantity;
        this.description = description;
        this.createdBy = createdBy;
        this.createOn = createOn;
        this.modifiedBy = modifiedBy;
        this.modifiedOn = modifiedOn;
        this.invMaterialId = invMaterialId;
        this.invMtlBarCode = invMtlBarCode;
        this.itemUomId = itemUomId;
        this.lotNumber = lotNumber;
        this.serialNumber = serialNumber;
        this.expiryDate = expiryDate;
        this.manufactDate = manufactDate;
        this.serviceId = serviceId;
        this.poLineId = poLineId;
        this.locationId = locationId;
        this.bonusQty = bonusQty;
        this.comments = comments;
        this.itemClass = itemClass;
        this.deviceId = deviceId;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public Long getReceivingId() {
        return receivingId;
    }

    public void setReceivingId(Long receivingId) {
        this.receivingId = receivingId;
    }

    public Long getPurchOrderId() {
        return purchOrderId;
    }

    public void setPurchOrderId(Long purchOrderId) {
        this.purchOrderId = purchOrderId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Long getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Long itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getInvMaterialId() {
        return invMaterialId;
    }

    public void setInvMaterialId(Long invMaterialId) {
        this.invMaterialId = invMaterialId;
    }

    public String getInvMtlBarCode() {
        return invMtlBarCode;
    }

    public void setInvMtlBarCode(String invMtlBarCode) {
        this.invMtlBarCode = invMtlBarCode;
    }

    public Long getItemUomId() {
        return itemUomId;
    }

    public void setItemUomId(Long itemUomId) {
        this.itemUomId = itemUomId;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getManufactDate() {
        return manufactDate;
    }

    public void setManufactDate(Date manufactDate) {
        this.manufactDate = manufactDate;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getPoLineId() {
        return poLineId;
    }

    public void setPoLineId(Long poLineId) {
        this.poLineId = poLineId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Double getBonusQty() {
        return bonusQty;
    }

    public void setBonusQty(Double bonusQty) {
        this.bonusQty = bonusQty;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
    
}
