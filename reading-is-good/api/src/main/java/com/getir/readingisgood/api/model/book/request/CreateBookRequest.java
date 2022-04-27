package com.getir.readingisgood.api.model.book.request;

import com.getir.readingisgood.api.model.book.request.error.BookRequestError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {

    @NotBlank(message = BookRequestError.INVALID_NAME)
    private String name;

    @NotBlank(message = BookRequestError.INVALID_AUTHOR)
    private String author;

    @Positive(message = BookRequestError.INVALID_PRICE)
    private BigDecimal price;

    @PositiveOrZero(message = BookRequestError.INVALID_QUANTITY)
    private int quantity;
}
