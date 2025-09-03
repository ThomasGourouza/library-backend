package com.tgourouza.library_backend.entity.multilingual;

import com.tgourouza.library_backend.dto.Multilingual;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "status")
@Getter @Setter @NoArgsConstructor
public class StatusEntity extends MultilingualEntity {
    public StatusEntity(Multilingual m) { super(m); }
    public static StatusEntity of(Multilingual m) { return new StatusEntity(m); }
}