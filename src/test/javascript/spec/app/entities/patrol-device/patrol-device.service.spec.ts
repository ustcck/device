/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import PatrolDeviceService from '@/entities/patrol-device/patrol-device.service';
import { PatrolDevice } from '@/shared/model/patrol-device.model';

const mockedAxios: any = axios;
const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

jest.mock('axios', () => ({
  get: jest.fn(),
  post: jest.fn(),
  put: jest.fn(),
  delete: jest.fn(),
}));

describe('Service Tests', () => {
  describe('PatrolDevice Service', () => {
    let service: PatrolDeviceService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new PatrolDeviceService();
      currentDate = new Date();

      elemDefault = new PatrolDevice(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 0, 'AAAAAAA', currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            installDate: format(currentDate, DATE_FORMAT),
            createTime: format(currentDate, DATE_FORMAT),
            updateTime: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        mockedAxios.get.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a PatrolDevice', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            installDate: format(currentDate, DATE_FORMAT),
            createTime: format(currentDate, DATE_FORMAT),
            updateTime: format(currentDate, DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            installDate: currentDate,
            createTime: currentDate,
            updateTime: currentDate,
          },
          returnedFromService
        );

        mockedAxios.post.mockReturnValue(Promise.resolve({ data: returnedFromService }));
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a PatrolDevice', async () => {
        mockedAxios.post.mockReturnValue(Promise.reject(error));

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a PatrolDevice', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            source: 'BBBBBB',
            serialNumber: 'BBBBBB',
            installDate: format(currentDate, DATE_FORMAT),
            status: 1,
            deviceModel: 'BBBBBB',
            createTime: format(currentDate, DATE_FORMAT),
            updateTime: format(currentDate, DATE_FORMAT),
            remark: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            installDate: currentDate,
            createTime: currentDate,
            updateTime: currentDate,
          },
          returnedFromService
        );
        mockedAxios.put.mockReturnValue(Promise.resolve({ data: returnedFromService }));

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a PatrolDevice', async () => {
        mockedAxios.put.mockReturnValue(Promise.reject(error));

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of PatrolDevice', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            source: 'BBBBBB',
            serialNumber: 'BBBBBB',
            installDate: format(currentDate, DATE_FORMAT),
            status: 1,
            deviceModel: 'BBBBBB',
            createTime: format(currentDate, DATE_FORMAT),
            updateTime: format(currentDate, DATE_FORMAT),
            remark: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            installDate: currentDate,
            createTime: currentDate,
            updateTime: currentDate,
          },
          returnedFromService
        );
        mockedAxios.get.mockReturnValue(Promise.resolve([returnedFromService]));
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of PatrolDevice', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a PatrolDevice', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a PatrolDevice', async () => {
        mockedAxios.delete.mockReturnValue(Promise.reject(error));

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
