package org.example.technostore2.service

import org.example.technostore2.dto.ImageDto
import org.example.technostore2.dto.ProductRequest
import org.example.technostore2.dto.ProductResponse
import org.example.technostore2.entity.Product
import org.example.technostore2.entity.ProductImage
import org.example.technostore2.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ProductService(private val productRepository: ProductRepository) {

    @Transactional
    fun createProduct(request: ProductRequest): Product {
        // 1. Извлекаем значения и СРАЗУ проверяем их на null
        // Если они null — выбрасываем ошибку. После этой проверки переменные становятся обычными String (не null)
        val name = request.name ?: throw IllegalArgumentException("Name must not be null")
        val brand = request.brand ?: throw IllegalArgumentException("Brand must not be null")
        val price = request.price ?: throw IllegalArgumentException("Price must not be null")

        // 2. Теперь при создании Product ошибки не будет, так как мы передаем не-null переменные
        val product = Product(
            name = name,
            brand = brand,
            price = price,
            attributes = request.attributes?.toMutableMap() ?: mutableMapOf()
        )

        // 3. Обработка картинок
        request.imageUrls?.forEach { url ->
            product.images.add(ProductImage(url = url, product = product))
        }

        return productRepository.save(product)
    }

    fun getAllProducts(): List<ProductResponse> {
        return productRepository.findAll().map { product ->
            product.toResponse()
        }
    }

    fun getProductById(id: UUID): Product {
        return productRepository.findById(id)
            .orElseThrow { NoSuchElementException("Товар с id $id не найден") }
    }

    private fun Product.toResponse() = ProductResponse(
        id = this.id,
        name = this.name,
        brand = this.brand,
        price = this.price,
        images = this.images.map { ImageDto(it.url, it.contentType) },
        attributes = this.attributes
    )

    @Transactional
    fun deleteProduct(id: UUID) {
        if (!productRepository.existsById(id)) {
            throw RuntimeException("Товар с ID $id не найден")
        }
        productRepository.deleteById(id)
    }

    @Transactional
    fun updateProduct(id: UUID, request: ProductRequest): ProductResponse {
        val product = productRepository.findById(id)
            .orElseThrow { RuntimeException("Товар не найден") }

        // Если в JSON пришло поле "name", обновляем. Если нет (null) — пропускаем.
        request.name?.let { product.name = it }
        request.brand?.let { product.brand = it }
        request.price?.let { product.price = it }

        // С атрибутами то же самое: если прислали новую карту атрибутов, обновляем её
        request.attributes?.let {
            product.attributes.clear()
            product.attributes.putAll(it)
        }

        request.imageUrls?.let { urls ->
            product.images.clear() // Удаляем старые связи
            urls.forEach { url ->
                val newImg = ProductImage(url = url, product = product)
                product.images.add(newImg)
            }
        }

        return productRepository.save(product).toResponse()
    }
}