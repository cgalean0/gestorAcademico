package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.input.CareerCreationDTO;
import com.gestoracademico.gestoracademico.dto.input.CareerUpdateDTO;
import com.gestoracademico.gestoracademico.dto.output.CareerDTO;

import java.util.List;

public interface ICareerService {
    CareerDTO createCareer(CareerCreationDTO career);
    CareerDTO updateCareer(Long id, CareerUpdateDTO career);
    CareerDTO getCareer(Long id);
    List<CareerDTO> getCareers();
    void deleteCareer(Long id);
}
