FROM node:14.4-buster
WORKDIR /app
COPY ./package.json /app/package.json
RUN npm install && npm install -g @angular/cli@9.1.7
COPY . /app
EXPOSE 4200
ENTRYPOINT ["/bin/sh", "/app/entrypoint.sh"]
CMD ["/app/node_modules/.bin/ng", "serve", "--prod", "--host=0.0.0.0", "--disable-host-check"]