import { Column, Generic, Image } from "rbx";
import { useState, useEffect } from "react";
import { APIMethods } from "../../services/ApiActions";
import { APIResponse, ImagePreviewDetails } from "../../types/API";
import './Gallery.scss';

const Gallery = () => {
  const [images, setImages] = useState<
    Array<Array<ImagePreviewDetails>> | undefined
  >(undefined);

  useEffect(() => {
    const fetchLatestImages = async (): Promise<
      APIResponse<Array<ImagePreviewDetails>>
    > => {
      return await APIMethods.getLatestImagesPreviews(1, 50);
    };

    fetchLatestImages().then((res) => {
      if (!res || !res.response) {
          setImages(undefined);
          return;
      }  

      const perChunk = res.response?.length / 12; // items per chunk

      let result: Array<Array<ImagePreviewDetails>> =
        res.response?.reduce(
          (
            resultArray: Array<Array<ImagePreviewDetails>>,
            item: ImagePreviewDetails,
            index: number
          ) => {
            const chunkIndex = Math.floor(index / perChunk);

            if (!resultArray[chunkIndex]) {
              resultArray[chunkIndex] = []; // start a new chunk
            }

            resultArray[chunkIndex].push(item);

            return resultArray;
          },
          []
        ) ?? [];

      setImages(result);
    });
  }, []);

  return (
      <Column.Group multiline gapless>
          {images?.map((group: Array<ImagePreviewDetails>) => {
              return ( 
                <Column size={1}>
                    {group.map((img: ImagePreviewDetails) => {
                        return (
                            <a href={APIMethods.getImageViewPageLink(img.token)}>
                                <Generic as="div" className="image-preview">
                                    <Image src={APIMethods.getImageThumbLink(img.token)} />
                                </Generic>
                            </a>
                        );
                    })}
                </Column>
              );
          })}
      </Column.Group>
  );
};

export default Gallery;
