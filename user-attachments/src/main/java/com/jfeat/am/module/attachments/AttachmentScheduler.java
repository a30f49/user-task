package com.jfeat.am.module.attachments;

import com.jfeat.am.module.attachments.services.gen.persistence.dao.AttachmentsMapper;
import com.jfeat.am.module.attachments.services.gen.persistence.model.Attachments;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ygg
 * @date 15:34
 */
@Component
@EnableScheduling
public class AttachmentScheduler {
    @Resource
    private AttachmentsMapper attachmentsMapper;

    /**
     * 每月15号3：00执行,清除没有引用的附件
     */
//    @Scheduled(cron = "0 00 03 15 * ?")
//    public void clearAttachments() {
//        List<String> files = new ArrayList<>();
//        this.getAllFileName(amProperties.getFileUploadPath(),files);
//        Integer count;
//        for(String file : files){
//            count = attachmentsMapper.selectCount(new EntityWrapper<Attachments>().eq("url",file));
//            if(count==null || (count!=null && count.compareTo(0)<=0)){
//                FileUtils.deleteQuietly(new File(file));
//            }
//        }
//
//        //获取没有引用的附件
//        /*List<Attachments> attachments = attachmentsMapper.selectList(new EntityWrapper<Attachments>().isNull("table_name").isNull("reference_id"));
//        for (Attachments attachment : attachments){
//            String url = attachment.getUrl();
//            String filePath = amProperties.getFileUploadPath()+url.substring(url.indexOf("/static") + 8);;
//            FileUtils.deleteQuietly(new File(filePath));
//        }*/
//    }

    /**
     * 获取某个文件夹下所有文件，并处理成url形式
     * @param path
     * @param fileNameList
     */
//    public void getAllFileName(String path, List<String> fileNameList) {
//        File file = new File(path);
//        File[] tempList = file.listFiles();
//
//        for (int i = 0; i < tempList.length; i++) {
//            if (tempList[i].isFile()) {
//                //System.out.println("文     件：" + tempList[i]);
//                //fileNameList.add(tempList[i].toString());
//                fileNameList.add(tempList[i].getAbsolutePath().replace(amProperties.getFileUploadPath(),amProperties.getFileHost()+File.separator));
//            }
//            if (tempList[i].isDirectory()) {
//                //System.out.println("文件夹：" + tempList[i]);
//                getAllFileName(tempList[i].getAbsolutePath(),fileNameList);
//            }
//        }
//    }

}
