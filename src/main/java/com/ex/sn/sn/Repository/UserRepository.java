package com.ex.sn.sn.Repository;

import com.ex.sn.sn.Entity.User;
import jakarta.transaction.Transactional;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneById(Long id);
    Optional<User> findByUsername(String username);
//    Page<User> findAllByFirstNameContainsOrLastNameContains(String firstName, String lastName, Pageable pageable);
}
