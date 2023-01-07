#!/usr/bin/env bash

handleIfErrorOccur() {
  if [ $? != 0 ]; then
          echo "[-] ${1}"
          exit 1
  fi
}

addParametersToSSM() {
    for row in $(echo "${1}" | jq -r '.[] | .[] | @base64'); do
        _jq() {
         echo ${row} | base64 -i --decode | jq -r ${1}
        }
       key=$(_jq '.OutputKey')
       value=$(_jq '.OutputValue')

      #The if conditinos is a hack, since I can't find the appropriate instrument to extract outputs from CloudFormation(such as tags for example)
      if [[ $key == Db* ]] ;
      then
        addedKey=$(aws ssm put-parameter \
               --name ${key} \
               --value ${value} \
               --type String \
               --overwrite)
        echo "The key with name '${key}' has been added to SSM"
      fi

    done
}

echo "[+] Getting parameters from stack..."
stackOutputs=$(aws cloudformation describe-stacks \
    --output json \
    --query "Stacks[*].Outputs[*]")
handleIfErrorOccur "Couldn't get outputs from created stack"

echo "[+] Adding parameters to SSM..."
addParametersToSSM "${stackOutputs}"

echo "[+] Parameters has been stored in SSM"
