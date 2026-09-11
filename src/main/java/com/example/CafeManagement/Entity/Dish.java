package com.example.CafeManagement.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(50)")
    String tenMon;
    Long giaTien;
    @OneToMany(mappedBy = "mon", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<RecipeDetail> recipeDetails =  new ArrayList<RecipeDetail>();

}
