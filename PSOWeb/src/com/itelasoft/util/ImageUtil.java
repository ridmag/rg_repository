/**
 * 
 */
package com.itelasoft.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author vajira
 *
 */
public class ImageUtil {
	
	public static byte[] resizeJpeg(BufferedImage loadImg) throws IOException{
		int w = loadImg.getWidth();
		int h = loadImg.getHeight();
		int newW = 0;
		int newH = 0;
		if (w > h) {
			newW = 150;
			newH = newW * h / w;
		} else {
			newH = 150;
			newW = newH * w / h;
		}

		BufferedImage dimg = new BufferedImage(newW, newH, loadImg
				.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(loadImg, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(dimg, "jpg", byteArrayOutputStream);
		/*BufferedOutputStream out = new BufferedOutputStream(
				byteArrayOutputStream);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(dimg);
		int quality = 90;
		quality = Math.max(0, Math.min(quality, 100));
		param.setQuality((float) quality / 100.0f, false);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(dimg);
		out.close();*/

		return byteArrayOutputStream.toByteArray();

	}
}
