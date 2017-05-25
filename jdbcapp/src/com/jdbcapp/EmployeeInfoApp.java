package com.jdbcapp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class EmployeeInfoApp extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
    private int input;
    private Connection connection ;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeInfoApp frame = new EmployeeInfoApp();
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
	public EmployeeInfoApp() {
		setTitle("Employee Details\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 450, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 15));
		textField.setBounds(158, 43, 227, 33);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterEmpId = new JLabel("Enter Emp ID :");
		lblEnterEmpId.setFont(new Font("Arial", Font.BOLD, 18));
		lblEnterEmpId.setBounds(20, 45, 149, 26);
		contentPane.add(lblEnterEmpId);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				input =Integer.parseInt(textField.getText());
			  
				try {
					Class.forName("com.mysql.jdbc.Driver");
					
					connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcprac","rootuser","password");

					/**
					 * 
					 * CREATE DEFINER=`rootuser`@`localhost` PROCEDURE `get_salary`(in EMP_ID int)
		               BEGIN
		                select ename,esal from emp where empid=EMP_ID;
		                 END
					 */
					String sql = "call jdbcprac.get_salary(?);";
					CallableStatement callableStatement =connection.prepareCall(sql);
					callableStatement.setInt(1,input);
					ResultSet resultSet = callableStatement.executeQuery();
					
					resultSet.next();
					textField_2.setText(resultSet.getString("ename"));
                    textField_3.setText(String.valueOf(resultSet.getInt("esal")));
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
                  JOptionPane.showMessageDialog(null, "Employee Id "+input+" dose not exists","Error Message",JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		btnSubmit.setFont(new Font("Arial", Font.BOLD, 18));
		btnSubmit.setBounds(158, 97, 108, 33);
		contentPane.add(btnSubmit);
		
		JLabel lblEmpName = new JLabel("Emp Name :");
		lblEmpName.setFont(new Font("Arial", Font.BOLD, 19));
		lblEmpName.setBounds(20, 160, 135, 26);
		contentPane.add(lblEmpName);
		
		JLabel lblEmpSalary = new JLabel("Emp Salary :\r\n");
		lblEmpSalary.setFont(new Font("Arial", Font.BOLD, 19));
		lblEmpSalary.setBounds(20, 207, 135, 26);
		contentPane.add(lblEmpSalary);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_2.setEditable(false);
		textField_2.setBounds(158, 160, 227, 33);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			textField.setText(null);
			textField_2.setText(null);
			textField_3.setText(null);
			}
		});
		btnClear.setFont(new Font("Arial", Font.BOLD, 18));
		btnClear.setBounds(287, 97, 108, 33);
		contentPane.add(btnClear);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Arial", Font.PLAIN, 15));
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(158, 207, 227, 33);
		contentPane.add(textField_3);
	}
}
