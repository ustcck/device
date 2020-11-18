<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="powerDevice">
                <h2 class="jh-entity-heading"><span v-text="$t('deviceApp.powerDevice.detail.title')">PowerDevice</span> {{powerDevice.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.area')">Area</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.area}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.space')">Space</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.space}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.mainPart')">Main Part</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.mainPart}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.subPart')">Sub Part</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.subPart}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.recognizeType')">Recognize Type</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.recognizeType}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.recognizeContent')">Recognize Content</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.recognizeContent}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.site')">Site</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.site}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.line')">Line</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.line}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.source')">Source</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.source}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.serialNumber')">Serial Number</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.serialNumber}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.installDate')">Install Date</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.installDate}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.status')">Status</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.status}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.deviceModel')">Device Model</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.deviceModel}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.createTime')">Create Time</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.createTime}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.updateTime')">Update Time</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.updateTime}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.remark')">Remark</span>
                    </dt>
                    <dd>
                        <span>{{powerDevice.remark}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.powerDevice.threshold')">Threshold</span>
                    </dt>
                    <dd>
                        <div v-if="powerDevice.threshold">
                            <router-link :to="{name: 'ThresholdView', params: {thresholdId: powerDevice.threshold.id}}">{{powerDevice.threshold.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="powerDevice.id" :to="{name: 'PowerDeviceEdit', params: {powerDeviceId: powerDevice.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./power-device-details.component.ts">
</script>
