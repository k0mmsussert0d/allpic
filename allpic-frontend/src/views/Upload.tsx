import React, {useState} from "react";
import {Generic, File, Field, Control, Checkbox, Label, Button} from "rbx";
import './Upload.scss';
import {useForm} from "react-hook-form";
import ControlledInput from "../components/ControlledInput";
import {APIMethods} from "../services/ApiActions";

export interface FileUploadDetails {
  image: FileList,
  title: string,
  private: boolean
}

const Upload = () => {

  const {register, handleSubmit, control, errors} = useForm<FileUploadDetails>({
    defaultValues: {
      image: undefined,
      title: '',
      private: false
    }
  });
  const [filename, setFilename] = useState('');
  const [isUploading, setIsUploading] = useState(false);

  const fileSelectedCbk = (e: Event) => {
    const target = e.target as HTMLInputElement;
    if (!target.files) {
      return;
    }
    setFilename(target.files[0].name);
  }

  const uploadFile = async (e: FileUploadDetails) => {
    setIsUploading(true);

    let formData = new FormData();
    formData.append('file', e.image[0], filename);
    formData.append('metadata', new Blob([JSON.stringify({
      title: e.title,
      public: !e.private
    })], {
      type: 'application/json'
    }));
    const res = await APIMethods.uploadFile(formData);

    setIsUploading(false);
    console.log(res);
  }

  return (
    <Generic as="div" className="upload_wrapper">
      <form onSubmit={handleSubmit(uploadFile)}>
        <Field>
          <File align="centered" fullwidth hasName boxed>
            <File.Label>
              <File.Input name="image" ref={register} onChange={fileSelectedCbk}/>
              <File.CTA>
                <File.Icon>
                </File.Icon>
                <File.Label as="span">Choose an Image</File.Label>
              </File.CTA>
              <File.Name>{filename}</File.Name>
            </File.Label>
          </File>
        </Field>
        <Field>
          <Control iconLeft>
            <ControlledInput
              control={control}
              name="title"
              placeholder="Enter the title (optional)"
              error={errors.title}
              type="text"
            />
          </Control>
        </Field>
        <Field>
          <Label>
            <Checkbox name="private" ref={register}/> Private upload
          </Label>
        </Field>
        <Field>
          <Button.Group align="right">
            {isUploading ?
              <Button state="loading" color="success">Uploading</Button> :
              <Button color="success" type="submit">Upload</Button>
            }
          </Button.Group>
        </Field>

      </form>
    </Generic>
  )
};

export default Upload;
