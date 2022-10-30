package com.gmakris.gameshop.gateway.repository.auth;

import java.util.UUID;
import com.gmakris.gameshop.gateway.entity.model.auth.Role;
import com.gmakris.gameshop.gateway.entity.model.auth.UserRole;
import com.gmakris.gameshop.gateway.repository.GenericRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRoleRepository extends GenericRepository<UserRole> {

    @Query("""
    select r.*
    from users u
        inner join user_roles ur on ur.user_id = u.id
        inner join roles r on ur.role_id = r.id
    where u.id = :userId
    """)
    Flux<Role> findRolesByUserId(@Param("userId") UUID userId);
}
