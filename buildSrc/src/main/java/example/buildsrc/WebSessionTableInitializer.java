package example.buildsrc;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Initializes the "example.websession" dynamo table with test data.
 */
public class WebSessionTableInitializer {

    private final DynamoDBMapper mapper;

    /**
     * Creates an instance of {@link WebSessionTableInitializer}.
     *
     * The LocalStack Gradle plugin requires a constructor that accepts the dynamodb client
     * as a single argument.
     *
     * @param dynamoClient
     */
    public WebSessionTableInitializer(AmazonDynamoDB dynamoClient) {
        this.mapper = new DynamoDBMapper(dynamoClient);
    }

    /**
     * Initializes the table.
     *
     * The LocalStack Gradle plugin requires a single void method named "run" that does not
     * accept any arguments.
     */
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        for (int i = 1; i <= 10; i++) {
            final WebSession webSession = new WebSession();
            webSession.setSessionId(UUID.randomUUID().toString());
            webSession.setUserId((long) i);
            webSession.setSessionData(RandomStringUtils.randomAlphanumeric(20));
            webSession.setCreatedOn(sdf.format(new Date()));

            mapper.save(webSession);
        }
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
    }
}
