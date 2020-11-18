package com.lenovo.cloud.device.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Threshold.
 */
@Entity
@Table(name = "threshold")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Threshold implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "low_limit")
    private Double lowLimit;

    @Column(name = "high_limit")
    private Double highLimit;

    @Column(name = "open_close_indicator")
    private Integer openCloseIndicator;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLowLimit() {
        return lowLimit;
    }

    public Threshold lowLimit(Double lowLimit) {
        this.lowLimit = lowLimit;
        return this;
    }

    public void setLowLimit(Double lowLimit) {
        this.lowLimit = lowLimit;
    }

    public Double getHighLimit() {
        return highLimit;
    }

    public Threshold highLimit(Double highLimit) {
        this.highLimit = highLimit;
        return this;
    }

    public void setHighLimit(Double highLimit) {
        this.highLimit = highLimit;
    }

    public Integer getOpenCloseIndicator() {
        return openCloseIndicator;
    }

    public Threshold openCloseIndicator(Integer openCloseIndicator) {
        this.openCloseIndicator = openCloseIndicator;
        return this;
    }

    public void setOpenCloseIndicator(Integer openCloseIndicator) {
        this.openCloseIndicator = openCloseIndicator;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Threshold)) {
            return false;
        }
        return id != null && id.equals(((Threshold) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Threshold{" +
            "id=" + getId() +
            ", lowLimit=" + getLowLimit() +
            ", highLimit=" + getHighLimit() +
            ", openCloseIndicator=" + getOpenCloseIndicator() +
            "}";
    }
}
