package com.ajie.controller;

import com.ajie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传和下载
 *
 * @author:hyj
 * @date:2022/10/2
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${ajie.imgPath}")
    private String basePath;


    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file是一个临时文件，需要转存到指定位置，否则本次请求后临时文件会删除
        log.info(file.toString());

        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();
        //提取出文件名称的后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        log.info(suffix);

        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID().toString() + suffix;

        //创建文件目录对象
        File dir = new File(basePath);

        //判断文件目录是否存在
        if (!dir.exists()) {
            //目录不存在，则创建该目录
            dir.mkdirs();
        }

        try {
            //将文件转存到指定目录
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回文件名称，转存数据库
        return R.success(fileName);
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            //获取输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //获取输出流，通过输出流将文件写回浏览器,在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();

            //设置图片文件
            response.setContentType("image/jpeg");

            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);

                outputStream.flush();
            }

            outputStream.close();
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
