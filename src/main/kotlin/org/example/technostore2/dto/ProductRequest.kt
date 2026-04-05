package org.example.technostore2.dto

data class ProductRequest(
    val name: String? = null,
    val brand: String? = null, // Добавь вопросительный знак
    val price: java.math.BigDecimal? = null,
    val imageUrls: List<String>? = null,
    val attributes: Map<String, String>? = null
)