package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends CrudRepository<Comment, UUID> {
}
