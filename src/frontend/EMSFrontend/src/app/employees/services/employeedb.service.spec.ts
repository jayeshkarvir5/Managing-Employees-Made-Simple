import { TestBed } from '@angular/core/testing';

import { EmployeedbService } from './employeedb.service';

describe('EmployeedbService', () => {
  let service: EmployeedbService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeedbService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
