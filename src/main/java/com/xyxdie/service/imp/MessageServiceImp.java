package com.xyxdie.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyxdie.dao.MessageDao;
import com.xyxdie.model.Message;
import com.xyxdie.service.MessageService;
import com.xyxdie.vo.MessageJsonBean;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MessageServiceImp implements MessageService{

    @Autowired
    private MessageDao messageDao;

    public List<Message> findMessagesByUserId(int id){
        return messageDao.findMessagesByUserId(id);
    }

    public Message findMessageById(int id){
        return messageDao.findMessageById(id);
    }

    public List<MessageJsonBean> findAllMessage(){
        return messageDao.findAllMessage();
    }

    public void saveMessage(Message message){
        messageDao.saveMessage(message);
    }

    public void deleteMessage(Message message){
        messageDao.deleteMessage(message);
    }

    public void deleteMessageById(int id){
        deleteMessage(findMessageById(id));
    }

    public Long findMessageCount(){
        return messageDao.findMessageCount();
    }
    
    public Long findMsingleCount(int userid){
        return messageDao.findMsingleCount(userid);
    }

    public List<MessageJsonBean> findMessageByPage(int pageNo,int pageSize ){
        return messageDao.findMessageByPage(pageNo, pageSize);
    }

	public List<MessageJsonBean> findMessageBySingle(int pageNo, int pageSize, int userid) {
        return messageDao.findMessageBySingle(pageNo, pageSize, userid);
	}
	
    public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String dataString = sdf.format(now);
        return dataString;
    }

}
