package org.example.technostore2.repository

import org.example.technostore2.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductRepository : JpaRepository<Product, UUID> {
    fun existsByName(name: String): Boolean
}