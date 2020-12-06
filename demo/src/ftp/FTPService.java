/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author admin123
 */
public class FTPService {

	// private static final String FTP_SERVER_ADDRESS = "192.168.1.127";
	private static String FTP_SERVER_ADDRESS;
	private static int FTP_SERVER_PORT_NUMBER;
	private static String FTP_USERNAME;
	private static String FTP_PASSWORD;

	public FTPService(String FTP_SERVER_ADDRESS, int FTP_SERVER_PORT_NUMBER, String FTP_USERNAME, String FTP_PASSWORD) {
		this.FTP_SERVER_ADDRESS = FTP_SERVER_ADDRESS;
		this.FTP_SERVER_PORT_NUMBER = FTP_SERVER_PORT_NUMBER;
		this.FTP_USERNAME = FTP_USERNAME;
		this.FTP_PASSWORD = FTP_PASSWORD;
	}

	private static final int FTP_TIMEOUT = 6000000;
	private static final int BUFFER_SIZE = 1024 * 1024 * 1;
	private static FTPClient ftpClient;

	public static boolean uploadFile(String localFileFullName, String fileName, String hostDir) {
		try {
			File LocalFile = new File(localFileFullName);
			InputStream inputStream = new FileInputStream(LocalFile);

			System.out.println("Start uploading file");

			OutputStream outputStream = ftpClient.storeFileStream("/" + hostDir + "/" + fileName);
			System.out.println("/" + hostDir + "/" + fileName);
			byte[] bytesIn = new byte[4096];
			int read = 0;
			while ((read = inputStream.read(bytesIn)) != -1) {
				outputStream.write(bytesIn, 0, read);
			}

			inputStream.close();
			outputStream.close();

			boolean completed = ftpClient.completePendingCommand();
			if (completed) {
				System.out.println("The file is uploaded successfully.");
				return true;
			}
		} catch (Exception ex) {
			System.out.println("Error: " + ex.getMessage());
			return false;
		}
		return false;
	}

	public static boolean dowloadFile(String filePath, String downloadFilePath) throws IOException {

		if (filePath.startsWith("/")) {
			filePath = filePath.substring(1, filePath.length());
		} else {
			filePath = "/" + filePath;
		}
		File downloadFile = new File(downloadFilePath);
		OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));

		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.setBufferSize(BUFFER_SIZE);

		boolean success = ftpClient.retrieveFile(filePath, outputStream);
		showServerReply(ftpClient);
		outputStream.close();

		return success;

	}

	public static String test() throws IOException {
		String path = ftpClient.printWorkingDirectory();
		return path;
	}

	public static ArrayList<FTPFile> getListFileFromFTPServer() {

		ArrayList<FTPFile> listFiles = new ArrayList<FTPFile>();

		try {
			FTPFile[] ftpFiles = ftpClient.listFiles();

			if (ftpFiles.length > 0) {
				for (FTPFile ftpFile : ftpFiles) {
					listFiles.add(ftpFile);
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return listFiles;
	}

	public static boolean makeNewFolder(String newFolderName) throws IOException {
		boolean check = ftpClient.makeDirectory(newFolderName);
		showServerReply(ftpClient);
		return check;
	}

	public static List<FTPFile> getListFileFromFTPServer(String path) {

		ArrayList<FTPFile> listFiles = new ArrayList<FTPFile>();
		try {
			FTPFile[] ftpFiles = ftpClient.listFiles(path);
			// System.out.println("Dri" + " has " + ftpFiles.length + " file(s)");
			if (ftpFiles.length > 0) {
				for (FTPFile ftpFile : ftpFiles) {

					listFiles.add(ftpFile);

				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return listFiles;
	}

	public static ArrayList<String> getListNameFileFromFTPServer(String pathname) {

		ArrayList<String> listFiles = new ArrayList<String>();
		// connect ftp server
		// getConnectionServer();
		try {
			// FTPFile[] ftpFiles = ftpClient.listFiles();
			FTPFile[] ftpFiles = ftpClient.listFiles(pathname);
			// System.out.println("Dri" + " has " + ftpFiles.length + " file(s)");
			if (ftpFiles.length > 0) {
				for (FTPFile ftpFile : ftpFiles) {
					listFiles.add(ftpFile.getName());
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return listFiles;
	}

	private static void showServerReply(FTPClient ftpClient) {
		String[] replies = ftpClient.getReplyStrings();
		if (replies != null && replies.length > 0) {
			for (String aReply : replies) {
				System.out.println("SERVER: " + aReply);
			}
		}
	}

	public static int getConnectionServer() {
		ftpClient = new FTPClient();
		try {
			System.out.println("connecting ftp server...");

			ftpClient.setDefaultTimeout(FTP_TIMEOUT);
			ftpClient.connect(FTP_SERVER_ADDRESS, FTP_SERVER_PORT_NUMBER);

			// run the passive mode command
			//ftpClient.enterLocalPassiveMode(); // command port 21, data port ...
			 ftpClient.enterLocalActiveMode(); //command port 21, data port 20

			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {

				return 0;
			} else {
				ftpClient.setSoTimeout(FTP_TIMEOUT);
				// login ftp server
				if (ftpClient.login(FTP_USERNAME, FTP_PASSWORD) == false) {
					return 1;
				} else {
					showServerReply(ftpClient);
					ftpClient.setDataTimeout(FTP_TIMEOUT);
					return 2;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public static void disconnectFTPServer() {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
				System.out.println("Disconnected to Server");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void delete(String pathFile) throws IOException {
		try {
			ftpClient.deleteFile(pathFile);
			ftpClient.removeDirectory(pathFile);
			System.out.println("da xoa file");
		} catch (FileNotFoundException e) {
			// the file is not there
			System.out.println("khong tim thay file");
		}
	}
}
