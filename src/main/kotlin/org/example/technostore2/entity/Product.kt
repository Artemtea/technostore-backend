package org.example.technostore2.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "products")
class Product(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true, nullable = false)
    var name: String,

    var brand: String,

    @Column(precision = 19, scale = 2)
    var price: BigDecimal,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], orphanRemoval = true)
    var images: MutableList<ProductImage> = mutableListOf(),

    @ElementCollection
    @CollectionTable(name = "product_attributes", joinColumns = [JoinColumn(name = "product_id")])
    @MapKeyColumn(name = "attribute_key")
    @Column(name = "attribute_value")
    var attributes: MutableMap<String, String> = mutableMapOf(),

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null
)