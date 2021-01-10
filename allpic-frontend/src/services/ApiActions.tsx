import {AuthenticationContextType} from "../contexts/AuthenticationContext";
import axios from "./axiosConfig";
import {RegisterFormData} from "../components/RegisterModal";
import {APIResponse, UserDetails, UserDTO} from "../types/API";
import {AxiosError, AxiosResponse} from "axios";
import {LoginFormData} from "../components/LoginModal";

export const APIMethods = {
  getAuth: async (): Promise<APIResponse<AuthenticationContextType>> => {
    return axios.get<UserDetails>('/auth/')
      .then((res: AxiosResponse<UserDetails>): APIResponse<AuthenticationContextType> => {
        return {
          response: {
            authenticated: true,
            userDetails: res.data
          }
        };
      })
      .catch((res: AxiosResponse<UserDetails>): APIResponse<AuthenticationContextType> => {
        return {
          response: {
            authenticated: false,
            userDetails: res.data ?? undefined
          }
        };
      });
  },

  authenticate: async (data: LoginFormData): Promise<APIResponse<UserDetails>> => {
    return axios.post<UserDetails>('/auth/login', data)
      .then((res: AxiosResponse<UserDetails>): APIResponse<UserDetails> => {
        return {
          message: {
            type: "success",
            text: "Successfully logged in"
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<UserDetails> => {
        return {
          message: {
            type: 'failure',
            text: reason.response?.status === 401 ? 'Incorrect username or password' : (reason.response?.data?.message as string ?? 'Unable to log in')
          }
        };
      });
  },

  register: async (data: RegisterFormData): Promise<APIResponse<UserDTO>> => {
    return axios.post<UserDTO>('/register/', data)
      .then((res: AxiosResponse<UserDTO>): APIResponse<UserDTO> => {
        return {
          message: {
            type: 'success',
            text: 'Successfully registered user!'
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<UserDTO> => {
        return {
          message: {
            type: 'failure',
            text: reason.response?.data?.message as string ?? 'Unable to register new user'
          }
        };
      })
  },

  logout: async (): Promise<void> => {
    return axios.get('/auth/logout');
  }
}
