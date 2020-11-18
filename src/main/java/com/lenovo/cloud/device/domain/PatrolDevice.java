package com.lenovo.cloud.device.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A PatrolDevice.
 */
@Entity
@Table(name = "patrol_device")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PatrolDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "patrol_device_power_device",
               joinColumns = @JoinColumn(name = "patrol_device_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "power_device_id", referencedColumnName = "id"))
    private Set<PowerDevice> powerDevices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PatrolDevice name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public PatrolDevice source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public PatrolDevice serialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocalDate getInstallDate() {
        return installDate;
    }

    public PatrolDevice installDate(LocalDate installDate) {
        this.installDate = installDate;
        return this;
    }

    public void setInstallDate(LocalDate installDate) {
        this.installDate = installDate;
    }

    public Integer getStatus() {
        return status;
    }

    public PatrolDevice status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public PatrolDevice deviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
        return this;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public PatrolDevice createTime(LocalDate createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getUpdateTime() {
        return updateTime;
    }

    public PatrolDevice updateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(LocalDate updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public PatrolDevice remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<PowerDevice> getPowerDevices() {
        return powerDevices;
    }

    public PatrolDevice powerDevices(Set<PowerDevice> powerDevices) {
        this.powerDevices = powerDevices;
        return this;
    }

    public PatrolDevice addPowerDevice(PowerDevice powerDevice) {
        this.powerDevices.add(powerDevice);
        powerDevice.getPatrolDevices().add(this);
        return this;
    }

    public PatrolDevice removePowerDevice(PowerDevice powerDevice) {
        this.powerDevices.remove(powerDevice);
        powerDevice.getPatrolDevices().remove(this);
        return this;
    }

    public void setPowerDevices(Set<PowerDevice> powerDevices) {
        this.powerDevices = powerDevices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatrolDevice)) {
            return false;
        }
        return id != null && id.equals(((PatrolDevice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatrolDevice{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
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
