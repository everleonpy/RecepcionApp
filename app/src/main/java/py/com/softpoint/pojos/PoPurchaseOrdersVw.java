package py.com.softpoint.pojos;

import java.io.Serializable;
import java.util.Date;

public class PoPurchaseOrdersVw implements Serializable
{

    private String comments;
    private Integer vendorSiteId;
    private Integer maxEarlyDays;
    private String overRecptAction;
    private Integer receivingId;
    private String vndSiteName;
    private String orderType;
    private Double volumenOvrTolPct;
    private Double stdOvrTolPct;
    private Integer maxLateDays;
    private Date promisedDate;
    private String receivingAction;
    private String siteName;
    private String notifyTo;
    private String poNumber; // Nro OC
    private String currencyName;
    private String poStatus;
    private String purchaserName;
    private Long purchaserId;
    private String fullReceipt;
    private Double weightOvrTolPct;
    private Long currendyId;
    private String vendorName;
    private Double lcAmount;
    private Double amount;
    private Date expirationDate;
    private Date awaitedDate;
    private Long unitId;
    private Long identifier;
    private Long vendorId;
    private Long orgId;
    private Date poDate;
    private Long siteId;

    public PoPurchaseOrdersVw() {
    }

    public PoPurchaseOrdersVw(String comments, Integer vendorSiteId, Integer maxEarlyDays, String overRecptAction, Integer receivingId, String vndSiteName,
                              String orderType, Double volumenOvrTolPct, Double stdOvrTolPct, Integer maxLateDays, Date promisedDate, String receivingAction,
                              String siteName, String notifyTo, String poNumber, String currencyName, String poStatus, String purchaserName, Long purchaserId,
                              String fullReceipt, Double weightOvrTolPct, Long currendyId, String vendorName, Double lcAmount, Double amount, Date expirationDate,
                              Date awaitedDate, Long unitId, Long identifier, Long vendorId, Long orgId, Date poDate, Long siteId) {

        this.comments = comments;
        this.vendorSiteId = vendorSiteId;
        this.maxEarlyDays = maxEarlyDays;
        this.overRecptAction = overRecptAction;
        this.receivingId = receivingId;
        this.vndSiteName = vndSiteName;
        this.orderType = orderType;
        this.volumenOvrTolPct = volumenOvrTolPct;
        this.stdOvrTolPct = stdOvrTolPct;
        this.maxLateDays = maxLateDays;
        this.promisedDate = promisedDate;
        this.receivingAction = receivingAction;
        this.siteName = siteName;
        this.notifyTo = notifyTo;
        this.poNumber = poNumber;
        this.currencyName = currencyName;
        this.poStatus = poStatus;
        this.purchaserName = purchaserName;
        this.purchaserId = purchaserId;
        this.fullReceipt = fullReceipt;
        this.weightOvrTolPct = weightOvrTolPct;
        this.currendyId = currendyId;
        this.vendorName = vendorName;
        this.lcAmount = lcAmount;
        this.amount = amount;
        this.expirationDate = expirationDate;
        this.awaitedDate = awaitedDate;
        this.unitId = unitId;
        this.identifier = identifier;
        this.vendorId = vendorId;
        this.orgId = orgId;
        this.poDate = poDate;
        this.siteId = siteId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getVendorSiteId() {
        return vendorSiteId;
    }

    public void setVendorSiteId(Integer vendorSiteId) {
        this.vendorSiteId = vendorSiteId;
    }

    public Integer getMaxEarlyDays() {
        return maxEarlyDays;
    }

    public void setMaxEarlyDays(Integer maxEarlyDays) {
        this.maxEarlyDays = maxEarlyDays;
    }

    public String getOverRecptAction() {
        return overRecptAction;
    }

    public void setOverRecptAction(String overRecptAction) {
        this.overRecptAction = overRecptAction;
    }

    public Integer getReceivingId() {
        return receivingId;
    }

    public void setReceivingId(Integer receivingId) {
        this.receivingId = receivingId;
    }

    public String getVndSiteName() {
        return vndSiteName;
    }

    public void setVndSiteName(String vndSiteName) {
        this.vndSiteName = vndSiteName;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Double getVolumenOvrTolPct() {
        return volumenOvrTolPct;
    }

    public void setVolumenOvrTolPct(Double volumenOvrTolPct) {
        this.volumenOvrTolPct = volumenOvrTolPct;
    }

    public Double getStdOvrTolPct() {
        return stdOvrTolPct;
    }

    public void setStdOvrTolPct(Double stdOvrTolPct) {
        this.stdOvrTolPct = stdOvrTolPct;
    }

    public Integer getMaxLateDays() {
        return maxLateDays;
    }

    public void setMaxLateDays(Integer maxLateDays) {
        this.maxLateDays = maxLateDays;
    }

    public Date getPromisedDate() {
        return promisedDate;
    }

    public void setPromisedDate(Date promisedDate) {
        this.promisedDate = promisedDate;
    }

    public String getReceivingAction() {
        return receivingAction;
    }

    public void setReceivingAction(String receivingAction) {
        this.receivingAction = receivingAction;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getNotifyTo() {
        return notifyTo;
    }

    public void setNotifyTo(String notifyTo) {
        this.notifyTo = notifyTo;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(String poStatus) {
        this.poStatus = poStatus;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public String getFullReceipt() {
        return fullReceipt;
    }

    public void setFullReceipt(String fullReceipt) {
        this.fullReceipt = fullReceipt;
    }

    public Double getWeightOvrTolPct() {
        return weightOvrTolPct;
    }

    public void setWeightOvrTolPct(Double weightOvrTolPct) {
        this.weightOvrTolPct = weightOvrTolPct;
    }

    public Long getCurrendyId() {
        return currendyId;
    }

    public void setCurrendyId(Long currendyId) {
        this.currendyId = currendyId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Double getLcAmount() {
        return lcAmount;
    }

    public void setLcAmount(Double lcAmount) {
        this.lcAmount = lcAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getAwaitedDate() {
        return awaitedDate;
    }

    public void setAwaitedDate(Date awaitedDate) {
        this.awaitedDate = awaitedDate;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getPoDate() {
        return poDate;
    }

    public void setPoDate(Date poDate) {
        this.poDate = poDate;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
}