import {UseModalType} from "../hooks/useModal";
import {Box, Button, Control, Field, Generic, Icon, Input, Modal} from "rbx";
import React from "react";

const RegisterModal = ({modalHook}: RegisterModalProps) => {

  return (
    <Generic as="div">
      <Modal.Background onClick={() => modalHook.toggle()}/>
      <Modal.Content>
        <Box as="div" className="custom-modal-window">
          <Generic as="div" className="custom-modal-close-button">
            <Button size="small" onClick={() => modalHook.toggle()}>X</Button>
          </Generic>

          <Generic as="div" className="custom-modal-content">
            <Generic as="div" className="custom-modal-header">
              <Generic><h2>Register:</h2></Generic>
            </Generic>

            <Generic as="div" className="custom-modal-middle">
              <Field>
                <Control iconLeft iconRight>
                  <Input type="text" placeholder="Username" />
                  <Icon size="small" align="left">
                  </Icon>
                  <Icon size="small" align="right">
                  </Icon>
                </Control>
              </Field>

              <Field>
                <Control iconLeft iconRight>
                  <Input type="email" placeholder="E-mail" />
                  <Icon size="small" align="left">
                  </Icon>
                  <Icon size="small" align="right">
                  </Icon>
                </Control>
              </Field>

              <Field>
                <Control iconLeft>
                  <Input type="password" placeholder="Password"/>
                  <Icon size="small" align="left">
                  </Icon>
                </Control>
              </Field>

              <Field>
                <Control iconLeft>
                  <Input type="password" placeholder="Repeat password"/>
                  <Icon size="small" align="left">
                  </Icon>
                </Control>
              </Field>


              <Field>
                <Control as="div" className="custom-modal-button">
                  <Button.Group align="right">
                    <Button color="success">Create account</Button>
                  </Button.Group>
                </Control>
              </Field>
            </Generic>
          </Generic>
        </Box>
      </Modal.Content>
    </Generic>
  )
};

export interface RegisterModalProps {
  modalHook: UseModalType
}

export default RegisterModal;
