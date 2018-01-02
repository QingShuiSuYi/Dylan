package com.weavernorth.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import com.weavernorth.util.LogUtil;

/**
 * 封装FTP功能函数
 * Apace commons-net架包中的ftp工具类实现的
 * @author Dylan
 * 
 */
public class FtpCollectionUtil  {
	
	private FTPClient ftp;	
	
	private FileOutputStream fos = null;  
	
	private static String encoding = System.getProperty("file.encoding");

	/**
	 * 
	 * @param path
	 *            上传到ftp服务器哪个路径下
	 * @param addr
	 *            地址
	 * @param port
	 *            端口号
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	public boolean connect(String path, String addr, int port, String username,
			String password) throws Exception {
		System.out.println(encoding);
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(addr, port);
		ftp.login(username, password);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return result;
		}
		ftp.changeWorkingDirectory(path);
		result = true;
		return result;
	}

	/**
	 * 
	 * @param file
	 *            上传的文件或文件夹
	 * @throws Exception
	 */
	public void upload(File file) throws Exception {
		if (file.isDirectory()) {
            System.out.println(file.getName());
            boolean b = ftp.makeDirectory(new String(file.getName().getBytes("GBK"),"iso-8859-1"));
            System.out.println(b);
            boolean flag = ftp.changeWorkingDirectory(new String(file.getName().getBytes("GBK"),"iso-8859-1"));
			System.out.println("  --file.getName()---" + file.getName());
			System.out.println("  --flag---" + flag);
			String[] files = file.list();
			for (int i = 0; i < files.length; i++) {
				File file1 = new File(file.getPath() + "\\" + files[i]);
                System.out.println(file.getPath() + "\\" + files[i]);
                if (file1.isDirectory()) {
					upload(file1);
					ftp.changeToParentDirectory();
				} else {
					System.out.println("文件名" + file.getPath() + "\\" + files[i]);
					File file2 = new File(file.getPath() + "\\" + files[i]);
					FileInputStream input = new FileInputStream(file2);
					System.out.println("文件名name" + file2.getName());
					ftp.storeFile(new String(
							file2.getName().getBytes("GBK"), "iso-8859-1"),
							input);
					input.close();
				}
			}
		} else {
			File file2 = new File(file.getPath());
			FileInputStream input = new FileInputStream(file2);
			ftp.storeFile(new String(file2.getName().getBytes("GBK"),
					"iso-8859-1"), input);
			input.close();
		}
	}

	public void downFileOrDir(String ftpFileName, String localDir) {
		try {
			File file = new File(ftpFileName);

			File temp = new File(localDir);

			if (!temp.exists()) {
				temp.mkdirs();
			}
			// 判断是否是目录
			if (isDir(ftpFileName)) {
				String[] names = ftp.listNames();
				for (int i = 0; i < names.length; i++) {
					System.out.println(names[i] + "^^^^^^^^^^^^^^");
					if (isDir(names[i])) {
						downFileOrDir(ftpFileName + '/' + names[i], localDir
								+ File.separator + names[i]);
						ftp.changeToParentDirectory();
					} else {
						File localfile = new File(localDir + File.separator
								+ names[i]);
						if (!localfile.exists()) {
							fos = new FileOutputStream(localfile);
							ftp.retrieveFile(names[i], fos);

						} else {
							System.out.println("开始删除文件");
							file.delete();
							System.out.println("文件已经删除");
							fos = new FileOutputStream(localfile);
							ftp.retrieveFile(ftpFileName, fos);

						}

					}
				}
			} else {

				File localfile = new File(localDir + File.separator
						+ file.getName());
				if (!localfile.exists()) {
					fos = new FileOutputStream(localfile);
					ftp.retrieveFile(ftpFileName, fos);

				} else {
					System.out.println("开始删除文件");
					file.delete();
					System.out.println("文件已经删除");
					fos = new FileOutputStream(localfile);
					ftp.retrieveFile(ftpFileName, fos);

				}
				ftp.changeToParentDirectory();

			}

			System.out.println("下载成功！");
		} catch (SocketException e) {
			System.out.println("连接失败！" + e);
		} catch (IOException e) {
			System.out.println("下载失败！" + e);
		}

	}

	// 判断是否是目录
	public boolean isDir(String fileName) {
		try {
			// 切换目录，若当前是目录则返回true,否则返回true。
			boolean falg = ftp.changeWorkingDirectory(fileName);
			return falg;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}

		return false;
	}

	/**
	 * 关闭FTP连接
	 * 
	 * @throws IOException
	 */
	public void disConnection() throws IOException {
		// 检验ftp是否连接---如果连接关闭连接
		if (this.ftp.isConnected()) {
			this.ftp.disconnect();
		}
	}
	


}
