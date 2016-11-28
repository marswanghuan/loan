package com.cy.ddsc.util;

import java.io.*;

/**
 * Created by xqc on 2016/5/7.
 */
public class FileUtil {
    public static boolean upFile(InputStream is,String fileName,String filePath){
    	boolean bl = false;
        FileOutputStream fos = null;

        BufferedOutputStream bos = null;

        BufferedInputStream bis = null;

        File file = new File(filePath);

        if(!file.exists()){

            file.mkdirs();

        }

        File f = new File(filePath+fileName);

        try {

            bis = new BufferedInputStream(is);

            fos = new FileOutputStream(f);

            bos = new BufferedOutputStream(fos);

            byte[] bt = new byte[4096];

            int len;

            while((len = bis.read(bt))>0){

                bos.write(bt, 0, len);

            }
            bl = true;

        } catch (Exception e) {

            e.printStackTrace();

        }finally {

            try {

                if(null != bos){

                    bos.close();

                    bos = null;}

                if(null != fos){

                    fos.close();

                    fos= null;

                }

                if(null != is){

                    is.close();

                    is=null;

                }

                if (null != bis) {

                    bis.close();

                    bis = null;

                }

            } catch (Exception e) {

                e.printStackTrace();

            }

        }
        return bl;
    }

    
    /** 
     * 删除单个文件 
     * @param   sPath    被删除文件的文件名 
     * @return 单个文件删除成功返回true，否则返回false 
     */  
    public static  boolean deleteFile(String sPath) {  
        boolean flag = false;  
        File file = new File(sPath);  
        // 路径为文件且不为空则进行删除  
        if (file.isFile() && file.exists()) {  
            file.delete();  
            flag = true;  
        }  
        return flag;  
    } 
}

