package com.getir.readingisgood.infrastructure.bus;

public final class RabbitQueueProperties {
    private RabbitQueueProperties(){}

    public static final String ARG_KEY_DL_EXCHANGE = "x-dead-letter-exchange";
    public static final String ARG_KEY_DL_ROUTING_KEY = "x-dead-letter-routing-key";

    public static final String DL_EXCHANGE_READING_IS_GOOD = "rig-dl";
    public static final String EXCHANGE_READING_IS_GOOD = "rig";

    //Order Completed
    public static final String RK_ORDER_COMPLETED = "order-completed";
    public static final String DL_RK_ORDER_COMPLETED = "order-completed.dl";
    public static final String QUEUE_ORDER_COMPLETED = "rig-order-completed";
    public static final String DL_QUEUE_ORDER_COMPLETED = "rig-order-completed.dl";

}
