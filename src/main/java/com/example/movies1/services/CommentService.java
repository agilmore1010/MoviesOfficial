package com.example.movies1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movies1.models.Comment;
import com.example.movies1.repositories.CommentRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository cRepo;

	public Comment createComment(Comment comment) {
		return cRepo.save(comment);
	}

	public Comment getCommentById(Long id) {
		return cRepo.findById(id).orElse(null);
	}

	public void deleteComment(Long id) {
		cRepo.deleteById(id);
	}
	
	public Comment updateComment(Comment comment){
		if (cRepo.existsById(comment.getId())) {
			return cRepo.save(comment);
		}
	return null;
	}
}
