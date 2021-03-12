FROM java:8
#FROM openjdk:8-alpine

WORKDIR /usr/src/app
USER root

RUN ["mkdir","images"] 
RUN ["mkdir","emotions"] 
RUN ["mkdir","videos"] 
copy ./target/*.jar ./
copy ./lib/*.so ./lib/ 
copy ./start.sh ./
EXPOSE 8080
#  java  -Djava.library.path=/usr/src/app/lib -jar wechatwork-fetch-messages-0.0.1-SNAPSHOT.jar
CMD ["sh","start.sh"] 
