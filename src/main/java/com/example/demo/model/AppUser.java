package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "users",indexes = {
        @Index(name = "idx_email", columnList = "email")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(unique = true,nullable = false)
    private String email;
    private String password;
    private String name;
    private String refreshToken;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Task> taskList;

}

