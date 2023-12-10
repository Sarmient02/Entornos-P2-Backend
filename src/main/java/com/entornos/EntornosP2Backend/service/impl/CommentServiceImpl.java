package com.entornos.EntornosP2Backend.service.impl;

import com.entornos.EntornosP2Backend.dto.CommentDTO;
import com.entornos.EntornosP2Backend.model.Comment;
import com.entornos.EntornosP2Backend.repository.ICommentRepository;
import com.entornos.EntornosP2Backend.repository.IPostRepository;
import com.entornos.EntornosP2Backend.repository.IUserRepository;
import com.entornos.EntornosP2Backend.service.interfaces.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    private ICommentRepository commentRepository;
    private IUserRepository userRepository;
    private IPostRepository postRepository;

    @Override
    public List<Comment> getAll(Long postId) {
        return this.commentRepository.findByPostId(postId);
    }

    @Override
    public Boolean deleteComment(Long id) {

        var exists = this.commentRepository.findById(id);
        if (exists.isEmpty()) {
            return false;
        }

        this.commentRepository.deleteById(id);

        return true;
    }

    @Override
    public Comment newComment(CommentDTO newComment) {

        var userExists = this.userRepository.findById(newComment.getIdUser());
        var postExists = this.postRepository.findById(newComment.getPostId());
        if (userExists.isEmpty() || postExists.isEmpty()) {
            return null;
        }
        var comment = new Comment();
        comment.setPostId(newComment.getPostId());
        comment.setIdUser(newComment.getIdUser());
        comment.setBody(newComment.getBody());
        comment.setCreatedAt(newComment.getCreatedAt() != null ? newComment.getCreatedAt() : new Date());
        return this.commentRepository.save(comment);
    }

    @Override
    public Comment editComment(CommentDTO editComment) {

        var exists = this.commentRepository.findById(editComment.getId());
        if (exists.isEmpty()) {
            return null;
        }
        var e = exists.get();
        e.setBody(editComment.getBody());

        return this.commentRepository.save(e);
    }

    @Override
    public Comment getCommentById(Long id) {

        var exists = this.commentRepository.findById(id);
        return exists.orElse(null);

    }

    @Autowired
    public void setCommentRepository(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Autowired
    public void setUserRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPostRepository(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
