package py.com.softpoint.pojos;

import java.io.Serializable;

/**
*
* @author eleon
*/
public class User implements Serializable {

    private long identifier;
    private String name;
    private String fullName;
    private String password;
    private long employeeId;
    private long siteId;
    private long unitId;

    public User() {
    }

    public User(long identifier, String name, String fullName, String password, long employeeId, long siteId, long unitId) {
        this.identifier = identifier;
        this.name = name;
        this.fullName = fullName;
        this.password = password;
        this.employeeId = employeeId;
        this.siteId = siteId;
        this.unitId = unitId;
    }

    public long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getSiteId() {
        return siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public long getUnitId() {
        return unitId;
    }

    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }
   
}
