REGION=eu-central-1
PROFILE=ArticleUser
STACK_NAME=EC2-RDS-Stack
TEMPLATE_URL=https://src-articles.s3.eu-central-1.amazonaws.com/ec2-rds/CF-parent.yaml
PARAMETERS_PATH=C:/articles/AWS-EC2/CF-templates/parameters.json

echo "Creating stack..."

aws cloudformation create-stack \
      --stack-name ${STACK_NAME} \
      --template-url ${TEMPLATE_URL} \
      --capabilities CAPABILITY_NAMED_IAM \
      --profile ${PROFILE} \
      --region ${REGION} \
      --parameters file://${PARAMETERS_PATH}

echo "Waiting on ${STACK_NAME} create completion..."

aws cloudformation wait stack-create-complete \
      --stack-name ${STACK_NAME} \
      --profile ${PROFILE} \
      --region ${REGION}

echo "Waiting on Description stack create completion..."
aws cloudformation describe-stacks \
     --stack-name ${STACK_NAME} \
     --profile ${PROFILE} \
     --region ${REGION}

echo "DbUrl is:"
aws cloudformation list-exports \
     --profile ${PROFILE} \
     --region ${REGION} \
     --no-paginate \
     --query "Exports[?Name=='DbUrl'].Value" \
     --output text


#TODO create the start of the service