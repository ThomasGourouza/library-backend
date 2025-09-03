package com.tgourouza.library_backend.entity.multilingual;

import com.tgourouza.library_backend.dto.Multilingual;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "book_tag")
@Getter @Setter @NoArgsConstructor
public class BookTagEntity extends MultilingualEntity {
    public BookTagEntity(Multilingual m) { super(m); }
    public static BookTagEntity of(Multilingual m) { return new BookTagEntity(m); }
}