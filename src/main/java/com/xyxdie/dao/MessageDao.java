package com.xyxdie.dao;

import java.util.List;

import com.xyxdie.model.Message;
import com.xyxdie.vo.MessageJsonBean;

public interface MessageDao {
    List<Message> findMessagesByUserId(int id);

    Message findMessageById(int id);

    List<MessageJsonBean> findAllMessage();

    int saveMessage(Message message);

    void deleteMessage(Message message);

    Long findMessageCount();

    Long findMsingleCount(int userid);
    
    List<MessageJsonBean> findMessageByPage(final int pageNo,final int pageSize );

	List<MessageJsonBean> findMessageBySingle(final int pageNo, final int pageSize, int userid);

	List<MessageJsonBean> findMessageJsonBeanById(int id);

}
