package com.example.CafeManagement.Service;

import com.example.CafeManagement.Entity.Unit;
import com.example.CafeManagement.Repository.UnitRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UnitService {
    UnitRepository unitRepository;

    public List<Unit> getUnitList(){
        return unitRepository.findAll();
    }
}
