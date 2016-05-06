FROM java:8
MAINTAINER saintdan saintdan1011@gmail.com
ADD build/libs/spring-rest-oauth2-sample-0.10.0.RELEASE.jar core.jar
RUN sh -c 'touch /core.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/core.jar"]