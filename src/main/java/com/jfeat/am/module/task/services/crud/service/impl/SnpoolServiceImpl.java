package com.jfeat.am.module.task.services.crud.service.impl;

import com.jfeat.am.module.task.services.crud.service.SnpoolService;
import org.springframework.stereotype.Service;

@Service
public class SnpoolServiceImpl implements SnpoolService {

    @Override
    public String getSerial(String prefix){
        StringBuffer stringBuffer = new StringBuffer(prefix);
        for(int i = 1 ;i<10;i++){
            Double db  = Math.random()*10;
            stringBuffer.append(db.intValue());
        }
        return stringBuffer.toString();
    }



}
