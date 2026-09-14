package fe.masv;

import fe.masv.dao.EmployeeDAO;
import fe.masv.pojo.Employee;
import fe.masv.pojo.Gender;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("HSF302FU");

        EmployeeDAO dao = new EmployeeDAO(emf);

        // =========================
        // TODO 8 - CREATE
        // =========================

        Employee employee = new Employee(
                "Nguyen Van An",
                "an@gmail.com",
                new BigDecimal("15000000"),
                Gender.MALE,
                LocalDate.of(2022, 5, 10),
                true
        );

        System.out.println("===== CREATE =====");

        dao.save(employee);

        System.out.println(
                "Created employee ID: " + employee.getId()
        );


        // =========================
        // TODO 8 - READ
        // =========================

        System.out.println("\n===== READ =====");

        Employee found = dao.findById(employee.getId());

        if (found != null) {
            System.out.println("ID: " + found.getId());
            System.out.println("Name: " + found.getFullName());
            System.out.println("Email: " + found.getEmail());
            System.out.println("Salary: " + found.getSalary());
        } else {
            System.out.println("Employee not found");
        }


        // =========================
        // TODO 8 - UPDATE
        // =========================

        System.out.println("\n===== UPDATE =====");

        employee.setFullName("Nguyen Van An Updated");
        employee.setSalary(new BigDecimal("20000000"));

        dao.update(employee);

        Employee updated = dao.findById(employee.getId());

        System.out.println(
                "Updated name: " + updated.getFullName()
        );

        System.out.println(
                "Updated salary: " + updated.getSalary()
        );


        // =========================
        // TODO 8 - READ AGAIN
        // =========================

        System.out.println("\n===== READ AFTER UPDATE =====");

        Employee checkUpdate =
                dao.findById(employee.getId());

        System.out.println(
                "Name: " + checkUpdate.getFullName()
        );

        System.out.println(
                "Salary: " + checkUpdate.getSalary()
        );


        // =========================
        // TODO 8 - DELETE
        // =========================

        System.out.println("\n===== DELETE =====");

        dao.delete(employee.getId());

        System.out.println(
                "Deleted employee ID: " + employee.getId()
        );


        // =========================
        // TODO 8 - READ AFTER DELETE
        // =========================

        System.out.println("\n===== READ AFTER DELETE =====");

        Employee deleted =
                dao.findById(employee.getId());

        if (deleted == null) {
            System.out.println("Employee not found - delete successful");
        } else {
            System.out.println("Employee still exists");
        }

        emf.close();
    }
}