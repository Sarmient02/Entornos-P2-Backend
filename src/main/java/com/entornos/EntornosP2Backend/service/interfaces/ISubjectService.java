package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.dto.SubjectDTO;
import com.entornos.EntornosP2Backend.model.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISubjectService {

    List<Subject> getAllByCareer(Long careerId);

    Boolean deleteSubject(Long id);

    Subject newSubject(SubjectDTO newSubject);

    Subject editSubject(SubjectDTO editSubject);

    Subject getSubjectById(Long id);
}
