package com.enviro.envirobankingapp.repository;

import com.enviro.envirobankingapp.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
