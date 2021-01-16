import { Control, Icon, Input, Field, Label } from "rbx";
import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCopy } from "@fortawesome/free-solid-svg-icons";
import './LinkTextbox.scss';

const LinkTextbox = ({ label, link }: LinkTextboxProps) => {
  const [tooltipText, setTooltipText] = useState("Copy");

  const copyToClipboard = (event: React.MouseEvent<HTMLSpanElement, MouseEvent>) => {
    const handleEvent = async () => {
      event.preventDefault();
      navigator.clipboard.writeText(link).then(() => {
        setTooltipText("Copied")
        setTimeout(() => {
          setTooltipText("Copy");
        }, 10000);
      });
      event.stopPropagation();
    }
    
    handleEvent();
  };

  return (
    <Field as="div" className="sharing-option" horizontal>
      <Field.Label size="small">
        <Label>{label}</Label>
      </Field.Label>
      <Field.Body as="div" className="sharing-option-field-body">
        <Control
          as="div"
          className="sharing-option-link-body"
          size="small"
        >
          <Input type="text" value={link} size="small" readOnly />
        </Control>
        <Icon
          align="right"
          size="small"
          as="span"
          className="copy-icon"
          tooltip={tooltipText}
          onClick={copyToClipboard}
        >
          <FontAwesomeIcon icon={faCopy} />
        </Icon>
      </Field.Body>
    </Field>
  );
};

export interface LinkTextboxProps {
  label: string;
  link: string;
}

export default LinkTextbox;
