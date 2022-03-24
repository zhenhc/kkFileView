package cn.keking.web;

import cn.hutool.http.HttpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;


import java.io.*;


public class ControllerTest {

    /**
     * 文件复制，上传
     */
    @Test
    public void copyTest(){
        long l = System.currentTimeMillis();
        File file = new File("F:\\MusicMV\\34+35.mp4");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream("F:\\MusicMV\\34+35(copy).mp4");
            //初始化缓存大小为1M,文件有多少M，大概就需要循环几次
            byte[] bytes = new byte[1024*1024];
            int i = 0;
            while (inputStream.read(bytes)!=-1){
                i++;
                outputStream.write(bytes);
            }
            System.out.println("循环读写次数："+i);
            //StreamUtils.copy(inputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long l1 = System.currentTimeMillis();
        System.out.println("总共花费时间："+(l1-l)/1000f+" s");
    }

    @Test
    public void test1(){
        File file = new File("F:\\tupian");
        File[] files = file.listFiles();
        for (File file1:files){
            System.out.println(file1.getPath());
        }
    }

    /**
     * 文件上传接口测试
     */
    @Test
    public void fileUploadTest(){
        File directory = new File("F:\\tupian");
        File[] files = directory.listFiles();
        for (File file:files){
            System.out.println(file.getPath());
            String body = HttpRequest.post("http://localhost:8012/fileUpload")
                    .form("file",file)
                    .execute()
                    .body();
            System.out.println(body);
        }
    }

    /**
     * 删除文件接口测试
     */
    @Test
    public void deleteFileTest(){
        File directory = new File("F:\\tupian");
        for (File file : directory.listFiles()) {
            System.out.println(file.getName());
            String body = HttpRequest.get("http://localhost:8012/deleteFile")
                    .form("fileName",file.getName())
                    .execute()
                    .body();
            System.out.println(body);
        }

    }

    @Test
    public void listFilesTest(){
        String body = HttpRequest.get("http://localhost:8012/listFiles")
                .execute()
                .body();
        System.out.println(body);
    }
}
