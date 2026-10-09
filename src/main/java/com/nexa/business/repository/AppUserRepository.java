package com.nexa.business.repository;
import com.nexa.business.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface AppUserRepository extends JpaRepository<AppUser,Long>{ Optional<AppUser> findByUsernameIgnoreCase(String username); }
