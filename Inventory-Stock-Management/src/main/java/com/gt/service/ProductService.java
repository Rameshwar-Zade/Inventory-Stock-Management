package com.gt.service;

import com.gt.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    ProductDTO addProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    List<ProductDTO> getProductsByDealer(Long dealerId);

    // Get products below minStockLevel (Low Stock Alert)
    List<ProductDTO> getLowStockProducts();

    ProductDTO updateStock(Long productId, int quantityChange, String userEmail);


}
