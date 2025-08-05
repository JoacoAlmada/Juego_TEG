import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReagruparComponent } from './reagrupar.component';

describe('ReagruparComponent', () => {
  let component: ReagruparComponent;
  let fixture: ComponentFixture<ReagruparComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReagruparComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReagruparComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
