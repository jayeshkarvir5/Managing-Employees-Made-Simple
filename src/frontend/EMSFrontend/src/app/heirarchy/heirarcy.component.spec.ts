import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeirarcyComponent } from './heirarcy.component';

describe('HeirarcyComponent', () => {
  let component: HeirarcyComponent;
  let fixture: ComponentFixture<HeirarcyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeirarcyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeirarcyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
