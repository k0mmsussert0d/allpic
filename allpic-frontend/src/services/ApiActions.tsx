import {AuthenticationContextType} from "../contexts/AuthenticationContext";
import axios from "./axiosConfig";
import {RegisterFormData} from "../components/RegisterModal";
import {APIResponse, CommentDTO, ImageDTO, ImagePreviewDetails, UserDetails, UserDTO} from "../types/API";
import {AxiosError, AxiosResponse} from "axios";
import {LoginFormData} from "../components/LoginModal";
import Configuration from "./Configuration";
import { ChangePasswordFormData } from "../components/ChangePasswordModal";

export const APIMethods = {
  getAuth: async (): Promise<APIResponse<AuthenticationContextType>> => {
    return axios.get<UserDetails>('/auth/')
      .then((res: AxiosResponse<UserDetails>): APIResponse<AuthenticationContextType> => {
        return {
          response: {
            authenticated: true,
            username: res.data.username
          }
        };
      })
      .catch((res: AxiosResponse<UserDetails>): APIResponse<AuthenticationContextType> => {
        return {
          response: {
            authenticated: false,
            username: res.data.username ?? undefined
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
  },

  uploadFile: async(data: FormData): Promise<APIResponse<ImageDTO>> => {
    return axios.post<ImageDTO>('/img/', data)
      .then((res: AxiosResponse<ImageDTO>): APIResponse<ImageDTO> => {
        return {
          message: {
            type: 'success',
            text: 'File uploaded!'
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<ImageDTO> => {
        return {
          message: {
            type: 'failure',
            text: reason.response?.data?.message as string ?? 'Unable to upload a file'
          }
        };
      })
  },

  getFileDetails: async (token: string): Promise<APIResponse<ImageDTO>> => {
    return axios.get<ImageDTO>(`/img/${token}`)
      .then((res: AxiosResponse<ImageDTO>): APIResponse<ImageDTO> => {
        return {
          message: {
            type: 'success',
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError) : APIResponse<ImageDTO> => {
        return {
          message: {
            type: 'failure',
            text: 'Cannot find image'
          }
        };
      });
  },

  getImageLink: (token: string): string => {
    return `${axios.defaults.baseURL}/img/i/${token}`;
  },

  getImageThumbLink: (token: string): string => {
    return `${axios.defaults.baseURL}/img/i/thumb/${token}`;
  },

  getImageViewPageLink: (token: string): string => {
    return `${Configuration.FrontURL}/${token}`;
  },

  getLatestImagesPreviews: (page: number, perPage: number): Promise<APIResponse<Array<ImagePreviewDetails>>> => {
    return axios.get<Array<ImagePreviewDetails>>(`/img/recent`, { params: { page: page ?? 1, per_page: perPage ?? 10 }})
      .then((res: AxiosResponse<Array<ImagePreviewDetails>>): APIResponse<Array<ImagePreviewDetails>> => {
        return {
          message: {
            type: 'success'
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<Array<ImagePreviewDetails>> => {
        return {
          message: {
            type: 'failure',
            text: 'Cannot fetch latest images'
          }
        };
      });
  },

  getComments: async (token: string): Promise<APIResponse<Array<CommentDTO>>> => {
    return axios.get<Array<CommentDTO>>(`/comment/${token}`)
      .then((res: AxiosResponse<Array<CommentDTO>>): APIResponse<Array<CommentDTO>> => {
        return {
          message: {
            type: 'success'
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<Array<CommentDTO>> => {
        return {
          message: {
            type: 'failure',
            text: 'Cannot fetch comments'
          }
        };
      })
  },

  postComment: async (token: string, content: string): Promise<APIResponse<CommentDTO>> => {
    return axios.post<CommentDTO>(`/comment/${token}`, content, {
      headers: {
        'Content-Type': 'text/plain'
      }
    })
      .then((res: AxiosResponse<CommentDTO>): APIResponse<CommentDTO> => {
        return {
          message: {
            type: 'success',
            text: 'Comment added'
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<CommentDTO> => {
        return {
          message: {
            type: 'failure',
            text: 'Failed to add comment'
          }
        };
      });
  },

  resetPassword: async (oldPwd: string, newPwd: string): Promise<APIResponse<UserDTO>> => {
    return axios.put<UserDTO>('/user/reset', { oldPwd: oldPwd, newPwd: newPwd } )
      .then((res: AxiosResponse<UserDTO>): APIResponse<UserDTO> => {
        return {
          message: {
            type: 'success',
            text: 'Password successfully reset'
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<UserDTO> => {
        return {
          message: {
            type: 'failure',
            text: reason.response?.data?.message as string ?? 'Error on resetting password'
          }
        };
      });
  },

  getAvatarLink: (username: string): string => {
    return `${axios.defaults.baseURL}/user/${username}/avatar`;
  },

  getUserDetails: async (username: string): Promise<APIResponse<UserDTO>> => {
    return axios.get<UserDTO>(`/user/${username}`)
      .then((res: AxiosResponse<UserDTO>): APIResponse<UserDTO> => {
        return {
          message: {
            type: 'success',
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<UserDTO> => {
        return {
          message: {
            type: 'failure',
            text: 'Error fetching user details'
          }
        };
      });
  },

  setAvatar: async(data: FormData): Promise<APIResponse<UserDTO>> => {
    return axios.post<UserDTO>('/user/av/', data)
      .then((res: AxiosResponse<UserDTO>): APIResponse<UserDTO> => {
        return {
          message: {
            type: 'success',
            text: 'Avatar set successfully'
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<UserDTO> => {
        return {
          message: {
            type: 'failure',
            text: reason.response?.data?.message as string ?? 'Error while setting avatar'
          }
        };
      });
  },

  getUserImages: async(username: string): Promise<APIResponse<Array<ImagePreviewDetails>>> => {
    return axios.get<Array<ImagePreviewDetails>>('/user/images')
      .then((res: AxiosResponse<Array<ImagePreviewDetails>>): APIResponse<Array<ImagePreviewDetails>> => {
        return {
          message: {
            type: 'success'
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<Array<ImagePreviewDetails>> => {
        return {
          message: {
            type: 'failure',
            text: 'Unable to fetch user images'
          }
        };
      });
  },

  changePassword: async(requestData: ChangePasswordFormData): Promise<APIResponse<UserDetails>> => {
    return axios.put('/user/reset', requestData)
      .then((res: AxiosResponse<UserDetails>): APIResponse<UserDetails> => {
        return {
          message: {
            type: 'success',
            text: 'Password changed'
          },
          response: res.data
        };
      })
      .catch((reason: AxiosError): APIResponse<UserDetails> => {
          return {
            message: {
              type: 'failure',
              text: reason.response?.data?.message as string ?? 'Error while changing passsword'
            }
          };
      });
  }
}
