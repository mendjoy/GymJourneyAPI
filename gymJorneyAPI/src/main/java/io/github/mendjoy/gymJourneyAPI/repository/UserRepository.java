package io.github.mendjoy.gymJourneyAPI.repository;

import io.github.mendjoy.gymJourneyAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
