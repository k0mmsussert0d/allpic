import React from "react";

export interface UserDetails {
  role: string,
  username: string,
}

export interface AuthenticationContextType {
  authenticated: boolean,
  userDetails: UserDetails | null
}

export let AuthenticationContext = React.createContext<AuthenticationContextType>({authenticated: false, userDetails: null});

export const AuthenticationContextProvider = AuthenticationContext.Provider;
export const AuthenticationContextConsumer = AuthenticationContext.Consumer;
