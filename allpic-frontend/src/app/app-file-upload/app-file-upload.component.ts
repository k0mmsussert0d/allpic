import { Component, OnInit } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UploadFileService } from '../_services/upload-file.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { title } from 'process';

@Component({
  selector: 'app-app-file-upload',
  templateUrl: './app-file-upload.component.html',
  styleUrls: ['./app-file-upload.component.css']
})
export class AppFileUploadComponent implements OnInit {

  imgForm: FormGroup;
  submitted = false;
  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';

  fileInfos: Observable<any>;
  

  constructor(
    private uploadService: UploadFileService,
    private formBuilder: FormBuilder
    ) {}

  selectFile(event) {
    this.selectedFiles = event.target.files;
  }
  get f() { return this.imgForm.controls; }

  upload() {
    this.progress = 0;
  
    this.currentFile = this.selectedFiles.item(0),
    
  
    this.uploadService.upload(this.currentFile,this.imgForm.get('title').value,this.imgForm.get('ispublic').value ).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.message = event.body.message;
          
        }
      },
      err => {
        this.progress = 0;
        this.message = 'Could not upload the file!';
        this.currentFile = undefined;
      });
  
    this.selectedFiles = undefined;
  }

    
    ngOnInit() {
      this.imgForm = this.formBuilder.group({
          title: ['', Validators.required],
          ispublic: ['', Validators.required ]
      });
    }
  

}
