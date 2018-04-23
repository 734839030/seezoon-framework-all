package com.seezoon.framework.common.utils;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.springframework.util.Assert;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.seezoon.framework.common.context.exception.ServiceException;

/**
 * 二维码工具类
 * 
 * @author hdf 2018年4月23日
 */
public class ZxingHelper {

	private static final String CHARSET = "utf-8";

	private static final String FORMAT_NAME = "JPG";
	/**
	 * 二维码尺寸
	 */
	private int imageSize = 300;
	/**
	 * logo尺寸
	 */
	private int logoSize = 60;

	/**
	 * 生成二维码
	 * 
	 * @param text
	 *            内容
	 * @param logo
	 *            logo 无则传null、
	 * @param dest
	 *            输出流
	 * @param needCompress
	 *            是否压缩
	 */

	public void ecnode(String text, InputStream logo, OutputStream dest, boolean needCompress) {
		Assert.hasLength(text, "生成二维码内容为空");
		try {
			BufferedImage image = this.createImage(text, logo, needCompress);
			ImageIO.write(image, FORMAT_NAME, dest);
			if (null != logo) {
				logo.close();
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public ZxingHelper() {
		super();
	}

	public ZxingHelper(int imageSize, int logoSize) {
		super();
		this.imageSize = imageSize;
		this.logoSize = logoSize;
	}

	/**
	 * 解析二维码
	 * 
	 * @param file
	 *            二维码图片
	 * @return
	 * @throws Exception
	 */
	public static String decode(InputStream in) throws Exception {
		BufferedImage image = ImageIO.read(in);
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		in.close();
		return resultStr;
	}

	private BufferedImage createImage(String content, InputStream logo, boolean needCompress) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, imageSize, imageSize,
				hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		if (logo == null) {
			return image;
		}
		// 插入logo
		this.insertLogo(image, logo, needCompress);
		return image;
	}

	/**
	 * 插入LOGO
	 * 
	 * @param source
	 *            二维码图片
	 * @param imgPath
	 *            LOGO图片地址
	 * @param needCompress
	 *            是否压缩
	 * @throws Exception
	 */
	private void insertLogo(BufferedImage source, InputStream logo, boolean needCompress) throws Exception {
		Image src = ImageIO.read(logo);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		if (needCompress) { // 压缩LOGO
			if (width > logoSize) {
				width = logoSize;
			}
			if (height > logoSize) {
				height = logoSize;
			}
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (imageSize - width) / 2;
		int y = (imageSize - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
		graph.dispose();
	}

	public int getImageSize() {
		return imageSize;
	}

	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}

	public int getLogoSize() {
		return logoSize;
	}

	public void setLogoSize(int logoSize) {
		this.logoSize = logoSize;
	}
}
