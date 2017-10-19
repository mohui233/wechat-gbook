package com.xyxdie.dao.imp;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import com.xyxdie.dao.UserDao;
import com.xyxdie.model.User;
import java.util.List;

@Repository("UserDao")
public class UserDaoImp extends BaseDaoImp<User>  implements UserDao {

	public User findById(int id){
		return get(User.class, id);

	}

	public List<User> findByName(String name){
		String hql = "from User where name =?";
		return find(hql, name);

	}

	public void saveUser(User user){
		getHibernateTemplate().save(user);
	}

	public void updateUser(User user){
		getHibernateTemplate().update(user);
	}

	public void deleteUser(User user){
		getHibernateTemplate().delete(user);
	}

	public Long findUserCount(){
		String hql = "select count(*) from User as user";
		return (Long)getHibernateTemplate().find(hql).listIterator().next();
	}

	public Long findUserCount(String str) {
		String hql = "select count(*) from User as a where a.passwd = '"+str+"'";
		Long a = (Long)getHibernateTemplate().find(hql).listIterator().next();
		return a;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUser(){
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		return (List<User>) getHibernateTemplate().findByCriteria(criteria);
	}

	public List<User> findUserByPage(int pageNo, int pageSize){
		String hql = "from User";
		return findByPage(hql, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserByPage(String str) {
		String hql = "from User as a where a.passwd = '"+str+"'";
        List<User> list = (List<User>) getHibernateTemplate().find(hql);
        return list;
	}

}
