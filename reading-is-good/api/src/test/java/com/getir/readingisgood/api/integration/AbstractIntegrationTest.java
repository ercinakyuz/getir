package com.getir.readingisgood.api.integration;


import com.getir.ReadingIsGoodApi;
import com.getir.framework.test.AbstractTest;
import com.getir.readingisgood.api.event.handler.OrderCompletedEventHandler;
import com.getir.readingisgood.infrastructure.util.ObjectMapperContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ReadingIsGoodApi.class)
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
public class AbstractIntegrationTest extends AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private OrderCompletedEventHandler orderCompletedEventHandler;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    protected static ObjectMapperContext objectMapperContext;


    @BeforeAll
    public static void beforeAllIntegration() {
        objectMapperContext = new ObjectMapperContext();
    }

}
