package com.winsigns.investment.invest;

//import java.sql.Connection;  
//import java.sql.DriverManager;  
//import java.sql.PreparedStatement;  
//import java.sql.SQLException; 

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvestServiceApplication {
	
//	public static final String url = "jdbc:mysql://192.168.99.100:3306/Test?useSSL=true";  
//    public static final String name = "com.mysql.jdbc.Driver";  
//    public static final String user = "root";  
//    public static final String password = "mysql";
	
	public static void main(String[] args) {
//		try {
//			Class.forName(name);//指定连接类型  
//			String sql = "CREATE TABLE tb_instruction(instruct_id varchar(50) not null,portfolio varchar(10) not NULL,primary KEY(instruct_id))";
//			Connection conn = DriverManager.getConnection(url, user, password);//获取连接  
//			PreparedStatement pst = conn.prepareStatement(sql);//准备执行语句  
//	        pst.execute();
//			
//			
//		} catch (Exception  e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
		
		SpringApplication.run(InvestServiceApplication.class, args);
	}
}
