package com.langonifood.order.controller;

import com.langonifood.order.dto.OrderDto;
import com.langonifood.order.dto.StatusDto;
import com.langonifood.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    public List<OrderDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable @NotNull Long id){
        OrderDto dto = service.getById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder){
        OrderDto realizedOrder = service.createOrder(dto);

        URI address = uriBuilder.path("/orders/{id}").buildAndExpand(realizedOrder.getId()).toUri();

        return ResponseEntity.created(address).body(realizedOrder);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestBody StatusDto status){
        OrderDto dto = service.updateStatus(id, status);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/paid")
    public ResponseEntity<Void> approvePayment(@PathVariable @NotNull Long id){
        service.approveOrderPayment(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/port")
    public String returnPort(@Value("${local.server.port}") String port){
        return String.format("Request answered by instance running on port %s ", port);
    }


}
