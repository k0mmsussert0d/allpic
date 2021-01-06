import {Box, Button, Control, Field, Generic, Icon, Input, Modal} from "rbx";
import React, {MutableRefObject, useRef, useState} from "react";
import {UseModalType} from "../hooks/useModal";

import './CustomModal.scss';
import {APIMethods} from "../services/ApiActions";

const LoginModal = ({modalHook}: LoginModalProps) => {

  const username = useRef(null);
  const password = useRef(null);
  const [errorMsg, setErrorMsg] = useState('');
  const [isAuthing, setIsAuthing] = useState(false);

  const performAuth = async (): Promise<boolean> => {

    setIsAuthing(true);

    let usernameStr = username as unknown as MutableRefObject<string>;
    let passwordStr = password as unknown as MutableRefObject<string>;

    let res = await APIMethods.authenticate(usernameStr.current.trim(), passwordStr.current.trim());

    setIsAuthing(false);

    if (!res) {
      setErrorMsg("Invalid username or password.");
    }

    return res;
  }

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
                  <Input type="text" placeholder="Username" ref={username}/>
                  <Icon size="small" align="left">
                  </Icon>
                  <Icon size="small" align="right">
                  </Icon>
                </Control>
              </Field>

              <Field>
                <Control iconLeft>
                  <Input type="password" placeholder="Password" ref={password}/>
                  <Icon size="small" align="left">
                  </Icon>
                </Control>
              </Field>


              <Field>
                <Control>
                  <Generic as="div" className="custom-modal-error">
                    {errorMsg ?
                      <p>{errorMsg}</p> :
                      ''
                    }
                  </Generic>
                  <Button.Group align="right">
                    {isAuthing ?
                      <Button state="loading" color="success">Logging in</Button> :
                      <Button color="success" onClick={performAuth}>Log in</Button>
                    }
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
