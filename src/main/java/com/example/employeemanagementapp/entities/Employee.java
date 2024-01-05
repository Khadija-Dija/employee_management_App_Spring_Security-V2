package com.example.employeemanagementapp.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(min=4,max=20)
    private String name;
    @Pattern(regexp = "^(Male|Female)$")
    private String gender;
    @Pattern(regexp = "^[0-9 ()-]{10,20}$")
    private String phoneNumber;
    @NotEmpty
    @Email
    private String emailAddress;
    @NotEmpty
    private String identificationNumber;
    @Temporal(TemporalType.DATE)
    private Date dateBirth;
}
