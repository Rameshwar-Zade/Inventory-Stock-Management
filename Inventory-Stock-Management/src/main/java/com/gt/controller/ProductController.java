package com.gt.controller;

import com.gt.dto.BaseResponseDTO;
import com.gt.dto.ProductDTO;
import com.gt.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ Admin: Add Product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BaseResponseDTO<ProductDTO>> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProduct = productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new BaseResponseDTO<>(
                        HttpStatus.CREATED.value(),
                        "Product added successfully",
                        savedProduct
                ));
    }

    // ✅ Dealer, Admin: Update Product
    @PreAuthorize("hasAnyRole('ADMIN','DEALER')")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<ProductDTO>> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updated = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(
                new BaseResponseDTO<>(
                        HttpStatus.OK.value(),
                        "Product updated successfully",
                        updated
                ));
    }

    // ✅ Admin: Delete Product
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<String>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(
                new BaseResponseDTO<>(
                        HttpStatus.OK.value(),
                        "Product deleted successfully",
                        null
                ));
    }

    //  Admin, Dealer, Customer: View Product by ID
    @PreAuthorize("hasAnyRole('ADMIN','DEALER','CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseDTO<ProductDTO>> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(
                new BaseResponseDTO<>(
                        HttpStatus.OK.value(),
                        "Product retrieved successfully",
                        product
                ));
    }

    //  Admin, Dealer, Customer: View All Products
    @PreAuthorize("hasAnyRole('ADMIN','DEALER','CUSTOMER')")
    @GetMapping
    public ResponseEntity<BaseResponseDTO<List<ProductDTO>>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(
                new BaseResponseDTO<>(
                        HttpStatus.OK.value(),
                        "All products fetched successfully",
                        products
                ));
    }

    //  Dealer: Update Stock Quantity (Ticket 13)
    @PreAuthorize("hasRole('DEALER')")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<BaseResponseDTO<ProductDTO>> updateStock(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> request,
            Principal principal) {

        int quantityChange = request.get("quantityChange");
        String userEmail = principal.getName();
        ProductDTO updated = productService.updateStock(id, quantityChange, userEmail);

        return ResponseEntity.ok(
                new BaseResponseDTO<>(
                        HttpStatus.OK.value(),
                        "Stock updated successfully",
                        updated
                ));
    }

    //   Admin: View Low Stock Products (Ticket 14)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/low-stock")
    public ResponseEntity<BaseResponseDTO<List<ProductDTO>>> getLowStockProducts() {
        List<ProductDTO> lowStock = productService.getLowStockProducts();
        return ResponseEntity.ok(
                new BaseResponseDTO<>(
                        HttpStatus.OK.value(),
                        "Low stock products fetched successfully",
                        lowStock
                ));
    }
}