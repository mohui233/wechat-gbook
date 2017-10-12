package com.xyxdie.dao;

import java.util.List;

import com.xyxdie.model.Message;
import com.xyxdie.vo.MessageJsonBean;

public interface MessageDao {
    List<Message> findMessagesByUserId(int id);

    Message findMessageById(int id);

    List<MessageJsonBean> findAllMessage();

    void saveMessage(Message message);

    void deleteMessage(Message message);

    Long findMessageCount();

    List<MessageJsonBean> findMessageByPage(final int pageNo,final int pageSize );
}
