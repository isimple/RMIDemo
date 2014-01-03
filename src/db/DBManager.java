package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	private static String user = "root";
	private static String pass = "root";
	private static String className = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/mydata";
	private static Connection conn;
	private static Statement state;

	public static void init() {
		try {
			Class.forName(className);
			conn = DriverManager.getConnection(url, user, pass);
			state = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void CreateTable() {
		String sql = "create table student (id int Primary key, name char (20),score double);";
		try {
			state.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insert(int id, String name, double score) {
		String sql = "insert into student values(" + id + ",'" + name + "',"
				+ score + ");";
		try {
			state.execute(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static double select(int id) {
		String sql = "select score from student where id = " + id + ";";
		double result = 0;
		try {
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				result = rs.getDouble("score");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static double select(String name) {
		String sql = "select score from student where name = '" + name + "';";
		double result = 0;
		try {
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				result = rs.getDouble("score");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
