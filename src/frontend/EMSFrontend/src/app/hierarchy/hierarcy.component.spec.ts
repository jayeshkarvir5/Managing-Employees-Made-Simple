import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HierarcyComponent } from './hierarcy.component';


describe('HeirarcyComponent', () => {
  let component: HierarcyComponent;
  let fixture: ComponentFixture<HierarcyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HierarcyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HierarcyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
