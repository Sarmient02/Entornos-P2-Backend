package com.entornos.EntornosP2Backend.service.impl;

import com.entornos.EntornosP2Backend.dto.CareerDTO;
import com.entornos.EntornosP2Backend.model.Career;
import com.entornos.EntornosP2Backend.repository.ICareerRepository;
import com.entornos.EntornosP2Backend.service.interfaces.ICareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerServiceImpl implements ICareerService {

    private ICareerRepository careerRepository;


    @Override
    public List<Career> getAll() {
        return this.careerRepository.findAll();
    }

    @Override
    public Boolean deleteCareer(Long id) {

        var exists = this.careerRepository.findById(id);
        if (exists.isEmpty()) {
            return false;
        }
        this.careerRepository.deleteById(id);

        return true;
    }

    @Override
    public Career newCareer(CareerDTO newCareer) {

        var exists = this.careerRepository.findTopByName(newCareer.getName());
        if (exists != null) {
            return null;
        }
        var career = new Career();
        career.setName(newCareer.getName());
        career.setCareerCode(newCareer.getCareerCode());
        return this.careerRepository.save(career);
    }

    @Override
    public Career updateCareer(CareerDTO career) {

        var exists = this.careerRepository.findById(career.getId());
        if (exists.isEmpty()) {
            return null;
        }
        var e = exists.get();
        e.setName(career.getName());
        e.setCareerCode(career.getCareerCode());
        return this.careerRepository.save(e);
    }

    @Override
    public Career getCareerById(Long id) {

        var exists = this.careerRepository.findById(id);
        return exists.orElse(null);
    }

    @Autowired
    public void setCareerRepository(ICareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

}
