package kz.bit.kormefinall.repositories;

import kz.bit.kormefinall.models.Permission;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Modifying
    @Query(value = "UPDATE users_permissions SET permissions_id = :permissionId WHERE user_id = :userId", nativeQuery = true)
    void updateUserPermissionRole(@Param("userId") Long userId, @Param("permissionId") Long permissionId);
}