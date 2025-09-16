package co.com.crediya.dynamodb.helper;

import co.com.crediya.dynamodb.report.ReportEntity;
import org.mockito.Mock;
import org.reactivecommons.utils.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<ReportEntity> customerTable;

    private ReportEntity reportEntity;
/*
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(dynamoDbEnhancedAsyncClient.table("table_name", TableSchema.fromBean(ApprovedRequestEntity.class)))
                .thenReturn(customerTable);

        reportEntity = new ApprovedRequestEntity();
        reportEntity.setId("id");
        reportEntity.setAtr1("atr1");
    }

    @Test
    void modelEntityPropertiesMustNotBeNull() {
        ApprovedRequestEntity reportEntityUnderTest = new ApprovedRequestEntity("id", "atr1");

        assertNotNull(reportEntityUnderTest.getId());
        assertNotNull(reportEntityUnderTest.getAtr1());
    }

    @Test
    void testSave() {
        when(customerTable.putItem(reportEntity)).thenReturn(CompletableFuture.runAsync(()->{}));
        when(mapper.map(reportEntity, ApprovedRequestEntity.class)).thenReturn(reportEntity);

        DynamoDBApprovedRequestAdapter dynamoDBReportAdapter =
                new DynamoDBApprovedRequestAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBReportAdapter.save(reportEntity))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testGetById() {
        String id = "id";

        when(customerTable.getItem(
                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
                .thenReturn(CompletableFuture.completedFuture(reportEntity));
        when(mapper.map(reportEntity, Object.class)).thenReturn("value");

        DynamoDBApprovedRequestAdapter dynamoDBReportAdapter =
                new DynamoDBApprovedRequestAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBReportAdapter.getById("id"))
                .expectNext("value")
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(mapper.map(reportEntity, ApprovedRequestEntity.class)).thenReturn(reportEntity);
        when(mapper.map(reportEntity, Object.class)).thenReturn("value");

        when(customerTable.deleteItem(reportEntity))
                .thenReturn(CompletableFuture.completedFuture(reportEntity));

        DynamoDBApprovedRequestAdapter dynamoDBReportAdapter =
                new DynamoDBApprovedRequestAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBReportAdapter.delete(reportEntity))
                .expectNext("value")
                .verifyComplete();
    }*/
}