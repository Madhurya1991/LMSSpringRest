package com.gcit.lms.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BookLoans {
	public List<Book> getBook() {
		return book;
	}
	public void setBook(List<Book> book) {
		this.book = book;
	}
	public List<Borrower> getBorrower() {
		return borrower;
	}
	public void setBorrower(List<Borrower> borrower) {
		this.borrower = borrower;
	}
	public List<Branch> getBranch() {
		return branch;
	}
	public void setBranch(List<Branch> branch) {
		this.branch = branch;
	}
	private List<Book> book;
	private List<Borrower> borrower;
	private List<Branch> branch;
	private Date dateOut;
	private Date dateIn;
	private Date dueDate;
	private Integer bookId;
	private Integer branchId;
	private Integer cardNo;

public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public Integer getCardNo() {
		return cardNo;
	}
	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}
	//	public Book getBook() {
//		return book;
//	}
//	public void setBook(Book book) {
//		this.book = book;
//	}
//	public Borrower getBorrower() {
//		return borrower;
//	}
//	public void setBorrower(Borrower borrower) {
//		this.borrower = borrower;
//	}
//	public Branch getBranch() {
//		return branch;
//	}
//	public void setBranch(Branch branch) {
//		this.branch = branch;
//	}
	public Date getDateOut() {
		return dateOut;
	}
	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


}
