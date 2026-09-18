package fe.masv;

import fe.masv.pojo.Employee;
import fe.masv.pojo.Gender;
import fe.masv.pojo.Project;
import fe.masv.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // =========================
            // 1. Create 3 Employees
            // =========================

            Employee nv1 = new Employee(
                    "nv1@gmail.com",
                    "Nguyen Van A",
                    Gender.MALE,
                    new BigDecimal("1500"),
                    LocalDate.of(2024, 1, 10)
            );

            Employee nv2 = new Employee(
                    "nv2@gmail.com",
                    "Tran Thi B",
                    Gender.FEMALE,
                    new BigDecimal("1800"),
                    LocalDate.of(2024, 2, 15)
            );

            Employee nv3 = new Employee(
                    "nv3@gmail.com",
                    "Le Van C",
                    Gender.MALE,
                    new BigDecimal("2000"),
                    LocalDate.of(2024, 3, 20)
            );

            nv1.setActive(true);
            nv2.setActive(true);
            nv3.setActive(true);

            // =========================
            // 2. Create 2 Projects
            // =========================

            Project projectA = new Project(
                    "PRJ-A",
                    "Project A",
                    new BigDecimal("50000"),
                    LocalDate.of(2024, 1, 1),
                    null
            );

            Project projectB = new Project(
                    "PRJ-B",
                    "Project B",
                    new BigDecimal("80000"),
                    LocalDate.of(2024, 2, 1),
                    null
            );

            // =========================
            // 3. Assign Employees
            // =========================

            // NV1 -> Project A + B
            nv1.assignToProject(projectA);
            nv1.assignToProject(projectB);

            // NV2 -> Project B
            nv2.assignToProject(projectB);

            // NV3 -> Project A
            nv3.assignToProject(projectA);

            // =========================
            // 4. Save entities
            // =========================

            em.persist(nv1);
            em.persist(nv2);
            em.persist(nv3);

            em.persist(projectA);
            em.persist(projectB);

            transaction.commit();

            // =========================
            // 5. Print projects
            // =========================

            System.out.println("===== EMPLOYEE PROJECTS =====");

            System.out.println(nv1.getFullName() + ":");
            for (Project p : nv1.getProjects()) {
                System.out.println(" - " + p.getProjectName());
            }

            System.out.println(nv2.getFullName() + ":");
            for (Project p : nv2.getProjects()) {
                System.out.println(" - " + p.getProjectName());
            }

            System.out.println(nv3.getFullName() + ":");
            for (Project p : nv3.getProjects()) {
                System.out.println(" - " + p.getProjectName());
            }

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
            JPAUtil.close();
        }
    }
}