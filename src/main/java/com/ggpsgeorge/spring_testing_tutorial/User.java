package com.ggpsgeorge.spring_testing_tutorial;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Entity(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 256, nullable = false, unique = true)
    @NotBlank(message = "Email must be not empty")
    @Email(message = "User must have a valid email")
    private String email;

    @Column(length = 256, nullable = false)
    private String firstName;
    @Column(length = 256, nullable = false)
    private String lastName;
    @Column(length = 32, nullable = false)
    private String password;
}
