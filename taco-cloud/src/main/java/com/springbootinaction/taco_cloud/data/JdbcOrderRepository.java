package com.springbootinaction.taco_cloud.data;

import com.springbootinaction.taco_cloud.TacoOrder;
import org.springframework.jdbc.core.JdbcOperations;

public class JdbcOrderRepository implements OrderRepository{

    private JdbcOperations jdbcOperations;


    @Override
    public TacoOrder save(TacoOrder order) {
        return null;
    }
}
