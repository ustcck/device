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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.lenovo.cloud.device.domain.PatrolDevice} entity. This class is used
 * in {@link com.lenovo.cloud.device.web.rest.PatrolDeviceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /patrol-devices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PatrolDeviceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter source;

    private StringFilter serialNumber;

    private LocalDateFilter installDate;

    private IntegerFilter status;

    private StringFilter deviceModel;

    private LocalDateFilter createTime;

    private LocalDateFilter updateTime;

    private StringFilter remark;

    private LongFilter powerDeviceId;

    public PatrolDeviceCriteria() {
    }

    public PatrolDeviceCriteria(PatrolDeviceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.serialNumber = other.serialNumber == null ? null : other.serialNumber.copy();
        this.installDate = other.installDate == null ? null : other.installDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.deviceModel = other.deviceModel == null ? null : other.deviceModel.copy();
        this.createTime = other.createTime == null ? null : other.createTime.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.powerDeviceId = other.powerDeviceId == null ? null : other.powerDeviceId.copy();
    }

    @Override
    public PatrolDeviceCriteria copy() {
        return new PatrolDeviceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSource() {
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public StringFilter getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(StringFilter serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDateFilter getInstallDate() {
        return installDate;
    }

    public void setInstallDate(LocalDateFilter installDate) {
        this.installDate = installDate;
    }

    public IntegerFilter getStatus() {
        return status;
    }

    public void setStatus(IntegerFilter status) {
        this.status = status;
    }

    public StringFilter getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(StringFilter deviceModel) {
        this.deviceModel = deviceModel;
    }

    public LocalDateFilter getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateFilter createTime) {
        this.createTime = createTime;
    }

    public LocalDateFilter getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateFilter updateTime) {
        this.updateTime = updateTime;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public LongFilter getPowerDeviceId() {
        return powerDeviceId;
    }

    public void setPowerDeviceId(LongFilter powerDeviceId) {
        this.powerDeviceId = powerDeviceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PatrolDeviceCriteria that = (PatrolDeviceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(source, that.source) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(installDate, that.installDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(deviceModel, that.deviceModel) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(powerDeviceId, that.powerDeviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        source,
        serialNumber,
        installDate,
        status,
        deviceModel,
        createTime,
        updateTime,
        remark,
        powerDeviceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatrolDeviceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (installDate != null ? "installDate=" + installDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (deviceModel != null ? "deviceModel=" + deviceModel + ", " : "") +
                (createTime != null ? "createTime=" + createTime + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (powerDeviceId != null ? "powerDeviceId=" + powerDeviceId + ", " : "") +
            "}";
    }

}
