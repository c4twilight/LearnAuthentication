package com.example.LearnAuthentication.entity;
import com.example.LearnAuthentication.model.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLES")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Enumerated(EnumType.STRING) // Store the enum as a String
    @Column(name = "NAME", nullable = false, unique = true)
    private ERole name;

}