#!/usr/bin/env bash

runScriptSafely() {
  sh ${1}
  if [ $? != 0 ]; then
          echo "[-] Process has been aborted"
          exit 1
  fi
}

REGION=#provide an AWS region
PROFILE=#provide an IAM User (The Admin permissions will be easiest way)

export AWS_PROFILE=${PROFILE}
export AWS_REGION=${REGION}

export WEB_APP_STACK_NAME=#provide any name for CloudFormation Stack
export WEB_APP_TEMPLATE_URL=#url template to the CF-templates/CF-parent.yaml your in S3 bucket
export WEB_APP_JAR_FILE_PATH=#path to jar file in your S3 bucket
export WEB_APP_JAR_FILENAME=#provide your_jar_filename.jar
export WEB_APP_WORKDIR='/app'
WEB_APP_FILTER='Key=tag:EC2Instance,Values=WebApp' #take a look at the CF-templates/CF-app.yaml

echo "[+] Executing script to create a stack"
runScriptSafely create-stack.sh

echo "[+] Retrieving instanceId..."
instanceId=$(aws ssm describe-instance-information \
    --filters ${WEB_APP_FILTER} \
    --output text \
    --query "InstanceInformationList[*].InstanceId")
export WEB_APP_INSTANCE_ID=${instanceId}
echo "The instanceId=${WEB_APP_INSTANCE_ID} has been retrieved"

echo "[+] Adding parameters to SSM..."
runScriptSafely add-parameters.sh

echo "[+] Uploading resources to the EC2 instance..."
runScriptSafely upload-resources.sh

echo "[+] Launching the web app"
runScriptSafely run-app.sh

echo "The app has been deployed"
