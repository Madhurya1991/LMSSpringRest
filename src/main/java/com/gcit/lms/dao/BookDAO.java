package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.*;

public class BookDAO extends BaseDAO<Book> implements ResultSetExtractor<List<Book>>{
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	BookLoansDAO bldao;
	
	@Autowired
	BookCopiesDAO bcdao;
	
	@Autowired
	BranchDAO brdao;
	
	@Autowired
	BorrowerDAO borrowerdao;

	public void addBook(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_book (title) VALUES (?)", new Object[] { book.getTitle()});
	}

	public Integer addBookWithID(final Book book) throws SQLException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "insert into tbl_book(title) values (?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, book.getTitle());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}

	public void updateBook(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book SET title =? WHERE bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });
	}

	public void deleteBook(Book book)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_book WHERE bookId = ?", new Object[] { book.getBookId() });
	}
	
	public void deleteBookById(Integer bookId)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_book WHERE bookId = ?", new Object[] { bookId });
	}

	public List<Book> readAllBooks(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		return jdbcTemplate.query("SELECT * FROM tbl_book", this);
	}

	public List<Book> readBooksByName(String title)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		title = "%" + title + "%";
		return jdbcTemplate.query("SELECT * FROM tbl_book WHERE title LIKE ?", new Object[] { title }, this);
	}
	
	public List<Book> readAllbooksWithBranch(Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		BookCopies bc = new BookCopies();
		List<BookCopies> copies  = (List<BookCopies>) bcdao.readBookCopiesByPK(branchId);
		 List<Book> books = new ArrayList<>();
		 for(BookCopies bcs :  copies){
			 List<Book> b = jdbcTemplate.query("select * from tbl_book where bookId = ?", new Object[]{bcs.getBookId()}, this);
			 if(!b.isEmpty()){
				 books.add(b.get(0));
			 }
		 }
		return books;

	}
	
	public Integer getBooksCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) AS COUNT FROM tbl_book", Integer.class);
	}

	public Book readBookByPK(Integer bookId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Book> books = jdbcTemplate.query("SELECT * FROM tbl_book WHERE bookId  = ?", new Object[] { bookId }, this);
		if (books != null) {
			return books.get(0);
		} else {
			return null;
		}
	}

	public List<Book> readAllBooksByAuthorID(Integer authorId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return jdbcTemplate.query("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId = ?)", new Object[]{authorId}, this);
	}
	
	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book a = new Book();
			a.setBookId(rs.getInt("authorId"));
			a.setTitle(rs.getString("title"));
			books.add(a);
		}
		return books;
	}
	
		
	public void addBookAuthors(Book book) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		for(Author a: book.getAuthors()){
			jdbcTemplate.update("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] {book.getBookId(), a.getAuthorId()});
		}
	}

	public List<Book> readBookByPK(Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Book> books = jdbcTemplate.query("SELECT * FROM tbl_book WHERE bookId  = ?", new Object[] { book.getBookId() }, this);
		if (books != null) {
			return books;
		} else {
			return null;
		}
	}
	

}
