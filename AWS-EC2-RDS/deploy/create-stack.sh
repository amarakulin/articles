#!/usr/bin/env bash

handleIfErrorOccur() {
  if [ $? != 0 ]; then
          echo "[-] ${1}"
          exit 1
  fi
}

echo "[+] Creating stack..."
aws cloudformation create-stack \
      --stack-name ${WEB_APP_STACK_NAME} \
      --template-url ${WEB_APP_TEMPLATE_URL} \
      --parameters file://parameters.json \
      --capabilities CAPABILITY_NAMED_IAM
handleIfErrorOccur "Could create stack with name ${WEB_APP_STACK_NAME}"

echo "[+] Waiting on ${WEB_APP_STACK_NAME} create completion..."
aws cloudformation wait stack-create-complete \
      --stack-name ${WEB_APP_STACK_NAME}

echo "[+] The ${WEB_APP_STACK_NAME} has been created"
