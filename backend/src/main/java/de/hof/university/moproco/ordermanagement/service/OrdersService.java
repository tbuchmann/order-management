package de.hof.university.moproco.ordermanagement.service;

import de.hof.university.moproco.ordermanagement.dto.OrderEntityDto;
import de.hof.university.moproco.ordermanagement.dto.OrderLineEntityDto;
import de.hof.university.moproco.ordermanagement.entity.OrderEntity;
import de.hof.university.moproco.ordermanagement.entity.OrderLineEntity;
import de.hof.university.moproco.ordermanagement.repository.OrderEntityRepository;
import de.hof.university.moproco.ordermanagement.repository.OrderLineEntityRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    private final OrderEntityRepository orderEntityRepository;
    private final OrderLineEntityRepository orderLineEntityRepository;

    public OrdersService(OrderEntityRepository orderEntityRepository,
                         OrderLineEntityRepository orderLineEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
        this.orderLineEntityRepository = orderLineEntityRepository;
    }

    public org.springframework.data.domain.Page<OrderEntityDto> listOrders(String orderStatus, Pageable pageable, String sort) {
        Specification<OrderEntity> spec = Specification.where((Specification<OrderEntity>) null);
        if (orderStatus != null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(root.get("orderStatus"), orderStatus));
        }
        Sort sortObj = Sort.by(sort);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortObj);

                return orderEntityRepository.findAll(spec, sortedPageable).map(OrderEntityDto::from);
    }
    public OrderEntityDto getOrderDetails(Long id) {
        var entity = orderEntityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        return OrderEntityDto.from(entity);
    }
    public OrderEntityDto createOrder(OrderEntityDto request) {
        var entity = request.toEntity();
        entity.setOrderId(null);
        var saved = orderEntityRepository.save(entity);
        return OrderEntityDto.from(saved);
    }
    public OrderEntityDto updateOrder(OrderEntityDto request) {
        var entity = orderEntityRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + request.id()));
        entity.setOrderDate(request.orderDate());
        entity.setDesiredShippingDate(request.desiredShippingDate());
        entity.setOrderStatus(request.orderStatus());
        entity.setOrderValue(request.orderValue());
        var saved = orderEntityRepository.save(entity);
        return OrderEntityDto.from(saved);
    }
    public List<OrderLineEntityDto> listLineItems(Long orderId) {
        var order = orderEntityRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        var items = order.getLineItemsList();
        if (items == null) {
            return List.of();
        }
        return items.stream().map(OrderLineEntityDto::from).toList();
    }
    public OrderLineEntityDto addLineItem(OrderLineEntityDto request) {
        var entity = request.toEntity();
        entity.setLineNumber(null);
        var saved = orderLineEntityRepository.save(entity);
        return OrderLineEntityDto.from(saved);
    }
    public void deleteLineItem(Long orderId, Long itemId) {
        var item = orderLineEntityRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Line item not found: " + itemId));
        orderLineEntityRepository.delete(item);
    }
}
