# gradle-localstack-dynamodb-example

An example of working with mock AWS DynamoDB tables using [LocalStack](https://github.com/localstack/localstack) and the [Gradle LocalStack Plugin](https://github.com/Nike-Inc/gradle-localstack).

In this example you will see how to configure a mock DynamoDB table and initialize it with test data using LocalStack and the
Gradle LocalStack Plugin.

## Building the Example
Run the following command to build the example:

    ./gradlew clean build

## Running the Example
Follow the steps below to run the example application:

1. Run the following command to start LocalStack and initialize the mock DynamoDB table:

        ./gradlew startLocalStack
        
    If successful, you will see the following in the terminal:
    
        > Task :setupLocalTable
        Creating DynamoDB table: example.websession
        Created DynamoDB table: example.websession
        Initializing table: example.websession
        Initialized table: example.websession
        
        > Task :startLocalStack
        LocalStack Started

2. Run the following command to list all tables within LocalStack DynamoDB:

        ./gradlew listDynamoDbTables
        
    If successful, you will see the following in the terminal:
    
        > Task :listDynamoDbTables
        ┌──────────────────────────────────────────────────────────────────────────────┐
        │TableName                                                                     │
        ├──────────────────────────────────────────────────────────────────────────────┤
        │example.websession                                                            │
        └──────────────────────────────────────────────────────────────────────────────┘

3. Run the following command insert a web session record into the DynamoDB table and then query all web session records for a specific user:

        ./gradlew run
        
If successful, you will see something similar to the following in the terminal:

        > Task :run
        [main] INFO example.DynamoExampleApplication - Adding new websession: 49fe6687-ad57-451a-a70c-c9dc6f72c050
        [main] INFO example.DynamoExampleApplication - Retrieving websession: 49fe6687-ad57-451a-a70c-c9dc6f72c050
        [main] INFO example.DynamoExampleApplication - Found websession: WebSession{sessionId='49fe6687-ad57-451a-a70c-c9dc6f72c050', userId=10, sessionData='test data', createdOn='2021-01-10 09:51:08'}
        [main] INFO example.DynamoExampleApplication - Retrieve all websessions for userId: 10
        [main] INFO example.DynamoExampleApplication - Found websession: WebSession{sessionId='49fe6687-ad57-451a-a70c-c9dc6f72c050', userId=10, sessionData='test data', createdOn='2021-01-10 09:51:08'}
        [main] INFO example.DynamoExampleApplication - Found websession: WebSession{sessionId='51e50af5-9830-4128-985f-b461765b23b1', userId=10, sessionData='vPDlnmgshQJmgevMjCDD', createdOn='2021-01-10 09:51:00'}
        [main] INFO example.DynamoExampleApplication - Found websession: WebSession{sessionId='e9daeb6f-66b7-43d8-a878-75e1e1e78e76', userId=10, sessionData='qBWA7bk2O4rJ00fKVZHl', createdOn='2021-01-10 09:51:02'}
        
## Bugs and Feedback
For bugs, questions, and discussions please use the [Github Issues](https://github.com/gregwhitaker/gradle-localstack-dynamodb-example/issues).
         
## License
MIT License

Copyright (c) 2021 Greg Whitaker

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.