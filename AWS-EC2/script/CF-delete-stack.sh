REGION=eu-central-1
PROFILE=ArticleUser
STACK_NAME=EC2-RDS-Stack

export AWS_PROFILE=${PROFILE}
export AWS_REGION=${REGION}

echo "Deleting ${STACK_NAME}"

aws cloudformation delete-stack \
      --stack-name ${STACK_NAME}
