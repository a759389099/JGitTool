package com.wowowo.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.eclipse.jgit.diff.DiffEntry;

import com.wowowo.utils.CopyFiles;
import com.wowowo.utils.SingleTest;

/**
 * @author ebpm
 *
 */
public class startcheckout {

	public static void main(String[] args) {
		// ���������ļ�
		//SingleTest.load();
		try {
			// ��ȡ�ô��ύ�Ķ���list
			List<DiffEntry> list = SingleTest.getLog();
			// ��list�н������½����޸ĵ��ļ�·��,�������ļ���Ŀ���ļ���
			SingleTest.getCommitPath(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
