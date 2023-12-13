package com.entornos.EntornosP2Backend.service.impl;

import com.entornos.EntornosP2Backend.dto.TagDTO;
import com.entornos.EntornosP2Backend.model.Tag;
import com.entornos.EntornosP2Backend.repository.ITagRepository;
import com.entornos.EntornosP2Backend.service.interfaces.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements ITagService {

    private ITagRepository tagRepository;

    @Override
    public List<Tag> getAll() {
        return this.tagRepository.findAll();
    }

    @Override
    public Boolean deleteTag(Long tagId) {
        this.tagRepository.deleteById(tagId);
        return true;
    }

    @Override
    public Tag newTag(TagDTO tagDTO) {
        var exists = this.tagRepository.findTopByName(tagDTO.getName());
        if (exists != null) {
            return null;
        }
        var newTag = new Tag();
        newTag.setName(tagDTO.getName());
        return this.tagRepository.save(newTag);
    }

    @Override
    public Tag editTag(TagDTO editTag) {
        var exists = this.tagRepository.findById(editTag.getId());
        if (exists.isEmpty()) {
            return null;
        }
        var e = exists.get();
        e.setName(editTag.getName());
        return this.tagRepository.save(e);
    }

    @Autowired
    public void setTagRepository(ITagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
}