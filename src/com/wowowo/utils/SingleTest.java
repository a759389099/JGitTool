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
	 * �����ļ���Ϣ
	 * 
	 * @param url
	 *            ��Ŀ�ֿ�����url
	 * @param commitid
	 *            ����ύ��Ӧ��commitid,��id
	 * @param proPath
	 *            �����ύ�ļ��Ĵ��λ��
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
				// ����ɾ�����ļ�
				if (diffEntry.getOldPath().equals("/dev/null")) {
					System.out.println("ɾ����" + diffEntry.getNewPath());
					continue;
				} else if (diffEntry.getNewPath().equals("/dev/null")) {
					System.out.println("�½����ļ�:" + diffEntry.getOldPath());
				} else {
					System.out.println("�޸ĵ��ļ�:" + diffEntry.getOldPath());
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
				// System.out.println("Ҫ������Դ�ļ���ַ"+gitRoot +
				// diffEntry.getOldPath());
				// System.out.println("���Ƶ���Ŀ���ļ���ַ"+destPath +
				// diffEntry.getOldPath());
				System.out.println("**************************************************************************");
			}
			// ���ֻ�����!,�������,�ص���ǰ�汾
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
		// ������,���ذ汾�л���commitid�İ汾
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
	 * û�õ�
	 * �� ClassLoader ��ȡresource��systemConfig.properties����Դ���Ѷ�Ӧ�Ĳ���������Ա���� 
	 */
//	public static void load() {
//
//		Properties properties = new Properties();
//		InputStream in = null;
//		try {
//			in = new BufferedInputStream(
//					startcheckout.class.getClassLoader().getSystemResourceAsStream("systemConfig.properties"));
//			// prop.load(in);//ֱ����ôд�����properties�ļ����к��ӣ����ֻ����롣��Ϊδ���ñ����ʽ��
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
