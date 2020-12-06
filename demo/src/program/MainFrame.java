package program;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.net.ftp.FTPFile;

import ftp.FTPService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	static String Host;
	static String Port;
	String pwd;
	String Comand;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtHostName;
	private JTextField txtPort;
	private JTextField txtPwd;
	private JTextField txtTextbox;
	private JButton btnChangeWorkingFolder;
	private JButton btnSend;
	private JButton btnReceive;
	private JTextField txtTextbox_1;
	private JButton btnNewFolder;
	private JButton btnDelete;
	private javax.swing.JFileChooser jFileChooser1;
	public JLabel lblPwd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Host = args[0];
		Port = args[1];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void close() {
		this.setVisible(false);
	}

	public MainFrame() throws IOException {
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				FTPService.disconnectFTPServer();
			}
		});
		jFileChooser1 = new javax.swing.JFileChooser();
		jFileChooser1.setDialogTitle("Choose File");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 674, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblHost = new JLabel("Host:");
		lblHost.setFont(new Font("Dialog", Font.BOLD, 14));
		lblHost.setBounds(12, 22, 101, 25);

		contentPane.add(lblHost);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPort.setBounds(255, 27, 56, 15);
		contentPane.add(lblPort);

		txtHostName = new JTextField();
		txtHostName.setText(Host);
		txtHostName.setEditable(false);
		txtHostName.setBounds(68, 12, 166, 45);
		contentPane.add(txtHostName);
		txtHostName.setColumns(10);

		txtPort = new JTextField();
		txtPort.setText(Port);
		txtPort.setEditable(false);
		txtPort.setColumns(10);
		txtPort.setBounds(311, 12, 121, 45);
		contentPane.add(txtPort);

		JButton btnDisconnect = new JButton("Disconnect");

		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FTPService.disconnectFTPServer();
				String[] args = { "as" };
				ClientUI.main(args);
				close();
			}
		});
		btnDisconnect.setBounds(492, 22, 117, 25);
		contentPane.add(btnDisconnect);

		lblPwd = new JLabel("PWD:");
		lblPwd.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPwd.setBounds(12, 86, 72, 25);
		contentPane.add(lblPwd);

		txtPwd = new JTextField();
		txtPwd.setText(FTPService.test());
		txtPwd.setEditable(false);
		txtPwd.setColumns(10);
		txtPwd.setBounds(68, 76, 364, 45);
		contentPane.add(txtPwd);

		txtTextbox = new JTextField();
		txtTextbox.setText("");
		txtTextbox.setColumns(10);
		txtTextbox.setBounds(12, 142, 420, 45);
		contentPane.add(txtTextbox);

		btnChangeWorkingFolder = new JButton("Change working folder");
		btnChangeWorkingFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					btbChangeEvent(arg0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnChangeWorkingFolder.setBounds(444, 152, 201, 25);
		contentPane.add(btnChangeWorkingFolder);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSendActionPerformed(arg0);
			}
		});
		btnSend.setBounds(68, 210, 138, 25);
		contentPane.add(btnSend);

		btnReceive = new JButton("Receive");
		btnReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					btnReceiveActionPerformed(arg0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnReceive.setBounds(283, 210, 138, 25);
		contentPane.add(btnReceive);

		txtTextbox_1 = new JTextField();

		txtTextbox_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtTextbox_1.setText("");
			}

		});
		txtTextbox_1.setText("Input name folder that's you want to create");
		txtTextbox_1.setColumns(10);
		txtTextbox_1.setBounds(12, 258, 420, 45);
		contentPane.add(txtTextbox_1);

		btnNewFolder = new JButton("New Folder");
		btnNewFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					btnNewFolderEvent(arg0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewFolder.setBounds(444, 268, 201, 25);
		contentPane.add(btnNewFolder);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					btnDeleteEvent(arg0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(223, 325, 198, 25);
		contentPane.add(btnDelete);
	}

	private void btnSendActionPerformed(ActionEvent arg0) {
		int returnVal = jFileChooser1.showOpenDialog(this);
		String pwd = txtPwd.getText();
		if (returnVal == jFileChooser1.APPROVE_OPTION) {
			File file = jFileChooser1.getSelectedFile();
			String localFile = file.getPath();
			String fileName = file.getName();

			boolean check;
			System.out.println(localFile);
			System.out.println(fileName);

			check = FTPService.uploadFile(localFile, fileName, pwd);
			System.out.println(pwd);
			if (check)
				System.out.println("upload thanh cong!");
			else
				System.out.println("upload khong thanh cong");
		} else {
			System.out.println("File access cancelled by user.");
		}
	}

	private void btnReceiveActionPerformed(ActionEvent arg0) throws IOException {
		List<String> listFileName = new ArrayList<String>();
		listFileName = FTPService.getListNameFileFromFTPServer(txtPwd.getText());

		String[] inputlist = new String[listFileName.size()];

		inputlist = listFileName.toArray(inputlist);
		System.out.println("In receive before pane");
		String fileName = (String) JOptionPane.showInputDialog(this, "Choose a File to Download/Receive", "Input",
				JOptionPane.QUESTION_MESSAGE, null, inputlist, "Titan");
		if (!fileName.isEmpty()) {
			System.out.println(fileName);

			System.out.println(FTPService.test());

			ArrayList<FTPFile> listFile = (ArrayList<FTPFile>) FTPService.getListFileFromFTPServer(txtPwd.getText());
			FTPFile file = new FTPFile();
			for (FTPFile f : listFile) {
				System.out.println("o trong "+txtPwd.getText()+" co "+f.getName());
				if (f.getName().equals(fileName)) {
					file = f;
				}
			}
			if (file == null)
				System.out.println("da tim thay " + file.getName());
			else
				System.out.println("khong tim thay file"+fileName+ " o trong "+txtPwd.getText());

			String clientDirect = null;
			Component parent = null;
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
				clientDirect = fc.getSelectedFile().getAbsolutePath();
			}
			File f = new File(".");
			// String clientDirect = f.getAbsolutePath().substring(0,
			// f.getAbsolutePath().length() - 1);

			boolean check = FTPService.dowloadFile(txtPwd.getText() + "/" + file.getName(),
					clientDirect + "/" + fileName);
			System.out.println(clientDirect + "/" + fileName);
			if (check)
				System.out.println("thanh cong");
			else
				System.out.println("khong thanh cong");
		}
	}

	private void btbChangeEvent(ActionEvent arg0) throws IOException {
		String newPwd = txtTextbox.getText();
		if (newPwd.isEmpty())
			txtPwd.setText(FTPService.test());
		else
			txtPwd.setText("/" + newPwd + "/");
	}

	private void btnNewFolderEvent(ActionEvent arg0) throws IOException {
		String folderName = txtTextbox_1.getText();
		if (FTPService.makeNewFolder(folderName))
			System.out.println("tao folder " + folderName + " thanh cong!");
		else
			System.out.println("khong thanh cong");
		txtTextbox_1.setText("Input name folder that's you want to create");
	}
	private void btnDeleteEvent(ActionEvent arg0) throws IOException {
		List<String> listFileName = new ArrayList<String>();
		listFileName = FTPService.getListNameFileFromFTPServer(txtPwd.getText());

		String[] inputlist = new String[listFileName.size()];

		inputlist = listFileName.toArray(inputlist);
		System.out.println("In receive before pane");
		String fileName = (String) JOptionPane.showInputDialog(this, "Choose a File to delete", "Input",
				JOptionPane.QUESTION_MESSAGE, null, inputlist, "Titan");
		FTPService.delete(txtPwd.getText()+fileName);
		System.out.println(txtPwd.getText()+fileName);
	}
}
