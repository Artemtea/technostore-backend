package org.example.technostore2.dto

import org.example.technostore2.entity.Product
import java.math.BigDecimal
import java.util.UUID

data class ProductResponse(
    val id: UUID?,
    val name: String,
    val brand: String,
    val price: BigDecimal,
    val images: List<ImageDto>,
    val attributes: Map<String, String>,
    val categoryName: String?,
    val categoryId: UUID?,
)

data class ImageDto(
    val url: String?,
    val contentType: String?
)

fun Product.toResponse(): ProductResponse {
    return ProductResponse(
        id = this.id,
        name = this.name,
        brand = this.brand,
        price = this.price,
        images = this.images.map { entity ->
            ImageDto(
                url = entity.url,
                contentType = entity.contentType
            )
        },
        attributes = this.attributes,
        categoryName = this.category?.name,
        categoryId = this.category?.id
    )
}