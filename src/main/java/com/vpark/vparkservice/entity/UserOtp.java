// Generated with g9.

package com.vpark.vparkservice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name="user_otp")
public class UserOtp implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true, nullable=false, precision=19)
    private long id;
	
    @Column(name="mobile_no", nullable=false)
    private String mobileNo;
    
    @Column(name = "otp",nullable=false)
    private String otp;
    
    @Column(name="validate_time", nullable=false)
    private Date validateTime;
    
    @Column(nullable=false, length=20)
    private String status;
    
    @Column(nullable=false, length=50)
    private String details;
    
    @Column(name="modified_on", nullable=false)
    private Date modifiedOn;
    
    @Column(name="linux_modified_on", nullable=false, precision=10)
    private long linuxModifiedOn;

    /** Default constructor. */
    public UserOtp() {
        super();
    }

    /**
     * Access method for id.
     *
     * @return the current value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for id.
     *
     * @param aId the new value for id
     */
    public void setId(long aId) {
        id = aId;
    }

    /**
     * Access method for mobileNo.
     *
     * @return the current value of mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Setter method for mobileNo.
     *
     * @param aMobileNo the new value for mobileNo
     */
    public void setMobileNo(String aMobileNo) {
        mobileNo = aMobileNo;
    }

    /**
     * Access method for otp.
     *
     * @return the current value of otp
     */
    public String getOtp() {
        return otp;
    }

    /**
     * Setter method for otp.
     *
     * @param aOtp the new value for otp
     */
    public void setOtp(String aOtp) {
        otp = aOtp;
    }

    /**
     * Access method for validateTime.
     *
     * @return the current value of validateTime
     */
    public Date getValidateTime() {
        return validateTime;
    }

    /**
     * Setter method for validateTime.
     *
     * @param aValidateTime the new value for validateTime
     */
    public void setValidateTime(Date aValidateTime) {
        validateTime = aValidateTime;
    }
    
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
     * Access method for details.
     *
     * @return the current value of details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Setter method for details.
     *
     * @param aDetails the new value for details
     */
    public void setDetails(String aDetails) {
        details = aDetails;
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
     * Compares the key for this instance with another UserOtp.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class UserOtp and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof UserOtp)) {
            return false;
        }
        UserOtp that = (UserOtp) other;
        if (this.getId() != that.getId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another UserOtp.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof UserOtp)) return false;
        return this.equalKeys(other) && ((UserOtp)other).equalKeys(this);
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
        i = (int)(getId() ^ (getId()>>>32));
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
        StringBuffer sb = new StringBuffer("[UserOtp |");
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
        ret.put("id", Long.valueOf(getId()));
        return ret;
    }

}
