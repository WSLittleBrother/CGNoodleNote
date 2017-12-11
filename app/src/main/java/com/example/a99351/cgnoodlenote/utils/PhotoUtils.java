package com.example.a99351.cgnoodlenote.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.example.a99351.cgnoodlenote.model.UserModel;

import java.io.File;

/**
 * Created by 99351 on 2017/11/2.
 */

public class PhotoUtils {
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
}
