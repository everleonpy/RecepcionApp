package py.com.softpoint.pojos;

/**
* @author eleon
*/
public class InvPoReceivingItemQTY
{
    private Long invMaterialId;
    private Double quantity;

    public InvPoReceivingItemQTY() {
    }

    public InvPoReceivingItemQTY(Long invMaterialId, Double quantity) {
        this.invMaterialId = invMaterialId;
        this.quantity = quantity;
    }

    public Long getInvMaterialId() {
        return invMaterialId;
    }

    public void setInvMaterialId(Long invMaterialId) {
        this.invMaterialId = invMaterialId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
