package py.com.softpoint.pojos;

import java.io.Serializable;

/**
*  Items Ordenes de Compras
*/
public class InvRcvWhseItem implements Serializable
{

    private Long identifier;
    private Long orgId;
    private Long unitId;
    private String itemBarCode;
    private String itemClass;
    private String itemCode;
    private String itemDescription;
    private String itemType;
    private Long itemUomId;
    private String itemUomName;
    private Double quantity;

    public InvRcvWhseItem() {
    }

    public InvRcvWhseItem(Long identifier, Long orgId, Long unitId, String itemBarCode, String itemClass, String itemCode,
                          String itemDescription, String itemType, Long itemUomId, String itemUomName, Double quantity) {
        this.identifier = identifier;
        this.orgId = orgId;
        this.unitId = unitId;
        this.itemBarCode = itemBarCode;
        this.itemClass = itemClass;
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.itemType = itemType;
        this.itemUomId = itemUomId;
        this.itemUomName = itemUomName;
        this.quantity = quantity;
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

    public String getItemBarCode() {
        return itemBarCode;
    }

    public void setItemBarCode(String itemBarCode) {
        this.itemBarCode = itemBarCode;
    }

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Long getItemUomId() {
        return itemUomId;
    }

    public void setItemUomId(Long itemUomId) {
        this.itemUomId = itemUomId;
    }

    public String getItemUomName() {
        return itemUomName;
    }

    public void setItemUomName(String itemUomName) {
        this.itemUomName = itemUomName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
