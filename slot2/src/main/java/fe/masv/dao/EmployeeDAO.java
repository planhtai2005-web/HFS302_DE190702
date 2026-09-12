package fe.masv.dao;

import fe.masv.pojo.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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
}
