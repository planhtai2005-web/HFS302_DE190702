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

        // Load employees using JOIN FETCH
        for (Department department : departments) {

            Department departmentWithEmployees =
                    departmentDAO.findWithEmployees(department.getId());

            System.out.println(
                    "Department: "
                            + departmentWithEmployees.getName()
            );

            System.out.println(
                    "Number of employees: "
                            + departmentWithEmployees.getEmployees().size()
            );
        }
    }
}