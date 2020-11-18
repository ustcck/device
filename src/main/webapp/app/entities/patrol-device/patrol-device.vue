<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('deviceApp.patrolDevice.home.title')" id="patrol-device-heading">Patrol Devices</span>
            <router-link :to="{name: 'PatrolDeviceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-patrol-device">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('deviceApp.patrolDevice.home.createLabel')">
                    Create a new Patrol Device
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
        <div class="alert alert-warning" v-if="!isFetching && patrolDevices && patrolDevices.length === 0">
            <span v-text="$t('deviceApp.patrolDevice.home.notFound')">No patrolDevices found</span>
        </div>
        <div class="table-responsive" v-if="patrolDevices && patrolDevices.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('deviceApp.patrolDevice.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('source')"><span v-text="$t('deviceApp.patrolDevice.source')">Source</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'source'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('serialNumber')"><span v-text="$t('deviceApp.patrolDevice.serialNumber')">Serial Number</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'serialNumber'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('installDate')"><span v-text="$t('deviceApp.patrolDevice.installDate')">Install Date</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'installDate'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('status')"><span v-text="$t('deviceApp.patrolDevice.status')">Status</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('deviceModel')"><span v-text="$t('deviceApp.patrolDevice.deviceModel')">Device Model</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'deviceModel'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('createTime')"><span v-text="$t('deviceApp.patrolDevice.createTime')">Create Time</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createTime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('updateTime')"><span v-text="$t('deviceApp.patrolDevice.updateTime')">Update Time</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'updateTime'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('remark')"><span v-text="$t('deviceApp.patrolDevice.remark')">Remark</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'remark'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="patrolDevice in patrolDevices"
                    :key="patrolDevice.id">
                    <td>
                        <router-link :to="{name: 'PatrolDeviceView', params: {patrolDeviceId: patrolDevice.id}}">{{patrolDevice.id}}</router-link>
                    </td>
                    <td>{{patrolDevice.name}}</td>
                    <td>{{patrolDevice.source}}</td>
                    <td>{{patrolDevice.serialNumber}}</td>
                    <td>{{patrolDevice.installDate}}</td>
                    <td>{{patrolDevice.status}}</td>
                    <td>{{patrolDevice.deviceModel}}</td>
                    <td>{{patrolDevice.createTime}}</td>
                    <td>{{patrolDevice.updateTime}}</td>
                    <td>{{patrolDevice.remark}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PatrolDeviceView', params: {patrolDeviceId: patrolDevice.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PatrolDeviceEdit', params: {patrolDeviceId: patrolDevice.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(patrolDevice)"
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
            <span slot="modal-title"><span id="deviceApp.patrolDevice.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-patrolDevice-heading" v-text="$t('deviceApp.patrolDevice.delete.question', {'id': removeId})">Are you sure you want to delete this Patrol Device?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-patrolDevice" v-text="$t('entity.action.delete')" v-on:click="removePatrolDevice()">Delete</button>
            </div>
        </b-modal>
        <div v-show="patrolDevices && patrolDevices.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./patrol-device.component.ts">
</script>
