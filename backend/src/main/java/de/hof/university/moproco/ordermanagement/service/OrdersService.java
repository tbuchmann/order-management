package de.hof.university.moproco.ordermanagement.service;

import de.hof.university.moproco.ordermanagement.dto.OrderEntityDto;
import de.hof.university.moproco.ordermanagement.dto.OrderLineEntityDto;
import de.hof.university.moproco.ordermanagement.entity.OrderEntity;
import de.hof.university.moproco.ordermanagement.entity.OrderLineEntity;
import de.hof.university.moproco.ordermanagement.repository.OrderEntityRepository;
import de.hof.university.moproco.ordermanagement.repository.OrderLineEntityRepository;
import de.hof.university.moproco.ordermanagement.repository.ProductEntityRepository;
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
    private final ProductEntityRepository productEntityRepository;

    public OrdersService(OrderEntityRepository orderEntityRepository,
                         OrderLineEntityRepository orderLineEntityRepository,
                         ProductEntityRepository productEntityRepository) {
        this.orderEntityRepository = orderEntityRepository;
        this.orderLineEntityRepository = orderLineEntityRepository;
        this.productEntityRepository = productEntityRepository;
    }

    public org.springframework.data.domain.Page<OrderEntityDto> listOrders(String orderStatus, Pageable pageable, String sort) {
        Specification<OrderEntity> spec = null;
        if (orderStatus != null) {
            spec = (root, query, cb) -> cb.equal(root.get("orderStatus"), orderStatus);
        }
        Sort sortObj = parseSort(sort);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortObj);

                return orderEntityRepository.findAll(spec, sortedPageable).map(OrderEntityDto::from);
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "orderDate");
        }
        String[] parts = sort.split(",");
        if (parts.length >= 2) {
            Sort.Direction dir = parts[1].trim().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            return Sort.by(dir, parts[0].trim());
        }
        return Sort.by(Sort.Direction.ASC, parts[0].trim());
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
    public OrderLineEntityDto addLineItem(Long orderId, OrderLineEntityDto request) {
        var order = orderEntityRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        var entity = request.toEntity();
        entity.setLineNumber(null);
        entity.setOrder(order);
        if (request.productId() != null) {
            var product = productEntityRepository.findById(request.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + request.productId()));
            entity.setProduct(product);
        }
        var saved = orderLineEntityRepository.save(entity);
        recalculateOrderValue(orderId);
        return OrderLineEntityDto.from(saved);
    }
    public void deleteLineItem(Long orderId, Long itemId) {
        var item = orderLineEntityRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Line item not found: " + itemId));
        orderLineEntityRepository.delete(item);
        recalculateOrderValue(orderId);
    }

    private void recalculateOrderValue(Long orderId) {
        var order = orderEntityRepository.findById(orderId).orElse(null);
        if (order == null) return;
        var items = order.getLineItemsList();
        double total = 0;
        if (items != null) {
            for (var item : items) {
                total += item.getQuantity() * item.getUnitPrice();
            }
        }
        order.setOrderValue(total);
        orderEntityRepository.save(order);
    }
}
