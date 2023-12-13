package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.dto.CareerDTO;
import com.entornos.EntornosP2Backend.model.Career;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICareerService {

    List<Career> getAll();

    Career newCareer(CareerDTO newCareer);

    Career updateCareer(CareerDTO career);

    Boolean deleteCareer(Long id);

    Career getCareerById(Long id);
}
