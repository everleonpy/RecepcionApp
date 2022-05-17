package py.com.softpoint.pojos;

import java.io.Serializable;

/**
 *
 * @author eleon
 */
public class InvUnitOfMeasure implements Serializable {
    
    private Long identifier;
    private String name;
    private String description;
    private String rootUnit;
    private Long rootOumId;
    private Long contentValue;
    private String magnitude;
    private String systemItem;
    private String activeFlag;

    public InvUnitOfMeasure() {
    }

    public InvUnitOfMeasure(Long identifier, String name, String description, String rootUnit, Long rootOumId, Long contentValue, 
            String magnitude, String systemItem, String activeFlag) {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.rootUnit = rootUnit;
        this.rootOumId = rootOumId;
        this.contentValue = contentValue;
        this.magnitude = magnitude;
        this.systemItem = systemItem;
        this.activeFlag = activeFlag;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRootUnit() {
        return rootUnit;
    }

    public void setRootUnit(String rootUnit) {
        this.rootUnit = rootUnit;
    }

    public Long getRootOumId() {
        return rootOumId;
    }

    public void setRootOumId(Long rootOumId) {
        this.rootOumId = rootOumId;
    }

    public Long getContentValue() {
        return contentValue;
    }

    public void setContentValue(Long contentValue) {
        this.contentValue = contentValue;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getSystemItem() {
        return systemItem;
    }

    public void setSystemItem(String systemItem) {
        this.systemItem = systemItem;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Override
    public String toString() {
        return identifier +"  " + description.trim();
    }
}
