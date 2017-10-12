package com.xyxdie.service;

import java.util.List;

import com.xyxdie.model.Message;
import com.xyxdie.vo.MessageJsonBean;

public interface MessageService {

    List<Message> findMessagesByUserId(int id);

    Message findMessageById(int id);

    List<MessageJsonBean> findAllMessage();

    void saveMessage(Message message);

    void deleteMessage(Message message);

    void deleteMessageById(int id);

    Long findMessageCount();

    String getDate();

    List<MessageJsonBean> findMessageByPage(int pageNo,int pageSize );
}
