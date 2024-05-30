package FE;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import BE.ExcelReader;
import BE.WaveOcean;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;

public class StartWave {
	
	static WaveOcean tool = new WaveOcean();
	static ExcelReader readWallet = new ExcelReader();

	public static void main(String[] args) {
		// Tạo một JFrame để chứa giao diện
		JFrame frame = new JFrame("GPT on Twitter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton start = new JButton("Start");
		JButton importWallet = new JButton("Import");
		frame.add(new JLabel("Time:"));
		JTextField setTime = new JTextField();
		frame.add(setTime);
		JTextField setWalletFile = new JTextField("link file wallet");
		frame.add(setWalletFile);

		frame.add(importWallet);
		frame.add(start);
		frame.setLayout(new GridLayout(5, 1));

		importWallet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] dataArrayListWallet = readWallet.readExcelFile(setWalletFile.getText());
				for (String s : dataArrayListWallet) {
					System.out.println(s);
					tool.importWallet(s);
				}
			}

			// TODO Auto-generated method stub

		});
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {

					tool.action(setTime.getText().trim());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
		// Thêm JTabbedPane vào JFrame

		// Thiết lập kích thước và hiển thị JFrame
		frame.setSize(300, 200);
		frame.setVisible(true);
	}

}