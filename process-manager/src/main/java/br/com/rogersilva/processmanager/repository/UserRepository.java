package br.com.rogersilva.processmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogersilva.processmanager.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}