package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.input.CareerCreationDTO;
import com.gestoracademico.gestoracademico.dto.input.CareerUpdateDTO;
import com.gestoracademico.gestoracademico.dto.output.CareerDTO;
import com.gestoracademico.gestoracademico.exceptions.CareerNotFounException;
import com.gestoracademico.gestoracademico.mapper.CareerMapper;
import com.gestoracademico.gestoracademico.mapper.CareerUpdateMapper;
import com.gestoracademico.gestoracademico.model.Career;
import com.gestoracademico.gestoracademico.repository.CareerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CareerService implements ICareerService{
    private final CareerRepository careerRepository;
    private final CareerMapper careerMapper;
    private final CareerUpdateMapper careerUpdateMapper;

    public CareerService(CareerRepository careerRepository, CareerMapper careerMapper, CareerUpdateMapper careerUpdateMapper) {
        this.careerRepository = careerRepository;
        this.careerMapper = careerMapper;
        this.careerUpdateMapper = careerUpdateMapper;
    }

    @Override
    public CareerDTO createCareer(CareerCreationDTO career) {
        if (career == null)
            return null;
        Career createdCareer = careerRepository.save(careerMapper.toEntity(career));
        return careerMapper.toDTO(createdCareer);
    }

    @Override
    public CareerDTO updateCareer(Long id, CareerUpdateDTO career) {
        Career foundCareer = careerRepository.findById(id)
                .orElseThrow(() -> new CareerNotFounException(""));
        careerUpdateMapper.updateEntityToDto(career, foundCareer);
        Career updateCareer = careerRepository.save(foundCareer);
        return careerMapper.toDTO(updateCareer);
    }

    @Override
    public CareerDTO getCareer(Long id) {
        Career foundCareer = careerRepository.findById(id)
                .orElseThrow(() -> new CareerNotFounException(""));
        return careerMapper.toDTO(foundCareer);
    }

    @Override
    public List<CareerDTO> getCareers() {
        return careerRepository.findAll()
                .stream()
                .map(careerMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteCareer(Long id) {
        if (!careerRepository.existsById(id))
            throw new CareerNotFounException("");
        careerRepository.deleteById(id);
    }
}
