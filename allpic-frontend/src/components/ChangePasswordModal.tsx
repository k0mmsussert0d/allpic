import { UseModalType } from "../hooks/useModal";
import { Message, UserDetails } from "../types/API";
import {Box, Button, Control, Field, Generic, Icon, Modal} from "rbx";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope, faLock } from "@fortawesome/free-solid-svg-icons";
import ControlledInput from "./ControlledInput";
import './CustomModal.scss';
import { useForm } from "react-hook-form";
import { APIMethods } from "../services/ApiActions";
import { useState } from "react";

const ChangePasswordModal = ({modalHook}: ChangePasswordModalProps) => {

  const [msg, setMsg] = useState<Message | undefined>(undefined);
  const [isLoading, setIsLoading] = useState(false);

  const {handleSubmit, control, errors, getValues} = useForm<ChangePasswordFormData>({
    defaultValues: {
      oldPwd: '',
      newPwd: '',
      newPwdConfirm: ''
    }
  });

  const changePassword = async (data: ChangePasswordFormData) => {
    setIsLoading(true);
    const res = await APIMethods.changePassword(data);

    if (res.message?.type === 'success') {
      setMsg(res.message);
      setTimeout(() => modalHook.toggle(), 2000);
    }

    setIsLoading(false);
    setMsg(res.message);
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
                <Generic><h2>Change password:</h2></Generic>
              </Generic>

              <Generic as="div" className="custom-modal-middle">
                <form onSubmit={handleSubmit(changePassword)}>
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
                        name="oldPwd"
                        placeholder="Old password"
                        error={errors.oldPwd}
                        type="password"
                      />
                      <Icon align="left">
                        <FontAwesomeIcon icon={faLock} />
                      </Icon>
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
                          },
                          pattern: {
                            value: RegExp(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$/),
                            message: 'Password must have at least 1 uppercase character, 1 lowercase character, 1 number and at least 8 characters in total'
                          }
                        }}
                        name="newPwd"
                        placeholder="New password"
                        error={errors.newPwd}
                        type="password"
                      />
                      <Icon align="left">
                        <FontAwesomeIcon icon={faLock} />
                      </Icon>
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
                          },
                          validate: (pw: string) => {
                            const {newPwd} = getValues();
                            return newPwd === pw || "Passwords don't match";
                          }
                        }}
                        name="newPwdConfirm"
                        placeholder="Repeat new password"
                        error={errors.newPwdConfirm}
                        type="password"
                      />
                      <Icon align="left">
                        <FontAwesomeIcon icon={faLock} />
                      </Icon>
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
  )
};

export interface ChangePasswordModalProps {
  modalHook: UseModalType
}

export interface ChangePasswordFormData {
  oldPwd: string,
  newPwd: string,
  newPwdConfirm: string
}

export default ChangePasswordModal;
