package com.text.schedule;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

@Slf4j
public class FileUtil implements Runnable{

    private String filePath1;
    private String filePath2;
    private String fileName;
    private String mac;
    private String win;

    public FileUtil(String filePath1,String filePath2, String fileName,String mac,String win) {
        this.filePath1 = filePath1;
        this.filePath2 = filePath2;
        this.fileName = fileName;
        this.mac = mac;
        this.win = win;
    }

    @Override
    public void run() {
        SimpleDateFormat tt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH_mm");
        SimpleDateFormat mm = new SimpleDateFormat("MM");
        SimpleDateFormat dd = new SimpleDateFormat("MM.dd");
        Date date = new Date();
        String copyFileName = fileName + sdf.format(date)+".xlsx";
        String month = mm.format(date);
        String day = dd.format(date);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String copyPath = "";
        System.getProperty("os.name");
        if(System.getProperty("os.name").contains("Mac")){
            copyPath = filePath2+year+mac+month+mac+day+mac;
        }else{
            copyPath = filePath1+year+win+month+win+day+win;
        }

        String path = copyPath;
        File file = new File(path);
        if(!file.exists()){
            log.info("文件不存在,系统停止：{}",sdf.format(new Date()));
            System.exit(0);
        }

        //列出该目录下所有文件和文件夹
        File[] files = file.listFiles();
        //按照目录中文件最后修改日期实现倒序排序
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return (int)(file2.lastModified()-file1.lastModified());
            }
        });
        //取最新修改的文件，get文件名
        String name = files[0].getName();
        try {
            copyFileUtil(new File(path+name),copyPath,copyFileName);
            log.info("文件复制时间：{}",tt.format(date));
        } catch (IOException e) {
            log.error("文件复制异常,异常时间：{}",tt.format(date));
            e.printStackTrace();
        }
    }

    public static void copyFileUtil(File file, String copyPath,String fileName )throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(!file.exists()){
            log.info("文件不存在,系统停止：{}",sdf.format(new Date()));
            System.exit(0);
        }
        //创建目的地文件夹
        File destfile = new File(copyPath);
        if(!destfile.exists()){
            destfile.mkdir();
        }
        //source是文件，则用字节输入输出流复制文件
        if(file.isFile()){
            FileInputStream fis = new FileInputStream(file);
            //创建新的文件，保存复制内容，文件名称与源文件名称一致
            File dfile = new File(copyPath+"//"+fileName);
            if(!dfile.exists()){
                dfile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(dfile);
            // 读写数据
            // 定义数组
            byte[] b = new byte[1024];
            // 定义长度
            int len;
            // 循环读取
            while ((len = fis.read(b))!=-1) {
                // 写出数据
                fos.write(b, 0 , len);
            }
            //关闭资源
            fos.close();
            fis.close();

        }
    }
}
