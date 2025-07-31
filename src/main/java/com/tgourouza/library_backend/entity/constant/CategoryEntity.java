package com.tgourouza.library_backend.entity.constant;

import com.tgourouza.library_backend.constant.Category;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Category name;

    public CategoryEntity(Category name) {
        this.name = name;
    }
}
