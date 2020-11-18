package com.lenovo.cloud.device.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A PowerDevice.
 */
@Entity
@Table(name = "power_device")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PowerDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "area")
    private Integer area;

    @Column(name = "space")
    private String space;

    @Column(name = "main_part")
    private String mainPart;

    @Column(name = "sub_part")
    private String subPart;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "recognize_type", nullable = false)
    private Integer recognizeType;

    @NotNull
    @Column(name = "recognize_content", nullable = false)
    private Integer recognizeContent;

    @Column(name = "site")
    private String site;

    @Column(name = "line")
    private String line;

    @Column(name = "source")
    private String source;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "install_date")
    private LocalDate installDate;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "device_model")
    private String deviceModel;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private LocalDate createTime;

    @NotNull
    @Column(name = "update_time", nullable = false)
    private LocalDate updateTime;

    @Column(name = "remark")
    private String remark;

    @OneToOne
    @JoinColumn(unique = true)
    private Threshold threshold;

    @ManyToMany(mappedBy = "powerDevices")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<PatrolDevice> patrolDevices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getArea() {
        return area;
    }

    public PowerDevice area(Integer area) {
        this.area = area;
        return this;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getSpace() {
        return space;
    }

    public PowerDevice space(String space) {
        this.space = space;
        return this;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getMainPart() {
        return mainPart;
    }

    public PowerDevice mainPart(String mainPart) {
        this.mainPart = mainPart;
        return this;
    }

    public void setMainPart(String mainPart) {
        this.mainPart = mainPart;
    }

    public String getSubPart() {
        return subPart;
    }

    public PowerDevice subPart(String subPart) {
        this.subPart = subPart;
        return this;
    }

    public void setSubPart(String subPart) {
        this.subPart = subPart;
    }

    public String getName() {
        return name;
    }

    public PowerDevice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRecognizeType() {
        return recognizeType;
    }

    public PowerDevice recognizeType(Integer recognizeType) {
        this.recognizeType = recognizeType;
        return this;
    }

    public void setRecognizeType(Integer recognizeType) {
        this.recognizeType = recognizeType;
    }

    public Integer getRecognizeContent() {
        return recognizeContent;
    }

    public PowerDevice recognizeContent(Integer recognizeContent) {
        this.recognizeContent = recognizeContent;
        return this;
    }

    public void setRecognizeContent(Integer recognizeContent) {
        this.recognizeContent = recognizeContent;
    }

    public String getSite() {
        return site;
    }

    public PowerDevice site(String site) {
        this.site = site;
        return this;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLine() {
        return line;
    }

    public PowerDevice line(String line) {
        this.line = line;
        return this;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getSource() {
        return source;
    }

    public PowerDevice source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public PowerDevice serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getInstallDate() {
        return installDate;
    }

    public PowerDevice installDate(LocalDate installDate) {
        this.installDate = installDate;
        return this;
    }

    public void setInstallDate(LocalDate installDate) {
        this.installDate = installDate;
    }

    public Integer getStatus() {
        return status;
    }

    public PowerDevice status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public PowerDevice deviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
        return this;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public PowerDevice createTime(LocalDate createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public PowerDevice updateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public PowerDevice remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Threshold getThreshold() {
        return threshold;
    }

    public PowerDevice threshold(Threshold threshold) {
        this.threshold = threshold;
        return this;
    }

    public void setThreshold(Threshold threshold) {
        this.threshold = threshold;
    }

    public Set<PatrolDevice> getPatrolDevices() {
        return patrolDevices;
    }

    public PowerDevice patrolDevices(Set<PatrolDevice> patrolDevices) {
        this.patrolDevices = patrolDevices;
        return this;
    }

    public PowerDevice addPatrolDevice(PatrolDevice patrolDevice) {
        this.patrolDevices.add(patrolDevice);
        patrolDevice.getPowerDevices().add(this);
        return this;
    }

    public PowerDevice removePatrolDevice(PatrolDevice patrolDevice) {
        this.patrolDevices.remove(patrolDevice);
        patrolDevice.getPowerDevices().remove(this);
        return this;
    }

    public void setPatrolDevices(Set<PatrolDevice> patrolDevices) {
        this.patrolDevices = patrolDevices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PowerDevice)) {
            return false;
        }
        return id != null && id.equals(((PowerDevice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PowerDevice{" +
            "id=" + getId() +
            ", area=" + getArea() +
            ", space='" + getSpace() + "'" +
            ", mainPart='" + getMainPart() + "'" +
            ", subPart='" + getSubPart() + "'" +
            ", name='" + getName() + "'" +
            ", recognizeType=" + getRecognizeType() +
            ", recognizeContent=" + getRecognizeContent() +
            ", site='" + getSite() + "'" +
            ", line='" + getLine() + "'" +
            ", source='" + getSource() + "'" +
            ", serialNumber='" + getSerialNumber() + "'" +
            ", installDate='" + getInstallDate() + "'" +
            ", status=" + getStatus() +
            ", deviceModel='" + getDeviceModel() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
