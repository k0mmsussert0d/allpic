#!/bin/bash
set -xe
: "${API_URL?Need an api url}"

sed -i "s%apiUrl_replace_me%$API_URL%g" /app/src/environments/environment.prod.ts

exec "$@"