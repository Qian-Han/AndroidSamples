package edu.ntu.android.maas.client.clientapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

/**
 * Created by pillar on 4/1/2016.
 */
public class Utils {

    public static final String TAG = Utils.class.getName();

    /**
     * Query the remote server to get the instructions
     * @return
     */
    public static String queryServer(){
        URL url = null;
        HttpURLConnection urlConn = null;
        try{
            url = new URL(Constants.getInstructionUrl());
            urlConn = (HttpURLConnection) url.openConnection();
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            byte[] buffer = new byte[1024];
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }

    /**
     * Get the dynamically loaded class from the remote server
     * @param context
     * @param serviceUrl
     * @return
     */
    public static Class<?> queryServer(Context context, String serviceUrl) {
        URL url = null;
        HttpURLConnection urlConn = null;
        try {
            url = new URL(serviceUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConn.getInputStream());
            byte[] buffer = new byte[65536];
            FileOutputStream fos = context.openFileOutput(Constants.DEX_FILE, context.MODE_PRIVATE);
            int len;
            while ((len = in.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            in.close();

            DexClassLoader loader = new DexClassLoader(context.getFilesDir().getAbsolutePath()+"/" + Constants.DEX_FILE, context.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
            Class clz = loader.loadClass("edu.ntu.android.maas.client.clientapp.Task");

            return clz;
        } catch (Exception e) {
            //Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }
    /**
     * Execute the specific action according to the class and method name
     * @param dexFile
     * @param context
     * @param clazz
     * @param method
     * @param args
     * @return
     */
    public static Object dynamicExecution(String dexFile, Context context, String clazz, String method, Class... args){
        Object message = null;
        try{
            DexClassLoader loader = new DexClassLoader(context.getFilesDir().getAbsolutePath()+"/"+dexFile, context.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
            Class clz = loader.loadClass(clazz);
            Object obj = clz.newInstance();
            Method mtd = clz.getDeclaredMethod(method, args);
            message = mtd.invoke(obj, context);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return message;
    }

    /**
     * Get the instructions of the tasks
     * @param context
     * @return
     */
    public static List<String> getInstructions(Context context){
        List<String> instructions = (List<String>)dynamicExecution(Constants.DEX_FILE, context, "edu.ntu.android.maas.client.clientapp.Task", "getInstructions");
        return instructions;
    }

    /**
     * Operate on specific point cuts
     * @param context
     * @param pointcut
     */
    public static Object operateOn(Context context, String component, String pointcut){
        try{
            DexClassLoader loader = new DexClassLoader(context.getFilesDir().getAbsolutePath()+"/"+Constants.DEX_FILE, context.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
            Class clz = loader.loadClass("edu.ntu.android.maas.client.clientapp.Task");
            Object obj = clz.newInstance();
            Method mtd = clz.getDeclaredMethod("operateOn", new Class[]{Context.class, String.class, String.class});
            return mtd.invoke(obj, context, component, pointcut);
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static BroadcastReceiver getBroadcastReceiver(Context context){
        try{
            DexClassLoader loader = new DexClassLoader(context.getFilesDir().getAbsolutePath()+"/"+Constants.DEX_FILE, context.getDir("outdex", Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
            Class clz = loader.loadClass("edu.ntu.android.maas.client.clientapp.Task");
            Object obj = clz.newInstance();
            Method mtd = clz.getDeclaredMethod("getBroadcastReceiver", new Class[]{Context.class});
            Object recv =  mtd.invoke(obj, context)
            if (recv != null)
                return (BroadcastReceiver)recv;
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
