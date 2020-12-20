import {AuthenticationContextType, UserDetails} from "../contexts/AuthenticationContext";
import axios from "./axiosConfig";

export const APIMethods = {
  getAuth: () : AuthenticationContextType => {
    axios.get<UserDetails>('/auth')
      .then(res => {
        if (res.status === 200) {
          return {
            authenticated: true,
            userDetails: res.data
          };
        }
      })
      .catch(res => {
        console.error('Error while checking authentication status');
      });

    return {
      authenticated: false,
      userDetails: null
    };
  }
};
