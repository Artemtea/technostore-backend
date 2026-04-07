package org.example.technostore2.controller

import org.example.technostore2.dto.ProductRequest
import org.example.technostore2.dto.ProductResponse
import org.example.technostore2.dto.toResponse
import org.example.technostore2.entity.Product
import org.example.technostore2.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.getAllProducts())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: UUID,
        @RequestBody request: ProductRequest
    ): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(productService.updateProduct(id, request))
    }

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: UUID): ResponseEntity<ProductResponse> {
        val product = productService.getProductById(id)
        // Мы берем сущность из сервиса и превращаем её в безопасный DTO
        return ResponseEntity.ok(product.toResponse())
    }

    @PostMapping
    fun create(@RequestBody request: ProductRequest): ResponseEntity<ProductResponse> { // Указываем тип явно
        return ResponseEntity.ok(productService.createProduct(request))
    }
}