package com.itelasoft.pso.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.itelasoft.pso.beans.User;

public class UserDao extends GenericDao<User, Long> implements IUserDao {

	// public User getUserById(long userId) {
	//
	// Session session= this.sessionFactory.getCurrentSession();
	// //session.getTransaction().begin();
	// List<User> list = session
	// .createQuery("from com.itelasoft.pso.beans.User user where user.userId=?")
	// .setParameter(0, userId).list();
	// ////session.getTransaction().commit();
	// if(list != null && !list.isEmpty()){
	// return list.iterator().next();
	// }
	// return null;
	// }

	public User getUserByUserName(String userName) {
		Session session = this.sessionFactory.getCurrentSession();
		User user = (User) session.createCriteria(User.class)
				.add(Restrictions.eq("userName", userName)).uniqueResult();
		return user;
	}

	public List<User> listAll() {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		@SuppressWarnings("unchecked")
		List<User> users = criteria.list();
		return users;
	}

	public List<User> listByName(String name) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from com.itelasoft.pso.beans.User as user where user.contact.firstName like ? or user.contact.lastName like ? order by user.contact.firstName, user.contact.lastName");
		query.setParameter(0, "%" + name + "%");
		query.setParameter(1, "%" + name + "%");
		@SuppressWarnings("unchecked")
		List<User> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public List<User> listByType(String type) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from User as user where user.userType = ? order by user.contact.firstName, user.contact.lastName");
		query.setParameter(0, type);
		@SuppressWarnings("unchecked")
		List<User> list = query.list();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	public boolean validateUserName(Long userId, String userName) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		if ((userName != null) && (!userName.isEmpty()))
			criteria = criteria.add(Restrictions.eq("userName", userName)
					.ignoreCase());
		if (userId != null)
			criteria.add(Restrictions.ne("id", userId));
		User user = (User) criteria.uniqueResult();
		if (user == null) {
			return true;
		} else {
			return false;
		}
	}

}
