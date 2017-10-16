package com.xyxdie.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyxdie.dao.ChildDao;
import com.xyxdie.model.Child;
import com.xyxdie.service.ChildService;
import com.xyxdie.vo.ChildJsonBean;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ChildServiceImp implements ChildService{

    @Autowired
    private ChildDao childDao;

    public List<Child> findChildsByUserId(int id){
        return childDao.findChildsByUserId(id);
    }

    public Child findChildById(int id){
        return childDao.findChildById(id);
    }

    public List<ChildJsonBean> findAllChild(int pid){
        return childDao.findAllChild(pid);
    }

    public void saveChild(Child Child){
    	childDao.saveChild(Child);
    }

    public void deleteChild(Child Child){
    	childDao.deleteChild(Child);
    }

    public void deleteChildById(int id){
        deleteChild(findChildById(id));
    }

    public Long findChildCount(){
        return childDao.findChildCount();
    }

    public List<ChildJsonBean> findChildByPage(int pageNo,int pageSize ){
        return childDao.findChildByPage(pageNo, pageSize);
    }

    public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String dataString = sdf.format(now);
        return dataString;
    }

	@Override
	public Long isGbook(int pid, int userid) {
        return childDao.isGbook(pid, userid);
	}
}
