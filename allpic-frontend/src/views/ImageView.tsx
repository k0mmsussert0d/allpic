import {RouteComponentProps} from "react-router-dom";
import {useEffect, useState} from "react";
import {Generic, Image, Title} from "rbx";
import {APIMethods} from "../services/ApiActions";
import {APIResponse, ImageDTO, Message} from "../types/API";
import './ImageView.scss';

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

  const renderTitle = () => {
    return (
      <Title size={2}>{imageDetails!.title}</Title>
    );
  }

  const renderDetails = () => {

    const parseDate = (date: string): string => {
      return new Date(date).toLocaleDateString(
        'en-gb',
        {
          year: 'numeric',
          month: 'long',
          day: 'numeric'
        }  
      );
    }

    const parseExactDate = (date: string): string => {
      return new Date(date).toLocaleTimeString(
        'en-gb',
        {
          year: 'numeric',
          month: 'long',
          day: 'numeric',
          hour: 'numeric',
          minute: 'numeric',
          second: 'numeric'
        }
      );
    }

    return (
      <Generic as="div" className="image-details" tooltip={imageDetails!.uploadTime && parseExactDate(imageDetails!.uploadTime)}>
        {`Uploaded by ${imageDetails!.uploader ? imageDetails!.uploader : 'Anonymous'} on ${imageDetails!.uploadTime ? parseDate(imageDetails!.uploadTime) : 'unknown date'}`}
      </Generic>
    );
  }

  return (
    <Generic as="div" className="main-wrapper">
      {imageDetails ? renderImage() : renderNotFound()}
      {imageDetails?.title && renderTitle()}
      {imageDetails && renderDetails()}
    </Generic>
  )
}

export default ImageView;

export interface ImageViewParams {
  id: string
}
