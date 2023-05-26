package bookFunc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BookDAO {
	
	static final  String connectionUrl = "exampleDBURL";
	static final String username = "username";
	static final String password = "password";
	static final String driverBd = "org.postgresql.Driver";
	
	public static boolean jdbcSave(Book book) {
		
		if(book == null) {return false;}
		
		boolean saveH = false;
		try{
			Class.forName(driverBd);
			Connection con = DriverManager.getConnection(
					connectionUrl, username, password);
			PreparedStatement stm = con.prepareStatement("INSERT INTO Books (Name, Author, viewId, Annotation) VALUES (?,?,?,?)");
				stm.setString(1, book.getNameBook());
				stm.setString(2, book.getAuthor());
				stm.setString(3, book.getViewId());
				stm.setString(4, book.getAnnotation());		
				stm.execute();
				
				saveH = true;
				try {
					if(stm != null) {
					stm.close();
				}
					}catch(Exception e) {}
				
				try {
				if(con != null) {
					con.close();
				}

				}catch(Exception e) {}
				

			}catch(Exception e){}
			return saveH;
		}
	
	public static Book findById(Long id) {
		if(id == null) {return null;}
		
		Book bookRet = new Book();
		try {
		Class.forName(driverBd);
		Connection con = DriverManager.getConnection(connectionUrl, username, password);
		PreparedStatement stm = con.prepareStatement("SELECT * FROM Books WHERE Id = ?");
		stm.setLong(1, id);
		
		ResultSet res = stm.executeQuery();
		
		while(res.next()) {
			bookRet.setNameBook(res.getString("Name"));
			bookRet.setAuthor(res.getString("Author"));
			bookRet.setViewId(res.getString("ViewId"));
			bookRet.setAnnotation(res.getString("Annotation"));
		}
		
		try {
			if(stm != null) {
			stm.close();
		}
			}catch(Exception e) {}
		
		try {
		if(con != null) {
			con.close();
		}
		}catch(Exception e) {}
		

		}catch(Exception e) {}
		
		return bookRet;
	}
	
	public static List<Book> findByName(String nameBook){
		if(nameBook == null) {return null;}
		
		List<Book> books = new ArrayList<>();
		try {
			Class.forName(driverBd);
			Connection con =  DriverManager.getConnection(connectionUrl, username, password);
			PreparedStatement stm = con.prepareStatement("SELECT * FROM Books WHERE Name ILIKE ?");
			stm.setString(1, "%" + nameBook + "%");
			
			ResultSet res = stm.executeQuery();
			
			while(res.next())
			{	

				Book bk = new Book();
				bk.setNameBook(res.getString("name"));
				bk.setAuthor(res.getString("author"));
				bk.setViewId(res.getString("viewId"));
				bk.setAnnotation(res.getString("annotation"));
				books.add(bk);
			}
			
			try {
				if(res != null) {
					res.close();
				}
			}catch(Exception e) {}
			try {
				if(con != null) {
					con.close();
				}
			}catch(Exception e) {}
			
		}catch(Exception e){}
		return books;
	}
}
