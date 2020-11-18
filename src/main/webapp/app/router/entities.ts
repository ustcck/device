import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const PatrolDevice = () => import('@/entities/patrol-device/patrol-device.vue');
// prettier-ignore
const PatrolDeviceUpdate = () => import('@/entities/patrol-device/patrol-device-update.vue');
// prettier-ignore
const PatrolDeviceDetails = () => import('@/entities/patrol-device/patrol-device-details.vue');
// prettier-ignore
const PowerDevice = () => import('@/entities/power-device/power-device.vue');
// prettier-ignore
const PowerDeviceUpdate = () => import('@/entities/power-device/power-device-update.vue');
// prettier-ignore
const PowerDeviceDetails = () => import('@/entities/power-device/power-device-details.vue');
// prettier-ignore
const Threshold = () => import('@/entities/threshold/threshold.vue');
// prettier-ignore
const ThresholdUpdate = () => import('@/entities/threshold/threshold-update.vue');
// prettier-ignore
const ThresholdDetails = () => import('@/entities/threshold/threshold-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/patrol-device',
    name: 'PatrolDevice',
    component: PatrolDevice,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/patrol-device/new',
    name: 'PatrolDeviceCreate',
    component: PatrolDeviceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/patrol-device/:patrolDeviceId/edit',
    name: 'PatrolDeviceEdit',
    component: PatrolDeviceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/patrol-device/:patrolDeviceId/view',
    name: 'PatrolDeviceView',
    component: PatrolDeviceDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/power-device',
    name: 'PowerDevice',
    component: PowerDevice,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/power-device/new',
    name: 'PowerDeviceCreate',
    component: PowerDeviceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/power-device/:powerDeviceId/edit',
    name: 'PowerDeviceEdit',
    component: PowerDeviceUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/power-device/:powerDeviceId/view',
    name: 'PowerDeviceView',
    component: PowerDeviceDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/threshold',
    name: 'Threshold',
    component: Threshold,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/threshold/new',
    name: 'ThresholdCreate',
    component: ThresholdUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/threshold/:thresholdId/edit',
    name: 'ThresholdEdit',
    component: ThresholdUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/threshold/:thresholdId/view',
    name: 'ThresholdView',
    component: ThresholdDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
