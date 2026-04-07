package org.example.technostore2.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "categories")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    var name: String,

    // Список товаров в этой категории
    @OneToMany(mappedBy = "category", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var products: MutableList<Product> = mutableListOf()
)