# IAM explaining on dogs

## Introducing
I would like to share my thoughts about AWS IAM. We will look through main statements and definitions. 
Let's put away hard official documentation and just take a look on hard things from real world point.
That is why we will need to get along with Dogs

![doge-meme](pictures/doge-meme-8bit-glasses.jpg)


## Role and Policy
IAM has tons of instruments to satisfy the security of your system.
But today I will walk you through two essential Roles and Policies.

**Role** is mostly a group of **Policies**, that need to have access to other services in AWS system.

**Policy** is allowed or forbidden actions to a service.

So, you can arrange a list allowed/not allowed actions to a service(*Policy*) -> group all that rules(*Role*) -> assign to an appropriate service.
Doesn't sound difficult. To make it easier, let's talk about... well, Dogs.

Let's think that our Role is a lifestyle of a dog, such as:
- Dog could have an owner (PetRole)
- Dog could be a street buddy (StreetRole)
- Dog could be a building's guard the whole life (GuardRole)
- etc.

The Policies would depend on the dog lifestyle. Each way of living allows them to do some actions, and some actions are forbidden as well.
So I made up with following Policies for our friends.

Examples of PetPolicies:
* Having access to the house
* Allowing play with pet owner
* Leaving the wool all over the couch
* And it's strictly forbidden to take a pee at the slippers

Examples of StreetPolicies:
* Doesn't have access to any house(sad)
* Having unlimited number of partners(cool)
* Taking a pee on every car(incredible)

___

## What is in common with Lambda AWS and dog?
For now, we know the theory of the Role and Policy, to follow them in a practice we will need to configure a json file with AWS format.

Let's think that AWS Lambda is our "Pet" dog.
The following table will get rid of your doubts:


| Pet                              | AWS Lambda                |
|----------------------------------|---------------------------|
| has access to the house          | access to S3 bucket       |
| leaves the wool all on the couch | store logs in CloudWatch  | 
| is allowed to notify pet owner   | send notifications to SNS |

___

### Dog example

How it would look in AWS systems if a "Dog" was a service:
Let's see how the regular "PET" dog looks in AWS configurations

**PetRole**
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "dog.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
```
Effect - could be "Allow" or "Deny"

Principal - describe a service which would have the role(our case have `dog` service)

Action - consist of two parts separated by `:`. First one is a service, second one is an action for the service.
So the `sts` is a SecurityTokenService, which would help us to `AssumeRole`. Here is an example of assuming role for the `dog.amazonaws.com` service

**PetPolicy**
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "house:AccessToHouse"
      ],
      "Resource": "arn:aws:house:*:*:address:/country/town/street/house/apartment-number"
    },
    {
      "Effect": "Allow",
      "Action": [
        "couch:AllowWoolToStick"
      ],
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "petOwner:AllowToNotify",
        "petOwner:PlayWith"
      ],
      "Resource": "arn:aws:user:*:*:per-owner:/username"
    },
    {
      "Effect": "Deny",
      "Action": [
        "slippers:TakePeeAt"
      ],
      "Resource": "*"
    }
  ]
}
```
The `Effect` and `Action` was described above, so let's take a look on the last field of `Statements`:

Resource - The path to apply the action.
You can specify a particular service or range of services to for which your rules will be used ~~or just hammer and slap the wildcard "*"~~

___

### Role and Policy for AWS Lambda example
From the description above let's make the Lambda IAM configuration

**LambdaRole**
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
```
The only differences with **PetRole** is the imagined Service `dog.amazonaws.com`

**LambdaPolicy**
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:*"
      ],
      "Resource": "arn:aws:s3:::*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents",
        "logs:DescribeLogStreams"
      ],
      "Resource": [
        "arn:aws:logs:*:*:log-group:/*"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "sns:PutDataProtectionPolicy",
        "sns:Publish",
        "sns:DeleteTopic",
        "sns:CreateTopic",
        "sns:Subscribe",
        "sns:ConfirmSubscription"
      ],
      "Resource": "arn:aws:sns:::*"
    }
  ]
}
```

You can compare with the **PetPolicies** each statement of the policy correspond to a table presented above.
Except slippers part, the statement is need's to just show the `"Effect": "Deny"` usages.

For now, you have a question
How does the Role could contain all Policies? There is no link for that in json configurations. 
There are two ways to attach Policies to Role:
* Programmatic approach by configuration in [AWS CloudFormation](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/aws-resource-iam-role.html)
* Manual approach by using the [AWS Console](https://docs.aws.amazon.com/IAM/latest/UserGuide/access_policies_job-functions_create-policies.html)

I won't cover all the steps of those two approaches in the article. So it falls on your shoulders, my friend. I know life is hard.

To sum up, you are able to arrange different Policies to a Role. Now you could attach your Role to an appropriate AWS service, so the service will behalf on the Role to the other services. In our example we took a look how AWS Lambda could have access to S3, SNS and CloudWatch.

If you are willing to assume the roles to your friend or colleague, then you will need to create User and maybe UserGroups. 
But this is discussion for another article, something telling me that dogs would not work there :)

___

## Not required actions

I had a chance to present the article to you. I am glad that you spend some time of your best years for it.
Why would we take a bit more?

Do you have a question? -> Go ahead and leave it in comments below.

Are you thinking the public edition could be useful for others, or at least could be fun? -> Thumbs up on this one, because we all depend on the algorithms.

With best regards, Aleksandr Marakulin (aka pichkasik)
See you in the next posts!

