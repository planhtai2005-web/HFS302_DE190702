package fe.masv;

import fe.masv.dao.DepartmentDAO;
import fe.masv.pojo.Department;
import fe.masv.pojo.Employee;
import fe.masv.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        // Create Department
        Department department = new Department(
                "IT Department",
                "Ho Chi Minh City"
        );

        // Create Employee 1
        Employee employee1 = new Employee(
                "a@gmail.com",
                "Nguyen Van A",
                Gender.MALE,
                new BigDecimal("1500"),
                LocalDate.of(2026, 1, 10)
        );

        // Create Employee 2
        Employee employee2 = new Employee(
                "b@gmail.com",
                "Nguyen Van B",
                Gender.MALE,
                new BigDecimal("1600"),
                LocalDate.of(2026, 2, 15)
        );

        // Create Employee 3
        Employee employee3 = new Employee(
                "c@gmail.com",
                "Nguyen Van C",
                Gender.FEMALE,
                new BigDecimal("1700"),
                LocalDate.of(2026, 3, 20)
        );

        // Add employees to department
        department.addEmployee(employee1);
        department.addEmployee(employee2);
        department.addEmployee(employee3);

        // Save only Department
        // CascadeType.ALL will persist all employees
        DepartmentDAO departmentDAO = new DepartmentDAO();
        departmentDAO.save(department);

        System.out.println("Department saved successfully!");
        System.out.println("Department ID: " + department.getId());
        System.out.println("Number of employees: "
                + department.getEmployees().size());
    }
}