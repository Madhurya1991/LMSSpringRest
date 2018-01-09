package com.gcit.lms.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.*;

public class BookLoansDAO extends BaseDAO<BookLoans> implements ResultSetExtractor<List<BookLoans>>{


	public BookLoans read(Integer[] pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookLoans> bc = jdbcTemplate.query("SELECT * FROM tbl_book_loans WHERE bookid=? AND branchid=? AND cardno=?", new Object[] { pk[0],pk[1],pk[2] }, this);
			return bc.get(0);
		
	}
	
	public BookLoans updateBookLoans(String dueDate, Integer noOfDays, Integer bookId,Integer branchId, Integer cardNo) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println("bo Id1 = "+bookId+" branch id1 ="+branchId+" cn1 ="+cardNo+"dueDate ="+dueDate);
		jdbcTemplate.update("Update tbl_book_loans SET dueDate = DATE_ADD(dueDate, INTERVAL ? DAY) where bookId = ? and branchId = ? and cardNo = ?", new Object[] {noOfDays,bookId,branchId,cardNo});
//	PreparedStatement pst = conn.prepareStatement("UPDATE tbl_book_loans SET dueDate = DATE_ADD(?, INTERVAL ? DAY)");
		return null;
	}
	
	public void updateDuedat(BookLoans be) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("update tbl_book_loans set duedate=? where bookid=? AND branchid=? AND cardno=?",
				new Object[] { be.getDueDate(),
						be.getBook().get(0),be.getBranch().get(0),be.getBorrower().get(0)});

	}

	public List<BookLoans> readBooksWithCardNo(Integer cardNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return jdbcTemplate.query("select * from tbl_book_loans where cardNo = ? and dateIn is null", new Object[]{cardNo}, this);

	}

	public void updateLoans(BookLoans bookLoans) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		jdbcTemplate.update("update tbl_book_loans set dateIn = CURDATE() where bookId = ? and branchId = ? and cardNo = ?", 
				new Object[]{ bookLoans.getBook().get(0), bookLoans.getBranch().get(0), bookLoans.getBorrower().get(0)});
	}

	public BookLoans readBookLoansByPK(Integer bookId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookLoans> bo = jdbcTemplate.query("SELECT * FROM tbl_book_loans WHERE bookId  = ?", new Object[] { bookId },this);
		if (bo != null) {
			return bo.get(0);
		} else {
			return null;
		}
	}
	
	public void addLoans(BookLoans bookLoans) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		jdbcTemplate.update("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate, dateIn) values (?, ?, ?, ?, ?, ?)"
				,new Object[]{bookLoans.getBookId(), bookLoans.getBranchId(), bookLoans.getCardNo(), bookLoans.getDateOut(), bookLoans.getDueDate(), bookLoans.getDateIn()} );
	}
	
	public List<BookLoans> readAllBookLoans()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_book_loans", this);
	}
	
	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException{
		List<BookLoans> bl = new ArrayList<>();
		while(rs.next()){
			BookLoans b = new BookLoans();
			b.setDateIn(rs.getDate("dateOut"));
			b.setDueDate(rs.getDate("dueDate"));
			b.setDateOut(rs.getDate("dateIn"));
			bl.add(b);
		}
		return bl;
	}
	

}
