package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;

public class BookCopiesDAO extends BaseDAO<BookCopies> implements ResultSetExtractor<List<BookCopies>> {


	
	public void addBookCopies(BookCopies bc)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) "
				+ "VALUES (?,?,?)", new Object[] {bc.getBookId(),bc.getBranchId(),bc.getNoOfCopies()});
	}

	public void updateBookCopies(BookCopies bc)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_copies SET noOfCopies = ? WHERE bookId = ? and branchId = ? ",
				new Object[] {bc.getNoOfCopies(),bc.getBookId(),bc.getBranchId()});
	}

	public void deleteBranch(BookCopies bc)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_book_copies WHERE branchId = ?", new Object[] { bc.getBranchId()});
	}

	public List<BookCopies> readAllBookCopies()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_book_copies", this);
	}
	
	public BookCopies getBookCopiesById(Integer bookId, Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<BookCopies> bookCopies =  jdbcTemplate.query("select * from tbl_book_copies where bookId = ? and branchId = ?", new Object[]{bookId, branchId}, this);
		if(!bookCopies.isEmpty()){
			return bookCopies.get(0);
		}
		return null;
	}
	
	public Integer getCopiesByBook(Integer bookId){
		List<BookCopies> copies =  jdbcTemplate.query("select * from tbl_book_copies where bookId IN (select bookId from tbl_book where bookId = ?)", new Object[]{bookId}, this);
		if(!copies.isEmpty()){
			return copies.get(0).getNoOfCopies();
		}
		return null;
	}
	public void updateBCCheckIn(BookCopies bookCopies) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		jdbcTemplate.update("update tbl_book_copies set noOfCopies = noOfCopies + 1 where bookId = ? and branchId = ?", 
				new Object[]{bookCopies.getBookId(), bookCopies.getBranchId()});
	}
	
	public void updateBCCheckOut(BookCopies bookCopies) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		jdbcTemplate.update("update tbl_book_copies set noOfCopies = noOfCopies - 1 where bookId = ? and branchId = ?", 
				new Object[]{bookCopies.getBookId(), bookCopies.getBranchId()});
	}
	
	public List<BookCopies> readBookCopiesByPK(Integer branchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookCopies> bc = jdbcTemplate.query("SELECT * FROM tbl_book_copies WHERE branchId  = ? and noOfCopies > 0;", new Object[] { branchId }, this);
		if(!bc.isEmpty()){
			return bc;
		}
		return null;
	}
	
	
	
	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bcs = new ArrayList<>();
		while(rs.next()){
			BookCopies b = new BookCopies();
			b.setBookId(rs.getInt("bookId"));
			b.setBranchId(rs.getInt("branchId"));
			b.setBranchId(rs.getInt("noOfCopies"));
			bcs.add(b);
		}
		return bcs;
	}
	

}
