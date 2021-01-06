import {AuthenticationContextType, UserDetails} from "../contexts/AuthenticationContext";
import axios from "./axiosConfig";

export const APIMethods = {
  getAuth: () : AuthenticationContextType => {
    axios.get<UserDetails>('/auth/')
      .then(res => {
        if (res.status === 200) {
          return {
            authenticated: true,
            userDetails: res.data
          };
        }
      })
      .catch(res => {
        if (res.response.status !== 401) {
          console.error('Error while checking authentication status');
        }
      });

    return {
      authenticated: false,
      userDetails: null
    };
  },

  authenticate: (login: string, password: string) : boolean => {
    axios.get('/auth/login', {data: {username: login, password: password}})
      .then(res => {
        if (res.status === 200) {
          return true;
        }
      })
      .catch(res => {
          console.error('Error. Authentication failed.');
        }
      );

    return false;
  }
};
