package org.live.baselib.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * <p>Created by LeonLee on 2014/9/4 17:21.</p>
 * 文件相关处理工具类
 * <p/>
 * <ul>
 * 文件名或路径相关
 * <li>{@link #getExtName(String)} 获取文件扩展名，带.</li>
 * <li>{@link #getFileNameWithExt(String)} 解析文件全名,带扩展名</li>
 * <li>{@link #getFileNameWithoutExt(String)} 获取文件名，不带扩展名</li>
 * <li>{@link #getParentPath(String)} 获取父目录路径</li>
 * <li>{@link #getFileFolderPath(String)} 解析文件所在的文件夹</li>
 * <li>{@link #getUnSameFileNameOfSize(String, String, int)} 获取不重复的文件名</li>
 * <li>{@link #getUnSameFileNameOfNum(String, String)} 获取不重复的文件名</li>
 * </ul>
 * <ul>
 * 文件读取相关
 * <li>{@link #loadPropertiesFromAsset(Context, String)} 从assset资源中读取Properties</li>
 * <li>{@link #loadPropertiesFromRaw(Context, int)} 从raw资源中读取Properties</li>
 * <li>{@link #getFileData(String)} 获取文件的数据</li>
 * <li>{@link #getFileSize(String)} 获取文件大小</li>
 * </ul>
 * <ul>
 * 文件处理相关
 * <li>{@link #rewriteData(String, byte[])} 重写文件的数据</li>
 * <li>{@link #rewriteData(String, InputStream)} 重写文件的数据</li>
 * <li>{@link #appendData(String, byte[])} 向文件的末尾添加数据</li>
 * <li>{@link #appendData(String, InputStream)} 向文件末尾添加数据</li>
 * <li>{@link #deleteFile(String)} 删除文件或文件夹(包括目录下的文件)</li>
 * <li>{@link #deleteFile(String, boolean)} 删除文件</li>
 * <li>{@link #createFile(String, int)} 创建一个空的文件(创建文件的模式，已经存在的是否要覆盖)</li>
 * <li>{@link #createFolder(String, int)} 创建一个空的文件夹(创建文件夹的模式，已经存在的是否要覆盖)</li>
 * <li>{@link #isExist(String)} 判断文件或文件夹是否存在</li>
 * <li>{@link #rename(String, String)}重命名文件/文件夹</li>
 * <li>{@link #listFiles(String)} 列出目录文件</li>
 * <li>{@link #moveFile(String, String)} 移动文件</li>
 * <li>{@link #writeData(String, byte[])} 写入新文件</li>
 * <li>{@link #isErrorFile(String)} 是否是下载出错文件（下到错误页面的数据）</li>
 * <li>{@link #hasfile(String)} 某个文件夹下是否文件</li>
 * </ul>
 * <ul>
 * 音频文件操作相关
 * <li>{@link #getAudioMimeType(String)} 获取音频文件的mimeType</li>
 * <li>{@link #isM4A(String)} 是否是m4a文件</li>
 * <li>{@link #writeMp3HashToM4a(String, String)} 哈希值写到m4a</li>
 * <li>{@link #readMp3HashFromM4a(String)} 从m4a读取mp3哈希值</li>
 * </ul>
 */
public class FileUtils {

    private FileUtils() {
    }

    /**
     * <p>获取文件扩展名，带.</p>
     * 如path=/sdcard/image.jpg --> .jpg
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getExtName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return "";
        } else {
            return fileName.substring(index, fileName.length());
        }
    }

    /**
     * <p>解析文件全名,带扩展名</p>
     * 如path=/sdcard/image.jpg --> image.jpg
     *
     * @param filePath 文件路径
     * @return 文件全名
     */
    public static String getFileNameWithExt(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int last = filePath.lastIndexOf("/");
        int end = filePath.length() - 1;
        if (last == -1) {
            return filePath;
        } else if (last < end) {
            return filePath.substring(last + 1);
        } else {
            return filePath.substring(last);
        }
    }

    /**
     * <p>获取文件名，不带扩展名</p>
     * 如path=/sdcard/image.jpg --> image
     *
     * @param filePath
     * @return
     */
    public static String getFileNameWithoutExt(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int last = filePath.lastIndexOf("/");
        int index = filePath.lastIndexOf(".");
        if (last == -1 && index == -1) {
            return filePath;
        } else if (index > last) {
            return filePath.substring(last + 1, index);
        } else {
            return filePath.substring(last);
        }
    }

    /**
     * 获取父目录路径
     *
     * @param filePath
     * @return
     */
    public static String getParentPath(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int last = filePath.lastIndexOf("/");
        if (last == -1) {
            return null;
        }
        return filePath.substring(0, last + 1);
    }

    /**
     * 解析文件所在的文件夹
     *
     * @param filePath 文件路径
     * @return 文件所在的文件夹路径
     */
    public static String getFileFolderPath(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        int last = filePath.lastIndexOf("/");
        if (last == -1) {
            return null;
        }
        return filePath.substring(0, last + 1);
    }

    /**
     * 从raw资源中读取Properties
     *
     * @param context
     * @param rawId
     * @return
     */
    public static Properties loadPropertiesFromRaw(Context context, int rawId) {
        Properties result = null;
        if (context != null) {
            InputStream ins = null;
            try {
                ins = context.getResources().openRawResource(rawId);
                result = new Properties();
                result.load(ins);
            } catch (IOException e) {
                result = null;
                e.printStackTrace();
            } finally {
                try {
                    if (ins != null) {
                        ins.close();
                    }
                } catch (Exception e2) {
                }
            }
        }
        return result;
    }

    /**
     * 从assset资源中读取Properties
     *
     * @param context
     * @param assetPath
     * @return
     */
    public static Properties loadPropertiesFromAsset(Context context, String assetPath) {
        Properties result = null;
        if (context != null && !TextUtils.isEmpty(assetPath)) {
            InputStream ins = null;
            try {
                ins = context.getAssets().open(assetPath);
                result = new Properties();
                result.load(ins);
            } catch (IOException e) {
                result = null;
                e.printStackTrace();
            } finally {
                try {
                    if (ins != null) {
                        ins.close();
                    }
                } catch (Exception e2) {
                }
            }
        }
        return result;
    }

    /**
     * 创建文件的模式，已经存在的文件要覆盖
     */
    public final static int MODE_COVER = 1;

    /**
     * 创建文件的模式，文件已经存在则不做其它事
     */
    public final static int MODE_UNCOVER = 0;

    /**
     * 获取文件的数据
     *
     * @param path
     * @return
     */
    public static byte[] getFileData(String path) {
        byte[] data = null;// 返回的数据
        try {
            File file = new File(path);
            if (file.exists()) {
                data = IOUtils.toByteArray(new FileInputStream(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 重写文件的数据
     *
     * @param path
     * @param data
     */
    public static void rewriteData(String path, byte[] data) {
        FileOutputStream fos = null;
        try {
            File file = new File(path);
            makeParentDir(file);
            fos = new FileOutputStream(file, false);
            fos.write(data);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * 重写文件的数据
     *
     * @param path
     * @param is
     */
    public static boolean rewriteData(String path, InputStream is) {
        FileOutputStream fos = null;
        try {
            File file = new File(path);
            makeParentDir(file);
            fos = new FileOutputStream(file, false);
            byte[] data = new byte[4 * 1024];
            int receive;
            while ((receive = is.read(data)) != -1) {
                fos.write(data, 0, receive);
                fos.flush();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
        }
        return false;
    }

    /**
     * 向文件的末尾添加数据
     *
     * @param path
     * @param data
     */
    public static boolean appendData(String path, byte[] data) {
        FileOutputStream fos = null;
        try {
            File file = new File(path);
            makeParentDir(file);
            fos = new FileOutputStream(file, true);
            fos.write(data);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
        }
        return false;
    }

    /**
     * 向文件末尾添加数据
     *
     * @param path
     * @param is
     */
    public static void appendData(String path, InputStream is) {
        FileOutputStream fos = null;
        try {
            File file = new File(path);
            makeParentDir(file);
            fos = new FileOutputStream(file, true);
            byte[] data = new byte[1024];
            int receive = 0;
            while ((receive = is.read(data)) != -1) {
                fos.write(data, 0, receive);
                fos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * 删除文件或文件夹(包括目录下的文件)
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        try {
            File f = new File(filePath);
            if (f.exists() && f.isDirectory()) {
                File[] delFiles = f.listFiles();
                if (delFiles != null && delFiles.length > 0) {
                    for (int i = 0; i < delFiles.length; i++) {
                        deleteFile(delFiles[i].getAbsolutePath());
                    }
                }
            }
            f.delete();
        } catch (Exception e) {

        }
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @param deleteParent 是否删除父目录
     */
    public static void deleteFile(String filePath, boolean deleteParent) {
        if (filePath == null) {
            return;
        }
        try {
            File f = new File(filePath);
            if (f.exists() && f.isDirectory()) {
                File[] delFiles = f.listFiles();
                if (delFiles != null && delFiles.length > 0) {
                    for (int i = 0; i < delFiles.length; i++) {
                        deleteFile(delFiles[i].getAbsolutePath(), deleteParent);
                    }
                }
            }
            if (deleteParent) {
                f.delete();
            } else if (f.isFile()) {
                f.delete();
            }
        } catch (Exception e) {

        }
    }


    /**
     * 删除指定文件夹下所有文件(包括子文件夹)
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        if (TextUtils.isEmpty(path)) {
            return flag;
        }
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        if (tempList != null && tempList.length > 0) {
            for (int i = 0; i < tempList.length; i++) {
                if (path.endsWith(File.separator)) {
                    temp = new File(path + tempList[i]);
                } else {
                    temp = new File(path + File.separator + tempList[i]);
                }
                if (temp.isFile()) {
                    temp.delete();
                }
                if (temp.isDirectory()) {
                    delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                    delFolder(path + "/" + tempList[i]);//再删除空文件夹
                    flag = true;
                }
            }
        }
        return flag;
    }


    /**
     * * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个空的文件(创建文件的模式，已经存在的是否要覆盖)
     *
     * @param path
     * @param mode
     */
    public static boolean createFile(String path, int mode) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                if (mode == FileUtils.MODE_COVER) {
                    file.delete();
                    file.createNewFile();
                }
            } else {
                // 如果路径不存在，先创建路径
                File mFile = file.getParentFile();
                if (!mFile.exists()) {
                    mFile.mkdirs();
                }
                file.createNewFile();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 创建一个空的文件夹(创建文件夹的模式，已经存在的是否要覆盖)
     *
     * @param path
     * @param mode
     */
    public static void createFolder(String path, int mode) {
        try {
            // LogUtil.debug(path);
            if (TextUtils.isEmpty(path)) return;
            File file = new File(path);
            if (file.exists()) {
                if (mode == FileUtils.MODE_COVER) {
                    file.delete();
                    file.mkdirs();
                }
            } else {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件大小
     *
     * @param path
     * @return
     */
    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        long size = 0;
        try {
            File file = new File(path);
            if (file.exists()) {
                size = file.length();
            }
        } catch (Exception e) {
            return 0;
        }
        return size;
    }

    /**
     * 判断文件或文件夹是否存在
     *
     * @param path
     * @return true 文件存在
     */
    public static boolean isExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        boolean exist;
        try {
            File file = new File(path);
            exist = file.exists();
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * DQ新增文件大小>0的判断，文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        boolean exist;
        try {
            File file = new File(path);
            exist = file.exists() && file.length() > 0;
        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 重命名文件/文件夹
     *
     * @param path    原路径
     * @param newName 新的路径
     */
    public static boolean rename(final String path, final String newName) {
        boolean result = false;
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(newName)) {
            return result;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                result = file.renameTo(new File(newName));
            }
        } catch (Exception e) {
        }

        return result;
    }

    /**
     * 列出目录文件
     *
     * @return
     */
    public static File[] listFiles(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            return file.listFiles();
        }
        return null;
    }

    /**
     * 获取不重复的文件名
     *
     * @param filename
     * @return
     */
    public static String getUnSameFileNameOfSize(String folder, String filename, int fileSize) {
        String tempFilename = folder + StringUtils.formatFilePath(filename);
        boolean isExist = isExist(tempFilename);
        if (isExist) {
            String title = filename.substring(0, filename.lastIndexOf("."));
            String ext = filename.substring(filename.lastIndexOf(".") + 1);
            title = title + "(" + StringUtils.getSizeText(fileSize) + ")";
            // tempFilename = getUnSameFileNameOfNum(folder, title, ext);
            tempFilename = folder + StringUtils.formatFilePath(title) + "." + ext;
        }
        return tempFilename;
    }

    /**
     * 获取音频文件的mimeType
     *
     * @param path hash.ext.kgtmp
     * @return
     */
    public static String getAudioMimeType(String path) {
        boolean isM4A = path.toLowerCase().endsWith(".m4a");
        return isM4A ? "audio/mp4" : "audio/mpeg";
    }

    public static String getUnSameFileNameOfNum(String folder, String filename) {
        if (TextUtils.isEmpty(filename)) {
            return "";
        }
        if (filename.lastIndexOf(".") == -1) {
            return folder + StringUtils.formatFilePath(filename);
        }
        String title = filename.substring(0, filename.lastIndexOf("."));
        String ext = filename.substring(filename.lastIndexOf(".") + 1);
        int count = 0;
        String tempFilename = folder + StringUtils.formatFilePath(title) + "." + ext;
        boolean isExist = isExist(tempFilename);
        while (isExist) {
            tempFilename = folder + StringUtils.formatFilePath(title + "(" + (++count) + ")") + "." + ext;
            isExist = isExist(tempFilename);
        }
        return tempFilename;
    }

    /**
     * 是否是m4a文件
     *
     * @param m4a m4a文件路径
     * @return
     */
    public static boolean isM4A(final String m4a) {
        if (TextUtils.isEmpty(m4a)) {
            return false;
        }
        try {
            FileInputStream stream = new FileInputStream(new File(m4a));
            byte[] buffer = new byte[8];
            if (stream.read(buffer) == 8) {
                IOUtils.closeQuietly(stream);
                return (buffer[4] == 'f' && buffer[5] == 't' && buffer[6] == 'y' && buffer[7] == 'p');
            } else {
                IOUtils.closeQuietly(stream);
                return false;
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 哈希值写到m4a
     *
     * @param m4a  m4a文件路径
     * @param hash mp3哈希值 kgmp3hash
     */
    public static void writeMp3HashToM4a(final String m4a, final String hash) {
        if (TextUtils.isEmpty(m4a) || TextUtils.isEmpty(hash)) {
            return;
        }
        try {
            File m4afile = new File(m4a);
            RandomAccessFile accessFile = new RandomAccessFile(m4afile, "rw");
            long m4aLength = m4afile.length();
            byte[] tagbyte = TAG_KGMP3HASH.getBytes();
            byte[] hashbyte = hash.getBytes();
            ByteArrayBuffer buffer = new ByteArrayBuffer(TAG_KGMP3HASH_LENGTH);
            buffer.append(tagbyte, 0, tagbyte.length);
            buffer.append(hashbyte, 0, hashbyte.length);
            accessFile.skipBytes((int) m4aLength);
            accessFile.write(buffer.toByteArray());
            IOUtils.closeQuietly(accessFile);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    private static final String TAG_KGMP3HASH = "kgmp3hash";

    private static final int TAG_KGMP3HASH_LENGTH = TAG_KGMP3HASH.length() + 32;

    /**
     * 从m4a读取mp3哈希值
     *
     * @param m4a m4a文件路径
     * @return
     */
    public static String readMp3HashFromM4a(final String m4a) {
        if (TextUtils.isEmpty(m4a)) {
            return null;
        }
        File m4afile = new File(m4a);
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(m4afile, "r");
            accessFile.skipBytes((int) (m4afile.length() - TAG_KGMP3HASH_LENGTH));
            byte[] b = new byte[TAG_KGMP3HASH_LENGTH];
            if (accessFile.read(b) == TAG_KGMP3HASH_LENGTH) {
                String taghash = new String(b);
                if (!TextUtils.isEmpty(taghash) && taghash.startsWith(TAG_KGMP3HASH)) {
                    return taghash.substring(TAG_KGMP3HASH.length());
                }
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            if (accessFile != null) {
                try {
                    accessFile.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * 移动文件
     *
     * @param oldFilePath 旧路径
     * @param newFilePath 新路径
     * @return
     */
    public static boolean moveFile(String oldFilePath, String newFilePath) {
        if (TextUtils.isEmpty(oldFilePath) || TextUtils.isEmpty(newFilePath)) {
            return false;
        }
        File oldFile = new File(oldFilePath);
        if (oldFile.isDirectory() || !oldFile.exists()) {
            return false;
        }
        try {
            File newFile = new File(newFilePath);
            makeParentDir(newFile);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(oldFile));
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buf = new byte[1024];
            int read;
            while ((read = bis.read(buf)) != -1) {
                fos.write(buf, 0, read);
            }
            fos.flush();
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(bis);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否是下载出错文件（下到错误页面的数据）
     *
     * @param filePath 文件路径
     * @return
     */
    public static boolean isErrorFile(final String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        try {
            FileInputStream stream = new FileInputStream(new File(filePath));
            byte[] buffer = new byte[16];
            if (stream.read(buffer) == 16) {
                IOUtils.closeQuietly(stream);
                return ((buffer[0] & 0xFF) == 0xFF && (buffer[1] & 0xFF) == 0xD8 && (buffer[2] & 0xFF) == 0xFF && (buffer[3] & 0xFF) == 0xE0 && (buffer[4] & 0xFF) == 0x00
                        && (buffer[5] & 0xFF) == 0x10 && (buffer[6] & 0xFF) == 0x4A && (buffer[7] & 0xFF) == 0x46 && (buffer[8] & 0xFF) == 0x49 && (buffer[9] & 0xFF) == 0x46
                        && (buffer[10] & 0xFF) == 0x00 && (buffer[11] & 0xFF) == 0x01 && (buffer[12] & 0xFF) == 0x02 && (buffer[13] & 0xFF) == 0x01 && (buffer[14] & 0xFF) == 0x00 && (buffer[15] & 0xFF) == 0x48);
            } else {
                IOUtils.closeQuietly(stream);
                return false;
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 某个文件夹下是否文件
     *
     * @param filepath 文件路径
     */
    public static boolean hasfile(String filepath) {
        boolean returnValue = false;
        File file = new File(filepath);
        if (!file.exists()) {
            returnValue = false;
        } else if (!file.isDirectory()) {
            returnValue = true;
        } else if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + "\\" + filelist[i]);
                if (!readfile.isDirectory()) {
                    returnValue = true;
                    break;
                } else if (readfile.isDirectory()) {
                    hasfile(filepath + "\\" + filelist[i]);
                }
            }
        }
        return returnValue;
    }

    /**
     * 写入文件,如果文件已经存在,则覆盖之.
     *
     * @param path 文件保存路径
     * @param data 文件保存的数据
     * @return 是否写入成功
     */
    public static boolean writeData(String path, byte[] data) {
        return writeData(path, data, false);
    }

    public static boolean writeData(String path, byte[] data, boolean append) {
        try {
            File file = new File(path);
            makeParentDir(file);
            FileOutputStream fos = new FileOutputStream(file, append);
            fos.write(data);
            fos.flush();
            IOUtils.closeQuietly(fos);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void makeParentDir(File file) {
        if (file == null || file.exists()) {
            return;
        }
        File parentFile = file.getParentFile();
        if (parentFile.exists()) {
            return;
        }
        parentFile.mkdirs();
    }

    public static byte[] readData(String filePath) {
        return readData(new File(filePath));
    }

    public static byte[] readData(File file) {
        byte[] content = null;
        if (file.exists()) {
            FileInputStream in = null;
            try {
                content = new byte[(int) file.length()];
                in = new FileInputStream(file);
                int len = in.read(content);
                if (len == 0) {
                    content = null;
                }
            } catch (Exception e) {
                content = null;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
        if (content == null) {
            content = new byte[0];
        }
        return content;
    }

    public static String getString(String filePath) {
        return new String(readData(filePath));
    }

    public static String getString(File file) {
        return new String(readData(file));
    }

    public static String getStringFromAssets(Context context, String file) {
        try {
            InputStream inputStream = context.getAssets().open(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            List<String> lines = new LinkedList<>();
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
            String separator = System.getProperty("line.separator");
            return TextUtils.join(separator, lines);
        } catch (IOException e) {
        }
        return "";
    }

    /**
     * 解压路径为zipFile的.zip文件到路径folderPath下， 如果有同名folderPath的文件，则删除该文件，新建为文件夹
     * 解压过程使用buffer大小为5k
     *
     * @param zipFilePath
     * @param folderPath
     * @return
     * @throws IOException
     */
    public static boolean unZipFile(String zipFilePath, String folderPath) {
        // new ZipInputStream(null);
        if (!folderPath.endsWith("/")) {
            folderPath = folderPath + "/";
        }
        ZipFile zfile = null;
        OutputStream os = null;
        ZipInputStream is = null;
        try {
            File folder = new File(folderPath);
            if (folder.exists() && !folder.isDirectory()) {
                folder.delete();
            }
            if (!folder.exists()) {
                folder.mkdirs();
            }

            is = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry zipEntry;
            while ((zipEntry = is.getNextEntry()) != null) {
                String subfilename = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    File subDire = new File(folderPath + subfilename);
                    if (subDire.exists() && subDire.isDirectory()) {
                        continue;
                    } else if (subDire.exists() && subDire.isFile()) {
                        subDire.delete();
                    }
                    subDire.mkdirs();
                } else {
                    File subFile = new File(folderPath + subfilename);
                    if (subFile.exists()) {
                        continue;
                    }
                    subFile.createNewFile();
                    os = new FileOutputStream(subFile);
                    int len;
                    byte[] buffer = new byte[5120];
                    while ((len = is.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                        os.flush();
                    }
                }
            }

        } catch (Exception e) {
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (zfile != null) {
                try {
                    zfile.close();
                } catch (IOException e) {
                }
            }

        }
        return true;
    }

    /**
     * 检测SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardAvailableForMoment() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sdcard状态是没有挂载的情况
            return false;
        }
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        long freeSize = (freeBlocks * blockSize) / 1024 / 1024; //单位MB
        return (freeSize >= 50L); //小于50M不允许录制短视频
    }

    /**
     * 判断sdcard是否存在
     *
     * @return
     */
    public static boolean isSdcardExist() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean copyAssetsDirectory(Context context, String fromAssetPath, String toPath) {
        try {
            AssetManager assetManager = context.getAssets();
            String[] files = context.getAssets().list(fromAssetPath);
            if (isExist(toPath)) {
                deleteFile(toPath);
            } else {
                new File(toPath).mkdirs();
            }
            boolean res = true;
            for (String file : files)
                if (file.contains(".")) {
                    res &= copyAssetFile(assetManager, fromAssetPath + "/" + file, toPath + "/" + file);
                } else {
                    res &= copyAssetsDirectory(context, fromAssetPath + "/" + file, toPath + "/" + file);
                }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyAssetFile(AssetManager assetManager, String fromAssetPath, String toPath) {
        if (assetManager == null || TextUtils.isEmpty(fromAssetPath) || TextUtils.isEmpty(toPath)) {
            return false;
        }
        try {
            deleteFile(toPath);
            new File(toPath).getParentFile().mkdirs();
            InputStream inputStream = assetManager.open(fromAssetPath);
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            FileOutputStream fos = new FileOutputStream(toPath);
            byte[] buf = new byte[1024];
            int read;
            while ((read = bis.read(buf)) != -1) {
                fos.write(buf, 0, read);
            }
            fos.flush();
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(bis);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 拷贝文件
     */
    public static boolean copyFileUsingFileChannels(String source, String dest) {
        if (TextUtils.isEmpty(source) || TextUtils.isEmpty(dest)) {
            return false;
        }
        return copyFileUsingFileChannels(new File(source), new File(dest));
    }

    /**
     * FileChannels 高效拷贝文件
     *
     * @param source 源文件
     * @param dest   目标文件
     * @return
     */
    public static boolean copyFileUsingFileChannels(File source, File dest) {
        boolean ret = false;
        if (source != null && dest != null) {
            FileChannel inputChannel = null;
            FileChannel outputChannel = null;
            try {
                File parentFile = dest.getParentFile();
                if (parentFile != null && !parentFile.exists()) {
                    parentFile.mkdirs();
                }
                inputChannel = (new FileInputStream(source)).getChannel();
                outputChannel = (new FileOutputStream(dest)).getChannel();
                outputChannel.transferFrom(inputChannel, 0L, inputChannel.size());
                ret = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(inputChannel);
                IOUtils.closeQuietly(outputChannel);
            }
        }
        return ret;
    }

    public static boolean write(String filePath, boolean append, String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        } else {
            try {
                File f = new File(filePath);
                if (!f.getParentFile().exists() && !f.getParentFile().mkdirs()) {
                    return false;
                } else {
                    BufferedWriter out = new BufferedWriter(new FileWriter(filePath, append));

                    try {
                        out.write(text);
                    } finally {
                        out.flush();
                        out.close();
                    }

                    return true;
                }
            } catch (Exception var9) {
                var9.printStackTrace();
                return false;
            }
        }
    }

    public static Object readSerializable(String filePath) {
        return readSerializable(new File(filePath));
    }

    public static Object readSerializable(File file) {
        Object object = null;
        if (file.exists()) {
            FileInputStream fileInputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                object = objectInputStream.readObject();
            } catch (Exception e) {
                object = null;
            } finally {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (Exception e) {
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return object;
    }

    public static boolean writeSerializable(String filePath, Serializable object) {
        return writeSerializable(new File(filePath), object);
    }

    public static boolean writeSerializable(File file, Serializable object) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (Exception e) {
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                }
            }
        }
        return true;
    }
}
