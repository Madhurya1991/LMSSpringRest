package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.dao.*;
import com.gcit.lms.entity.*;


@RestController
public class LibraryService {

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
	
	@RequestMapping(value = "/AddUpdateLibBranch", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void saveBranch(@RequestBody Branch branch) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			if(branch.getBranchId()!=null){
				brdao.updateBranch(branch);
			}else{
				brdao.addBranch(branch);
			}
	}
	
	@RequestMapping(value = "/deleteLibBranch/{branchId}", method = RequestMethod.GET, produces="application/json")
	public void deleteBranch(@PathVariable Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{

			brdao.deleteBranchById(branchId);
		
	}
	
	@RequestMapping(value = "/readBranchByPk/{branchId}", method = RequestMethod.GET, produces="application/json")
	public Branch readBranchByPk(@PathVariable Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return brdao.readBranchByPK(branchId);
	}
	
	@RequestMapping(value = "/readBranchByName/{branchName}", method = RequestMethod.GET, produces="application/json")
	public List<Branch> readBranchByName(@PathVariable String branchName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return brdao.readBranchByName(branchName);
		
	}
	
	@RequestMapping(value = "/readBookCopies/{branchId}", method = RequestMethod.GET, produces="application/json")
	public BookCopies readBookCopies(@PathVariable Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return (BookCopies) bcdao.readBookCopiesByPK(branchId);
		
	}
	
	@RequestMapping(value = "/updateBookCopies/{branchId}/{bookId}/{noCopies}", method = RequestMethod.GET, produces = "application/json")
	public BookCopies updateBookCopies(@PathVariable int branchId, @PathVariable int bookId, @PathVariable int noOfCopies) throws SQLException{
		try {
			BookCopies copies = new BookCopies();
			copies.setBookId(bookId);
			copies.setBranchId(branchId);
			copies.setNoOfCopies(noOfCopies);
			if(copies.getBookId() != null){
				bcdao.updateBookCopies(copies);
			}else{
				bcdao.addBookCopies(copies);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return new BookCopies();
	}	
	
	@RequestMapping(value = "/viewBranches", method = RequestMethod.GET, produces = "application/json")
	public List<Branch> getAllBranches() throws SQLException{
		try {
			return brdao.readAllBranches();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@RequestMapping(value = "/readBCbyBookIdBranchId/{bookId}/{branchId}", method = RequestMethod.GET, produces="application/json")
	public BookCopies getBookCopiesById(@PathVariable Integer bookId,@PathVariable Integer branchId) throws SQLException{
		try {
			return bcdao.getBookCopiesById(bookId,branchId);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@RequestMapping(value = "/readBorrowerName/{cardNo}", method = RequestMethod.GET, produces="application/json")
	public String readBorrowerName(Integer cardNo) throws SQLException{
		try {
			Borrower borrower = borrowerdao.readBorrowerByPK(cardNo);
			if(borrower != null){
				return borrower.getName();
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@RequestMapping(value = "/viewBranchAndBooks/{branchId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Book> getAllBooksForBranchId( @PathVariable Integer branchId) throws SQLException{
		System.out.println(branchId);
		try {
			List<Book> books = bdao.readAllbooksWithBranch(branchId);
			for(Book b : books){
				b.setAuthors(adao.readAuthorsByBook(b.getBookId()));
				b.setGenres(gdao.readGenreByBook(b.getBookId()));
				b.setNoOfCopies(bcdao.getCopiesByBook(b.getBookId()));
			}
			return books;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
}
