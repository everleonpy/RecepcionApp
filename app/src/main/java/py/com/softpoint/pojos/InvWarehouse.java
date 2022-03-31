package py.com.softpoint.pojos;

import java.io.Serializable;

public class InvWarehouse  implements Serializable {

    private String activeFlag;
    private String description;
    private Integer unitId;
    private Integer identifier;
    private String name;
    private Integer orgId;
    private Integer siteId;
    private Object createdOn;
    private Object createdBy;

    public InvWarehouse() {}

    public InvWarehouse(String activeFlag, String description, Integer unitId, Integer identifier,
                        String name, Integer orgId, Integer siteId, Object createdOn, Object createdBy) {
        this.activeFlag = activeFlag;
        this.description = description;
        this.unitId = unitId;
        this.identifier = identifier;
        this.name = name;
        this.orgId = orgId;
        this.siteId = siteId;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Object getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Object createdOn) {
        this.createdOn = createdOn;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return identifier +" "+ name;
    }
}
