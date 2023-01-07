#!/usr/bin/env bash

handleIfErrorOccur() {
  if [ $? != 0 ]; then
          echo "[-] ${1}"
          exit 1
  fi
}

echo "[+] Downloading jar file to the ec2 instance..."
cmdUploadJarId=$(aws ssm send-command \
    --document-name "AWS-RunShellScript" \
    --targets "Key=InstanceIds,Values=${WEB_APP_INSTANCE_ID}" \
    --parameters '{"commands":["#!/bin/bash", "mkdir '"${WEB_APP_WORKDIR}"'", "aws s3 cp '"${WEB_APP_JAR_FILE_PATH}${WEB_APP_JAR_FILENAME}"' '"${WEB_APP_WORKDIR}"'/"],"executionTimeout":["300"]}' \
    --timeout-seconds 300 \
    --output text \
    --query "Command.CommandId")
handleIfErrorOccur "Couldn't upload jar[${WEB_APP_JAR_FILE_PATH}${WEB_APP_JAR_FILENAME}] file to the server path ${WEB_APP_WORKDIR}"

echo "[+] Waiting util the file will be downloaded..."
aws ssm wait command-executed \
    --command-id ${cmdUploadJarId} \
    --instance-id ${WEB_APP_INSTANCE_ID} \
    --output text
handleIfErrorOccur "Downloading has been failed"

echo "[+] Jar file has been downloaded"