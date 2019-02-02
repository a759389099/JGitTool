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
		// 加载配置文件
		//SingleTest.load();
		try {
			// 获取该次提交的对象list
			List<DiffEntry> list = SingleTest.getLog();
			// 从list中解析出新建和修改的文件路径,并拷贝文件到目标文件夹
			SingleTest.getCommitPath(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
