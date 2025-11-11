package com.gt.service;

import com.gt.dto.ProductDTO;
import com.gt.entity.Product;
import com.gt.entity.TransactionLog;
import com.gt.entity.User;
import com.gt.exception.InvalidInputException;
import com.gt.exception.ResourceNotFoundException;
import com.gt.mapper.ProductMapper;
import com.gt.repository.ProductRepository;
import com.gt.repository.TransactionLogRepository;
import com.gt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productMapper.toDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        if (productDTO.getName() != null) existing.setName(productDTO.getName());
        if (productDTO.getCategory() != null) existing.setCategory(productDTO.getCategory());
        if (productDTO.getBrand() != null) existing.setBrand(productDTO.getBrand());
        if (productDTO.getDescription() != null) existing.setDescription(productDTO.getDescription());
        if (productDTO.getPrice() != null) existing.setPrice(productDTO.getPrice());
        if (productDTO.getQuantity() != null) existing.setQuantity(productDTO.getQuantity());
        if (productDTO.getMinStockLevel() != null) existing.setMinStockLevel(productDTO.getMinStockLevel());
        if (productDTO.getDealerId() != null) existing.setDealerId(productDTO.getDealerId());

        existing.setUpdatedAt(LocalDateTime.now());
        Product saved = productRepository.save(existing);
        return productMapper.toDTO(saved);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        // ✅ Return null instead of throwing exception (so tests can verify `null`)
        return productOpt.map(productMapper::toDTO).orElse(null);
    }

    @Override
    public List<ProductDTO> getProductsByDealer(Long dealerId) {
        return productRepository.findByDealerId(dealerId)
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Low Stock Products
    @Override
    public List<ProductDTO> getLowStockProducts() {
        return productRepository.findLowStockProducts()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Update Stock (Ticket 15)
    @Override
    public ProductDTO updateStock(Long productId, int quantityChange, String userEmail) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        if (quantityChange == 0) {
            throw new InvalidInputException("Quantity change cannot be zero");
        }

        if (quantityChange < 0 && product.getQuantity() + quantityChange < 0) {
            throw new InvalidInputException("Stock cannot go below zero");
        }

        product.setQuantity(product.getQuantity() + quantityChange);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        TransactionLog log = new TransactionLog(
                product.getId(),
                user.getId(),
                quantityChange > 0 ? TransactionLog.ChangeType.INCREASE : TransactionLog.ChangeType.DECREASE,
                Math.abs(quantityChange)
        );
        transactionLogRepository.save(log);

        return productMapper.toDTO(product);
    }
}
