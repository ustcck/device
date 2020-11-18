<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="threshold">
                <h2 class="jh-entity-heading"><span v-text="$t('deviceApp.threshold.detail.title')">Threshold</span> {{threshold.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('deviceApp.threshold.lowLimit')">Low Limit</span>
                    </dt>
                    <dd>
                        <span>{{threshold.lowLimit}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.threshold.highLimit')">High Limit</span>
                    </dt>
                    <dd>
                        <span>{{threshold.highLimit}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('deviceApp.threshold.openCloseIndicator')">Open Close Indicator</span>
                    </dt>
                    <dd>
                        <span>{{threshold.openCloseIndicator}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="threshold.id" :to="{name: 'ThresholdEdit', params: {thresholdId: threshold.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./threshold-details.component.ts">
</script>
