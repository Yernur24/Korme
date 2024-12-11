package kz.bit.kormefinall.services;

import kz.bit.kormefinall.models.Permission;
import kz.bit.kormefinall.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission findById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Transactional
    public void updateUserRole(Long userId, Long permissionId) {
        permissionRepository.updateUserPermissionRole(userId, permissionId);
    }
}
