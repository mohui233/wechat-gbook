package com.xyxdie.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import com.xyxdie.model.Message;
import com.xyxdie.model.User;
import java.util.regex.Pattern;

@Component
public class Validate {

    //公共验证规则
    public void commonValidate(User user, Errors errors) {
        //判断用户名和邮箱是否为空
        if(user.getName() != ""){
            //判断email格式是否则正确
            String pattern = "(.*)@(.).(.*)";
            @SuppressWarnings("unused")
			Pattern r = Pattern.compile(pattern);
            //判断密码格式是否正确
            if(user.getPasswd().length() < 6)
                errors.rejectValue("passwd", "userpasswd.valid");
        }else{
            ValidationUtils.rejectIfEmpty(errors, "name", "username.required");
        }
    }

    //注册验证
    public void registValidate(User user, Errors errors){
        commonValidate(user, errors);
        //判断name格式是否正确
        if(user.getName().length() == 0 ){
            errors.rejectValue("name", "username.valid");
        }
    }

    //留言验证
    public void messageValidate(Message message, Errors errors){
        if(message.getMessage() != ""){
            if(message.getMessage().length() < 255){

            }else{
                errors.rejectValue("message", "message.required");
            }
        }else{
            ValidationUtils.rejectIfEmpty(errors, "message", "message.required");
        }
    }
}
