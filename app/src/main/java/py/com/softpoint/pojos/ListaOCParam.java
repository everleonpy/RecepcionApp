package py.com.softpoint.pojos;

import java.io.Serializable;

/**
 *
 * @author eleon
 */
public class ListaOCParam  implements Serializable{
    
    private Long vendorId;
    private Long siteId;
    private String fechaDesde;
    private String fechaHasta;

    public ListaOCParam() {
    }

    public ListaOCParam(Long vendorId, Long siteId, String fechaDesde, String fechaHasta) {
        this.vendorId = vendorId;
        this.siteId = siteId;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
    
}
