#!/usr/bin/env bash

handleIfErrorOccur() {
  if [ $? != 0 ]; then
          echo "[-] ${1}"
          exit 1
  fi
}

javaParams='-DDB_HOST={{ssm:DbUrl}} -DDB_PORT={{ssm:DbPort}} -DDB_NAME={{ssm:DbName}} -DDB_USERNAME={{ssm:DbUsername}} -DDB_PASSWORD={{ssm:DbPassword}}'

echo "[+] Run app on the instance with instanceId=${WEB_APP_INSTANCE_ID}"
cmdRunAppId=$(aws ssm send-command \
    --document-name "AWS-RunShellScript" \
    --targets "Key=InstanceIds,Values=${WEB_APP_INSTANCE_ID}" \
    --parameters '{"commands":["#!/bin/bash", "java -jar '"${javaParams}"' '"${WEB_APP_JAR_FILENAME}"'"], "workingDirectory":["'${WEB_APP_WORKDIR}'"]}' \
    --output text \
    --query "Command.CommandId")
handleIfErrorOccur "Couldn't run the app with jar file: ${WEB_APP_JAR_FILENAME}"

echo "[+] Command to run the application has been executed with id='${cmdRunAppId}'"