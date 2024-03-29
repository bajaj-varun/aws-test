import json, boto3
from botocore.exceptions import ClientError


def lambda_handler(event, context):

    iam_client = boto3.client('iam')

    role_name = event['RoleName']
    account_id = event['AccountId']

    trust_relationship_policy_another_iam_user = {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Effect": "Allow",
                "Principal": {
                    "AWS": "arn:aws:iam::<ACCOUNT>:user/<REPLACE_WITH_USER_NAME>"
                },
                "Action": "sts:AssumeRole"
            }
        ]
    }

   trust_relationship_policy_another_aws_service = {
        "Version": "2012-10-17",
        "Statement": [
            {
                "Effect": "Allow",
                "Principal": {
                    "Service": "ec2"
                },
                "Action": "sts:AssumeRole"
            }
        ]
    }

    try:
        create_role_res = iam_client.create_role(
            RoleName=role_name,
            AssumeRolePolicyDocument=json.dumps(trust_relationship_policy_another_iam_user),
            Description='This is a test role',
            Tags=[
                {
                    'Key': '<>',
                    'Value': '<>'
                }
            ]
        )
    except ClientError as error:
        if error.response['Error']['Code'] == 'EntityAlreadyExists':
            return 'Role already exists... hence exiting from here'
        else:
            return 'Unexpected error occurred... Role could not be created', error

    policy_json = {
        "Version": "2012-10-17",
        "Statement": [{
            "Effect": "Allow",
            "Action": [
                "ec2:*"
            ],
            "Resource": "*"
        }]
    }

    policy_name = role_name + '_policy'
    policy_arn = ''

    try:
        policy_res = iam_client.create_policy(
            PolicyName=policy_name,
            PolicyDocument=json.dumps(policy_json)
        )
        policy_arn = policy_res['Policy']['Arn']
    except ClientError as error:
        if error.response['Error']['Code'] == 'EntityAlreadyExists':
            print('Policy already exists... hence using the same policy')
            policy_arn = 'arn:aws:iam::' + account_id + ':policy/' + policy_name
        else:
            print('Unexpected error occurred... hence cleaning up', error)
            iam_client.delete_role(
                RoleName= role_name
            )
            return 'Role could not be created...', error

    try:
        policy_attach_res = iam_client.attach_role_policy(
            RoleName=role_name,
            PolicyArn=policy_arn
        )
    except ClientError as error:
        print('Unexpected error occurred... hence cleaning up')
        iam_client.delete_role(
            RoleName= role_name
        )
        return 'Role could not be created...', error

    return 'Role {0} successfully got created'.format(role_name)
