package fe.masv.dao;

import fe.masv.pojo.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDAO {

    private final EntityManagerFactory emf;

    public EmployeeDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // TODO 0.3 - CREATE
    public void save(Employee e) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            // Entity e dang o trang thai New / Transient
// persist() dua entity vao Persistence Context
// Sau persist(), entity chuyen sang Managed
            em.persist(e);

            em.getTransaction().commit();

        } catch (Exception ex) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw ex;

        } finally {
            em.close();
        }
    }

    // TODO 0.4 - READ BY ID
    public Employee findById(Long id) {

        EntityManager em = emf.createEntityManager();

        try {
            // find() tim entity trong Persistence Context
// Neu tim thay, entity o trang thai Managed
            return em.find(Employee.class, id);

        } finally {
            em.close();
        }
    }

    // TODO 0.4 - READ ALL
    public List<Employee> findAll() {

        EntityManager em = emf.createEntityManager();

        try {
            String jpql = "SELECT e FROM Employee e";

            return em.createQuery(jpql, Employee.class)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // TODO 0.5 - FIND BY EMAIL
    public Employee findByEmail(String email) {

        EntityManager em = emf.createEntityManager();

        try {
            String jpql =
                    "SELECT e FROM Employee e WHERE e.email = :email";

            return em.createQuery(jpql, Employee.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

        } finally {
            em.close();
        }
    }

    // TODO 0.5 - FIND BY SALARY
    public List<Employee> findBySalaryGreaterThan(BigDecimal salary) {

        EntityManager em = emf.createEntityManager();

        try {
            String jpql =
                    "SELECT e FROM Employee e WHERE e.salary > :salary";

            return em.createQuery(jpql, Employee.class)
                    .setParameter("salary", salary)
                    .getResultList();

        } finally {
            em.close();
        }
    }

    // TODO 0.6 - UPDATE
    public void update(Employee e) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Entity truyen vao co the dang Detached
// merge() dua du lieu cua entity vao Persistence Context
// merge() tra ve entity o trang thai Managed
            e = em.merge(e);

            em.getTransaction().commit();

        } catch (Exception ex) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw ex;

        } finally {
            em.close();
        }
    }

    // TODO 0.7 - DELETE
    public void delete(Long id) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
// find() dua entity vao Persistence Context
// Entity o trang thai Managed
            Employee e = em.find(Employee.class, id);

            if (e != null) {
                // remove() chuyen entity sang trang thai Removed
                em.remove(e);
            }

            em.getTransaction().commit();

        } catch (Exception ex) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw ex;

        } finally {
            em.close();
        }
    }
}
