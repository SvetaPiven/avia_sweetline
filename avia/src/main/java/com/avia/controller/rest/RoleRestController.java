package com.avia.controller.rest;

import com.avia.dto.requests.RoleDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.FlightStatus;
import com.avia.model.entity.PlaneType;
import com.avia.model.entity.Role;
import com.avia.repository.RoleRepository;
import com.avia.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/roles")
public class RoleRestController {

    private final RoleRepository roleRepository;

    private final RoleService roleService;

    private static final Logger log = Logger.getLogger(RoleRestController.class);

    @GetMapping()
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllRolesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, 2, Sort.by("idRoles").ascending());

        Page<Role> roles = roleRepository.findAll(pageable);

        if (roles.hasContent()) {
            return new ResponseEntity<>(roles, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleDto roleDto) {
        Role createdRole = roleService.createRole(roleDto);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Role with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Integer id, @RequestBody RoleDto roleDto) {
        Role updatedRole = roleService.updateRole(id, roleDto);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Integer id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            roleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Role with id " + id + " not found!");
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeleteRole(@PathVariable("id") Integer id) {
        Optional<Role> roleOptional = roleRepository.findById(id);

        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            role.setIsDeleted(true);
            roleRepository.save(role);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Role with id " + id + " not found!");
        }
    }
}