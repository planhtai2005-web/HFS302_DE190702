package fe.masv.dao;

import fe.masv.pojo.Employee;
import fe.masv.pojo.Project;
import fe.masv.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EmployeeDAO {

    // =========================
    // TODO 5.6
    // Assign employee to project
    // =========================

    public void assignEmployeeToProject(Long employeeId, Long projectId) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            if (employee == null) {
                throw new IllegalArgumentException(
                        "Employee not found: " + employeeId
                );
            }

            if (project == null) {
                throw new IllegalArgumentException(
                        "Project not found: " + projectId
                );
            }

            employee.assignToProject(project);

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }

    // =========================
    // TODO 5.11
    // Deactivate employee
    // =========================

    public void deactivateEmployee(Long employeeId) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Employee employee = em.find(Employee.class, employeeId);

            if (employee == null) {
                throw new IllegalArgumentException(
                        "Employee not found: " + employeeId
                );
            }

            // Deactivate employee
            employee.setActive(false);

            // Do NOT remove employee from projects.
            // The employee_project relationship is kept
            // for history.

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;

        } finally {
            em.close();
        }
    }
}