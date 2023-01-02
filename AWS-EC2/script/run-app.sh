REGION=eu-central-1
PROFILE=ArticleUser

addParametersToSSM() {
    for row in $(echo "${1}" | jq -r '.[] | .[] | @base64'); do
        _jq() {
         echo ${row} | base64 -i --decode | jq -r ${1}
        }
       key=$(_jq '.OutputKey')
       value=$(_jq '.OutputValue')

      #TODO Hack since I can't find the appropriate instrument to extract outputs
      if [[ $key == Db* ]] ;
      then
        addedKey=$(aws ssm put-parameter \
               --name ${key} \
               --value ${value} \
               --type String)
        echo "The key with name '${key}' has been added to SSM"
      fi

    done
}

export AWS_PROFILE=${PROFILE}
export AWS_REGION=${REGION}

instanceId=$(aws ssm describe-instance-information \
    --output text --query "InstanceInformationList[*].InstanceId")

echo "Run app on the instance with instanceId=${instanceId}"

OUTPUTS=$(aws cloudformation describe-stacks \
    --output json \
    --query "Stacks[*].Outputs[*]")

echo "Adding parameters to SSM..."
addParametersToSSM "${OUTPUTS}"

#TODO delete profile
javaParams='-DDB_HOST={{ssm:DbUrl}} -DDB_PORT={{ssm:DbPort}} -DDB_NAME={{ssm:DbName}} -DDB_USERNAME={{ssm:DbUsername}} -DDB_PASSWORD={{ssm:DbPassword}} -Dspring.profiles.active=aws'

commandId=$(aws ssm send-command \
    --document-name "AWS-RunShellScript" \
    --targets "Key=InstanceIds,Values=${instanceId}" \
    --parameters '{"commands":["#!/bin/bash", "cd /app", "java -jar '"${javaParams}"' AWS-EC2-RDS-1.0-SNAPSHOT.jar"]}' \
    --output text --query "Command.CommandId")

echo "Command to run the application has been executed with id='${commandId}'"


