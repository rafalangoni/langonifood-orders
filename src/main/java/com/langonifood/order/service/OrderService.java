package com.langonifood.order.service;

import com.langonifood.order.dto.OrderDto;
import com.langonifood.order.dto.StatusDto;
import com.langonifood.order.model.Order;
import com.langonifood.order.model.Status;
import com.langonifood.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private final ModelMapper modelMapper;

    public List<OrderDto> getAll(){
        return repository.findAll().stream()
                .map(o -> modelMapper.map(o, OrderDto.class))
                .collect(Collectors.toList());
    }

    public OrderDto getById(Long id){
        Order order = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(order, OrderDto.class);
    }

    public OrderDto createOrder(OrderDto dto){
        Order order = modelMapper.map(dto, Order.class);

        order.setDateTime(LocalDateTime.now());
        order.setStatus(Status.ORDERED);
        order.getItems().forEach(item -> item.setOrder(order));
        Order saved = repository.save(order);

        return modelMapper.map(order, OrderDto.class);
    }

    public OrderDto updateStatus(Long id, StatusDto dto){
        Order order = repository.byIdWithItems(id);

        if(order == null){
            throw new EntityNotFoundException();
        }

        order.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), order);
        return modelMapper.map(order, OrderDto.class);
    }

    public void approveOrderPayment(Long id){
        Order order = repository.byIdWithItems(id);

        if(order == null){
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAID);
        repository.updateStatus(Status.PAID, order);
    }
}
