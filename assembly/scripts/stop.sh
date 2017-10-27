#!/bin/bash

PROJECT_NAME=ik-crm-fix-tools
PID=`ps -ef | grep $PROJECT_NAME | grep -v "grep" | awk '{print $2}'`

if [ $PID ]; then
    kill -9 $PID
    if [[ $? -eq 0 ]]; then
        echo "success to stop $PROJECT_NAME. pid: $PID"
    else
        echo "fail to stop $PROJECT_NAME. pid: $PID"
    fi
fi