package org.example.technostore2.dto

import java.math.BigDecimal
import java.util.UUID

data class ProductResponse(
    val id: UUID?,
    val name: String,
    val brand: String,
    val price: BigDecimal,
    val images: List<ImageDto>,
    val attributes: Map<String, String>
)

data class ImageDto(
    val url: String?,
    val contentType: String?
)