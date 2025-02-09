package com.example.test12.domain.common

import com.example.test12.data.remote_data_source.shoes.ShoesDto
import com.example.test12.domain.shoes.Shoes

fun ShoesDto.toShoes(): Shoes {
    return Shoes(
        id = this.id,
        name = this.shoes_name,
        cost = this.shoes_cost,
        description = this.shoes_description,
        image = this.shoes_url,
        category = this.CategoryId,
        isPopular = this.IsPopular
    )
}