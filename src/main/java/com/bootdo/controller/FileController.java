package com.bootdo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.common.aspect.MedCaseAnn;
import com.bootdo.common.constant.ResponseCodeEnum;
import com.bootdo.common.domain.entity.MedCase;
import com.bootdo.common.domain.entity.Response;
import com.bootdo.common.properties.FileProperties;
import com.bootdo.common.utils.FastDfsUtils;
import com.bootdo.common.utils.FileUtil;
import com.bootdo.common.utils.ResponseUtil;
import com.bootdo.service.MedCaseService;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author rory
 * @date 2022/1/4
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController extends BaseController{

    @Autowired
    private FastDfsUtils fastDfsUtils;

    @Autowired
    private FileProperties fileProperties;

    @Autowired
    private MedCaseService medCaseService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/medCase/upload")
    public Response uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            //获取文件hash值
            String hashCode = FileUtil.getMd5Hash(file.getInputStream());
            parseXmlAndSave(file.getInputStream());
            String fileUrl = fastDfsUtils.uploadFile(file);
            redisTemplate.opsForValue().set("medCase_hashCode_"+getUserId(), hashCode);
            redisTemplate.opsForValue().set("medCase_fileUrl_"+getUserId(), fileUrl);
            log.info("文件上传地址 : {}, hashCode : {}", fileUrl, hashCode);
            return ResponseUtil.getSuccess(fileProperties.getFileUrlPrefix() + fileUrl);
        } catch (Exception e) {
            log.error("文件上传异常:", e);
        }
        return ResponseUtil.getFail(ResponseCodeEnum.FAIL_FILE_UPLOAD_ERROR);
    }

    private void parseXmlAndSave(InputStream file) throws Exception {
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        List<MedCase> list = new ArrayList<>();
        while (iterator.hasNext()){
            Element stu = (Element) iterator.next();
            if ("QueryResult".equals(stu.getName())) {
                Iterator iterator1 = stu.elementIterator();
                MedCase medCase = new MedCase();
                while (iterator1.hasNext()){
                    Element stuChild = (Element) iterator1.next();
                    for (Field field : MedCase.class.getDeclaredFields()) {
                        MedCaseAnn annotation = field.getAnnotation(MedCaseAnn.class);
                        if (annotation != null && stuChild.getName().equals(annotation.value())) {
                            field.setAccessible(true);
                            field.set(medCase, stuChild.getStringValue());
                        }
                    }
                }
                medCase.setUserId(getUserId());
                medCase.setCreateDate(new Date());
                list.add(medCase);
            }
        }
        medCaseService.remove(new QueryWrapper<MedCase>().eq("user_id", getUserId()));
        medCaseService.saveBatch(list);
    }

    @PostMapping("/medCase/download")
    public Response download() throws Exception {
        String fileUrl = redisTemplate.opsForValue().get("medCase_fileUrl_"+getUserId()) + "";
        return ResponseUtil.getSuccess(fileProperties.getFileUrlPrefix() + fileUrl);
    }

}
