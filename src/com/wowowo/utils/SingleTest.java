package com.wowowo.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.RenameDetector;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;

import com.wowowo.constant.JGitConstant;
import com.wowowo.main.startcheckout;

public class SingleTest {
	/**
	 * 配置文件信息
	 * 
	 * @param url
	 *            项目仓库所在url
	 * @param commitid
	 *            这次提交对应的commitid,长id
	 * @param proPath
	 *            导出提交文件的存放位置
	 */
	private static String gitRoot = JGitConstant.gitRoot;
	private static String revision = JGitConstant.revision;
	private static String destPath = JGitConstant.destPath;
//	private static Boolean flag = JGitConstant.flag;
//	private static String currentid = JGitConstant.currentid;
	private static File file;
	private static File fileDir;
	private static Git git;

	public static void getCommitPath(List<DiffEntry> list) {
		try {
			for (DiffEntry diffEntry : list) {
				// 跳过删除的文件
				if (diffEntry.getOldPath().equals("/dev/null")) {
					System.out.println("删除了" + diffEntry.getNewPath());
					continue;
				} else if (diffEntry.getNewPath().equals("/dev/null")) {
					System.out.println("新建的文件:" + diffEntry.getOldPath());
				} else {
					System.out.println("修改的文件:" + diffEntry.getOldPath());
				}
				String srcFile = gitRoot + "/" + diffEntry.getOldPath();
				String destDir = destPath + "/"
						+ diffEntry.getOldPath().substring(0, diffEntry.getOldPath().lastIndexOf("/"));
				String destFile = destPath + "/" + diffEntry.getOldPath();
				fileDir = new File(destDir);
				fileDir.mkdirs();
				file = new File(destFile);
				System.out.println(destFile);
				file.createNewFile();
				CopyFiles.copy(new File(srcFile), new File(destFile));
				// System.out.println("要拷贝的源文件地址"+gitRoot +
				// diffEntry.getOldPath());
				// System.out.println("复制到的目标文件地址"+destPath +
				// diffEntry.getOldPath());
				System.out.println("**************************************************************************");
			}
			// 我又回来啦!,拷贝完毕,回到当前版本
//			if (flag) {
//				git.checkout().setName(currentid).call();
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<DiffEntry> getLog() throws Exception {
		//load();
		git = Git.open(new File(gitRoot));
		// 我走了,本地版本切换该commitid的版本
//		if (flag) {
			git.checkout().setName(revision).call();
//		}
		Repository repository = git.getRepository();
		ObjectId objId = repository.resolve(revision);
		Iterable<RevCommit> allCommitsLater = git.log().add(objId).call();
		Iterator<RevCommit> iter = allCommitsLater.iterator();
		RevCommit commit = iter.next();
		TreeWalk tw = new TreeWalk(repository);
		tw.addTree(commit.getTree());
		commit = iter.next();
		if (commit != null)
			tw.addTree(commit.getTree());
		else
			return null;
		tw.setRecursive(true);
		RenameDetector rd = new RenameDetector(repository);
		rd.addAll(DiffEntry.scan(tw));

		return rd.compute();
	}

	/**
	 * 没用到
	 * 用 ClassLoader 读取resource下systemConfig.properties的资源，把对应的参数传给成员属性 
	 */
//	public static void load() {
//
//		Properties properties = new Properties();
//		InputStream in = null;
//		try {
//			in = new BufferedInputStream(
//					startcheckout.class.getClassLoader().getSystemResourceAsStream("systemConfig.properties"));
//			// prop.load(in);//直接这么写，如果properties文件中有汉子，则汉字会乱码。因为未设置编码格式。
//			properties.load(new InputStreamReader(in, "utf-8"));
//			gitRoot = (properties.getProperty("gitRoot"));
//			revision = properties.getProperty("revision");
//			destPath = properties.getProperty("destPath");
////			flag = Boolean.parseBoolean(properties.getProperty("flag"));
////			currentid = properties.getProperty("currentid");
//
//			System.out.println(destPath);
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//					System.out.println(e.getMessage());
//				}
//			}
//		}
//
//	}
}
