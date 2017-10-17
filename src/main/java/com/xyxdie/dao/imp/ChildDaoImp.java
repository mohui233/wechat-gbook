package com.xyxdie.dao.imp;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.xyxdie.dao.ChildDao;
import com.xyxdie.model.Child;
import com.xyxdie.vo.ChildJsonBean;

import java.util.List;

@Repository
public class ChildDaoImp extends BaseDaoImp<Child> implements ChildDao {

    @SuppressWarnings("unchecked")
    public List<Child> findChildsByUserId(int id){
        String hql = "from Child m where m.userid = "+ id +
                "Order by m.id desc";
        //return find(hql, id);
        return find(hql);
    }

    @SuppressWarnings("unchecked")
    public Child findChildById(int id){
        return get(Child.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<ChildJsonBean> findAllChild(int pid){
        String hql = "select new com.xyxdie.vo.ChildJsonBean(" +
                "u.id,  m.pid, u.name, m.ip, m.date, m.message, u.imgUrl, u.type) " +
                "from User u, Child m where m.userid = u.id and m.pid = "+ pid + " Order by m.id desc ";
        List<ChildJsonBean> list = (List<ChildJsonBean>) getHibernateTemplate().find(hql);
        return list;
    }

    public Long isGbook(int pid, int userid){
        String hql = "select count(*) from Child as m where m.userid = " + userid + " and m.pid = " + pid;
        return (Long)getHibernateTemplate().find(hql).listIterator().next();
    }
    
    public int saveChild(Child child){
        return save(child);
    }

    public void deleteChild(Child child){
        delete(child);
    }

    public Long findChildCount(){
        String hql = "select count(*) from Child as child";
        return (Long)getHibernateTemplate().find(hql).listIterator().next();
    }

    @SuppressWarnings("unchecked")
    public List<ChildJsonBean> findChildByPage(final int pageNo,final int pageSize ){

        final String hql = "select new com.xyxdie.vo.ChildJsonBean(" +
                "m.id, u.name, m.ip, m.date, m.message, u.imgUrl) " +
                "from User u, Child m where m.userid = u.id Order by m.id desc";

        List<ChildJsonBean> list = (List<ChildJsonBean>) getHibernateTemplate().execute(new HibernateCallback<List<ChildJsonBean>>() {

            @SuppressWarnings("unchecked")
            public List<ChildJsonBean> doInHibernate(Session session) throws HibernateException {
                // TODO Auto-generated method stub
                List<ChildJsonBean> result = session.createQuery(hql).setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
                return result;
            }
        });
        return list;
    }
}
