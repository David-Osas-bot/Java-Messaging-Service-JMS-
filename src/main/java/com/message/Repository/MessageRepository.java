/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.message.Repository;

import com.message.Entity.Messages;
import jakarta.data.repository.CrudRepository;
//import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
//import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Messages, Integer> {
//
//    @Query("SELECT m FROM MessageEntity m")
//    List<Messages> findAllMessages();
}
