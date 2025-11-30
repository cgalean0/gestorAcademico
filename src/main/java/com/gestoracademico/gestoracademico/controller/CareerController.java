package com.gestoracademico.gestoracademico.controller;

import com.gestoracademico.gestoracademico.dto.input.CareerCreationDTO;
import com.gestoracademico.gestoracademico.dto.input.CareerUpdateDTO;
import com.gestoracademico.gestoracademico.dto.output.CareerDTO;
import com.gestoracademico.gestoracademico.service.ICareerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/careers")
public class CareerController {
    private final ICareerService careerService;

    public CareerController(ICareerService careerService) {
        this.careerService = careerService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CareerDTO> createCareer(@RequestBody CareerCreationDTO career) {
        CareerDTO createdCareer = careerService.createCareer(career);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCareer);
    }

    @PreAuthorize("hasAnyRole('ADMIN)")
    @PutMapping("/{id}")
    public ResponseEntity<CareerDTO> updatCareer(@PathVariable Long id, @RequestBody CareerUpdateDTO career) {
        CareerDTO updatedCareer = careerService.updateCareer(id, career);
        return ResponseEntity.ok(updatedCareer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerDTO> getCareer(@PathVariable Long id) {
        CareerDTO career = careerService.getCareer(id);
        return ResponseEntity.ok(career);
    }

    @GetMapping
    public ResponseEntity<List<CareerDTO>> getCareers() {
        List<CareerDTO> careerDTOList = careerService.getCareers();
        return ResponseEntity.ok(careerDTOList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        careerService.deleteCareer(id);
        return ResponseEntity.noContent().build();
    }
}
