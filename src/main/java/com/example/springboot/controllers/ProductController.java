package com.example.springboot.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dtos.ProductRecordDtos;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import com.example.springboot.response.ProductResponse;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Validated
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	
	@PostMapping("/products")
	public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductRecordDtos productRecordDto) {
		try {
            var productModel = new ProductModel();
            BeanUtils.copyProperties(productRecordDto, productModel);
            ProductModel savedProduct = productRepository.save(productModel);
            ProductResponse response = new ProductResponse(savedProduct.getIdProduct(), "Produto criado com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro durante a criação do produto.");
        }
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductModel>> getAllProducts(){
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Object> getOneProducts(@PathVariable(value="id") UUID id){
		Optional<ProductModel> product = productRepository.findById(id);
		if(product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
		}
		return ResponseEntity.status(HttpStatus.OK).body(product.get());
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<Object> deleteOneProducts(@PathVariable(value="id") UUID id){
		Optional<ProductModel> product = productRepository.findById(id);
		if(product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
		}
		productRepository.delete(product.get());
		return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso");
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProducts(@PathVariable(value="id") UUID id, @RequestBody @Valid ProductRecordDtos productRecordDto){
		Optional<ProductModel> product = productRepository.findById(id);
		if(product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
		}
		var productModel = product.get();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productModel);
	}
}
