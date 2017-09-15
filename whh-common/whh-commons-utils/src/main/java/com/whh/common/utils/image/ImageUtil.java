package com.whh.common.utils.image;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * 图片处理辅助类
 *
 * @author ShenHuaJie
 * @since 2012-03-21
 */
public final class ImageUtil {
    private ImageUtil() {
    }

    /**
     * * 转换图片大小，不变形
     *
     * @param img    图片文件
     * @param width  图片宽
     * @param height 图片高
     */
    public static final void changeImge(File img, int width, int height) {
        try {
            Thumbnails.of(img).size(width, height).keepAspectRatio(false).toFile(img);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("图片转换出错！", e);
        }
    }

    /**
     * 根据比例缩放图片
     *
     * @param orgImg     源图片路径
     * @param scale      比例
     * @param targetFile 缩放后的图片存放路径
     * @throws IOException
     */
    public static final void scale(BufferedImage orgImg, double scale, String targetFile) throws IOException {
        Thumbnails.of(orgImg).scale(scale).toFile(targetFile);
    }

    public static final void scale(String orgImgFile, double scale, String targetFile) throws IOException {
        Thumbnails.of(orgImgFile).scale(scale).toFile(targetFile);
    }

    /**
     * 图片格式转换
     *
     * @param orgImgFile
     * @param width
     * @param height
     * @param suffixName
     * @param targetFile
     * @throws IOException
     */
    public static final void format(String orgImgFile, int width, int height, String suffixName, String targetFile)
            throws IOException {
        Thumbnails.of(orgImgFile).size(width, height).outputFormat(suffixName).toFile(targetFile);
    }

    /**
     * 根据宽度同比缩放
     *
     * @param orgImg      源图片
     * @param targetWidth 缩放后的宽度
     * @param targetFile  缩放后的图片存放路径
     * @throws IOException
     */
    public static final double scaleWidth(BufferedImage orgImg, int targetWidth, String targetFile) throws IOException {
        int orgWidth = orgImg.getWidth();
        // 计算宽度的缩放比例
        double scale = targetWidth * 1.00 / orgWidth;
        // 裁剪
        scale(orgImg, scale, targetFile);

        return scale;
    }

    public static final void scaleWidth(String orgImgFile, int targetWidth, String targetFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(orgImgFile));
        scaleWidth(bufferedImage, targetWidth, targetFile);
    }

    /**
     * 根据高度同比缩放
     *
     * @param orgImg       //源图片
     * @param targetHeight //缩放后的高度
     * @param targetFile   //缩放后的图片存放地址
     * @throws IOException
     */
    public static final double scaleHeight(BufferedImage orgImg, int targetHeight, String targetFile) throws IOException {
        int orgHeight = orgImg.getHeight();
        double scale = targetHeight * 1.00 / orgHeight;
        scale(orgImg, scale, targetFile);
        return scale;
    }

    public static final void scaleHeight(String orgImgFile, int targetHeight, String targetFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(orgImgFile));
        // int height = bufferedImage.getHeight();
        scaleHeight(bufferedImage, targetHeight, targetFile);
    }

    // 原始比例缩放
    public static final void scaleWidth(File file, Integer width) throws IOException {
        String fileName = file.getName();
        String filePath = file.getAbsolutePath();
        String postFix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        // 缩放
        BufferedImage bufferedImg = ImageIO.read(file);
        String targetFile = filePath + "_s" + postFix;
        scaleWidth(bufferedImg, width, targetFile);
        String targetFile2 = filePath + "@" + width;
        new File(targetFile).renameTo(new File(targetFile2));
    }

    /*
    * 根据尺寸图片居中裁剪
    */
    public static void cutCenterImage(String src, String dest, int w, int h)
            throws IOException {
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader) iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - w) / 2,
                (reader.getHeight(imageIndex) - h) / 2, w, h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, "jpg", new File(dest));

    }

    /*
     * 图片裁剪二分之一
     */
    public static void cutHalfImage(String src, String dest) throws IOException {
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader) iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        int width = reader.getWidth(imageIndex) / 2;
        int height = reader.getHeight(imageIndex) / 2;
        Rectangle rect = new Rectangle(width / 2, height / 2, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, "jpg", new File(dest));
    }

    /*
     * 图片裁剪通用接口
     */

    public static void cutImage(String src, String dest, int x, int y, int w,
                                int h, String formatName) throws IOException {
        Iterator iterator = ImageIO.getImageReadersByFormatName(formatName);
        ImageReader reader = (ImageReader) iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, w, h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, formatName, new File(dest));

    }

    /*
     * 图片缩放
     */
    public static void zoomImage(String src, String dest, int w, int h)
            throws Exception {
        double wr = 0, hr = 0;
        File srcFile = new File(src);
        File destFile = new File(dest);
        BufferedImage bufImg = ImageIO.read(srcFile);
        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);
        wr = w * 1.0 / bufImg.getWidth();
        hr = h * 1.0 / bufImg.getHeight();
        AffineTransformOp ato = new AffineTransformOp(
                AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp,
                    dest.substring(dest.lastIndexOf(".") + 1), destFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param with
     * @param height
     * @param src
     * @param dest
     * @throws IOException
     * @author lovepanda 2016年3月4日16:36:30 图片缩略图
     * 如果图片小于你要求的图片，会对图片缩放后在裁剪，图片的不会拉伸，保证图片的真实性
     */
    public static void resize(int with, int height, String src, String dest)
            throws IOException {
        String imagePath = src;

        BufferedImage image = ImageIO.read(new File(imagePath));

        Thumbnails.Builder<BufferedImage> builder = null;

        int imageWidth = image.getWidth();

        int imageHeitht = image.getHeight();

        if ((float) with / height != (float) imageWidth / imageHeitht) {

            if (imageWidth > imageHeitht) {

                image = Thumbnails.of(imagePath)
                        .height(height)
                        .asBufferedImage();

            } else {

                image = Thumbnails.of(imagePath).width(with).asBufferedImage();

            }

            builder = Thumbnails.of(image)
                    .sourceRegion(Positions.CENTER, with, height)
                    .size(with, height);

        } else {

            builder = Thumbnails.of(image).size(with, height);

        }

        builder.toFile(dest);
    }
}
