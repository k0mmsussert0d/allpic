import {AuthenticationContextType} from "../contexts/AuthenticationContext";
import axios from "./axiosConfig";
import {RegisterFormData} from "../components/RegisterModal";
import {APIResponse, UserDetails, UserDTO} from "../types/API";
import {AxiosError, AxiosResponse} from "axios";

export const APIMethods = {
  getAuth: async (): Promise<APIResponse<AuthenticationContextType>> => {
    const res = await axios.get<UserDetails>('/auth/');
    if (res.status === 200) {
      return {
        response: {
          authenticated: true,
          userDetails: res.data
        }
      };
    }

    return {
      response: {
        authenticated: false,
        userDetails: null
      }
    };
  },

  authenticate: async (login: string, password: string): Promise<APIResponse<UserDetails>> => {
    const res = await axios.get<UserDetails>('/auth/login', {data: {username: login, password: password}})
    if (res.status === 200) {
      return {
        message: {
          type: 'success',
          text: 'Successfully logged in!'
        },
        response: res.data
      };
    }

    return {
      message: {
        type: 'failure',
        text: 'Incorrect username or password'
      }
    };
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
  }
}
