package com.springbootinaction.taco_cloud;

import com.springbootinaction.taco_cloud.data.JdbcOrderRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(OrderController.class);

    private JdbcOrderRepository jdbcOrderRepository;

    public OrderController(JdbcOrderRepository jdbcOrderRepository) {
        this.jdbcOrderRepository = jdbcOrderRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        jdbcOrderRepository.save(order);
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete(); // clearing the session
        return "redirect:/";
    }
}
