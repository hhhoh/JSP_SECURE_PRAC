#!/bin/bash
javac -sourcepath /var/lib/tomcat/webapps/java/user /var/lib/tomcat/webapps/java/user/*.java
javac -sourcepath /var/lib/tomcat/webapps/java/bbs /var/lib/tomcat/webapps/java/bbs/*.java
cp -f /var/lib/tomcat/webapps/java/bbs/*.class /var/lib/tomcat/webapps/ROOT/WEB-INF/classes/bbs
cp -f /var/lib/tomcat/webapps/java/user/*.class /var/lib/tomcat/webapps/ROOT/WEB-INF/classes/user
service tomcat restart
service httpd restart
