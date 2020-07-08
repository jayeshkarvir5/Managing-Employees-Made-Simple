import { TestBed } from '@angular/core/testing';

import { LeaveappdbService } from './leaveappdb.service';

describe('LeaveappdbService', () => {
  let service: LeaveappdbService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LeaveappdbService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
