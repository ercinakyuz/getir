package com.getir.readingisgood.api.integration;

import com.getir.ReadingIsGoodApi;
import com.getir.framework.test.AbstractTest;
import com.getir.readingisgood.api.event.OrderCompletedEvent;
import com.getir.readingisgood.api.event.handler.OrderCompletedEventHandler;
import com.getir.readingisgood.infrastructure.faker.OrderInfrastructureFaker;
import com.getir.readingisgood.infrastructure.faker.StatisticsInfrastructureFaker;
import com.getir.readingisgood.infrastructure.persistence.entity.OrderStatisticsEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.order.OrderEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.OrderRepository;
import com.getir.readingisgood.infrastructure.persistence.repository.OrderStatisticsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.getir.readingisgood.infrastructure.DateUtil.toSystemLocalDateTime;
import static com.getir.readingisgood.infrastructure.bus.RabbitQueueProperties.EXCHANGE_READING_IS_GOOD;
import static com.getir.readingisgood.infrastructure.bus.RabbitQueueProperties.RK_ORDER_COMPLETED;
import static com.getir.readingisgood.infrastructure.util.AsyncTestUtil.countDown;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ReadingIsGoodApi.class)
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
public class OrderCompletedEventHandlerIntegrationTest extends AbstractTest {

    private static long COUNT_DOWN_LATCH_TIMEOUT = 15;

    private static OrderInfrastructureFaker orderInfrastructureFaker;

    private static StatisticsInfrastructureFaker statisticsInfrastructureFaker;

    private CountDownLatch countDownLatch;

    @SpyBean
    private OrderCompletedEventHandler orderCompletedEventHandler;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatisticsRepository orderStatisticsRepository;

    @BeforeEach
    public void beforeEach() {
        countDownLatch = new CountDownLatch(1);
    }

    @BeforeAll
    public static void beforeAll() {
        orderInfrastructureFaker = new OrderInfrastructureFaker();
        statisticsInfrastructureFaker = new StatisticsInfrastructureFaker();
    }

    @AfterEach
    public void afterEach() {
        orderRepository.deleteAll();
        orderStatisticsRepository.deleteAll();
    }

    @Test
    public void should_handle_with_creating_statistics() throws Exception {
        //given
        final OrderEntity orderEntity = orderInfrastructureFaker.orderEntity();
        orderRepository.save(orderEntity);

        final LocalDateTime orderCreatedAt = toSystemLocalDateTime(orderEntity.getCreatedAt());

        final OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.builder()
                .id(orderEntity.getId())
                .build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        //when
        countDown(countDownLatch).when(orderCompletedEventHandler).handle(any(OrderCompletedEvent.class));
        rabbitTemplate.convertAndSend(EXCHANGE_READING_IS_GOOD, RK_ORDER_COMPLETED, orderCompletedEvent);
        countDownLatch.await(COUNT_DOWN_LATCH_TIMEOUT, TimeUnit.SECONDS);

        //then
        assertThat(countDownLatch.getCount()).isEqualTo(0L);

        final Optional<OrderStatisticsEntity> optionalOrderStatisticsEntity = orderStatisticsRepository.findAll().stream().findFirst();
        assertThat(optionalOrderStatisticsEntity).isPresent();

        final OrderStatisticsEntity orderStatisticsEntity = optionalOrderStatisticsEntity.get();
        assertThat(orderStatisticsEntity.getId()).isNotNull();
        assertThat(orderStatisticsEntity.getYear()).isEqualTo(orderCreatedAt.getYear());
        assertThat(orderStatisticsEntity.getMonth()).isEqualTo(orderCreatedAt.getMonth());
        assertThat(orderStatisticsEntity.getTotalOrderAmount()).isGreaterThan(ZERO);
        assertThat(orderStatisticsEntity.getTotalOrderCount()).isGreaterThan(0);
        assertThat(orderStatisticsEntity.getTotalBookCount()).isGreaterThan(0);
    }

    @Test
    public void should_handle_with_updating_statistics() throws Exception {
        //given
        final OrderEntity orderEntity = orderInfrastructureFaker.orderEntity();
        orderRepository.save(orderEntity);

        final UUID customerId = orderEntity.getCustomer().getId();
        final LocalDateTime orderCreatedAt = toSystemLocalDateTime(orderEntity.getCreatedAt());

        final OrderStatisticsEntity orderStatisticsEntity = statisticsInfrastructureFaker.orderStatisticsEntity(customerId, orderCreatedAt.getYear(), orderCreatedAt.getMonth());
        orderStatisticsRepository.save(orderStatisticsEntity);

        final OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.builder()
                .id(orderEntity.getId())
                .build();

        //when
        countDown(countDownLatch).when(orderCompletedEventHandler).handle(any(OrderCompletedEvent.class));
        rabbitTemplate.convertAndSend(EXCHANGE_READING_IS_GOOD, RK_ORDER_COMPLETED, orderCompletedEvent);
        countDownLatch.await(15, TimeUnit.SECONDS);

        //then
        assertThat(countDownLatch.getCount()).isEqualTo(0L);

        final Optional<OrderStatisticsEntity> optionalOrderStatisticsEntity = orderStatisticsRepository.findById(orderStatisticsEntity.getId());
        assertThat(optionalOrderStatisticsEntity).isPresent();

        final OrderStatisticsEntity updatedOrderStatisticsEntity = optionalOrderStatisticsEntity.get();
        assertThat(updatedOrderStatisticsEntity.getYear()).isEqualTo(orderCreatedAt.getYear());
        assertThat(updatedOrderStatisticsEntity.getMonth()).isEqualTo(orderCreatedAt.getMonth());
        assertThat(updatedOrderStatisticsEntity.getTotalOrderAmount()).isGreaterThan(orderStatisticsEntity.getTotalOrderAmount());
        assertThat(updatedOrderStatisticsEntity.getTotalOrderCount()).isGreaterThan(orderStatisticsEntity.getTotalOrderCount());
        assertThat(updatedOrderStatisticsEntity.getTotalBookCount()).isGreaterThan(orderStatisticsEntity.getTotalBookCount());
    }
}
