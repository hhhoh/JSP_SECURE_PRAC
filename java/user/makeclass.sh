#!/bin/bash
javac -sourcepath . *.java
cp -f *.class /var/lib/tomcat/webapps/ROOT/WEB-INF/classes/user
service tomcat restart
service httpd restart
