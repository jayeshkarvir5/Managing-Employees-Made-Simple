import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveManagerComponent } from './leave-manager.component';

describe('LeaveManagerComponent', () => {
  let component: LeaveManagerComponent;
  let fixture: ComponentFixture<LeaveManagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeaveManagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaveManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
