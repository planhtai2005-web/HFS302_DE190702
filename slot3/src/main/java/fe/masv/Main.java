package fe.masv;

import fe.masv.dao.DepartmentDAO;
import fe.masv.pojo.Department;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        DepartmentDAO departmentDAO = new DepartmentDAO();

        // Get all departments
        List<Department> departments = departmentDAO.findAll();

        System.out.println("Number of departments: "
                + departments.size());

        // Access employees of each department
        // This demonstrates the N+1 query problem
        for (Department department : departments) {

            System.out.println(
                    "Department: " + department.getName()
            );

            System.out.println(
                    "Number of employees: "
                            + department.getEmployees().size()
            );
        }
    }
}