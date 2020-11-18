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
 * Criteria class for the {@link com.lenovo.cloud.device.domain.PowerDevice} entity. This class is used
 * in {@link com.lenovo.cloud.device.web.rest.PowerDeviceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /power-devices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PowerDeviceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter area;

    private StringFilter space;

    private StringFilter mainPart;

    private StringFilter subPart;

    private StringFilter name;

    private IntegerFilter recognizeType;

    private IntegerFilter recognizeContent;

    private StringFilter site;

    private StringFilter line;

    private StringFilter source;

    private StringFilter serialNumber;

    private LocalDateFilter installDate;

    private IntegerFilter status;

    private StringFilter deviceModel;

    private LocalDateFilter createTime;

    private LocalDateFilter updateTime;

    private StringFilter remark;

    private LongFilter thresholdId;

    private LongFilter patrolDeviceId;

    public PowerDeviceCriteria() {
    }

    public PowerDeviceCriteria(PowerDeviceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.space = other.space == null ? null : other.space.copy();
        this.mainPart = other.mainPart == null ? null : other.mainPart.copy();
        this.subPart = other.subPart == null ? null : other.subPart.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.recognizeType = other.recognizeType == null ? null : other.recognizeType.copy();
        this.recognizeContent = other.recognizeContent == null ? null : other.recognizeContent.copy();
        this.site = other.site == null ? null : other.site.copy();
        this.line = other.line == null ? null : other.line.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.serialNumber = other.serialNumber == null ? null : other.serialNumber.copy();
        this.installDate = other.installDate == null ? null : other.installDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.deviceModel = other.deviceModel == null ? null : other.deviceModel.copy();
        this.createTime = other.createTime == null ? null : other.createTime.copy();
        this.updateTime = other.updateTime == null ? null : other.updateTime.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.thresholdId = other.thresholdId == null ? null : other.thresholdId.copy();
        this.patrolDeviceId = other.patrolDeviceId == null ? null : other.patrolDeviceId.copy();
    }

    @Override
    public PowerDeviceCriteria copy() {
        return new PowerDeviceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getArea() {
        return area;
    }

    public void setArea(IntegerFilter area) {
        this.area = area;
    }

    public StringFilter getSpace() {
        return space;
    }

    public void setSpace(StringFilter space) {
        this.space = space;
    }

    public StringFilter getMainPart() {
        return mainPart;
    }

    public void setMainPart(StringFilter mainPart) {
        this.mainPart = mainPart;
    }

    public StringFilter getSubPart() {
        return subPart;
    }

    public void setSubPart(StringFilter subPart) {
        this.subPart = subPart;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getRecognizeType() {
        return recognizeType;
    }

    public void setRecognizeType(IntegerFilter recognizeType) {
        this.recognizeType = recognizeType;
    }

    public IntegerFilter getRecognizeContent() {
        return recognizeContent;
    }

    public void setRecognizeContent(IntegerFilter recognizeContent) {
        this.recognizeContent = recognizeContent;
    }

    public StringFilter getSite() {
        return site;
    }

    public void setSite(StringFilter site) {
        this.site = site;
    }

    public StringFilter getLine() {
        return line;
    }

    public void setLine(StringFilter line) {
        this.line = line;
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

    public LongFilter getThresholdId() {
        return thresholdId;
    }

    public void setThresholdId(LongFilter thresholdId) {
        this.thresholdId = thresholdId;
    }

    public LongFilter getPatrolDeviceId() {
        return patrolDeviceId;
    }

    public void setPatrolDeviceId(LongFilter patrolDeviceId) {
        this.patrolDeviceId = patrolDeviceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PowerDeviceCriteria that = (PowerDeviceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(area, that.area) &&
            Objects.equals(space, that.space) &&
            Objects.equals(mainPart, that.mainPart) &&
            Objects.equals(subPart, that.subPart) &&
            Objects.equals(name, that.name) &&
            Objects.equals(recognizeType, that.recognizeType) &&
            Objects.equals(recognizeContent, that.recognizeContent) &&
            Objects.equals(site, that.site) &&
            Objects.equals(line, that.line) &&
            Objects.equals(source, that.source) &&
            Objects.equals(serialNumber, that.serialNumber) &&
            Objects.equals(installDate, that.installDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(deviceModel, that.deviceModel) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(thresholdId, that.thresholdId) &&
            Objects.equals(patrolDeviceId, that.patrolDeviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        area,
        space,
        mainPart,
        subPart,
        name,
        recognizeType,
        recognizeContent,
        site,
        line,
        source,
        serialNumber,
        installDate,
        status,
        deviceModel,
        createTime,
        updateTime,
        remark,
        thresholdId,
        patrolDeviceId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerDeviceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (area != null ? "area=" + area + ", " : "") +
                (space != null ? "space=" + space + ", " : "") +
                (mainPart != null ? "mainPart=" + mainPart + ", " : "") +
                (subPart != null ? "subPart=" + subPart + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (recognizeType != null ? "recognizeType=" + recognizeType + ", " : "") +
                (recognizeContent != null ? "recognizeContent=" + recognizeContent + ", " : "") +
                (site != null ? "site=" + site + ", " : "") +
                (line != null ? "line=" + line + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (serialNumber != null ? "serialNumber=" + serialNumber + ", " : "") +
                (installDate != null ? "installDate=" + installDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (deviceModel != null ? "deviceModel=" + deviceModel + ", " : "") +
                (createTime != null ? "createTime=" + createTime + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (thresholdId != null ? "thresholdId=" + thresholdId + ", " : "") +
                (patrolDeviceId != null ? "patrolDeviceId=" + patrolDeviceId + ", " : "") +
            "}";
    }

}
