package net.javaguides.springmvc.dao;

import net.javaguides.springmvc.entity.Customer;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> getCustomers() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> root = cq.from(Customer.class);
        cq.select(root);
        Query query = session.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public void deleteCustomer(int id) {
        Session session = sessionFactory.getCurrentSession();
        Customer book = session.byId(Customer.class).load(id);
        session.delete(book);
    }


    @Override
    public void saveCustomer(Customer theCustomer) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(theCustomer);
    }

    @Override
    public Customer getCustomer(int theId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Customer theCustomer = currentSession.get(Customer.class, theId);
        return theCustomer;
    }


    @Override
    public void merge(int theId) {
        Session session1 = HibernateUtils.getSession();

        Transaction transaction1 = session1.beginTransaction();

        Customer theCustomer1 = (Customer) session1.get(Customer.class, theId);
        transaction1.commit();
        session1.clear();
        session1.close();

        Session session2 = HibernateUtils.getSession();
        Transaction transaction2 = session2.beginTransaction();
        session2.get(Customer.class, theId);
        session2.merge(theCustomer1);
        transaction2.commit();
        session2.clear();
        session2.close();

    }

    @Override
    public List<Customer> getCustomersById(int id) {
        String sql = "select * from customer  where id >= ? limit 20";
        Session currentSession = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = currentSession.createSQLQuery(sql);
        sqlQuery.setParameter(0, id);
        sqlQuery.addEntity(Customer.class);
        List<Customer> customerList = (List<Customer>) sqlQuery.list();
        return customerList;
    }

    @Override
    public int getTotalNumber() {
        String sql = "select COUNT(id) from customer ";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        Object objResult = sqlQuery.uniqueResult();
        if (objResult != null) {
            return Integer.valueOf(objResult.toString());
        }
        return 0;
    }

    /**
     * 分页查询数据
     *
     * @param startId 起始数据ID参数
     * @param page    查询页码，从1开始
     * @param size    每页查询数量
     * @return 查询的数据列表
     */
    @Override
    @Transactional
    public List<Customer> pageCustomers(int startId, int page, int size) {
        //获取hibernate session
        Session session = sessionFactory.getCurrentSession();
        //创建分页查询
        org.hibernate.query.Query<Customer> customerQuery = session.createQuery("FROM Customer WHERE id>=:id ORDER BY id", Customer.class)
                .setParameter("id", startId)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        //执行查询语句
        return customerQuery.list();
    }

}