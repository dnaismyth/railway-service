package com.flow.railwayservice.repository;

import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.dto.Platform;

import java.time.ZonedDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<RUser, Long> {

    Optional<RUser> findOneByActivationKey(String activationKey);

    List<RUser> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

    Optional<RUser> findOneByResetKey(String resetKey);

    Optional<RUser> findOneByEmail(String email);

    Optional<RUser> findOneByLogin(String login);
    
    @Query("SELECT platform FROM RUser ru WHERE ru.id = ?1")
    public Platform findPlatformByUserId(Long userId);

    @Query(value = "select ru FROM RUser ru",
        countQuery = "select count(user) from RUser user")
    Page<RUser> findAllWithAuthorities(Pageable pageable);
}
