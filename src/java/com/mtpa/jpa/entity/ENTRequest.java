//230314    MtpA    Created

package com.mtpa.jpa.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(name="findAllRequests",query="SELECT req FROM ENTRequest req"),
})
public class ENTRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private Long requestorId;
    
    @NotNull
    private Long requesteeId;
    
    @NotNull
    private double requestAmt;
    
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date requestDate;

    public ENTRequest() {
        
    }
    
    public ENTRequest(long vRequestorId, long vRequesteeId, double vAmount, Date vCreateDate) {
        this.requestorId = vRequestorId;
        this.requesteeId = vRequesteeId;
        this.requestAmt = vAmount;
        this.requestDate = vCreateDate;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(Long requestorId) {
        this.requestorId = requestorId;
    }

    public Long getRequesteeId() {
        return requesteeId;
    }

    public void setRequesteeId(Long requesteeId) {
        this.requesteeId = requesteeId;
    }

    public double getRequestAmt() {
        return requestAmt;
    }

    public void setRequestAmt(double requestAmt) {
        this.requestAmt = requestAmt;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.requestorId);
        hash = 89 * hash + Objects.hashCode(this.requesteeId);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.requestAmt) ^ (Double.doubleToLongBits(this.requestAmt) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.requestDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ENTRequest other = (ENTRequest) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.requestorId, other.requestorId)) {
            return false;
        }
        if (!Objects.equals(this.requesteeId, other.requesteeId)) {
            return false;
        }
        if (Double.doubleToLongBits(this.requestAmt) != Double.doubleToLongBits(other.requestAmt)) {
            return false;
        }
        if (!Objects.equals(this.requestDate, other.requestDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTRequest{" + "id=" + id + ", requestorId=" + requestorId + ", requesteeId=" + requesteeId + ", requestAmt=" + requestAmt + ", requestDate=" + requestDate + '}';
    }

    
}
