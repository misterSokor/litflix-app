package com.store.litflix.repository.roles;

import com.store.litflix.model.Role;
import com.store.litflix.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(RoleName roleName);
}
