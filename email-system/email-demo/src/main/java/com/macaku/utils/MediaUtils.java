package com.macaku.utils;

import com.macaku.config.AppConfig;
import com.macaku.domain.FileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: 马拉圈
 * Date: 2023-10-27
 * Time: 0:00
 */
@Slf4j
public class MediaUtils {

    // 获取UUID
    public static String getUUID_32() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     *
     */
    public static String getImageUniquePath(String originName) {
        String path = AppConfig.MAP_ROOT;
        // 获取唯一id
        String id = getUUID_32();
        //获取文件后缀
        String suffix = originName.substring(originName.lastIndexOf("."));

        //拼接
        path += id + suffix;
        return path;
    }

    public static File loadFile(MultipartFile file, String path) {
        try {
            File loadFile = new File(path);
            file.transferTo(loadFile);
            return loadFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File[] getFiles(List<MultipartFile> multipartFiles) {
        if (Objects.isNull(multipartFiles)) {
            return new File[0];
        }
        return multipartFiles.stream().parallel().map(multipartFile -> {
            // 获得一个唯一的路径
            String uniquePath = getImageUniquePath(multipartFile.getOriginalFilename());
            // 加载
            String path = AppConfig.ROOT + uniquePath;
            return loadFile(multipartFile, path);
        }).toArray(File[]::new);
    }
    public static File[] getFilesFilterImage(List<MultipartFile> multipartFiles) {
        if (Objects.isNull(multipartFiles)) {
            return new File[0];
        }
        List<MultipartFile> list = new ArrayList<>();
        try {
            for (MultipartFile file : multipartFiles) {
                InputStream inputStream = file.getInputStream();
                if (isImage(inputStream)) {
                    list.add(file);
                }
                inputStream.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }
        return list.stream().parallel()
                .map(multipartFile -> {
                    // 获得一个唯一的路径
                    String uniquePath = getImageUniquePath(multipartFile.getOriginalFilename());
                    // 加载
                    String path = AppConfig.ROOT + uniquePath;
                    return loadFile(multipartFile, path);
                }).toArray(File[]::new);
    }

    private static boolean isUrlAccessible(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            return responseCode == 200; // 如果状态码为 200，则返回 true，表示可以访问
        } catch (IOException e) {
            return false; // 发生异常时，返回 false，表示不可访问
        }
    }

    public static InputStream getFileInputStream(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();
        return connection.getInputStream();
    }


    public static boolean isImage(String url) {
        if(Boolean.FALSE.equals(isUrlAccessible(url))) {
            return false;
        }
        try {
            InputStream inputStream = getFileInputStream(url);
            if (inputStream == null) {
                return false;
            }
            Image img = ImageIO.read(inputStream);
            return !(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isImage(InputStream inputStream) {
        if (inputStream == null) {
            return false;
        }
        Image img;
        try {
            img = ImageIO.read(inputStream);
            return !(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0);
        } catch (Exception e) {
            return false;
        }
    }
    public static List<File> getFileList(List<MultipartFile> multipartFiles) {
        if (Objects.isNull(multipartFiles)) {
            return new ArrayList<>();
        }
        return multipartFiles.stream().parallel().map(multipartFile -> {
            // 获得一个唯一的路径
            String uniquePath = getImageUniquePath(multipartFile.getOriginalFilename());
            // 加载
            String path = AppConfig.ROOT + uniquePath;
            return loadFile(multipartFile, path);
        }).collect(Collectors.toList());
    }

    /**
     * @param is
     * @return
     * @throws IOException
     * @author jiangzeyin
     * @date 2016-8-17
     */
    public static FileType getFileType(InputStream is) throws IOException {
        byte[] src = new byte[28];
        is.read(src, 0, 28);
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v).toUpperCase();
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        FileType[] fileTypes = FileType.values();
        for (FileType fileType : fileTypes) {
            if (stringBuilder.toString().startsWith(fileType.getValue())) {
                return fileType;
            }
        }
        return null;
    }

    public static String getImageType(File srcFilePath) {
        FileInputStream imgFile;
        byte[] b = new byte[10];
        int l = -1;
        try {
            imgFile = new FileInputStream(srcFilePath);
            l = imgFile.read(b);
            imgFile.close();
        } catch (Exception e) {
            return null;
        }
        if (l == 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            byte b3 = b[3];
            byte b6 = b[6];
            byte b7 = b[7];
            byte b8 = b[8];
            byte b9 = b[9];
            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                return "gif";
            } else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
                return "png";
            } else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
                return "jpg";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


}
