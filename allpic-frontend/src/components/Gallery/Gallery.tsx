import { Column, Generic, Image } from "rbx";
import { APIMethods } from "../../services/ApiActions";
import { ImagePreviewDetails } from "../../types/API";
import "./Gallery.scss";

const Gallery = ({images}: GalleryProps) => {

  return (
    <Column.Group multiline gapless>
      {images?.map((group: Array<ImagePreviewDetails>, i: number) => {
        return (
          <Column size={1} key={i}>
            {group.map((img: ImagePreviewDetails, j: number) => {
              return (
                <a
                  href={APIMethods.getImageViewPageLink(img.token)}
                  key={i * 100 + j}
                >
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

export interface GalleryProps {
  images: Array<Array<ImagePreviewDetails>>
}

export default Gallery;
