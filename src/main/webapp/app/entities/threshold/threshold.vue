<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('deviceApp.threshold.home.title')" id="threshold-heading">Thresholds</span>
            <router-link :to="{name: 'ThresholdCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-threshold">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('deviceApp.threshold.home.createLabel')">
                    Create a new Threshold
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
        <div class="alert alert-warning" v-if="!isFetching && thresholds && thresholds.length === 0">
            <span v-text="$t('deviceApp.threshold.home.notFound')">No thresholds found</span>
        </div>
        <div class="table-responsive" v-if="thresholds && thresholds.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('lowLimit')"><span v-text="$t('deviceApp.threshold.lowLimit')">Low Limit</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lowLimit'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('highLimit')"><span v-text="$t('deviceApp.threshold.highLimit')">High Limit</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'highLimit'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('openCloseIndicator')"><span v-text="$t('deviceApp.threshold.openCloseIndicator')">Open Close Indicator</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'openCloseIndicator'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="threshold in thresholds"
                    :key="threshold.id">
                    <td>
                        <router-link :to="{name: 'ThresholdView', params: {thresholdId: threshold.id}}">{{threshold.id}}</router-link>
                    </td>
                    <td>{{threshold.lowLimit}}</td>
                    <td>{{threshold.highLimit}}</td>
                    <td>{{threshold.openCloseIndicator}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ThresholdView', params: {thresholdId: threshold.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ThresholdEdit', params: {thresholdId: threshold.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(threshold)"
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
            <span slot="modal-title"><span id="deviceApp.threshold.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-threshold-heading" v-text="$t('deviceApp.threshold.delete.question', {'id': removeId})">Are you sure you want to delete this Threshold?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-threshold" v-text="$t('entity.action.delete')" v-on:click="removeThreshold()">Delete</button>
            </div>
        </b-modal>
        <div v-show="thresholds && thresholds.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./threshold.component.ts">
</script>
