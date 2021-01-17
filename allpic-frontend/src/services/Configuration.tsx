const Configuration = {
  APIAddress: process.env.REACT_APP_API_ADDRESS as string,
  APITimeout: process.env.REACT_APP_API_TIMEOUT as unknown as number,

  FrontURL: process.env.REACT_APP_FRONT_URL as string,
}

export default Configuration
