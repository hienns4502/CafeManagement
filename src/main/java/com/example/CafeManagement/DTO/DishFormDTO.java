package com.example.CafeManagement.DTO;


import com.example.CafeManagement.Entity.RecipeDetail;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DishFormDTO {
    String id;
    String tenMon;
    Long giaTien;
    List<RecipeDetailDTO> recipeDetails =  new ArrayList<RecipeDetailDTO>();
}
