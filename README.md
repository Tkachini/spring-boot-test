# Spring Boot Controller app

## 1. Build app & Dockerfile

`mvn clean install`

## 2. Run docker-compose

`docker-compose up -d`

The app shoud automatic create database with name 'testdb'

## 3. Test

1. Generate infrastructure. Navigate to `localhost:8080/hello/generate?count=50` where count is number of generated contacts. 
Words will contains [A-z, 0-9].

2. Test application by rout `localhost:8080//hello/contacts?nameFilter=val`, where val is regexp.

OR:

`localhost:8080//hello/contacts/stream?nameFilter=val` - this resource use java stream api to filter contacts.