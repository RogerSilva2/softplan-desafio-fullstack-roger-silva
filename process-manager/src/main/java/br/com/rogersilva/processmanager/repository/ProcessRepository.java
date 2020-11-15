package br.com.rogersilva.processmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rogersilva.processmanager.model.Process;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Long> {
}