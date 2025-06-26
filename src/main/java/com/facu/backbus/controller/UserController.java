package com.facu.backbus.controller;

import com.facu.backbus.dto.UserDTO;
import com.facu.backbus.mapper.UserMapper;
import com.facu.backbus.model.User;
import com.facu.backbus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador responsável por gerenciar operações relacionadas a usuários.
 * Permite listar, buscar por ID, criar, atualizar e deletar usuários.
 * Apenas usuários do tipo GERENTE podem criar ou atualizar outros usuários como GERENTE.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Lista todos os usuários cadastrados.
     * @return Lista de DTOs de usuários
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um usuário pelo ID.
     * @param id ID do usuário
     * @return DTO do usuário encontrado ou 404 se não encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(UserMapper.toDTO(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Cria um novo usuário.
     * @param userDTO DTO com os dados do usuário a ser criado
     * @return DTO do usuário criado
     */
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setUserType(userDTO.getUserType());
        User savedUser = userService.save(user);
        return UserMapper.toDTO(savedUser);
    }


    /**
     * Atualiza um usuário existente.
     * @param id ID do usuário a ser atualizado
     * @param userDTO DTO com os novos dados do usuário
     * @return DTO do usuário atualizado ou 404 se não encontrado
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User existingUser = userService.findById(id);
        if (existingUser != null) {
            existingUser.setFullName(userDTO.getFullName());
            existingUser.setLogin(userDTO.getLogin());

            // Apenas atualiza a senha se uma nova for fornecida
            if (userDTO.getPassword() != null && !userDTO.getPassword().trim().isEmpty()) {
                existingUser.setPassword(userDTO.getPassword());
            }

            User updated = userService.save(existingUser);
            return ResponseEntity.ok(UserMapper.toDTO(updated));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Remove um usuário pelo ID.
     * @param id ID do usuário a ser removido
     * @return Resposta vazia com status 204 (No Content) ou 404 se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            userService.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}
