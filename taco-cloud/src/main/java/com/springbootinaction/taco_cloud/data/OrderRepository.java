package com.springbootinaction.taco_cloud.data;

import com.springbootinaction.taco_cloud.TacoOrder;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByDeliveryZip(String deliveryZip);
    List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
    List<TacoOrder> findByDeliveryCityOrderByDeliveryTo(String city);
}
