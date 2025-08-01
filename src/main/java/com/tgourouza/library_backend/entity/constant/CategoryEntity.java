package com.tgourouza.library_backend.entity.constant;

import com.tgourouza.library_backend.constant.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private Category name;
}
