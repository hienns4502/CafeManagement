package com.example.CafeManagement.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RecipeDetailDTO {
    String merchandiseId;
    int quantity;
    String unitId;
}
