// Generated with g9.

package com.vpark.vparkservice.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity(name="otp_api_error_logs")
public class OtpApiErrorLogs implements Serializable {

   

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=10)
    private int id;
    @Column(name="user_id", nullable=false, precision=10)
    private int userId;
    private String request;
    @Column(length=8)
    private String status;
    @Column(name="linux_modified_on", precision=10)
    private long linuxModifiedOn;
    @Column(name="modified_on", nullable=false)
    private Date modifiedOn;

    /** Default constructor. */
    public OtpApiErrorLogs() {
        super();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(int aId) {
        id = aId;
    }

    /**
     * Access method for userId.
     *
     * @return the current value of userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter method for userId.
     *
     * @param aUserId the new value for userId
     */
    public void setUserId(int aUserId) {
        userId = aUserId;
    }

    /**
     * Access method for request.
     *
     * @return the current value of request
     */
    public String getRequest() {
        return request;
    }

    /**
     * Setter method for request.
     *
     * @param aRequest the new value for request
     */
    public void setRequest(String aRequest) {
        request = aRequest;
    }

    /**
     * Access method for status.
     *
     * @return the current value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for status.
     *
     * @param aStatus the new value for status
     */
    public void setStatus(String aStatus) {
        status = aStatus;
    }

    /**
     * Access method for linuxModifiedOn.
     *
     * @return the current value of linuxModifiedOn
     */
    public long getLinuxModifiedOn() {
        return linuxModifiedOn;
    }

    /**
     * Setter method for linuxModifiedOn.
     *
     * @param aLinuxModifiedOn the new value for linuxModifiedOn
     */
    public void setLinuxModifiedOn(long aLinuxModifiedOn) {
        linuxModifiedOn = aLinuxModifiedOn;
    }

    /**
     * Access method for modifiedOn.
     *
     * @return the current value of modifiedOn
     */
    public Date getModifiedOn() {
        return modifiedOn;
    }

    /**
     * Setter method for modifiedOn.
     *
     * @param aModifiedOn the new value for modifiedOn
     */
    public void setModifiedOn(Date aModifiedOn) {
        modifiedOn = aModifiedOn;
    }

    /**
     * Compares the key for this instance with another OtpApiErrorLogs.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class OtpApiErrorLogs and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof OtpApiErrorLogs)) {
            return false;
        }
        OtpApiErrorLogs that = (OtpApiErrorLogs) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another OtpApiErrorLogs.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof OtpApiErrorLogs)) return false;
        return this.equalKeys(other) && ((OtpApiErrorLogs)other).equalKeys(this);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return Hash code
     */
    @Override
    public int hashCode() {
        int i;
        int result = 17;
        i = getId();
        result = 37*result + i;
        return result;
    }

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[OtpApiErrorLogs |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("id", Integer.valueOf(getId()));
        return ret;
    }

}
