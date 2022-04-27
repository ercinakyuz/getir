package com.getir.readingisgood.api.integration;

import com.getir.framework.data.entity.AbstractEntity;
import com.getir.framework.jwt.builder.JwtTokenBuilder;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import com.getir.readingisgood.api.event.handler.OrderCompletedEventHandler;
import com.getir.readingisgood.api.faker.AuthorizationFaker;
import com.getir.readingisgood.api.faker.OrderControllerFaker;
import com.getir.readingisgood.api.model.order.request.CompleteOrderRequest;
import com.getir.readingisgood.api.model.order.response.CompleteOrderResponse;
import com.getir.readingisgood.api.model.order.response.GetOrderListResponse;
import com.getir.readingisgood.api.model.order.response.GetOrderResponse;
import com.getir.readingisgood.application.contract.*;
import com.getir.readingisgood.application.usecase.order.completeorder.command.CompleteOrderItemCommand;
import com.getir.readingisgood.infrastructure.persistence.entity.CustomerEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.MerchantEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.book.BookEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.book.SaleEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.order.*;
import com.getir.readingisgood.infrastructure.persistence.repository.BookRepository;
import com.getir.readingisgood.infrastructure.persistence.repository.CustomerRepository;
import com.getir.readingisgood.infrastructure.persistence.repository.MerchantRepository;
import com.getir.readingisgood.infrastructure.persistence.repository.OrderRepository;
import com.getir.readingisgood.infrastructure.faker.BookInfrastructureFaker;
import com.getir.readingisgood.infrastructure.faker.CustomerInfrastructureFaker;
import com.getir.readingisgood.infrastructure.faker.MerchantInfrastructureFaker;
import com.getir.readingisgood.infrastructure.faker.OrderInfrastructureFaker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.getir.readingisgood.api.faker.AuthorizationFaker.AUTHORIZATION_HEADER_KEY;
import static com.getir.readingisgood.infrastructure.DateUtil.stringifyWithIsoDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderIntegrationTest extends AbstractIntegrationTest {

    private static OrderControllerFaker orderControllerFaker;

    private static OrderInfrastructureFaker orderInfrastructureFaker;

    private static MerchantInfrastructureFaker merchantInfrastructureFaker;

    private static CustomerInfrastructureFaker customerInfrastructureFaker;

    private static BookInfrastructureFaker bookInfrastructureFaker;

    private static AuthorizationFaker authorizationFaker;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;

    private static final String ORDER_URL = "/order";

    @BeforeAll
    public static void beforeAll() {
        orderControllerFaker = new OrderControllerFaker();
        authorizationFaker = new AuthorizationFaker();
        orderInfrastructureFaker = new OrderInfrastructureFaker();
        merchantInfrastructureFaker = new MerchantInfrastructureFaker();
        bookInfrastructureFaker = new BookInfrastructureFaker();
        customerInfrastructureFaker = new CustomerInfrastructureFaker();
    }

    @AfterEach
    public void afterEach() {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        bookRepository.deleteAll();
        merchantRepository.deleteAll();
    }

    @Test
    public void should_complete() throws Exception {
        // given
        final int merchantCount = faker.number().numberBetween(1, 3);
        final List<MerchantEntity> merchantEntities = merchantInfrastructureFaker.merchantEntities(merchantCount);
        merchantRepository.saveAll(merchantEntities);

        final List<BookEntity> bookEntities = bookInfrastructureFaker.bookEntities(merchantEntities);
        bookRepository.saveAll(bookEntities);
        final Map<UUID, BookEntity> bookEntityMap = bookEntities.stream().collect(Collectors.toMap(AbstractEntity::getId, Function.identity()));

        final CustomerEntity customerEntity = customerInfrastructureFaker.customerEntity();
        customerRepository.save(customerEntity);

        final CompleteOrderRequest request = orderControllerFaker.completeOrderRequest(bookEntities);
        final Map<UUID, CompleteOrderItemCommand> completeOrderItemCommandMap = request.getItems().stream().collect(Collectors.toMap(CompleteOrderItemCommand::getBookId, Function.identity()));

        final UUID customerId = customerEntity.getId();

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(customerId)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(post(String.format("%s/complete", ORDER_URL))
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER_KEY, authorizationFaker.authorizationToken(getirJwtTokens.getAccessToken()))
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isCreated());

        final CompleteOrderResponse response = objectMapperContext.mvcResultToResponse(CompleteOrderResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();

        final Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(response.getResult());
        assertThat(optionalOrderEntity.isPresent()).isTrue();

        final OrderEntity orderEntity = optionalOrderEntity.get();

        //Customer
        final CustomerOfOrderEntity customerOfOrderEntity = orderEntity.getCustomer();
        assertThat(customerOfOrderEntity).isNotNull();
        assertThat(customerOfOrderEntity.getId()).isEqualTo(customerId);
        assertThat(customerOfOrderEntity.getEmail()).isEqualTo(customerEntity.getCredentials().getEmail());

        //Items
        final List<OrderItemEntity> orderItemEntities = orderEntity.getItems();
        assertThat(orderItemEntities).isNotEmpty();
        assertThat(orderItemEntities).hasSize(completeOrderItemCommandMap.size());

        for (final var orderItemEntity : orderItemEntities) {
            assertThat(orderItemEntity).isNotNull();
            assertThat(orderItemEntity.getId()).isNotNull();

            //Book
            final OrderedBookEntity orderedBookEntity = orderItemEntity.getBook();
            assertThat(orderedBookEntity).isNotNull();
            final Optional<BookEntity> optionalBookEntity = bookRepository.findById(orderedBookEntity.getId());
            assertThat(optionalBookEntity).isPresent();
            final BookEntity bookEntity = optionalBookEntity.get();

            assertThat(orderedBookEntity.getAuthor()).isEqualTo(bookEntity.getAuthor());
            assertThat(orderedBookEntity.getName()).isEqualTo(bookEntity.getName());
            final SaleEntity saleOfBookEntity = bookEntity.getSale();
            assertThat(orderedBookEntity.getPrice()).isEqualByComparingTo(saleOfBookEntity.getPrice());

            //Merchant of ordered book
            final MerchantOfOrderedBookEntity merchantOfOrderedBookEntity = orderedBookEntity.getMerchant();
            assertThat(merchantOfOrderedBookEntity).isNotNull();
            final Optional<MerchantEntity> optionalMerchantEntity = merchantRepository.findById(merchantOfOrderedBookEntity.getId());
            assertThat(optionalMerchantEntity).isPresent();
            final MerchantEntity merchantEntity = optionalMerchantEntity.get();
            assertThat(merchantOfOrderedBookEntity.getEmail()).isEqualTo(merchantEntity.getCredentials().getEmail());

            //Requested items
            final CompleteOrderItemCommand completeOrderItemCommand = completeOrderItemCommandMap.get(orderedBookEntity.getId());
            assertThat(completeOrderItemCommand).isNotNull();
            assertThat(orderItemEntity.getQuantity()).isEqualTo(completeOrderItemCommand.getQuantity());

            //Stock decrease
            final BookEntity initialBookEntity = bookEntityMap.get(completeOrderItemCommand.getBookId());
            assertThat(saleOfBookEntity.getQuantity()).isEqualTo(initialBookEntity.getSale().getQuantity() - completeOrderItemCommand.getQuantity());

        }
    }

    @Test
    public void should_get() throws Exception {
        // given
        final UUID customerId = customerInfrastructureFaker.id();

        final List<OrderEntity> orderEntities = orderInfrastructureFaker.orderEntities(customerId, 3);
        orderRepository.saveAll(orderEntities);

        final OrderEntity orderEntity = faker.options().nextElement(orderEntities);
        final CustomerOfOrderEntity customerEntity = orderEntity.getCustomer();
        final Map<UUID, OrderItemEntity> orderItemEntityMap = orderEntity.getItems().stream().collect(Collectors.toMap(OrderItemEntity::getId, Function.identity()));

        final UUID orderId = orderEntity.getId();

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(customerId)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(String.format("%s/%s", ORDER_URL, orderId))
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER_KEY, authorizationFaker.authorizationToken(getirJwtTokens.getAccessToken()))
        );

        // then
        resultActions.andExpect(status().isOk());

        final GetOrderResponse response = objectMapperContext.mvcResultToResponse(GetOrderResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();

        final OrderContract orderContract = response.getResult();
        assertThat(orderContract).isNotNull();

        assertThat(orderContract).isNotNull();
        assertThat(orderContract.getId()).isEqualTo(orderEntity.getId());
        assertThat(orderContract.getPrice()).isNotNull();
        assertThat(orderContract.getQuantity()).isGreaterThan(0);

        final CustomerOfOrderContract customer = orderContract.getCustomer();
        assertThat(customer).isNotNull();

        assertThat(customer.getId()).isEqualTo(customerEntity.getId());
        assertThat(customer.getEmail()).isEqualTo(customerEntity.getEmail());

        final List<OrderItemContract> orderItemContracts = orderContract.getItems();
        assertThat(orderItemContracts).isNotEmpty();

        for (final var orderItemContract : orderItemContracts) {
            final OrderItemEntity orderItemEntity = orderItemEntityMap.get(orderItemContract.getId());
            assertThat(orderItemContract.getQuantity()).isEqualTo(orderItemEntity.getQuantity());
            assertThat(orderItemContract.getPrice()).isNotNull();

            final BookOfOrderItemContract bookContract = orderItemContract.getBook();

            assertThat(bookContract).isNotNull();
            final OrderedBookEntity bookEntity = orderItemEntity.getBook();
            assertThat(bookContract.getId()).isEqualTo(bookEntity.getId());
            assertThat(bookContract.getName()).isEqualTo(bookEntity.getName());
            assertThat(bookContract.getAuthor()).isEqualTo(bookEntity.getAuthor());
            assertThat(bookContract.getPrice()).isEqualByComparingTo(bookEntity.getPrice());

            final MerchantOfBookOrderItemContract merchantContract = bookContract.getMerchant();
            assertThat(merchantContract).isNotNull();
            final MerchantOfOrderedBookEntity merchantEntity = bookEntity.getMerchant();
            assertThat(merchantContract.getId()).isEqualTo(merchantEntity.getId());
            assertThat(merchantContract.getEmail()).isEqualTo(merchantEntity.getEmail());
        }
    }

    @Test
    public void should_list_with_pagination() throws Exception {
        // given
        final UUID customerId = customerInfrastructureFaker.id();
        final int orderCount = faker.number().numberBetween(5, 10);
        final int page = faker.number().numberBetween(0, 2);
        final int size = faker.number().numberBetween(1, 2);

        final List<OrderEntity> orderEntities = orderInfrastructureFaker.orderEntities(customerId, orderCount);
        orderRepository.saveAll(orderEntities);

        final Map<UUID, OrderEntity> orderEntityMap = orderEntities
                .stream().collect(Collectors.toMap(OrderEntity::getId, Function.identity()));

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(customerId)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(ORDER_URL)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER_KEY, authorizationFaker.authorizationToken(getirJwtTokens.getAccessToken()))
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
        );

        // then
        resultActions.andExpect(status().isOk());

        final GetOrderListResponse response = objectMapperContext.mvcResultToResponse(GetOrderListResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();

        final Page<OrderContract> pagedOrderContract = response.getResult();
        assertThat(pagedOrderContract).isNotNull();

        assertThat(pagedOrderContract.getTotalElements()).isEqualTo(orderCount);

        final List<OrderContract> orderContracts = pagedOrderContract.getContent();
        assertThat(orderContracts).isNotEmpty();
        for (final var orderContract : orderContracts) {
            final OrderEntity orderEntity = orderEntityMap.get(orderContract.getId());
            assertThat(orderContract.getPrice()).isNotNull();
            assertThat(orderContract.getQuantity()).isGreaterThan(0);

            final CustomerOfOrderContract customer = orderContract.getCustomer();
            assertThat(customer).isNotNull();

            final CustomerOfOrderEntity customerEntity = orderEntity.getCustomer();
            assertThat(customer.getId()).isEqualTo(customerEntity.getId());
            assertThat(customer.getEmail()).isEqualTo(customerEntity.getEmail());

            final List<OrderItemContract> orderItemContracts = orderContract.getItems();
            assertThat(orderItemContracts).isNotEmpty();
            final Map<UUID, OrderItemEntity> orderItemEntityMap = orderEntity.getItems()
                    .stream().collect(Collectors.toMap(OrderItemEntity::getId, Function.identity()));

            for (final var orderItemContract : orderItemContracts) {
                final OrderItemEntity orderItemEntity = orderItemEntityMap.get(orderItemContract.getId());
                assertThat(orderItemContract.getQuantity()).isEqualTo(orderItemEntity.getQuantity());
                assertThat(orderItemContract.getPrice()).isNotNull();

                final BookOfOrderItemContract bookContract = orderItemContract.getBook();

                assertThat(bookContract).isNotNull();
                final OrderedBookEntity bookEntity = orderItemEntity.getBook();
                assertThat(bookContract.getId()).isEqualTo(bookEntity.getId());
                assertThat(bookContract.getName()).isEqualTo(bookEntity.getName());
                assertThat(bookContract.getAuthor()).isEqualTo(bookEntity.getAuthor());
                assertThat(bookContract.getPrice()).isEqualByComparingTo(bookEntity.getPrice());

                final MerchantOfBookOrderItemContract merchantContract = bookContract.getMerchant();
                assertThat(merchantContract).isNotNull();
                final MerchantOfOrderedBookEntity merchantEntity = bookEntity.getMerchant();
                assertThat(merchantContract.getId()).isEqualTo(merchantEntity.getId());
                assertThat(merchantContract.getEmail()).isEqualTo(merchantEntity.getEmail());
            }
        }
    }

    @Test
    public void should_list_by_date_interval_with_pagination() throws Exception {
        // given
        final UUID customerId = customerInfrastructureFaker.id();
        final int orderCount = faker.number().numberBetween(5, 10);
        final int page = faker.number().numberBetween(0, 2);
        final int size = faker.number().numberBetween(1, 2);

        final List<OrderEntity> orderEntities = orderInfrastructureFaker.orderEntities(customerId, orderCount);

        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime oneMinuteBeforeNow = now.minusMinutes(1);
        final LocalDateTime oneMinuteAfterNow = now.plusMinutes(1);

        orderRepository.saveAll(orderEntities);

        final Map<UUID, OrderEntity> orderEntityMap = orderEntities
                .stream().collect(Collectors.toMap(OrderEntity::getId, Function.identity()));

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(customerId)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(String.format("%s/date-interval", ORDER_URL))
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER_KEY, authorizationFaker.authorizationToken(getirJwtTokens.getAccessToken()))
                .param("start", stringifyWithIsoDateTime(oneMinuteBeforeNow))
                .param("end", stringifyWithIsoDateTime(oneMinuteAfterNow))
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
        );

        // then
        resultActions.andExpect(status().isOk());

        final GetOrderListResponse response = objectMapperContext.mvcResultToResponse(GetOrderListResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();

        final Page<OrderContract> pagedOrderContract = response.getResult();
        assertThat(pagedOrderContract).isNotNull();

        assertThat(pagedOrderContract.getTotalElements()).isEqualTo(orderCount);

        final List<OrderContract> orderContracts = pagedOrderContract.getContent();
        assertThat(orderContracts).isNotEmpty();
        for (final var orderContract : orderContracts) {
            final OrderEntity orderEntity = orderEntityMap.get(orderContract.getId());
            assertThat(orderContract.getPrice()).isNotNull();
            assertThat(orderContract.getQuantity()).isGreaterThan(0);

            final CustomerOfOrderContract customer = orderContract.getCustomer();
            assertThat(customer).isNotNull();

            final CustomerOfOrderEntity customerEntity = orderEntity.getCustomer();
            assertThat(customer.getId()).isEqualTo(customerEntity.getId());
            assertThat(customer.getEmail()).isEqualTo(customerEntity.getEmail());

            final List<OrderItemContract> orderItemContracts = orderContract.getItems();
            assertThat(orderItemContracts).isNotEmpty();
            final Map<UUID, OrderItemEntity> orderItemEntityMap = orderEntity.getItems()
                    .stream().collect(Collectors.toMap(OrderItemEntity::getId, Function.identity()));

            for (final var orderItemContract : orderItemContracts) {
                final OrderItemEntity orderItemEntity = orderItemEntityMap.get(orderItemContract.getId());
                assertThat(orderItemContract.getQuantity()).isEqualTo(orderItemEntity.getQuantity());
                assertThat(orderItemContract.getPrice()).isNotNull();

                final BookOfOrderItemContract bookContract = orderItemContract.getBook();

                assertThat(bookContract).isNotNull();
                final OrderedBookEntity bookEntity = orderItemEntity.getBook();
                assertThat(bookContract.getId()).isEqualTo(bookEntity.getId());
                assertThat(bookContract.getName()).isEqualTo(bookEntity.getName());
                assertThat(bookContract.getAuthor()).isEqualTo(bookEntity.getAuthor());
                assertThat(bookContract.getPrice()).isEqualByComparingTo(bookEntity.getPrice());

                final MerchantOfBookOrderItemContract merchantContract = bookContract.getMerchant();
                assertThat(merchantContract).isNotNull();
                final MerchantOfOrderedBookEntity merchantEntity = bookEntity.getMerchant();
                assertThat(merchantContract.getId()).isEqualTo(merchantEntity.getId());
                assertThat(merchantContract.getEmail()).isEqualTo(merchantEntity.getEmail());
            }
        }
    }

}
