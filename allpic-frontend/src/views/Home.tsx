import { Generic } from "rbx";
import { useState, useEffect } from "react";
import { APIMethods } from "../services/ApiActions";
import { ImagePreviewDetails, APIResponse } from "../types/API";
import Gallery from "./../components/Gallery/Gallery";
import "./../components/Gallery/Gallery.scss";

const Home = () => {
  const [images, setImages] = useState<Array<Array<ImagePreviewDetails>>>([[]]);

  useEffect(() => {
    const fetchLatestImages = async (): Promise<
      APIResponse<Array<ImagePreviewDetails>>
    > => {
      return await APIMethods.getLatestImagesPreviews(1, 50);
    };

    fetchLatestImages().then((res) => {
      if (!res || !res.response) {
        setImages([[]]);
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
    <Generic as="div" className="gallery-wrapper">
      <Gallery images={images} />
    </Generic>
  );
};

export default Home;
