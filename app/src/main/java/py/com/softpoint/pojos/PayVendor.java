package py.com.softpoint.pojos;

import java.io.Serializable;

public class PayVendor implements Serializable {

    private Long Identifier;
    private Long OrgId;
    private Long UnitId;
    private String Name;
    private String Code;
    private String TaxNumber;
    private String IdentityNumber;

    public Long getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(Long identifier) {
        Identifier = identifier;
    }

    public Long getOrgId() {
        return OrgId;
    }

    public void setOrgId(Long orgId) {
        OrgId = orgId;
    }

    public Long getUnitId() {
        return UnitId;
    }

    public void setUnitId(Long unitId) {
        UnitId = unitId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getTaxNumber() {
        return TaxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        TaxNumber = taxNumber;
    }

    public String getIdentityNumber() {
        return IdentityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        IdentityNumber = identityNumber;
    }

    @Override
    public String toString() {
        return "PayVendor{" +
                "Identifier=" + Identifier +
                ", OrgId=" + OrgId +
                ", UnitId=" + UnitId +
                ", Name='" + Name + '\'' +
                ", Code='" + Code + '\'' +
                ", TaxNumber='" + TaxNumber + '\'' +
                ", IdentityNumber='" + IdentityNumber + '\'' +
                '}';
    }
}
