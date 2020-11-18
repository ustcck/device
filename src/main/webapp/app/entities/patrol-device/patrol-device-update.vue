<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="deviceApp.patrolDevice.home.createOrEditLabel" v-text="$t('deviceApp.patrolDevice.home.createOrEditLabel')">Create or edit a PatrolDevice</h2>
                <div>
                    <div class="form-group" v-if="patrolDevice.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="patrolDevice.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.patrolDevice.name')" for="patrol-device-name">Name</label>
                        <input type="text" class="form-control" name="name" id="patrol-device-name"
                            :class="{'valid': !$v.patrolDevice.name.$invalid, 'invalid': $v.patrolDevice.name.$invalid }" v-model="$v.patrolDevice.name.$model"  required/>
                        <div v-if="$v.patrolDevice.name.$anyDirty && $v.patrolDevice.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.patrolDevice.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.patrolDevice.source')" for="patrol-device-source">Source</label>
                        <input type="text" class="form-control" name="source" id="patrol-device-source"
                            :class="{'valid': !$v.patrolDevice.source.$invalid, 'invalid': $v.patrolDevice.source.$invalid }" v-model="$v.patrolDevice.source.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.patrolDevice.serialNumber')" for="patrol-device-serialNumber">Serial Number</label>
                        <input type="text" class="form-control" name="serialNumber" id="patrol-device-serialNumber"
                            :class="{'valid': !$v.patrolDevice.serialNumber.$invalid, 'invalid': $v.patrolDevice.serialNumber.$invalid }" v-model="$v.patrolDevice.serialNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.patrolDevice.installDate')" for="patrol-device-installDate">Install Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="patrol-device-installDate"
                                    v-model="$v.patrolDevice.installDate.$model"
                                    name="installDate"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="patrol-device-installDate" type="text" class="form-control" name="installDate"  :class="{'valid': !$v.patrolDevice.installDate.$invalid, 'invalid': $v.patrolDevice.installDate.$invalid }"
                            v-model="$v.patrolDevice.installDate.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.patrolDevice.status')" for="patrol-device-status">Status</label>
                        <input type="number" class="form-control" name="status" id="patrol-device-status"
                            :class="{'valid': !$v.patrolDevice.status.$invalid, 'invalid': $v.patrolDevice.status.$invalid }" v-model.number="$v.patrolDevice.status.$model"  required/>
                        <div v-if="$v.patrolDevice.status.$anyDirty && $v.patrolDevice.status.$invalid">
                            <small class="form-text text-danger" v-if="!$v.patrolDevice.status.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.patrolDevice.status.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.patrolDevice.deviceModel')" for="patrol-device-deviceModel">Device Model</label>
                        <input type="text" class="form-control" name="deviceModel" id="patrol-device-deviceModel"
                            :class="{'valid': !$v.patrolDevice.deviceModel.$invalid, 'invalid': $v.patrolDevice.deviceModel.$invalid }" v-model="$v.patrolDevice.deviceModel.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.patrolDevice.createTime')" for="patrol-device-createTime">Create Time</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="patrol-device-createTime"
                                    v-model="$v.patrolDevice.createTime.$model"
                                    name="createTime"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="patrol-device-createTime" type="text" class="form-control" name="createTime"  :class="{'valid': !$v.patrolDevice.createTime.$invalid, 'invalid': $v.patrolDevice.createTime.$invalid }"
                            v-model="$v.patrolDevice.createTime.$model"  required />
                        </b-input-group>
                        <div v-if="$v.patrolDevice.createTime.$anyDirty && $v.patrolDevice.createTime.$invalid">
                            <small class="form-text text-danger" v-if="!$v.patrolDevice.createTime.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.patrolDevice.updateTime')" for="patrol-device-updateTime">Update Time</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="patrol-device-updateTime"
                                    v-model="$v.patrolDevice.updateTime.$model"
                                    name="updateTime"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="patrol-device-updateTime" type="text" class="form-control" name="updateTime"  :class="{'valid': !$v.patrolDevice.updateTime.$invalid, 'invalid': $v.patrolDevice.updateTime.$invalid }"
                            v-model="$v.patrolDevice.updateTime.$model"  required />
                        </b-input-group>
                        <div v-if="$v.patrolDevice.updateTime.$anyDirty && $v.patrolDevice.updateTime.$invalid">
                            <small class="form-text text-danger" v-if="!$v.patrolDevice.updateTime.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.patrolDevice.remark')" for="patrol-device-remark">Remark</label>
                        <input type="text" class="form-control" name="remark" id="patrol-device-remark"
                            :class="{'valid': !$v.patrolDevice.remark.$invalid, 'invalid': $v.patrolDevice.remark.$invalid }" v-model="$v.patrolDevice.remark.$model" />
                    </div>
                    <div class="form-group">
                        <label v-text="$t('deviceApp.patrolDevice.powerDevice')" for="patrol-device-powerDevice">Power Device</label>
                        <select class="form-control" id="patrol-device-powerDevice" multiple name="powerDevice" v-model="patrolDevice.powerDevices">
                            <option v-bind:value="getSelected(patrolDevice.powerDevices, powerDeviceOption)" v-for="powerDeviceOption in powerDevices" :key="powerDeviceOption.id">{{powerDeviceOption.name}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.patrolDevice.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./patrol-device-update.component.ts">
</script>
