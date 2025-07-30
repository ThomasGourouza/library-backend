package com.tgourouza.library_backend.constant;

public enum Country {
    UNKNOWN("Inconnu"),
    FRANCE("France"),
    GREAT_BRITAIN("Grande-Bretagne"),
    SPAIN("Espagne"),
    GERMANY("Allemagne"),
    ITALY("Italie"),
    USA("États-Unis"),
    CANADA("Canada"),
    JAPAN("Japon"),
    CHINA("Chine"),
    RUSSIA("Russie"),
    NETHERLANDS("Pays-Bas"),
    DENMARK("Danemark");

    private String value;

    Country(String value) {
        this.value = value;
    }
    
}
