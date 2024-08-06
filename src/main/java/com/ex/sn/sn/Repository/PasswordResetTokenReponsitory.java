package com.ex.sn.sn.Repository;

import com.ex.sn.sn.Entity.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PasswordResetTokenReponsitory extends CrudRepository<PasswordResetToken,Long> {

    PasswordResetToken findByToken(String token);
}
