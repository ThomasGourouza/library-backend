package com.tgourouza.library_backend.entity.multilingual;

import com.tgourouza.library_backend.dto.Multilingual;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "occupation")
@Getter @Setter @NoArgsConstructor
public class OccupationEntity extends MultilingualEntity {
    public OccupationEntity(Multilingual m) { super(m); }
    public static OccupationEntity of(Multilingual m) { return new OccupationEntity(m); }
}