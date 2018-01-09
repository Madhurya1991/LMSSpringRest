package com.gcit.lms.service;

import java.sql.Connection;
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
public class AdminService {

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

	@RequestMapping(value = "/saveAuthor", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void saveAuthor(@RequestBody Author author) {
		try {
			if (author.getAuthorId() != null) {
				adao.updateAuthor(author);
			} else {
				adao.addAuthor(author);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteAuthor/{authorId}", method = RequestMethod.GET, consumes="application/json", produces="application/json")
	public void deleteAuthor(@PathVariable Integer authorId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		adao.deleteAuthorById(authorId);
	}
	
	
	@Transactional
	public void addBook(@RequestBody Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		book.setBookId(bdao.addBookWithID(book));
		bdao.addBookAuthors(book);
		// do for genre
		// publisher

	}

	@RequestMapping(value = "/viewAuthors/{searchString}/{pageNo}", method = RequestMethod.GET, produces="application/json")
	public List<Author> readAuthors(@PathVariable String searchString, @PathVariable Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Author> authors = new ArrayList<>();
		if (searchString != null) {
			authors = adao.readAuthorsByName(searchString);
		}else{
			authors = adao.readAllAuthors(pageNo);
		}
		for(Author a: authors){
			a.setBooks(bdao.readAllBooksByAuthorID(a.getAuthorId()));
		}
		return authors;
	}

	@RequestMapping(value = "/viewAllAuthors", method = RequestMethod.GET,  produces="application/json")
	public List<Author> readAuthors() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			List<Author> authors = adao.readAllAuthors(1);
			for(Author a: authors){
//				System.out.println("book = "+bdao.readAllBooksByAuthorID(a.getAuthorId()));
				 a.setBooks(bdao.readAllBooksByAuthorID(a.getAuthorId()));
			}
			return authors;
	}

	@RequestMapping(value = "/readAuthorByPk/{authorId}", method = RequestMethod.GET, produces="application/json")
	public Author readAuthorByPk(@PathVariable Integer authorId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return adao.readAuthorByPK(authorId);
	}
	
	
	public Integer getAuthorsCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return adao.getAuthorsCount();
	}

	
	// books
	@RequestMapping(value = "/saveBook", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	@Transactional
	public void saveBook(@RequestBody Book book) {
		try {
			if (book.getBookId() != null) {
				bdao.updateBook(book);
			} else {
				bdao.addBook(book);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteBook/{bookId}", method = RequestMethod.GET, produces="application/json")
	public void deleteBook(@PathVariable Integer bookId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		bdao.deleteBookById(bookId);
	}
	
	@RequestMapping(value = "/viewBooks/{searchString}/{pageNo}", method = RequestMethod.GET, produces="application/json")
	public List<Book> readBooks(@PathVariable String searchString2, @PathVariable Integer pageNo2) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		if(searchString2 != null){
			return bdao.readBooksByName(searchString2);
		}
		else
		{
		return bdao.readAllBooks(pageNo2);
		}
	}
	
	@RequestMapping(value = "/readBookByPk/{bookId}", method = RequestMethod.GET, produces="application/json")
	public Book readBookByPk(@PathVariable Integer bookId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return bdao.readBookByPK(bookId);
	}
	
	
	public Integer getBooksCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return bdao.getBooksCount();
	}
	
	// Publisher
	
		@RequestMapping(value = "/readPublisherByPk/{publisherId}", method = RequestMethod.GET, produces="application/json")
		public Publisher readPublisherByPk(@PathVariable Integer publisherId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			return pdao.readPublisherByPK(publisherId);
		}
		
		@RequestMapping(value = "/viewAllPublishers", method = RequestMethod.GET,  produces="application/json")
		public List<Publisher> readPublisher() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			return pdao.readAllPublishers();
		}
		
		@RequestMapping(value = "/savePublisher", method = RequestMethod.POST, consumes="application/json", produces="application/json")
		@Transactional
		public void savePublisher(@RequestBody  Publisher publisher) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
				if(publisher.getPublisherId()!=null){
					pdao.updatePublisher(publisher);
				}else{
					pdao.addPublisher(publisher);
				}		
		}
		
		@RequestMapping(value = "/deletePublisher/{publisherId}", method = RequestMethod.GET, produces="application/json")
		public void deletePublisher(@PathVariable Integer publisherId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
				pdao.deletePublisherById(publisherId);
				}
		
		// book loans
		
		@RequestMapping(value = "/viewAllBookLoans", method = RequestMethod.GET,  produces="application/json")
		public List<BookLoans> readBookLoans() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			return bldao.readAllBookLoans();
		}
	
		@RequestMapping(value = "/overrideDueDate/{dueDate}/{noOfDays}/{bookId}/{branchId}/{cardNo}", method = RequestMethod.GET, produces="application/json")
		public BookLoans updateBookLoans(@PathVariable String dueDate,@PathVariable Integer noOfDays,@PathVariable Integer bookId,@PathVariable Integer branchId,@PathVariable Integer cardNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			return bldao.updateBookLoans(dueDate, noOfDays, bookId, branchId, cardNo);
		}
		
		// borrower
		
		@RequestMapping(value = "/readBorrowerByPk/{cardNo}", method = RequestMethod.GET, produces="application/json")
		public Borrower readBorrowerByPk(@PathVariable Integer cardNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			return borrowerdao.readBorrowerByPK(cardNo);
		}
		
		@RequestMapping(value = "/viewAllBorrowers", method = RequestMethod.GET,  produces="application/json")
		public List<Borrower> readBorrower() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			return borrowerdao.readAllBorrowers();		
		}
		
		@RequestMapping(value = "/saveBorrower", method = RequestMethod.POST, consumes="application/json", produces="application/json")
		@Transactional
		public void saveBorrower(@RequestBody Borrower borrower) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
				if(borrower.getCardNo()!=null){
					borrowerdao.updateBorrower(borrower);
				}else{
					borrowerdao.addBorrower(borrower);
				}
			
		}
		
		@RequestMapping(value = "/deleteBorrower/{cardNo}", method = RequestMethod.GET, produces="application/json")
		public void deleteBorrower(@PathVariable Integer cardNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			borrowerdao.deleteBorrowerById(cardNo);
		}
		
		// library branch
		
		@RequestMapping(value = "/readBranchByPk/{branchId}", method = RequestMethod.GET, produces="application/json")
		public Branch readBranchByPk(@PathVariable Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			return brdao.readBranchByPK(branchId);
		}
		
		@RequestMapping(value = "/viewAllBranches", method = RequestMethod.GET,  produces="application/json")
		public List<Branch> readBranch() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			return brdao.readAllBranches();
		}
		
		@RequestMapping(value = "/saveBranch", method = RequestMethod.POST, consumes="application/json", produces="application/json")
		@Transactional
		public void saveBranch(@RequestBody Branch branch) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
				if(branch.getBranchId()!=null){
					brdao.updateBranch(branch);
				}else{
					brdao.addBranch(branch);
				}
		}
		
		@RequestMapping(value = "/deleteBranch/{branchId}", method = RequestMethod.GET, produces="application/json")
		public void deleteBranch(@PathVariable Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
			
				brdao.deleteBranchById(branchId);
		}
}
