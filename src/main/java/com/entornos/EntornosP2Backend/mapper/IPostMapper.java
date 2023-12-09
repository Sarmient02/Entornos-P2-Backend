package com.entornos.EntornosP2Backend.mapper;

import com.entornos.EntornosP2Backend.dto.PostResponseDTO;
import com.entornos.EntornosP2Backend.dto.SubjectResponseDTO;
import com.entornos.EntornosP2Backend.dto.UserPostData;
import com.entornos.EntornosP2Backend.model.File;
import com.entornos.EntornosP2Backend.model.Post;
import com.entornos.EntornosP2Backend.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IPostMapper {

    IPostMapper INSTANCE = Mappers.getMapper(IPostMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "createdAt", target = "createdAt")
    PostResponseDTO postToPostResponseDTO(Post post);

    List<PostResponseDTO> postsToPostResponseDTOList(List<Post> posts);

    @AfterMapping
    default void fillContent(Post post, @MappingTarget PostResponseDTO dto) {
        List<File> files = post.getFile();
        if (files != null && !files.isEmpty()){
            dto.setFiles(IFileMapper.INSTANCE.filesToFileResponseDTOList(post.getFile()));
        }
        SubjectResponseDTO subject = new SubjectResponseDTO();
        subject.setName(post.getSubject().getName());
        dto.setSubject(subject);
        User user = post.getUser();
        UserPostData userData = new UserPostData();
        userData.setId(user.getId());
        userData.setFullName(user.getFullName());
        userData.setUsername(user.getUsername());
        dto.setUser(userData);
    }

}
