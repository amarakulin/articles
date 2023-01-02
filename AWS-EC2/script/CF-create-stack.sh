REGION=eu-central-1
PROFILE=ArticleUser
STACK_NAME=EC2-RDS-Stack
TEMPLATE_URL=https://src-articles.s3.eu-central-1.amazonaws.com/ec2-rds/CF-parent.yaml

export AWS_PROFILE=${PROFILE}
export AWS_REGION=${REGION}

echo "Creating stack..."

aws cloudformation create-stack \
      --stack-name ${STACK_NAME} \
      --template-url ${TEMPLATE_URL} \
      --capabilities CAPABILITY_NAMED_IAM

echo "Waiting on ${STACK_NAME} create completion..."

aws cloudformation wait stack-create-complete \
      --stack-name ${STACK_NAME}

echo "The ${STACK_NAME} has been created"
