package com.mr.deanshop.service;

import com.mr.deanshop.auth.entity.User;
import com.mr.deanshop.dto.OrderItemRequest;
import com.mr.deanshop.dto.OrderRequest;
import com.mr.deanshop.dto.OrderResponse;
import com.mr.deanshop.entity.*;
import com.mr.deanshop.repository.OrderRepository;
import com.stripe.model.PaymentIntent;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentIntentService paymentIntentService;

    public OrderResponse createOrder(OrderRequest orderRequest, Principal principal) throws Exception {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Address address = user.getAddressList().stream().filter(address1 -> orderRequest.getAddressId().equals(address1.getId())).findFirst().orElseThrow(()-> new BadRequestException("Address not found"));

        Order order = Order.builder()
                .user(user)
                .orderDate(orderRequest.getOrderDate())
                .address(address)
                .discount(orderRequest.getDiscount())
                .expectedDeliveryDate(orderRequest.getExpectedDeliveryDate())
                .paymentMethod(orderRequest.getPaymentMethod())
                .orderStatus(OrderStatus.PENDING)
                .totalAmount(orderRequest.getTotalAmount())
                .build();

        List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream().map(orderItemRequest -> {
            Product product = productService.fetchProductById(orderItemRequest.getProductId());
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(orderItemRequest.getQuantity())
                    .productVariantId(orderItemRequest.getProductVariantId())
                    .build();
            return orderItem;
        }).toList();

        order.setOrderItemList(orderItems);
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentDate(new Date());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentMethod(order.getPaymentMethod());
        payment.setAmount(order.getTotalAmount());
        order.setPayment(payment);
        Order savedOrder = orderRepository.save(order);

        OrderResponse orderResponse = OrderResponse.builder()
                .orderId(savedOrder.getId())
                .paymentMethod(orderRequest.getPaymentMethod())
                .build();
        if(Objects.equals(orderRequest.getPaymentMethod(), "CARD")){
            orderResponse.setCredentials(paymentIntentService.createPaymentIntent(order));
        }
        return orderResponse;

    }

    public Map<String,String> updateStatus(String paymentIntentId) {

        try{
            PaymentIntent paymentIntent= PaymentIntent.retrieve(paymentIntentId);
            if (paymentIntent != null && paymentIntent.getStatus().equals("succeeded")) {
                String orderId = paymentIntent.getMetadata().get("orderId") ;
                Order order= orderRepository.findById(UUID.fromString(orderId)).orElseThrow(BadRequestException::new);
                Payment payment = order.getPayment();
                payment.setPaymentStatus(PaymentStatus.COMPLETED);
                payment.setPaymentMethod(paymentIntent.getPaymentMethod());
                order.setPaymentMethod(paymentIntent.getPaymentMethod());
                order.setPayment(payment);
                Order savedOrder = orderRepository.save(order);
                Map<String,String> map = new HashMap<>();
                map.put("orderId", String.valueOf(savedOrder.getId()));
                return map;
            }
            else{
                throw new IllegalArgumentException("PaymentIntent not found or missing metadata");
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException("PaymentIntent not found or missing metadata");
        }
    }
}
