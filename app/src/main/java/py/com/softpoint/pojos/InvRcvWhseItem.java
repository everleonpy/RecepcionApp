package py.com.softpoint.pojos;

import java.io.Serializable;

/**
*  Items Ordenes de Compras
*/
public class InvRcvWhseItem implements Serializable {

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

}
