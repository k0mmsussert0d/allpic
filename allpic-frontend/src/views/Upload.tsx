import React from "react";
import {Generic, File} from "rbx";

import './Upload.scss';

const Upload = () => {
  return (
    <Generic as="div" className="upload_wrapper">
      <File hasName boxed>
        <File.Label>
          <File.Input name="image" />
          <File.CTA>
            <File.Icon>

            </File.Icon>
            <File.Label as="span">Choose an Image</File.Label>
          </File.CTA>
          <File.Name>filename.jpg</File.Name>
        </File.Label>
      </File>
    </Generic>
  )
};

export default Upload;
