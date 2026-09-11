package fe.masv.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String email;

    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate hireDate;

    private boolean active;

    @Transient
    private int yearsOfService;

    public Employee(String fullName,
                    String email,
                    BigDecimal salary,
                    Gender gender,
                    LocalDate hireDate,
                    boolean active) {

        this.fullName = fullName;
        this.email = email;
        this.salary = salary;
        this.gender = gender;
        this.hireDate = hireDate;
        this.active = active;
    }

    public int getYearsOfService() {
        if (hireDate == null) {
            return 0;
        }

        return Period.between(hireDate, LocalDate.now()).getYears();
    }
}