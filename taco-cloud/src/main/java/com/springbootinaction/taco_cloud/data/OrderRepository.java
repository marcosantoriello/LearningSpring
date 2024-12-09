package com.springbootinaction.taco_cloud.data;

import com.springbootinaction.taco_cloud.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
