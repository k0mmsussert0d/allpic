import {Box, Button, Control, Field, Generic, Icon, Input, Modal} from "rbx";
import React from "react";
import {UseModalType} from "../hooks/useModal";

import './LoginModal.scss';

const LoginModal = ({modalHook}: LoginModalProps) => {

  return (
    <Generic as="div" class="login_modal_window">
      <Modal.Background/>
      <Modal.Content>
        <Box>
          <Generic as="div" class="login_modal_close">
            <Button size="small" onClick={() => modalHook.toggle()}>X</Button>
          </Generic>

          <Generic as="div" class="login_modal_content">
            <Generic as="div" class="login_modal_header">
              <Generic><h2>Sign in:</h2></Generic>
            </Generic>

            <Generic as="div" class="login_modal_middle">
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
                <Control as="div" className="login_modal_button">
                  <Button.Group align="right">
                    <Button color="success">Sign in</Button>
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
