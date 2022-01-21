package com.bootdo.common.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class FileProperties {

    @Value("${fdfs.fileUrlPrefix}")
    private String fileUrlPrefix;

}