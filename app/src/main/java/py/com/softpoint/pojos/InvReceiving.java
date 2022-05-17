package py.com.softpoint.pojos;

import java.io.Serializable;
import java.util.Date;

/**
*
* @author eleon
*/
public class InvReceiving implements Serializable {
    
    private Long identifier;
    private Long orgId;
    private Long unitId;
    private String receivingNo;
    private Date rcvDate;
    private String status;
    private Long siteId;
    private String receivingClass;
    private String createdBy;
    private Date createdOn;
    private Long vendorId;
    private Long vendorSiteId;
    private Long warehouseId;
    private Long receiverId;
    private Long purchaseOrderId;
    private String program;
    private String receiptPath;

    public InvReceiving() {
    }

    public InvReceiving(Long identifier, Long orgId, Long unitId, String receivingNo, Date rcvDate, String status, Long siteId, String receivingClass,
            String createdBy, Date createdOn, Long vendorId, Long vendorSiteId, Long warehouseId, Long receiverId, Long purchaseOrderId, 
            String program, String receiptPath) {
        
        this.identifier = identifier;
        this.orgId = orgId;
        this.unitId = unitId;
        this.receivingNo = receivingNo;
        this.rcvDate = rcvDate;
        this.status = status;
        this.siteId = siteId;
        this.receivingClass = receivingClass;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.vendorId = vendorId;
        this.vendorSiteId = vendorSiteId;
        this.warehouseId = warehouseId;
        this.receiverId = receiverId;
        this.purchaseOrderId = purchaseOrderId;
        this.program = program;
        this.receiptPath = receiptPath;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
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

    public String getReceivingNo() {
        return receivingNo;
    }

    public void setReceivingNo(String receivingNo) {
        this.receivingNo = receivingNo;
    }

    public Date getRcvDate() {
        return rcvDate;
    }

    public void setRcvDate(Date rcvDate) {
        this.rcvDate = rcvDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getReceivingClass() {
        return receivingClass;
    }

    public void setReceivingClass(String receivingClass) {
        this.receivingClass = receivingClass;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getVendorSiteId() {
        return vendorSiteId;
    }

    public void setVendorSiteId(Long vendorSiteId) {
        this.vendorSiteId = vendorSiteId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getReceiptPath() {
        return receiptPath;
    }

    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }
    
    
}
