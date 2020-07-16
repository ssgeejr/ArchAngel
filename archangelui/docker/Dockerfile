FROM tomcat:7-jre8

LABEL maintainer="ioexcept@gmail.com"
LABEL version="1.1"

ADD pasedb.war /usr/local/tomcat/webapps/
ADD server.xml /usr/local/tomcat/conf/server.xml
RUN rm -rf /usr/local/tomcat/webapps/ROOT*

EXPOSE 80
