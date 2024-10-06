package com.example.movies1.repositories;

	import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movies1.models.Comment;

	@Repository
	public interface CommentRepository extends JpaRepository<Comment, Long> {
	}

