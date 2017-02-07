package pl.dev.davids.Remover;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.ParseConversionEvent;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;

public class FileRemover {

	private JFrame frmFileRemover;
	private PathText txtPath1, txtPath2;
	private JMenuBar menuBar;
	private JMenu mnFile, mnEdit, mnHelp;
	private JRadioButtonMenuItem rdbtnmntmNewRadioItem, rdbtnmntmNewRadioItem_1;
	private JMenuItem mntmClose, mntmAbout;
	private JLabel lblDoUsuniecia, lblNumber, counterJPG, counterRAW;
	private FileList listFile1, listFile2;
	private JCheckBox checkBox;
	private PathButton btnFilePath1, btnFilePath2;
	private ActionButton btnRun, btnRunDelete, btnCheck;
	private Pather path1, path2;
	private Deleter deleter;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileRemover window = new FileRemover();
					window.frmFileRemover.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FileRemover() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFileRemover = new JFrame();
		frmFileRemover.setTitle("PhotoFileRemover");
		frmFileRemover.setBounds(100, 100, 512, 464);
		frmFileRemover.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFileRemover.getContentPane().setLayout(null);

		path1 = new Pather(new Chooser());
		path2 = new Pather(new Chooser());
		deleter = new DeleterRAW(path1, path2);

		btnFilePath1 = new PathButton("Choose Path1", true);
		btnFilePath1.setBounds(7, 7, 230, 25);
		btnFilePath1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				path1.setPath(path1.choosePath());
				txtPath1.setText(path1.getPath());
				setNotEnabled();
			}
		});
		btnFilePath2 = new PathButton("Choose Path2", false);
		btnFilePath2.setBounds(278, 7, 204, 25);
		btnFilePath2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				path2.setPath(path2.choosePath());
				txtPath2.setText(path2.getPath());
				setNotEnabled();
			}
		});
		frmFileRemover.getContentPane().add(btnFilePath1);
		frmFileRemover.getContentPane().add(btnFilePath2);

		checkBox = new JCheckBox("", true);
		checkBox.setBounds(245, 7, 25, 25);
		checkBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (checkBox.isSelected()) {
					btnFilePath2.setEnabled(false);
					txtPath2.setEnabled(false);
				} else {
					btnFilePath2.setEnabled(true);
					txtPath2.setEnabled(true);
				}
			}
		});
		frmFileRemover.getContentPane().add(checkBox);

		txtPath1 = new PathText("No Selection", true);
		txtPath1.setBounds(7, 36, 230, 22);
		txtPath1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				path1.setPath(txtPath1.getText());
			}
		});
		txtPath2 = new PathText("No Selection", false);
		txtPath2.setBounds(245, 36, 237, 22);
		txtPath2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				path2.setPath(txtPath2.getText());
				setNotEnabled();
			}
		});
		frmFileRemover.getContentPane().add(txtPath1);
		frmFileRemover.getContentPane().add(txtPath2);
		txtPath1.setColumns(10);
		txtPath2.setColumns(10);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(7, 71, 230, 288);
		frmFileRemover.getContentPane().add(scrollPane_1);
		listFile1 = new FileList("JPG");
		scrollPane_1.setViewportView(listFile1);
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(245, 71, 237, 288);
		frmFileRemover.getContentPane().add(scrollPane_2);
		listFile2 = new FileList("RAW");
		scrollPane_2.setViewportView(listFile2);

		menuBar = new JMenuBar();
		frmFileRemover.setJMenuBar(menuBar);
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mntmClose = new JMenuItem("Exit");
		mnFile.add(mntmClose);

		mnEdit = new JMenu("Tools");
		menuBar.add(mnEdit);
		rdbtnmntmNewRadioItem = new JRadioButtonMenuItem("DeleteRAW");
		rdbtnmntmNewRadioItem.setSelected(true);
		buttonGroup.add(rdbtnmntmNewRadioItem);
		mnEdit.add(rdbtnmntmNewRadioItem);
		rdbtnmntmNewRadioItem.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (rdbtnmntmNewRadioItem.isSelected())
					deleter = new DeleterRAW(path1, path2);
				else
					deleter = new DeleterJPG(path1, path2);
				btnRunDelete.setEnabled(false);
			}
		});
		rdbtnmntmNewRadioItem_1 = new JRadioButtonMenuItem("DeleteJPG");
		buttonGroup.add(rdbtnmntmNewRadioItem_1);
		mnEdit.add(rdbtnmntmNewRadioItem_1);

		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);

		btnRun = new ActionButton("Show", true);
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkBox.isSelected()) {
					path2.setPath(path1.getPath());
					txtPath2.setText(path2.getPath());
				}
				try {
					listFile1.setModel(path1.generateFileList("jpg"));
					listFile2.setModel(path2.generateFileList("nef"));
					btnCheck.setEnabled(true);
					counterJPG.setText(String.valueOf(path1.getCounter()));
					counterRAW.setText(String.valueOf(path2.getCounter()));
				} catch (Exception exception) {
				}
			}
		});
		menuBar.add(btnRun);

		btnCheck = new ActionButton("Check", false);
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleter.searchFileToDelete();
				lblNumber.setText(String.valueOf(deleter.getCounter()));
				if (Integer.parseInt(lblNumber.getText()) > 0)
					btnRunDelete.setEnabled(true);
			}
		});
		menuBar.add(btnCheck);

		lblDoUsuniecia = new JLabel("Do usuniecia:");
		menuBar.add(lblDoUsuniecia);
		lblNumber = new JLabel("number");
		menuBar.add(lblNumber);
		btnRunDelete = new ActionButton("Run Delete", false);
		btnRunDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleter.runFileDelete();
				setNotEnabled();
			}
		});
		menuBar.add(btnRunDelete);

		counterJPG = new JLabel("");
		counterJPG.setBounds(12, 361, 56, 16);
		frmFileRemover.getContentPane().add(counterJPG);
		counterRAW = new JLabel("");
		counterRAW.setBounds(245, 361, 56, 16);
		frmFileRemover.getContentPane().add(counterRAW);
	}

	public void setNotEnabled() {
		btnRunDelete.setEnabled(false);
		btnCheck.setEnabled(false);
	}
}
