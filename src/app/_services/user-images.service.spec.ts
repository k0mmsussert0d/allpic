import { TestBed } from '@angular/core/testing';

import { UserImagesService } from './user-images.service';

describe('UserImagesService', () => {
  let service: UserImagesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserImagesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
