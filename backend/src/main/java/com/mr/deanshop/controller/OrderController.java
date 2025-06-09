package com.mr.deanshop.controller;

import com.mr.deanshop.dto.OrderDetails;
import com.mr.deanshop.dto.OrderRequest;
import com.mr.deanshop.dto.OrderResponse;;
import com.mr.deanshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, Principal principal) throws Exception {
        OrderResponse orderResponse = orderService.createOrder(orderRequest,principal);

        return new ResponseEntity<>(orderResponse,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDetails>> getOrderByUser(Principal principal){
        List<OrderDetails> orderDetailList = orderService.getOrderByUser(principal.getName());
        return new ResponseEntity<>(orderDetailList,HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable UUID id, Principal principal)  {
        orderService.cancelOrder(id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update-payment")
    public ResponseEntity<?> updatePaymentStatus(@RequestBody Map<String, String> request)  {
        Map<String, String> response = orderService.updateStatus(request.get("paymentIntent"));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
