package com.example.CafeManagement.Service;

import com.example.CafeManagement.DTO.DishFormDTO;
import com.example.CafeManagement.DTO.RecipeDetailDTO;
import com.example.CafeManagement.DTO.VoucherResponse;
import com.example.CafeManagement.Entity.*;
import com.example.CafeManagement.Mapper.DishMapper;
import com.example.CafeManagement.Mapper.VoucherMapper;
import com.example.CafeManagement.Repository.DishRepository;
import com.example.CafeManagement.Repository.MerchandiseRepository;
import com.example.CafeManagement.Repository.UnitRepository;
import com.example.CafeManagement.Repository.VoucherRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class DishService {

    DishMapper dishMapper;
    UnitRepository unitRepository;
    MerchandiseRepository merchandiseRepository;
    DishRepository dishRepository;

    public List<Dish> getDishList() {
        List<Dish> dishList = dishRepository.findAll();
        return dishList;
    }

    @Transactional
    public void addDish(DishFormDTO dishFormDTO) {
        Dish dish = dishMapper.toDish(dishFormDTO);

        List<RecipeDetailDTO> recipeDetailDTOList = dishFormDTO.getRecipeDetails();
        if (recipeDetailDTOList == null || recipeDetailDTOList.isEmpty()) {
            throw new IllegalArgumentException("Công thức nấu ăn không được để trống nguyên liệu!");
        }


        List<String> merchandiseIds = recipeDetailDTOList.stream()
                .map(RecipeDetailDTO::getMerchandiseId)
                .distinct()
                .toList();

        List<String> unitIds = recipeDetailDTOList.stream()
                .map(RecipeDetailDTO::getUnitId)
                .distinct()
                .toList();


        Map<String, Merchandise> merchandiseMap = merchandiseRepository.findAllById(merchandiseIds).stream()
                .collect(Collectors.toMap(Merchandise::getId, Function.identity()));

        Map<String, Unit> unitMap = unitRepository.findAllById(unitIds).stream()
                .collect(Collectors.toMap(Unit::getId, Function.identity()));


        List<RecipeDetail> recipeDetailList = recipeDetailDTOList.stream().map(dto -> {
            Merchandise merchandise = merchandiseMap.get(dto.getMerchandiseId());
            Unit unit = unitMap.get(dto.getUnitId());

            if (merchandise == null || unit == null) {
                throw new IllegalArgumentException("Hàng hóa hoặc đơn vị tính không tồn tại!");
            }

            return RecipeDetail.builder()
                    .hangHoa(merchandise)
                    .soLuong(dto.getQuantity())
                    .donViTinh(unit)
                    .mon(dish)
                    .build();
        }).toList();

        dish.setRecipeDetails(recipeDetailList);
        dishRepository.save(dish);
    }

    public void deleteDish(String voucherId) {
        Dish foundDish = dishRepository.findById(voucherId).orElseThrow(() -> new RuntimeException("Dish not found!"));
        dishRepository.delete(foundDish);
    }

    public Dish getDish(String dishId) {
        Dish foundDish = dishRepository.findById(dishId).orElseThrow(() -> new RuntimeException("Dish not found!"));
        return foundDish;
    }

    @Transactional
    public void updateDish(DishFormDTO dto) {
        Dish dish = dishRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn!"));

        dish.setTenMon(dto.getTenMon());
        dish.setGiaTien(dto.getGiaTien());

        List<RecipeDetailDTO> detailDTOs = dto.getRecipeDetails();
        if (detailDTOs == null || detailDTOs.isEmpty()) {
            throw new IllegalArgumentException("Nguyên liệu không được để trống!");
        }


        List<String> merchandiseIds = detailDTOs.stream()
                .map(RecipeDetailDTO::getMerchandiseId)
                .distinct()
                .toList();

        List<String> unitIds = detailDTOs.stream()
                .map(RecipeDetailDTO::getUnitId)
                .distinct()
                .toList();

        Map<String, Merchandise> merchandiseMap = merchandiseRepository.findAllById(merchandiseIds).stream()
                .collect(Collectors.toMap(Merchandise::getId, Function.identity()));

        Map<String, Unit> unitMap = unitRepository.findAllById(unitIds).stream()
                .collect(Collectors.toMap(Unit::getId, Function.identity()));


        dish.getRecipeDetails().clear();


        List<RecipeDetail> newDetails = detailDTOs.stream().map(itemDto -> {
            Merchandise merchandise = merchandiseMap.get(itemDto.getMerchandiseId());
            Unit unit = unitMap.get(itemDto.getUnitId());

            if (merchandise == null || unit == null) {
                throw new IllegalArgumentException("Hàng hóa hoặc đơn vị tính không tồn tại!");
            }

            return RecipeDetail.builder()
                    .hangHoa(merchandise)
                    .soLuong(itemDto.getQuantity())
                    .donViTinh(unit)
                    .mon(dish)
                    .build();
        }).toList();


        dish.getRecipeDetails().addAll(newDetails);
        dishRepository.save(dish);
    }

    public List<Dish> getDishListByKey(String key) {
        List<Dish> dishList = dishRepository.findByTenMonContainingIgnoreCase(key);
        return dishList;
    }

}
