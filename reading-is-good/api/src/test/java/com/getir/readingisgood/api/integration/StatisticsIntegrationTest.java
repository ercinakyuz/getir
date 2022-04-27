package com.getir.readingisgood.api.integration;

import com.getir.framework.jwt.builder.JwtTokenBuilder;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import com.getir.readingisgood.api.faker.AuthorizationFaker;
import com.getir.readingisgood.api.model.order.response.GetOrderStatisticsResponse;
import com.getir.readingisgood.application.contract.OrderStatisticsContract;
import com.getir.readingisgood.infrastructure.persistence.entity.OrderStatisticsEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.OrderStatisticsRepository;
import com.getir.readingisgood.infrastructure.faker.CustomerInfrastructureFaker;
import com.getir.readingisgood.infrastructure.faker.StatisticsInfrastructureFaker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.getir.readingisgood.api.faker.AuthorizationFaker.AUTHORIZATION_HEADER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatisticsIntegrationTest extends AbstractIntegrationTest {

    private static CustomerInfrastructureFaker customerInfrastructureFaker;

    private static StatisticsInfrastructureFaker statisticsInfrastructureFaker;

    private static AuthorizationFaker authorizationFaker;

    @Autowired
    private OrderStatisticsRepository orderStatisticsRepository;

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;

    private static final String STATISTICS_URL = "/statistics";

    @BeforeAll
    public static void beforeAll() {
        statisticsInfrastructureFaker = new StatisticsInfrastructureFaker();
        customerInfrastructureFaker = new CustomerInfrastructureFaker();
        authorizationFaker = new AuthorizationFaker();
    }

    @AfterEach
    public void afterEach() {
        orderStatisticsRepository.deleteAll();
    }

    @Test
    public void should_list_order_statistics_belong_to_customer() throws Exception {
        // given
        final UUID customerId = customerInfrastructureFaker.id();
        final int year = statisticsInfrastructureFaker.year();

        final List<OrderStatisticsEntity> orderStatisticsEntities = statisticsInfrastructureFaker.orderStatisticsEntities(customerId, year);
        orderStatisticsRepository.saveAll(orderStatisticsEntities);
        final Map<Month, OrderStatisticsEntity> orderStatisticsEntityMap = orderStatisticsEntities.stream().collect(Collectors.toMap(OrderStatisticsEntity::getMonth, Function.identity()));

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(customerId)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(String.format("%s/order", STATISTICS_URL))
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER_KEY, authorizationFaker.authorizationToken(getirJwtTokens.getAccessToken()))
                .param("year", String.valueOf(year))
        );

        // then
        resultActions.andExpect(status().isOk());

        final GetOrderStatisticsResponse response = objectMapperContext.mvcResultToResponse(GetOrderStatisticsResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();

        final List<OrderStatisticsContract> orderStatisticsContractList = response.getResult();
        assertThat(orderStatisticsContractList).hasSameSizeAs(orderStatisticsEntities);

        orderStatisticsContractList.forEach(orderStatisticsContract -> {
            final OrderStatisticsEntity orderStatisticsEntity = orderStatisticsEntityMap.get(orderStatisticsContract.getMonth());
            assertThat(orderStatisticsContract.getTotalOrderCount()).isEqualTo(orderStatisticsEntity.getTotalOrderCount());
            assertThat(orderStatisticsContract.getTotalBookCount()).isEqualTo(orderStatisticsEntity.getTotalBookCount());
            assertThat(orderStatisticsContract.getTotalOrderAmount()).isEqualTo(orderStatisticsEntity.getTotalOrderAmount());
        });
    }
}