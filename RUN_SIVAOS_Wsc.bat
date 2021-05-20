@echo off
start java -Xmx1024m -jar -Dspring.profiles.active=dev build\libs\sivaos-wsc-1.1.0.jar &
