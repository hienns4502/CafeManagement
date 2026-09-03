package com.example.CafeManagement.Service;

import com.example.CafeManagement.Entity.Position;
import com.example.CafeManagement.Repository.PositionRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PositionService {
    PositionRepository positionRepository;
    public List<Position> getAllPositions()
    {
        return positionRepository.findAll();
    }
}
