package com.lenovo.cloud.device.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.lenovo.cloud.device.domain.Threshold} entity. This class is used
 * in {@link com.lenovo.cloud.device.web.rest.ThresholdResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /thresholds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ThresholdCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter lowLimit;

    private DoubleFilter highLimit;

    private IntegerFilter openCloseIndicator;

    public ThresholdCriteria() {
    }

    public ThresholdCriteria(ThresholdCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.lowLimit = other.lowLimit == null ? null : other.lowLimit.copy();
        this.highLimit = other.highLimit == null ? null : other.highLimit.copy();
        this.openCloseIndicator = other.openCloseIndicator == null ? null : other.openCloseIndicator.copy();
    }

    @Override
    public ThresholdCriteria copy() {
        return new ThresholdCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getLowLimit() {
        return lowLimit;
    }

    public void setLowLimit(DoubleFilter lowLimit) {
        this.lowLimit = lowLimit;
    }

    public DoubleFilter getHighLimit() {
        return highLimit;
    }

    public void setHighLimit(DoubleFilter highLimit) {
        this.highLimit = highLimit;
    }

    public IntegerFilter getOpenCloseIndicator() {
        return openCloseIndicator;
    }

    public void setOpenCloseIndicator(IntegerFilter openCloseIndicator) {
        this.openCloseIndicator = openCloseIndicator;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ThresholdCriteria that = (ThresholdCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(lowLimit, that.lowLimit) &&
            Objects.equals(highLimit, that.highLimit) &&
            Objects.equals(openCloseIndicator, that.openCloseIndicator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        lowLimit,
        highLimit,
        openCloseIndicator
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThresholdCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (lowLimit != null ? "lowLimit=" + lowLimit + ", " : "") +
                (highLimit != null ? "highLimit=" + highLimit + ", " : "") +
                (openCloseIndicator != null ? "openCloseIndicator=" + openCloseIndicator + ", " : "") +
            "}";
    }

}
