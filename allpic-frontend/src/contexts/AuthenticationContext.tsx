import React from "react";
import {UserDetails} from "../types/API";

export interface AuthenticationContextType {
  authenticated: boolean,
  userDetails: UserDetails | null
}

export let AuthenticationContext = React.createContext<AuthenticationContextType>({authenticated: false, userDetails: null});

export const AuthenticationContextProvider = AuthenticationContext.Provider;
export const AuthenticationContextConsumer = AuthenticationContext.Consumer;
