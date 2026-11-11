/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.user.Repository;

import com.user.Entity.User;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
     Optional<User> findByEmail(String email);
}
