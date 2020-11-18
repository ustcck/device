/* tslint:disable max-line-length */
import axios from 'axios';
import { format } from 'date-fns';

import * as config from '@/shared/config/config';
import { DATE_FORMAT } from '@/shared/date/filters';
import PowerDeviceService from '@/entities/power-device/power-device.service';
import { PowerDevice } from '@/shared/model/power-device.model';

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
  describe('PowerDevice Service', () => {
    let service: PowerDeviceService;
    let elemDefault;
    let currentDate: Date;
    beforeEach(() => {
      service = new PowerDeviceService();
      currentDate = new Date();

      elemDefault = new PowerDevice(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        0,
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA'
      );
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

      it('should create a PowerDevice', async () => {
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

      it('should not create a PowerDevice', async () => {
        mockedAxios.post.mockReturnValue(Promise.reject(error));

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a PowerDevice', async () => {
        const returnedFromService = Object.assign(
          {
            area: 1,
            space: 'BBBBBB',
            mainPart: 'BBBBBB',
            subPart: 'BBBBBB',
            name: 'BBBBBB',
            recognizeType: 1,
            recognizeContent: 1,
            site: 'BBBBBB',
            line: 'BBBBBB',
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

      it('should not update a PowerDevice', async () => {
        mockedAxios.put.mockReturnValue(Promise.reject(error));

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of PowerDevice', async () => {
        const returnedFromService = Object.assign(
          {
            area: 1,
            space: 'BBBBBB',
            mainPart: 'BBBBBB',
            subPart: 'BBBBBB',
            name: 'BBBBBB',
            recognizeType: 1,
            recognizeContent: 1,
            site: 'BBBBBB',
            line: 'BBBBBB',
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

      it('should not return a list of PowerDevice', async () => {
        mockedAxios.get.mockReturnValue(Promise.reject(error));

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a PowerDevice', async () => {
        mockedAxios.delete.mockReturnValue(Promise.resolve({ ok: true }));
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a PowerDevice', async () => {
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
