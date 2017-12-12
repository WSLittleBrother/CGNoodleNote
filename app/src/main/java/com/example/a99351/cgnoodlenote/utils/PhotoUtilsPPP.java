package com.example.a99351.cgnoodlenote.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.example.a99351.cgnoodlenote.model.UserModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 99351 on 2017/11/2.
 */

public class PhotoUtilsPPP {
    /**
     * 相册
     */
    public static void openPhotoAlbum(Activity activity, int requestCode){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 拍照
     * */
    public static String takePhoto(Activity activity, int requestCode) {
        String fileName = "";
        if (SDCardUtils.isSDCardEnable()) {
            fileName = SDCardUtils.getSDCardPath()+"ws/myapp/"+UserModel.getUser().getUsername()
                     +"/comment/"+ System.currentTimeMillis()+".jpg";
        }

        File file = new File(fileName);
        Uri outputFileUri = null;
        Intent intent = new Intent();
        outputFileUri = Uri.fromFile(file);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            outputFileUri = FileProvider.getUriForFile(activity,"com.example.a99351.cgnoodlenote.fileprovider", file);
        }

        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        activity.startActivityForResult(intent, requestCode);
        return outputFileUri.getPath();
    }

    /**
     * 图片裁剪
     *
     * @param activity    当前activity
     * @param orgUri      剪裁原图的Uri
     * @param desUri      剪裁后的图片的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     * @param requestCode 剪裁图片的请求码
     */
    public static void cropImageUri(Activity activity, Uri orgUri, Uri desUri, int aspectX, int aspectY, int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
    }
    /**
     * 复制文件
     * @param path
     * @return
     */
    protected String copyFile(String path){
        //先得到后缀
        String suffix ="";
        if (path.lastIndexOf(".") ==-1){
            suffix = "";
        }else{
            suffix=path.substring(path.lastIndexOf("."));
        }
        String fileName = "";
        if (SDCardUtils.isSDCardEnable()) {
            fileName = SDCardUtils.getSDCardPath()+"ws/myapp/"+UserModel.getUser().getUsername()
                    +"/comment/"+ System.currentTimeMillis()+".jpg";
        }

        FileInputStream in=null;
        FileOutputStream out=null;
        byte[] b=new byte[1024];
        try {//输入流的来源
            in=new FileInputStream(path);
            //输出流的去向
            out=new FileOutputStream(fileName);
            while(true)
            {//进行输入操作
                int num=in.read(b, 0, b.length);
                if(num==-1)
                {break;}
                //进行输出操作
                out.write(b,0,b.length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try {
                //关闭流
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return fileName;
    }

}
