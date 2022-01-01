FROM node:14.17.5-alpine as builder
WORKDIR '/app'
COPY package*.json ./
RUN npm ci
COPY . .
RUN npx ng build frontend --localize

FROM nginx
EXPOSE 80
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=builder /app/dist/frontend /www/data