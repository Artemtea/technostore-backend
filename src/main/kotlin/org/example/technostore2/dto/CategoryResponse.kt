package org.example.technostore2.dto

import org.example.technostore2.entity.Category
import java.util.UUID

data class CategoryResponse(
    val id: UUID?,
    val name: String,
    // Если тебе нужны товары внутри категории, используй ProductResponse,
    // но обычно для списка категорий достаточно ID и имени
)

fun Category.toResponse(): CategoryResponse {
    return CategoryResponse(
        id = this.id,
        name = this.name
    )
}