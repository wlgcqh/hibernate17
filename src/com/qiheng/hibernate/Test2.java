package com.qiheng.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Test2 {
	public static SessionFactory sessionFactory;

	static {
		try {
			Configuration cfg = new Configuration().configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(cfg.getProperties()).buildServiceRegistry();
			sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			// HQL按对象查询
			// Student student = (Student) session.get(Student.class,
			// "402880e54a75b761014a75b762b90000");
			// Query query =
			// session.createQuery("from Course c where c.student=:student and c.credit>2");
			// query.setEntity("student", student);
			//
			// List<Course> list = query.list();
			// for(Course l:list){
			// System.out.println(l.getCourse_name());
			// }

			
//			QBC查询
			Criteria criteria = session.createCriteria(Course.class).add(
					Restrictions.like("course_name", "%h")).addOrder(Order.asc("credit"));
			List<Course> list = criteria.list();
			for (Course l : list) {
				System.out.println(l.getCourse_name());
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}

		} finally {
			session.close();
		}

	}
}
