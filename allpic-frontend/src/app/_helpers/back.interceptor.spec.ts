import { TestBed } from '@angular/core/testing';

import { BackInterceptor } from './back.interceptor';

describe('BackInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      BackInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: BackInterceptor = TestBed.inject(BackInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
