package com.jfeat.am.module.message.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jfeat.am.module.message.services.domain.dao.QueryMessageDao;
import com.jfeat.am.module.message.services.domain.model.MessageRecord;
import com.jfeat.am.module.message.services.domain.service.MessageService;
import com.jfeat.am.module.message.services.gen.persistence.model.Message;
import com.jfeat.crud.base.tips.SuccessTip;
import com.jfeat.crud.base.tips.Tip;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * <p>
 * api
 * </p>
 *
 * @author Code Generator
 * @since 2019-04-15
 */
@RestController

@Api("Message")
@RequestMapping("/api/message/messages")
public class MessageEndpoint {
    @Resource
    MessageService messageService;
    @Resource
    QueryMessageDao queryMessageDao;

    //@BusinessLog(name = "Message", value = "查看 Message")
    @GetMapping("/{userId}")
    @ApiOperation(value = "查看 Message", response = Message.class)
    public Tip getMessage(@PathVariable Long userId,
                          Page<MessageRecord> page,
                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page.setRecords(queryMessageDao.getMessagePage(page, userId));
        return SuccessTip.create(page);
    }
}
