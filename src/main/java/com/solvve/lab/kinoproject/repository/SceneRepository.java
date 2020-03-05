package com.solvve.lab.kinoproject.repository;

import com.solvve.lab.kinoproject.domain.Scene;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SceneRepository extends CrudRepository<Scene, UUID> {
}
