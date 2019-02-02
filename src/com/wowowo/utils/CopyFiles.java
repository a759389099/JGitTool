package com.wowowo.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFiles {

	/**
	 * 用IO流复制文件
	 * 
	 * @param src
	 * @param dest
	 */
	public static void copy(File src, File dest) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			bis = new BufferedInputStream(new FileInputStream(src));
			bos = new BufferedOutputStream(new FileOutputStream(dest));

			byte[] b = new byte[1024];
			int len = 0;

			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}

			bos.flush();
			System.out.println(dest.getPath() + "复制完毕.");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
