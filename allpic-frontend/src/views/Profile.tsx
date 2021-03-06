import { useContext, useEffect, useRef, useState } from "react";
import { AuthenticationContext } from "../contexts/AuthenticationContext";
import {
  Container,
  Notification,
  Generic,
  Image,
  Title,
  Button,
  Icon,
} from "rbx";
import { APIResponse, ImagePreviewDetails, UserDTO } from "../types/API";
import { APIMethods } from "../services/ApiActions";
import "./Profile.scss";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit } from "@fortawesome/free-solid-svg-icons";
import "./../components/Gallery/Gallery.scss";
import Gallery from "../components/Gallery/Gallery";
import { UseModalType } from "../hooks/useModal";

const Profile = ({changePasswordModal}: ProfileProps) => {
  const auth = useContext(AuthenticationContext);
  const [userDetails, setUserDetails] = useState<UserDTO | undefined>(
    undefined
  );
  const formRef = useRef<HTMLFormElement>(null);
  const formInputRef = useRef<HTMLInputElement>(null);
  const [images, setImages] = useState<Array<Array<ImagePreviewDetails>>>([[]]);

  useEffect(() => {
    const fetchUserDetails = async () => {
      if (auth.username) {
        APIMethods.getUserDetails(auth.username).then((res) => {
          if (res.message?.type === "success") {
            setUserDetails(res.response);
          }
        });
      }
    };

    fetchUserDetails();
  }, [auth.username]);

  useEffect(() => {
    const fetchLatestImages = async (): Promise<
      APIResponse<Array<ImagePreviewDetails>>
    > => {
      return await APIMethods.getUserImages(userDetails!.username as string);
    };

    if (userDetails?.username) {
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
    }
  }, [userDetails]);

  const handleButtonClick = (e: React.MouseEvent<HTMLButtonElement>): void => {
    e.preventDefault();

    formInputRef.current?.click();
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    if (e.currentTarget?.files) {
      const avatarFile = e.currentTarget.files[0];
      let formData = new FormData();
      formData.append("file", avatarFile, avatarFile?.name);

      APIMethods.setAvatar(formData).then((res) => {
        if (res.message?.type === "success") {
          setUserDetails(res.response);
        }
      });
    }
  };

  const parseDate = (date: string): string => {
    return new Date(date).toLocaleTimeString("en-gb", {
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  };

  const renderAuthorized = () => {
    return (
      <Generic as="div" className="user-card">
        <Generic as="div" className="user-avatar">
          <form ref={formRef}>
            <Button color="dark" onClick={handleButtonClick}>
              <Icon size="small">
                <FontAwesomeIcon icon={faEdit} />
              </Icon>
            </Button>
            <input
              ref={formInputRef}
              type="file"
              name="file"
              onChange={(e) => handleFileChange(e)}
              hidden
            />
          </form>
          <Image
            src={
              userDetails?.hasAvatar
                ? APIMethods.getAvatarLink(userDetails?.username)
                : "https://bulma.io/images/placeholders/256x256.png"
            }
          />
        </Generic>
        <Generic as="div" className="user-details">
          <Title size={2}>{userDetails && userDetails.username}</Title>
          <Title size={4} subtitle>
            {userDetails &&
              `Registered on ${parseDate(userDetails.registerTime)}`}
          </Title>
          <Button color='primary' onClick={() => changePasswordModal.toggle()}>
            Reset password
          </Button>
        </Generic>
      </Generic>
    );
  };

  const renderUnauthorized = () => {
    return (
      <Container fluid>
        <Notification>You must log in to access this page.</Notification>
      </Container>
    );
  };

  return (
    <>
      <Generic as="div" className="main-wrapper">
        {auth.authenticated ? renderAuthorized() : renderUnauthorized()}
      </Generic>
      <Generic as="div" className="gallery-wrapper">
        {auth.authenticated && <Gallery images={images} />}
      </Generic>
    </>
  );
};

export interface ProfileProps {
  changePasswordModal: UseModalType
}

export default Profile;
