<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="deviceApp.threshold.home.createOrEditLabel" v-text="$t('deviceApp.threshold.home.createOrEditLabel')">Create or edit a Threshold</h2>
                <div>
                    <div class="form-group" v-if="threshold.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="threshold.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.threshold.lowLimit')" for="threshold-lowLimit">Low Limit</label>
                        <input type="number" class="form-control" name="lowLimit" id="threshold-lowLimit"
                            :class="{'valid': !$v.threshold.lowLimit.$invalid, 'invalid': $v.threshold.lowLimit.$invalid }" v-model.number="$v.threshold.lowLimit.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.threshold.highLimit')" for="threshold-highLimit">High Limit</label>
                        <input type="number" class="form-control" name="highLimit" id="threshold-highLimit"
                            :class="{'valid': !$v.threshold.highLimit.$invalid, 'invalid': $v.threshold.highLimit.$invalid }" v-model.number="$v.threshold.highLimit.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('deviceApp.threshold.openCloseIndicator')" for="threshold-openCloseIndicator">Open Close Indicator</label>
                        <input type="number" class="form-control" name="openCloseIndicator" id="threshold-openCloseIndicator"
                            :class="{'valid': !$v.threshold.openCloseIndicator.$invalid, 'invalid': $v.threshold.openCloseIndicator.$invalid }" v-model.number="$v.threshold.openCloseIndicator.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.threshold.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./threshold-update.component.ts">
</script>
