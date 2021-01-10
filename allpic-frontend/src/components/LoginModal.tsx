import {Box, Button, Control, Field, Generic, Modal} from "rbx";
import React, {useState} from "react";
import {UseModalType} from "../hooks/useModal";
import './CustomModal.scss';
import {useForm} from "react-hook-form";
import {Message} from "../types/API";
import ControlledInput from "./ControlledInput";
import {APIMethods} from "../services/ApiActions";

export interface LoginFormData {
  username: string,
  password: string
}

const LoginModal = ({modalHook}: LoginModalProps) => {

  const {handleSubmit, control, errors} = useForm<LoginFormData>();

  const [msg, setMsg] = useState<Message | undefined>(undefined);
  const [isLoading, setIsLoading] = useState(false);

  const performAuthentication = async (data: LoginFormData) => {
    setIsLoading(true);
    const res = await APIMethods.authenticate(data);

    setMsg(res.message);

    if (res.message?.type === "success") {
      setTimeout(() => window.location.reload(), 2000);
    }

    setIsLoading(false);
  }

  return (
    <Generic as="div">
      <Modal active={true}>
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
                <form onSubmit={handleSubmit(performAuthentication)}>
                  <Field>
                    <Control iconLeft iconRight>
                      <ControlledInput
                        control={control}
                        rules={{
                          required: {
                            value: true,
                            message: 'Username is required'
                          }
                        }}
                        name="username"
                        placeholder="Username"
                        error={errors.username}
                      />
                    </Control>
                  </Field>

                  <Field>
                    <Control iconLeft>
                      <ControlledInput
                        control={control}
                        rules={{
                          required: {
                            value: true,
                            message: 'Password is required'
                          }
                        }}
                        name="password"
                        placeholder="Password"
                        error={errors.password}
                        type="password"
                      />
                    </Control>
                  </Field>


                  <Field>
                    <Control>
                      <Generic as="div" className="custom-modal-error">
                        {msg &&
                        <p style={{color: msg.type === 'success' ? '#23d160' : '#ff3860'}}>{msg.text}</p>
                        }
                      </Generic>
                      <Button.Group align="right">
                        {isLoading ?
                          <Button state="loading" color="success">Logging in</Button> :
                          <Button color="success" type="submit">Log in</Button>
                        }
                      </Button.Group>
                    </Control>
                  </Field>
                </form>
              </Generic>
            </Generic>
          </Box>
        </Modal.Content>
      </Modal>
    </Generic>
  );
}

export interface LoginModalProps {
  modalHook: UseModalType
}

export default LoginModal;
