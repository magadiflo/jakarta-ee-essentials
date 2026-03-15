package dev.magadiflo.app.model;

import java.math.BigDecimal;

public record Product(Long id,
                      String name,
                      String category,
                      BigDecimal price) {
}
