package org.example.technostore2.controller

import org.example.technostore2.dto.CategoryResponse
import org.example.technostore2.dto.toResponse
import org.example.technostore2.entity.Category
import org.example.technostore2.repository.CategoryRepository
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
@RequestMapping("/api/categories")
class CategoryController(private val repository: CategoryRepository) {

//    @GetMapping
//    fun getAll() = repository.findAll()

    @PostMapping
    fun create(@RequestBody category: Category) = repository.save(category)

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody details: Category): Category {
        val category = repository.findById(id).orElseThrow()
        category.name = details.name
        return repository.save(category)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) {
        // Здесь будет твоя логика отвязки товаров перед удалением
        val category = repository.findById(id).orElseThrow()
        category.products.forEach { it.category = null }
        repository.delete(category)
    }

    @GetMapping
    fun getAll(): List<CategoryResponse> {
        return repository.findAll().map { it.toResponse() }
    }
}