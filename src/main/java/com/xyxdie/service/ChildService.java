package com.xyxdie.service;

import java.util.List;

import com.xyxdie.model.Child;
import com.xyxdie.vo.ChildJsonBean;

public interface ChildService {

    List<Child> findChildsByUserId(int id);

    Child findChildById(int id);

    List<ChildJsonBean> findAllChild(int pid);

    int saveChild(Child child);

    void deleteChild(Child child);

    void deleteChildById(int id);

    Long findChildCount();

    String getDate();

    List<ChildJsonBean> findChildByPage(int pageNo,int pageSize );

	Long isGbook(int pid, int userid);
}
