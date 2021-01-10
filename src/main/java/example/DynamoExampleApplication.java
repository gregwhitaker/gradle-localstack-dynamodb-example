package example;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Simple example that inserts data into the mock AWS DynamoDB table and queries data using both
 * the primary key and secondary indexes.
 */
public class DynamoExampleApplication {
    private static final Logger LOG = LoggerFactory.getLogger(DynamoExampleApplication.class);

    public static void main(String... args) {
        // Get dynamo client for localstack dynamo
        final AmazonDynamoDB dynamoClient = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1"))
                .build();

        final DynamoDBMapper mapper = new DynamoDBMapper(dynamoClient);

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        // Insert data into dynamo table
        final String sessionId = UUID.randomUUID().toString();

        WebSession webSession = new WebSession();
        webSession.setSessionId(sessionId);
        webSession.setUserId(10L);
        webSession.setSessionData("test data");
        webSession.setCreatedOn(sdf.format(new Date()));

        LOG.info("Adding new websession: {}", sessionId);
        mapper.save(webSession);

        // Get data from dynamo table by hash key
        LOG.info("Retrieving websession: {}", sessionId);
        WebSession keyObj = new WebSession();
        keyObj.setSessionId(sessionId);

        WebSession retrievedWebSession = mapper.load(keyObj);
        LOG.info("Found websession: {}", retrievedWebSession);

        // Get data from dynamo table by GSI
        LOG.info("Retrieve all websessions for userId: 10");

        WebSession keyObj1 = new WebSession();
        keyObj1.setUserId(10L);

        DynamoDBQueryExpression<WebSession> query = new DynamoDBQueryExpression<WebSession>()
                .withIndexName("user-session-idx")
                .withConsistentRead(false)
                .withHashKeyValues(keyObj1);

        final PaginatedQueryList<WebSession> results = mapper.query(WebSession.class, query);
        results.forEach(ws -> {
            LOG.info("Found websession: {}", ws);
        });
    }


    /**
     * Web session record to load into dynamo table.
     */
    @DynamoDBTable(tableName = "example.websession")
    public static class WebSession {

        @DynamoDBHashKey
        @DynamoDBIndexRangeKey(globalSecondaryIndexName = "user-session-idx")
        private String sessionId;

        @DynamoDBIndexHashKey(globalSecondaryIndexName = "user-session-idx")
        private Long userId;

        private String sessionData;

        private String createdOn;

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getSessionData() {
            return sessionData;
        }

        public void setSessionData(String sessionData) {
            this.sessionData = sessionData;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

        @Override
        public String toString() {
            return "WebSession{" +
                    "sessionId='" + sessionId + '\'' +
                    ", userId=" + userId +
                    ", sessionData='" + sessionData + '\'' +
                    ", createdOn='" + createdOn + '\'' +
                    '}';
        }
    }
}
