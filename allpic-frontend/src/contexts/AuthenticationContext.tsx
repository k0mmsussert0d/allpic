import React from "react";

export interface AuthenticationContextType {
  authenticated: boolean,
  username: string | null
}

export let AuthenticationContext = React.createContext<AuthenticationContextType>({authenticated: false, username: null});

export const AuthenticationContextProvider = AuthenticationContext.Provider;
export const AuthenticationContextConsumer = AuthenticationContext.Consumer;
