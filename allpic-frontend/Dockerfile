FROM node:14-alpine

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
RUN yarn global add serve
COPY . ./
RUN yarn build
CMD ["serve", "-s", "build"]
