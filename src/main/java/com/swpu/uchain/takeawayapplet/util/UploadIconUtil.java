package com.swpu.uchain.takeawayapplet.util;

import com.swpu.uchain.takeawayapplet.config.UploadIconConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName UploadIconUtil
 * @Author hobo
 * @Date 19-3-18 下午8:02
 * @Description
 **/
@Service
public class UploadIconUtil {

    @Autowired
    private UploadIconConfig uploadIconConfig;

    /**
     * 获得文件上传路径
     *
     * @param file
     * @return
     */
    public String getUploadFilePath(MultipartFile file) {
        if (file.isEmpty()) {
            throw new NullPointerException("上传图片为空");
        }
        //设置图片上传路径
        String filePath = uploadIconConfig.getUploadDir();
        System.out.println(filePath);

        //获取图片后缀名
        String suffix = file.getOriginalFilename();
        String prefix = suffix.substring(suffix.lastIndexOf(".") + 1);

        //避免文件重名 文件名为时间+随机数
        String fileName = RandomUtil.creatRandom() + "."+prefix;

        //创建文件路径
        File dest = new File(filePath + "/"+fileName);

        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
            String fileNameNew = dest.getName();
            System.out.println(fileNameNew);
            return fileNameNew;
        } catch (IOException e) {
            e.getMessage();
        }
        return "error";
    }


}
