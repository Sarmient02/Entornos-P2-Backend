package com.entornos.EntornosP2Backend.service.impl;

import com.entornos.EntornosP2Backend.dto.SubjectDTO;
import com.entornos.EntornosP2Backend.model.Subject;
import com.entornos.EntornosP2Backend.repository.ISubjectRepository;
import com.entornos.EntornosP2Backend.service.interfaces.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements ISubjectService {

    private ISubjectRepository subjectRepository;

    @Override
    public List<Subject> getAllByCareer(Long careerId) {
        return this.subjectRepository.findByCareerId(careerId);
    }

    @Override
    public List<Subject> getAllSubjects() {
        return this.subjectRepository.findAll();
    }

    @Override
    public Boolean deleteSubject(Long id) {

        var exists = this.subjectRepository.findById(id);
        if (exists.isEmpty()) {
            return false;
        }
        this.subjectRepository.deleteById(id);
        return true;
    }

    @Override
    public Subject newSubject(SubjectDTO newSubject) {

        var exists = this.subjectRepository.findTopByName(newSubject.getName());
        if (exists != null) {
            return null;
        }
        var subject = new Subject();
        subject.setName(newSubject.getName());
        subject.setCareerId(newSubject.getCareerId());
        return this.subjectRepository.save(subject);
    }

    @Override
    public Subject editSubject(SubjectDTO editSubject) {

        var exists = this.subjectRepository.findById(editSubject.getId());
        if (exists.isEmpty()) {
            return null;
        }
        var subject = exists.get();
        subject.setName(editSubject.getName());
        subject.setCareerId(editSubject.getCareerId());
        return this.subjectRepository.save(subject);
    }

    @Override
    public Subject getSubjectById(Long id) {

        var exists = this.subjectRepository.findById(id);
        return exists.orElse(null);
    }

    @Autowired
    public void setSubjectRepository(ISubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
}
