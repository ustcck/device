<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="patrolDevice">
                <h2 class="jh-entity-heading"><span v-text="$t('deviceApp.patrolDevice.detail.title')">PatrolDevice</span> {{patrolDevice.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('deviceApp.patrolDevice.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{patrolDevice.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.patrolDevice.source')">Source</span>
                    </dt>
                    <dd>
                        <span>{{patrolDevice.source}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.patrolDevice.serialNumber')">Serial Number</span>
                    </dt>
                    <dd>
                        <span>{{patrolDevice.serialNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.patrolDevice.installDate')">Install Date</span>
                    </dt>
                    <dd>
                        <span>{{patrolDevice.installDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.patrolDevice.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{patrolDevice.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.patrolDevice.deviceModel')">Device Model</span>
                    </dt>
                    <dd>
                        <span>{{patrolDevice.deviceModel}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.patrolDevice.createTime')">Create Time</span>
                    </dt>
                    <dd>
                        <span>{{patrolDevice.createTime}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.patrolDevice.updateTime')">Update Time</span>
                    </dt>
                    <dd>
                        <span>{{patrolDevice.updateTime}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.patrolDevice.remark')">Remark</span>
                    </dt>
                    <dd>
                        <span>{{patrolDevice.remark}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.patrolDevice.powerDevice')">Power Device</span>
                    </dt>
                    <dd>
                        <span v-for="(powerDevice, i) in patrolDevice.powerDevices" :key="powerDevice.id">{{i > 0 ? ', ' : ''}}
                            <router-link :to="{name: 'PowerDeviceView', params: {powerDeviceId: powerDevice.id}}">{{powerDevice.name}}</router-link>
                        </span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="patrolDevice.id" :to="{name: 'PatrolDeviceEdit', params: {patrolDeviceId: patrolDevice.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./patrol-device-details.component.ts">
</script>
