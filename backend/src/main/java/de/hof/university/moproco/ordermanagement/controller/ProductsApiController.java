package de.hof.university.moproco.ordermanagement.controller;

import de.hof.university.moproco.ordermanagement.dto.ProductEntityDto;
import de.hof.university.moproco.ordermanagement.repository.PriceListEntryEntityRepository;
import de.hof.university.moproco.ordermanagement.repository.ProductEntityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsApiController {

    private final ProductEntityRepository productEntityRepository;
    private final PriceListEntryEntityRepository priceListEntryEntityRepository;

    public ProductsApiController(ProductEntityRepository productEntityRepository,
                                 PriceListEntryEntityRepository priceListEntryEntityRepository) {
        this.productEntityRepository = productEntityRepository;
        this.priceListEntryEntityRepository = priceListEntryEntityRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductEntityDto>> listProducts() {
        var products = productEntityRepository.findAll();
        var dtos = products.stream().map(p -> {
            var priceEntry = priceListEntryEntityRepository.findFirstByProduct_ProductNumber(p.getProductNumber());
            if (priceEntry.isPresent()) {
                return ProductEntityDto.from(p, priceEntry.get().getPrice(), priceEntry.get().getCurrency());
            }
            return ProductEntityDto.from(p);
        }).toList();
        return ResponseEntity.ok(dtos);
    }
}
