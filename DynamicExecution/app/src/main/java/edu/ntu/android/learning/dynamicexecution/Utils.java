package edu.ntu.android.learning.dynamicexecution;

import android.content.Context;
import android.graphics.Path;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Map;
import javassist.*;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

/**
 * Created by pillar on 4/1/2016.
 */
public class Utils {

    public static void changeCode(){
        try {
            ClassPool cl = ClassPool.getDefault();
            CtClass ctc = cl.get("Task");
            CtConstructor constructor = ctc.getConstructors()[0];
            constructor.insertAfter("android.util.Log.d(\"++++++(^_^)++++++\", \"Hey, I'm here in the constructor\");");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String executeTask(Context context, String urlString, Map<String, String> features) {
        URL url = null;
        HttpURLConnection urlConn = null;
        try {
            url = new URL(urlString + "?feature="/* + featureId*/);
            urlConn = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConn.getInputStream());
            byte[] buffer = new byte[65536];
            FileOutputStream fos = context.openFileOutput("a.dex", context.MODE_PRIVATE);
            int len;
            while ((len = in.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            in.close();

            /*DexFile dx = DexFile.loadDex(context.getFilesDir().getAbsolutePath()+"/a.dex", File.createTempFile("opt", "dex", context.getCacheDir()).getPath(), 0);
            for (Enumeration<String> classNames = dx.entries(); classNames.hasMoreElements();){
                String className = classNames.nextElement();
                Log.d("+++++++++++", String.format("class: %s", className));
            }*/
            DexClassLoader loader = new DexClassLoader(context.getFilesDir().getAbsolutePath()+"/a.dex", context.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
            changeCode();
            Class clz = loader.loadClass("Task");
            Object obj = clz.newInstance();
            Method mtd = clz.getDeclaredMethod("executeTask", new Class[]{android.content.Context.class});
            Object message = mtd.invoke(obj, context);
            return message.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }

    public static Class<?> queryServer(Context context, String urlString, int featureId) {
        URL url = null;
        HttpURLConnection urlConn = null;
        try {
            Log.d("++++++++++++++","Start to download the jar file");
            url = new URL(urlString + "?feature=" + featureId);
            urlConn = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConn.getInputStream());
            byte[] buffer = new byte[65536];
            FileOutputStream fos = context.openFileOutput("a.dex", context.MODE_PRIVATE);
            int len;
            while ((len = in.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            in.close();
            Log.d("+++++++++", "Store the jar into a.jar");

            DexFile dx = DexFile.loadDex(context.getFilesDir().getAbsolutePath()+"/a.dex", File.createTempFile("opt", "dex", context.getCacheDir()).getPath(), 0);
            for (Enumeration<String> classNames = dx.entries(); classNames.hasMoreElements();){
                String className = classNames.nextElement();
                Log.d("+++++++++++", String.format("class: %s", className));
            }
            DexClassLoader loader = new DexClassLoader(context.getFilesDir().getAbsolutePath()+"/a.dex", context.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
            Class clz = loader.loadClass("Task");
            Object obj = clz.newInstance();
            Method mtd = clz.getDeclaredMethod("executeTask", new Class[]{android.content.Context.class});
            Object message = mtd.invoke(obj, context);
            Log.d("++++++++++", obj.toString());
            Log.d("++++++++++", message.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }

     public static void queryServer(String urlString){
         try{
             URLClassLoader loader = new URLClassLoader(new URL[]{new URL(urlString)});
             Class clazz = loader.loadClass("Task");
             Object obj = clazz.newInstance();

             Log.d("+++++++++++", obj.toString());
         }catch (Exception e){
            e.printStackTrace();
         }
     }


}
