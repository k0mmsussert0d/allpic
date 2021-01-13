import {RouteComponentProps} from "react-router-dom";
import {useEffect, useState} from "react";
import {Generic, Image} from "rbx";
import {APIMethods} from "../services/ApiActions";
import {APIResponse, ImageDTO, Message} from "../types/API";

const ImageView = ({match}: RouteComponentProps<ImageViewParams>) => {

  const [message, setMessage] = useState<Message | undefined>(undefined);
  const [imageDetails, setImageDetails] = useState<ImageDTO | undefined>(undefined);

  useEffect(() => {
    const fetchDetails = async () => {
      return await APIMethods.getFileDetails(match.params.id);
    };

    fetchDetails()
      .then((details: APIResponse<ImageDTO>) => {
        setMessage(details?.message);
        setImageDetails(details?.response);
      })
  }, [match.params.id]);

  const renderImage = () => {
    return (
      <Image.Container>
        <Image src={APIMethods.getImageLink((imageDetails as ImageDTO).token)} />
      </Image.Container>
    );
  }

  const renderNotFound = () => {
    return (
      <p>Cannot find</p>
    );
  }

  return (
    <Generic as="div" className="main-wrapper">
      {imageDetails ? renderImage() : renderNotFound()}
    </Generic>
  )
}

export default ImageView;

export interface ImageViewParams {
  id: string
}
