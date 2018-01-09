package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.*;
import com.gcit.lms.entity.*;

@RestController

public class BorrowerService {
	
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
	
	@RequestMapping(value = "/cardValidation/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public boolean cardValidation(@PathVariable Integer cardNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		 if(borrowerdao.readBorrowerByPK(cardNo) != null)
			 return true;
		 else
			 return false;
		 }
	
	@RequestMapping(value = "/viewBookLoans/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public List<BookLoans> getAllBookWithLoans(@PathVariable Integer cardNo) throws SQLException{
		try {
			List<BookLoans> loans = bldao.readBooksWithCardNo(cardNo);
			for(BookLoans l : loans){
				l.setBook((List<Book>) bdao.readBookByPK(l.getBook().get(0)));
			}
			return loans;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@RequestMapping(value = "/bookCheckIn/{bookId}/{branchId}/{cardNo}", method = RequestMethod.GET, produces = "application/json")
	public BookLoans checkIn(@PathVariable Integer bookId, @PathVariable Integer branchId, @PathVariable Integer cardNo) throws SQLException{
		 BookLoans bookLoans = new BookLoans();
		try {
		    bookLoans.setBookId(bookId);
		    bookLoans.setBranchId(branchId);
		    bookLoans.setCardNo(cardNo);
		    bldao.updateLoans(bookLoans);
		  
			BookCopies bc = new BookCopies();
			bc.setBookId(bookId);
			bc.setBranchId(branchId);
			bcdao.updateBCCheckIn(bc);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return bookLoans;
	}
		 
	@RequestMapping(value = "/checkOut/{cardNo}/{bookId}/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public BookLoans checkOut(@PathVariable Integer cardNo, @PathVariable Integer bookId, @PathVariable Integer branchId) throws SQLException{
		 BookLoans bookLoans = new BookLoans();
		try {
			long millis = System.currentTimeMillis();  
		    Date dateOut = new Date(millis);
		    long ltime = dateOut.getTime()+5*24*60*60*1000;
		    Date dueDate = new Date(ltime);
		    bookLoans.setBookId(bookId);
		    bookLoans.setBranchId(branchId);
		    bookLoans.setCardNo(cardNo);
		    bookLoans.setDateOut(dateOut);
		    bookLoans.setDueDate(dueDate);
		    bookLoans.setDateIn(null);
		    bldao.addLoans(bookLoans);
			BookCopies bookCopies = new BookCopies();
			bookCopies.setBookId(bookId);
			bookCopies.setBranchId(branchId);
			bcdao.updateBCCheckOut(bookCopies);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return bookLoans;
	}
}
