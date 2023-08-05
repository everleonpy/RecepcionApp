package py.com.softpoint.pojos;

import java.io.Serializable;

/**
 *
 * @author eleon
 */
public class InvMaterial implements Serializable
{
    
    private Long identifier;
    private String internalCode;
    private String barCode;
    private String scaleCode;
    private String description;
    private Long baseUomId;
    private Long unitId;
    private Long orgId;
    private Long vendorId;
    private String activeFlag;

    public InvMaterial() {
    }

    public InvMaterial(Long identifier, String internalCode, String barCode, String scaleCode, String description, Long baseUomId, 
            Long unitId, Long orgId, Long vendorId, String activeFlag) {
        this.identifier = identifier;
        this.internalCode = internalCode;
        this.barCode = barCode;
        this.scaleCode = scaleCode;
        this.description = description;
        this.baseUomId = baseUomId;
        this.unitId = unitId;
        this.orgId = orgId;
        this.vendorId = vendorId;
        this.activeFlag = activeFlag;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getScaleCode() {
        return scaleCode;
    }

    public void setScaleCode(String scaleCode) {
        this.scaleCode = scaleCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBaseUomId() {
        return baseUomId;
    }

    public void setBaseUomId(Long baseUomId) {
        this.baseUomId = baseUomId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }
    
}
