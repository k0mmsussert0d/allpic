import {Box, Button, Control, Field, Generic, Icon, Input, Modal} from "rbx";
import React from "react";
import {UseModalType} from "../hooks/useModal";

import './CustomModal.scss';

const LoginModal = ({modalHook}: LoginModalProps) => {

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
              <Generic><h2>Log in:</h2></Generic>
            </Generic>

            <Generic as="div" className="custom-modal-middle">
              <Field>
                <Control iconLeft iconRight>
                  <Input type="email" placeholder="Email"/>
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
                <Control as="div" className="custom-modal-button">
                  <Button.Group align="right">
                    <Button color="success">Log in</Button>
                  </Button.Group>
                </Control>
              </Field>
            </Generic>
          </Generic>
        </Box>
      </Modal.Content>
    </Generic>
  );
}

export interface LoginModalProps {
  modalHook: UseModalType
}

export default LoginModal;
