package com.getir.readingisgood.api.integration;

import com.getir.framework.jwt.builder.JwtTokenBuilder;
import com.getir.framework.jwt.model.GetirJwtClaims;
import com.getir.framework.jwt.model.GetirJwtTokens;
import com.getir.readingisgood.api.faker.AuthorizationFaker;
import com.getir.readingisgood.api.faker.BookControllerFaker;
import com.getir.readingisgood.api.model.book.request.ChangeBookQuantityRequest;
import com.getir.readingisgood.api.model.book.request.CreateBookRequest;
import com.getir.readingisgood.api.model.book.response.CreateBookResponse;
import com.getir.readingisgood.infrastructure.persistence.entity.MerchantEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.book.BookEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.book.MerchantOfBookEntity;
import com.getir.readingisgood.infrastructure.persistence.entity.book.SaleEntity;
import com.getir.readingisgood.infrastructure.persistence.repository.BookRepository;
import com.getir.readingisgood.infrastructure.persistence.repository.MerchantRepository;
import com.getir.readingisgood.infrastructure.faker.BookInfrastructureFaker;
import com.getir.readingisgood.infrastructure.faker.MerchantInfrastructureFaker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;
import java.util.UUID;

import static com.getir.readingisgood.api.faker.AuthorizationFaker.AUTHORIZATION_HEADER_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookIntegrationTest extends AbstractIntegrationTest {

    private static BookControllerFaker bookControllerFaker;

    private static BookInfrastructureFaker bookInfrastructureFaker;

    private static MerchantInfrastructureFaker merchantInfrastructureFaker;

    private static AuthorizationFaker authorizationFaker;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;

    private static final String BOOK_URL = "/book";

    @BeforeAll
    public static void beforeAll() {
        bookControllerFaker = new BookControllerFaker();
        bookInfrastructureFaker = new BookInfrastructureFaker();
        authorizationFaker = new AuthorizationFaker();
        merchantInfrastructureFaker = new MerchantInfrastructureFaker();
    }

    @AfterEach
    public void afterEach() {
        bookRepository.deleteAll();
        merchantRepository.deleteAll();
    }

    @Test
    public void should_create() throws Exception {
        // given
        final CreateBookRequest request = bookControllerFaker.createBookRequest();

        final MerchantEntity merchantEntity = merchantInfrastructureFaker.merchantEntity();
        merchantRepository.save(merchantEntity);

        final UUID merchantId = merchantEntity.getId();

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(merchantId)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(post(BOOK_URL)
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER_KEY, authorizationFaker.authorizationToken(getirJwtTokens.getAccessToken()))
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isCreated());

        final CreateBookResponse response = objectMapperContext.mvcResultToResponse(CreateBookResponse.class, resultActions.andReturn());
        assertThat(response).isNotNull();


        final Optional<BookEntity> optionalBookEntity = bookRepository.findById(response.getResult());
        assertThat(optionalBookEntity.isPresent()).isTrue();

        final BookEntity bookEntity = optionalBookEntity.get();
        assertThat(bookEntity.getAuthor()).isEqualTo(request.getAuthor());
        assertThat(bookEntity.getName()).isEqualTo(request.getName());

        //Sale
        final SaleEntity sale = bookEntity.getSale();
        assertThat(sale).isNotNull();
        assertThat(sale.getPrice()).isEqualByComparingTo(request.getPrice());
        assertThat(sale.getQuantity()).isEqualTo(request.getQuantity());

        //Merchant
        final MerchantOfBookEntity merchant = bookEntity.getMerchant();
        assertThat(merchant).isNotNull();
        assertThat(merchant.getId()).isEqualTo(merchantId);
    }

    @Test
    public void should_increase_quantity() throws Exception {
        // given
        final MerchantEntity merchantEntity = merchantInfrastructureFaker.merchantEntity();
        merchantRepository.save(merchantEntity);

        final UUID merchantId = merchantEntity.getId();

        final BookEntity bookEntity = bookInfrastructureFaker.bookEntity(merchantId);
        bookRepository.save(bookEntity);

        final SaleEntity saleEntity = bookEntity.getSale();

        final ChangeBookQuantityRequest request = bookControllerFaker.increaseBookQuantityRequest();

        final UUID bookId = bookEntity.getId();

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(merchantId)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(patch(String.format("%s/%s/quantity/increase", BOOK_URL, bookId))
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER_KEY, authorizationFaker.authorizationToken(getirJwtTokens.getAccessToken()))
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isOk());

        final Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        assertThat(optionalBookEntity.isPresent()).isTrue();

        final BookEntity quantityChangedBookEntity = optionalBookEntity.get();
        assertThat(quantityChangedBookEntity.getAuthor()).isEqualTo(bookEntity.getAuthor());
        assertThat(quantityChangedBookEntity.getName()).isEqualTo(bookEntity.getName());

        //Merchant
        final MerchantOfBookEntity quantityChangedMerchantEntity = quantityChangedBookEntity.getMerchant();
        assertThat(quantityChangedMerchantEntity).isNotNull();
        assertThat(quantityChangedMerchantEntity.getId()).isEqualTo(merchantId);

        //Sale
        final SaleEntity quantityChangedSaleEntity = quantityChangedBookEntity.getSale();
        assertThat(quantityChangedSaleEntity).isNotNull();
        assertThat(quantityChangedSaleEntity.getPrice()).isEqualByComparingTo(saleEntity.getPrice());
        assertThat(quantityChangedSaleEntity.getQuantity()).isEqualTo(saleEntity.getQuantity() + request.getQuantity());
    }

    @Test
    public void should_decrease_quantity() throws Exception {
        // given
        final MerchantEntity merchantEntity = merchantInfrastructureFaker.merchantEntity();
        merchantRepository.save(merchantEntity);

        final UUID merchantId = merchantEntity.getId();

        final BookEntity bookEntity = bookInfrastructureFaker.bookEntity(merchantId);
        bookRepository.save(bookEntity);

        final SaleEntity saleEntity = bookEntity.getSale();

        final ChangeBookQuantityRequest request = bookControllerFaker.decreaseBookQuantityRequest(saleEntity.getQuantity());

        final UUID bookId = bookEntity.getId();

        final GetirJwtTokens getirJwtTokens = jwtTokenBuilder.build(GetirJwtClaims.builder()
                .memberId(merchantId)
                .build());

        // when
        final ResultActions resultActions = mockMvc.perform(patch(String.format("%s/%s/quantity/decrease", BOOK_URL, bookId))
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION_HEADER_KEY, authorizationFaker.authorizationToken(getirJwtTokens.getAccessToken()))
                .content(objectMapperContext.objectToByteArray(request))
        );

        // then
        resultActions.andExpect(status().isOk());

        final Optional<BookEntity> optionalBookEntity = bookRepository.findById(bookId);
        assertThat(optionalBookEntity.isPresent()).isTrue();

        final BookEntity quantityChangedBookEntity = optionalBookEntity.get();
        assertThat(quantityChangedBookEntity.getAuthor()).isEqualTo(bookEntity.getAuthor());
        assertThat(quantityChangedBookEntity.getName()).isEqualTo(bookEntity.getName());

        //Merchant
        final MerchantOfBookEntity quantityChangedMerchantEntity = quantityChangedBookEntity.getMerchant();
        assertThat(quantityChangedMerchantEntity).isNotNull();
        assertThat(quantityChangedMerchantEntity.getId()).isEqualTo(merchantId);

        //Sale
        final SaleEntity quantityChangedSaleEntity = quantityChangedBookEntity.getSale();
        assertThat(quantityChangedSaleEntity).isNotNull();
        assertThat(quantityChangedSaleEntity.getPrice()).isEqualByComparingTo(saleEntity.getPrice());
        assertThat(quantityChangedSaleEntity.getQuantity()).isEqualTo(saleEntity.getQuantity() - request.getQuantity());
    }
}