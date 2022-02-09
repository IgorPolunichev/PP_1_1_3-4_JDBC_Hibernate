package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.action.internal.EntityDeleteAction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            sessionFactory = Util.connectHibernate();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS MyUserTable";
        try {
            session = Util.connectHibernate().openSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(dropTable).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Throwable e) {
            if (transaction.getStatus() == TransactionStatus.ACTIVE
                    || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            session = Util.connectHibernate().openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Throwable e) {
            if (transaction.getStatus() == TransactionStatus.ACTIVE
                    || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        System.out.printf("User с именем - %s добавлен в базу данных\n", user.getName());


    }

    @Override
    public void removeUserById(long id) {
        try {
            session = Util.connectHibernate().openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Throwable e) {
            if (transaction.getStatus() == TransactionStatus.ACTIVE
                    || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) Util.connectHibernate().openSession().createQuery("from User").list();
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = Util.connectHibernate().openSession();
            transaction = session.beginTransaction();
            for (User user : getAllUsers()) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Throwable e) {
            if (transaction.getStatus() == TransactionStatus.ACTIVE
                    || transaction.getStatus() == TransactionStatus.MARKED_ROLLBACK) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}
