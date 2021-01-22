#
# Build stage
FROM node:14-alpine AS build

# Specify API URL (e.g. 'http://localhost:8080/')
ARG API_ENDPOINT=''

# Use for hosting behind reverse proxy. For example, if you want to access your site with
# http://example.com/allpic set this to '/allpic'
ARG PROXY_PATH='/'

# Timeout calls to API after (ms):
ARG API_TIMEOUT='5000'

ENV REACT_APP_API_ADDRESS=${API_ENDPOINT}
ENV REACT_APP_FRONT_URL=${PROXY_PATH}
ENV REACT_APP_API_TIMEOUT=${API_TIMEOUT}

WORKDIR /app
COPY package.json yarn.lock ./
RUN yarn
COPY . ./
RUN yarn build

#
# Package stage
FROM nginx:mainline-alpine
WORKDIR /app
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]