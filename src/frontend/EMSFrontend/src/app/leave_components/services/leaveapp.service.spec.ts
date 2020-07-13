import { TestBed } from '@angular/core/testing';

import { LeaveappService } from './leaveapp.service';

describe('LeaveappService', () => {
  let service: LeaveappService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LeaveappService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
