package com.example.hotelmanager.service;

import com.example.hotelmanager.Domain.Role;
import com.example.hotelmanager.Domain.User;
import java.util.List;

public interface RoleService {
    List<Role> getRoles();
    void createRole(Role theRole);
    void deleteRole(Long id);
    User removeUserFromRole(Long userId, Long roleId);
    User assignRoleToUser(Long userId, Long roleId);
    Role removeAllUsersFromRole(Long roleId);
}
