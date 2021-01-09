import {UseModalType} from "../hooks/useModal";
import {Box, Button, Control, Field, Generic, Icon, Modal} from "rbx";
import React, {useState} from "react";
import {useForm} from "react-hook-form";
import ControlledInput from "./ControlledInput";
import {APIMethods} from "../services/ApiActions";
import {Message} from "../types/API";

export interface RegisterFormData {
  username: string,
  email: string,
  password: string,
  repeatpassword: string
}

const RegisterModal = ({modalHook}: RegisterModalProps) => {

  const {handleSubmit, control, errors, getValues} = useForm<RegisterFormData>({
    defaultValues: {
      username: '',
      email: '',
      password: '',
      repeatpassword: ''
    }
  });

  const [msg, setMsg] = useState<Message | undefined>(undefined);
  const [isLoading, setIsLoading] = useState(false);

  const performRegistration = async (data: RegisterFormData) => {
    setIsLoading(true);
    const res = await APIMethods.register(data);

    if (res.message?.type === "success") {
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
                <Generic><h2>Register:</h2></Generic>
              </Generic>

              <Generic as="div" className="custom-modal-middle">
                <form onSubmit={handleSubmit(performRegistration)}>
                  <Field>
                    <Control iconLeft iconRight>
                      <ControlledInput
                        control={control}
                        rules={{
                          required: {
                            value: true,
                            message: 'Username is required'
                          },
                          minLength: {
                            value: 3,
                            message: 'Username must have at least 3 characters'
                          },
                          maxLength: {
                            value: 20,
                            message: 'Username must not have more than 20 characters'
                          }
                        }}
                        name="username"
                        placeholder="Username"
                        error={errors.username}
                      />
                      <Icon size="small" align="left">
                      </Icon>
                      <Icon size="small" align="right">
                      </Icon>
                    </Control>
                  </Field>

                  <Field>
                    <Control iconLeft iconRight>
                      <ControlledInput
                        control={control}
                        rules={{
                          required: {
                            value: true,
                            message: 'E-mail address is required'
                          }
                        }}
                        name="email"
                        type="email"
                        placeholder="E-mail"
                        error={errors.email}
                      />
                      <Icon size="small" align="left">
                      </Icon>
                      <Icon size="small" align="right">
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
                        name="password"
                        type="password"
                        placeholder="Password"
                        error={errors.password}
                      />
                      <Icon size="small" align="left">
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
                            const {password} = getValues();
                            return password === pw || "Passwords don't match";
                          }
                        }}
                        name="repeatpassword"
                        type="password"
                        placeholder="Repeat password"
                        error={errors.repeatpassword}
                      />
                      <Icon size="small" align="left">
                      </Icon>
                    </Control>
                  </Field>


                  <Field as="div" className="custom-modal-bottom">
                    <Generic as="div" className="custom-modal-error">
                      {msg &&
                        <p style={{color: msg.type === 'success' ? '#23d160' : '#ff3860'}}>{msg.text}</p>
                      }
                    </Generic>
                    <Control as="div" className="custom-modal-button">
                      <Button.Group align="right">
                        {isLoading ?
                          <Button state="loading" color="success">Loading</Button> :
                          <Button type="submit" color="success">Create account</Button>
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

export interface RegisterModalProps {
  modalHook: UseModalType
}

export default RegisterModal;
