package com.entornos.EntornosP2Backend.service.interfaces;

import com.entornos.EntornosP2Backend.dto.TagDTO;
import com.entornos.EntornosP2Backend.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITagService {

    List<Tag> getAll();

    Boolean deleteTag(Long id);


    Tag newTag(TagDTO newTag);

    Tag editTag(TagDTO editTag);
}
