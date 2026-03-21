package com.mca.learning_platform.repository;

import com.mca.learning_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // யூசர் பெயரைக் கொடுத்து அவரை டேட்டாபேஸில் தேடிக் கண்டுபிடிக்கும் வசதி
    User findByUsername(String username);
    
}