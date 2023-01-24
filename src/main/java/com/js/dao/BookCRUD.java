package com.js.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.js.dto.Book;

public class BookCRUD {
	 final static String path="com.mysql.cj.jdbc.Driver";
	 final static String address="jdbc:mysql://localhost:3306/jdbc_newyear?user=root&password=root";
	 private Connection c;
	 
	 public int insertBook(Book b) {
		Connection c = null;
		try {
			Class.forName(path);
			c = DriverManager.getConnection(address);
			PreparedStatement ps=c.prepareStatement("insert into book values(?,?,?,?,?)"); //?-placeholder
			ps.setInt(1,b.getBook_id());
			ps.setString(2, b.getBook_name());
			ps.setString(3,b.getAuthor());
			ps.setInt(4,b.getNo_of_pages());
			ps.setDouble(5,b. getPrice());
			return ps.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		return 0;
  
	 }
	public int deleteBookId(int id) {
		try {
			Class.forName(path);
			c = DriverManager.getConnection(address);
			PreparedStatement ps = c.prepareStatement("delete from book where book_id=?");
			ps.setInt(1, id);
			return ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	
	}
	public int updateBookById(int id, Book b) {
		try {
			Class.forName(path);
			c = DriverManager.getConnection(address);
			PreparedStatement ps = c.prepareStatement("update book set book_name=?,author_name=?,no_of_pages=?,price=? where book_id=?");
			ps.setString(1, b.getBook_name());
			ps.setString(2, b.getAuthor());
			ps.setInt(3, b.getNo_of_pages());
			ps.setDouble(4, b.getPrice());
			ps.setInt(5, id);
			return ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
		
	}
	public Book getBookByid(int id) {
		Book b = null;
		try {
			Class.forName(path);
			c = DriverManager.getConnection(address);//java.sql package, driver is a class
			PreparedStatement ps = c.prepareStatement("select*from Book where Book_id=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();//it is a interface from java.sql package
			if(rs.next()) {
				b = new Book();//process the result
				b.setBook_id(rs.getInt(1));
				b.setBook_name(rs.getString(2));
				b.setAuthor(rs.getString(3));
				b.setNo_of_pages(rs.getInt(4));
				b.setPrice(rs.getDouble(5));
				return b;
			}
			else {
				return b;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return b;
	}
	public ArrayList<Book> getAllBooks() {
		ArrayList<Book> al = new ArrayList<Book>();
		try {
			Class.forName(path);
		    c = DriverManager.getConnection(address);
			PreparedStatement ps = c.prepareStatement("select * from book");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
			Book b = new Book();
			b.setBook_id(rs.getInt(1));
			b.setBook_name(rs.getString(2));
			b.setAuthor(rs.getString(3));
			b.setNo_of_pages(rs.getInt(4));
			b.setPrice(rs.getDouble(5));//non static variables
			al.add(b);//All the books list added into the ArrayList
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) { //batch execution tomorrow
			e.printStackTrace();
		}
		finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	public boolean batchExecution(ArrayList<Book> books) {
		try {
			Class.forName(path);
			c = DriverManager.getConnection(address);
			PreparedStatement ps = c.prepareStatement("insert into book values(?,?,?,?,?)");//dynamic queries( execution-placeholder)
			for(Book b : books) {
				ps.setInt(1, b.getBook_id());
				ps.setString(2, b.getBook_name());
				ps.setString(3, b.getAuthor());
				ps.setInt(4, b.getNo_of_pages());
				ps.setDouble(5, b.getPrice());
				ps.addBatch();
				}
			ps.executeBatch();
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
