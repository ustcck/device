<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="deviceApp.powerDevice.home.createOrEditLabel" v-text="$t('deviceApp.powerDevice.home.createOrEditLabel')">Create or edit a PowerDevice</h2>
                <div>
                    <div class="form-group" v-if="powerDevice.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="powerDevice.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.area')" for="power-device-area">Area</label>
                        <input type="number" class="form-control" name="area" id="power-device-area"
                            :class="{'valid': !$v.powerDevice.area.$invalid, 'invalid': $v.powerDevice.area.$invalid }" v-model.number="$v.powerDevice.area.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.space')" for="power-device-space">Space</label>
                        <input type="text" class="form-control" name="space" id="power-device-space"
                            :class="{'valid': !$v.powerDevice.space.$invalid, 'invalid': $v.powerDevice.space.$invalid }" v-model="$v.powerDevice.space.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.mainPart')" for="power-device-mainPart">Main Part</label>
                        <input type="text" class="form-control" name="mainPart" id="power-device-mainPart"
                            :class="{'valid': !$v.powerDevice.mainPart.$invalid, 'invalid': $v.powerDevice.mainPart.$invalid }" v-model="$v.powerDevice.mainPart.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.subPart')" for="power-device-subPart">Sub Part</label>
                        <input type="text" class="form-control" name="subPart" id="power-device-subPart"
                            :class="{'valid': !$v.powerDevice.subPart.$invalid, 'invalid': $v.powerDevice.subPart.$invalid }" v-model="$v.powerDevice.subPart.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.name')" for="power-device-name">Name</label>
                        <input type="text" class="form-control" name="name" id="power-device-name"
                            :class="{'valid': !$v.powerDevice.name.$invalid, 'invalid': $v.powerDevice.name.$invalid }" v-model="$v.powerDevice.name.$model"  required/>
                        <div v-if="$v.powerDevice.name.$anyDirty && $v.powerDevice.name.$invalid">
                            <small class="form-text text-danger" v-if="!$v.powerDevice.name.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.recognizeType')" for="power-device-recognizeType">Recognize Type</label>
                        <input type="number" class="form-control" name="recognizeType" id="power-device-recognizeType"
                            :class="{'valid': !$v.powerDevice.recognizeType.$invalid, 'invalid': $v.powerDevice.recognizeType.$invalid }" v-model.number="$v.powerDevice.recognizeType.$model"  required/>
                        <div v-if="$v.powerDevice.recognizeType.$anyDirty && $v.powerDevice.recognizeType.$invalid">
                            <small class="form-text text-danger" v-if="!$v.powerDevice.recognizeType.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.powerDevice.recognizeType.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.recognizeContent')" for="power-device-recognizeContent">Recognize Content</label>
                        <input type="number" class="form-control" name="recognizeContent" id="power-device-recognizeContent"
                            :class="{'valid': !$v.powerDevice.recognizeContent.$invalid, 'invalid': $v.powerDevice.recognizeContent.$invalid }" v-model.number="$v.powerDevice.recognizeContent.$model"  required/>
                        <div v-if="$v.powerDevice.recognizeContent.$anyDirty && $v.powerDevice.recognizeContent.$invalid">
                            <small class="form-text text-danger" v-if="!$v.powerDevice.recognizeContent.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.powerDevice.recognizeContent.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.site')" for="power-device-site">Site</label>
                        <input type="text" class="form-control" name="site" id="power-device-site"
                            :class="{'valid': !$v.powerDevice.site.$invalid, 'invalid': $v.powerDevice.site.$invalid }" v-model="$v.powerDevice.site.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.line')" for="power-device-line">Line</label>
                        <input type="text" class="form-control" name="line" id="power-device-line"
                            :class="{'valid': !$v.powerDevice.line.$invalid, 'invalid': $v.powerDevice.line.$invalid }" v-model="$v.powerDevice.line.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.source')" for="power-device-source">Source</label>
                        <input type="text" class="form-control" name="source" id="power-device-source"
                            :class="{'valid': !$v.powerDevice.source.$invalid, 'invalid': $v.powerDevice.source.$invalid }" v-model="$v.powerDevice.source.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.serialNumber')" for="power-device-serialNumber">Serial Number</label>
                        <input type="text" class="form-control" name="serialNumber" id="power-device-serialNumber"
                            :class="{'valid': !$v.powerDevice.serialNumber.$invalid, 'invalid': $v.powerDevice.serialNumber.$invalid }" v-model="$v.powerDevice.serialNumber.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.installDate')" for="power-device-installDate">Install Date</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="power-device-installDate"
                                    v-model="$v.powerDevice.installDate.$model"
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
                            <b-form-input id="power-device-installDate" type="text" class="form-control" name="installDate"  :class="{'valid': !$v.powerDevice.installDate.$invalid, 'invalid': $v.powerDevice.installDate.$invalid }"
                            v-model="$v.powerDevice.installDate.$model"  />
                        </b-input-group>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.status')" for="power-device-status">Status</label>
                        <input type="number" class="form-control" name="status" id="power-device-status"
                            :class="{'valid': !$v.powerDevice.status.$invalid, 'invalid': $v.powerDevice.status.$invalid }" v-model.number="$v.powerDevice.status.$model"  required/>
                        <div v-if="$v.powerDevice.status.$anyDirty && $v.powerDevice.status.$invalid">
                            <small class="form-text text-danger" v-if="!$v.powerDevice.status.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.powerDevice.status.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.deviceModel')" for="power-device-deviceModel">Device Model</label>
                        <input type="text" class="form-control" name="deviceModel" id="power-device-deviceModel"
                            :class="{'valid': !$v.powerDevice.deviceModel.$invalid, 'invalid': $v.powerDevice.deviceModel.$invalid }" v-model="$v.powerDevice.deviceModel.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.createTime')" for="power-device-createTime">Create Time</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="power-device-createTime"
                                    v-model="$v.powerDevice.createTime.$model"
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
                            <b-form-input id="power-device-createTime" type="text" class="form-control" name="createTime"  :class="{'valid': !$v.powerDevice.createTime.$invalid, 'invalid': $v.powerDevice.createTime.$invalid }"
                            v-model="$v.powerDevice.createTime.$model"  required />
                        </b-input-group>
                        <div v-if="$v.powerDevice.createTime.$anyDirty && $v.powerDevice.createTime.$invalid">
                            <small class="form-text text-danger" v-if="!$v.powerDevice.createTime.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.updateTime')" for="power-device-updateTime">Update Time</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="power-device-updateTime"
                                    v-model="$v.powerDevice.updateTime.$model"
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
                            <b-form-input id="power-device-updateTime" type="text" class="form-control" name="updateTime"  :class="{'valid': !$v.powerDevice.updateTime.$invalid, 'invalid': $v.powerDevice.updateTime.$invalid }"
                            v-model="$v.powerDevice.updateTime.$model"  required />
                        </b-input-group>
                        <div v-if="$v.powerDevice.updateTime.$anyDirty && $v.powerDevice.updateTime.$invalid">
                            <small class="form-text text-danger" v-if="!$v.powerDevice.updateTime.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.remark')" for="power-device-remark">Remark</label>
                        <input type="text" class="form-control" name="remark" id="power-device-remark"
                            :class="{'valid': !$v.powerDevice.remark.$invalid, 'invalid': $v.powerDevice.remark.$invalid }" v-model="$v.powerDevice.remark.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.powerDevice.threshold')" for="power-device-threshold">Threshold</label>
                        <select class="form-control" id="power-device-threshold" name="threshold" v-model="powerDevice.threshold">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="powerDevice.threshold && thresholdOption.id === powerDevice.threshold.id ? powerDevice.threshold : thresholdOption" v-for="thresholdOption in thresholds" :key="thresholdOption.id">{{thresholdOption.id}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.powerDevice.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./power-device-update.component.ts">
</script>
