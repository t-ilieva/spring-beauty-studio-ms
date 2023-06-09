package spring.ms.com.security;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Controller;

@Data
@Entity
@Table(name = "user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String password;
    private String role;



}
