import { Icon, Dropdown, Button } from "rbx";
import { APIMethods } from "../../services/ApiActions";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faShare } from "@fortawesome/free-solid-svg-icons";
import './SharingOptions.scss';
import LinkTextbox from "./LinkTextbox";

const SharingOptions = ({ token }: SharpingOptionsProps) => {

  return (
    <Dropdown align="right" hoverable>
      <Dropdown.Trigger>
        <Button color="info">
          <span>Share</span>
          <Icon size="small">
            <FontAwesomeIcon icon={faShare} />
          </Icon>
        </Button>
        <Dropdown.Menu as="div" className="sharing-dropdown">
          <Dropdown.Content>
            <Dropdown.Item as="div" className="sharing-options">
              <LinkTextbox label="Link:" link={APIMethods.getImageViewPageLink(token)} />
              <LinkTextbox label="Direct link:" link={APIMethods.getImageLink(token)} />
              <LinkTextbox label="BB-Code:" link={`[img]${APIMethods.getImageLink(token)}[/img]`} />
              <LinkTextbox label="BB-Code (thumbnail):" link={`[url=${APIMethods.getImageLink(token)}][img]${APIMethods.getImageThumbLink(token)}[/img][/url]`} />
              <LinkTextbox label="HTML:" link={`<img src="${APIMethods.getImageLink(token)}" />`} />
              <LinkTextbox label="HTML (thumbnail):" link={`<a href="${APIMethods.getImageLink(token)}"><img src="${APIMethods.getImageThumbLink(token)}" /></a>`} />
            </Dropdown.Item>
          </Dropdown.Content>
        </Dropdown.Menu>
      </Dropdown.Trigger>
    </Dropdown>
  );
};

export interface SharpingOptionsProps {
  token: string;
}

export default SharingOptions;
