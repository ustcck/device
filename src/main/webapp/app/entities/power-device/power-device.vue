<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('deviceApp.powerDevice.home.title')" id="power-device-heading">Power Devices</span>
            <router-link :to="{name: 'PowerDeviceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-power-device">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('deviceApp.powerDevice.home.createLabel')">
                    Create a new Power Device
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && powerDevices && powerDevices.length === 0">
            <span v-text="$t('deviceApp.powerDevice.home.notFound')">No powerDevices found</span>
        </div>
        <div class="table-responsive" v-if="powerDevices && powerDevices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('area')"><span v-text="$t('deviceApp.powerDevice.area')">Area</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'area'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('space')"><span v-text="$t('deviceApp.powerDevice.space')">Space</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'space'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('mainPart')"><span v-text="$t('deviceApp.powerDevice.mainPart')">Main Part</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'mainPart'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('subPart')"><span v-text="$t('deviceApp.powerDevice.subPart')">Sub Part</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subPart'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('deviceApp.powerDevice.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('recognizeType')"><span v-text="$t('deviceApp.powerDevice.recognizeType')">Recognize Type</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'recognizeType'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('recognizeContent')"><span v-text="$t('deviceApp.powerDevice.recognizeContent')">Recognize Content</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'recognizeContent'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('site')"><span v-text="$t('deviceApp.powerDevice.site')">Site</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'site'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('line')"><span v-text="$t('deviceApp.powerDevice.line')">Line</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'line'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('source')"><span v-text="$t('deviceApp.powerDevice.source')">Source</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'source'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('serialNumber')"><span v-text="$t('deviceApp.powerDevice.serialNumber')">Serial Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'serialNumber'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('installDate')"><span v-text="$t('deviceApp.powerDevice.installDate')">Install Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'installDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('status')"><span v-text="$t('deviceApp.powerDevice.status')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('deviceModel')"><span v-text="$t('deviceApp.powerDevice.deviceModel')">Device Model</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deviceModel'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createTime')"><span v-text="$t('deviceApp.powerDevice.createTime')">Create Time</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createTime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('updateTime')"><span v-text="$t('deviceApp.powerDevice.updateTime')">Update Time</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updateTime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('remark')"><span v-text="$t('deviceApp.powerDevice.remark')">Remark</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'remark'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('threshold.id')"><span v-text="$t('deviceApp.powerDevice.threshold')">Threshold</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'threshold.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="powerDevice in powerDevices"
                    :key="powerDevice.id">
                    <td>
                        <router-link :to="{name: 'PowerDeviceView', params: {powerDeviceId: powerDevice.id}}">{{powerDevice.id}}</router-link>
                    </td>
                    <td>{{powerDevice.area}}</td>
                    <td>{{powerDevice.space}}</td>
                    <td>{{powerDevice.mainPart}}</td>
                    <td>{{powerDevice.subPart}}</td>
                    <td>{{powerDevice.name}}</td>
                    <td>{{powerDevice.recognizeType}}</td>
                    <td>{{powerDevice.recognizeContent}}</td>
                    <td>{{powerDevice.site}}</td>
                    <td>{{powerDevice.line}}</td>
                    <td>{{powerDevice.source}}</td>
                    <td>{{powerDevice.serialNumber}}</td>
                    <td>{{powerDevice.installDate}}</td>
                    <td>{{powerDevice.status}}</td>
                    <td>{{powerDevice.deviceModel}}</td>
                    <td>{{powerDevice.createTime}}</td>
                    <td>{{powerDevice.updateTime}}</td>
                    <td>{{powerDevice.remark}}</td>
                    <td>
                        <div v-if="powerDevice.threshold">
                            <router-link :to="{name: 'ThresholdView', params: {thresholdId: powerDevice.threshold.id}}">{{powerDevice.threshold.id}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PowerDeviceView', params: {powerDeviceId: powerDevice.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PowerDeviceEdit', params: {powerDeviceId: powerDevice.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(powerDevice)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="deviceApp.powerDevice.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-powerDevice-heading" v-text="$t('deviceApp.powerDevice.delete.question', {'id': removeId})">Are you sure you want to delete this Power Device?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-powerDevice" v-text="$t('entity.action.delete')" v-on:click="removePowerDevice()">Delete</button>
            </div>
        </b-modal>
        <div v-show="powerDevices && powerDevices.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./power-device.component.ts">
</script>
