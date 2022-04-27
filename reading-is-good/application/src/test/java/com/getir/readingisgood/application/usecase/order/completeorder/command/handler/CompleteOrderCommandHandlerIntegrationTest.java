package com.getir.readingisgood.application.usecase.order.completeorder.command.handler;


import com.getir.framework.test.AbstractTest;
import com.getir.readingisgood.application.usecase.order.completeorder.command.CompleteOrderCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompleteOrderCommandHandler.class)
@ActiveProfiles("fast")
public class CompleteOrderCommandHandlerIntegrationTest extends AbstractTest {

    @Autowired
    private CompleteOrderCommandHandler completeOrderCommandHandler;

    @Test
    public void should_handle(){
        //given
        final CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder().build();

        //when
        completeOrderCommandHandler.handle(completeOrderCommand);

        //then

    }

}
