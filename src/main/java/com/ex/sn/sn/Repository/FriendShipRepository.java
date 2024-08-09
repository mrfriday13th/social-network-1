package com.ex.sn.sn.Repository;

import com.ex.sn.sn.Entity.Friendship;
import com.ex.sn.sn.Entity.User;
import org.mapstruct.control.MappingControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendShipRepository extends JpaRepository<Friendship, Long> {
    @Query(value = "INSERT INTO friendships (address_user_id, user_id) values(?1, ?2) ", nativeQuery = true)
    Friendship createFriendship(long userId,long addressUserId);

}
