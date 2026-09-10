package de.hof.university.moproco.ordermanagement.controller;

import de.hof.university.moproco.ordermanagement.dto.OrderEntityDto;
import de.hof.university.moproco.ordermanagement.dto.OrderLineEntityDto;
import de.hof.university.moproco.ordermanagement.service.OrdersService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersApiController {

    private final OrdersService ordersService;

    public OrdersApiController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @Secured({"ROLE_User","ROLE_SalesClerk","ROLE_WarehouseClerk"})
    @GetMapping
    public ResponseEntity<Page<OrderEntityDto>> listOrders(@RequestParam(required = false) String orderStatus, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size, @RequestParam(defaultValue = "orderDate,desc") String sort) {
        var pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ordersService.listOrders(orderStatus, pageable, sort));
    }
    @Secured({"ROLE_SalesClerk","ROLE_WarehouseClerk"})
    @GetMapping("/{id}")
    public ResponseEntity<OrderEntityDto> getOrderDetails(@PathVariable Long id) {
        return ResponseEntity.ok(ordersService.getOrderDetails(id));
    }
    @Secured({"ROLE_SalesClerk"})
    @PostMapping
    public ResponseEntity<OrderEntityDto> createOrder(@RequestBody OrderEntityDto request) {
        return ResponseEntity.status(201).body(ordersService.createOrder(request));
    }
    @Secured({"ROLE_SalesClerk"})
    @PutMapping("/{id}")
    public ResponseEntity<OrderEntityDto> updateOrder(@RequestBody OrderEntityDto request) {
        return ResponseEntity.status(200).body(ordersService.updateOrder(request));
    }
    @Secured({"ROLE_SalesClerk","ROLE_WarehouseClerk"})
    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderLineEntityDto>> listLineItems(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.listLineItems(orderId));
    }
    @Secured({"ROLE_SalesClerk"})
    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderLineEntityDto> addLineItem(@PathVariable Long orderId, @RequestBody OrderLineEntityDto request) {
        return ResponseEntity.status(201).body(ordersService.addLineItem(orderId, request));
    }
    @Secured({"ROLE_SalesClerk"})
    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<Void> deleteLineItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        ordersService.deleteLineItem(orderId, itemId);
        return ResponseEntity.status(200).build();
    }
}
