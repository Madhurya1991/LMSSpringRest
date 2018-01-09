package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.*;


public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>>{
	
	public void addBorrower(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_borrower (name, address, phone) "
				+ "VALUES (?,?,?)", new Object[] { borrower.getName(),
						borrower.getAddress(),borrower.getPhone()});
	}

	public Integer addBorrowerWithID(final Borrower borrower) throws SQLException{
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_borrower (name, address, phone) VALUES (?,?,?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, borrower.getName());
				ps.setString(2, borrower.getAddress());
				ps.setString(3, borrower.getPhone());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}

	public void updateBorrower(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_borrower SET name = ?, address = ?, phone = ? WHERE cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(),borrower.getPhone(), borrower.getCardNo()});
	}

	public void deleteBorrower(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] { borrower.getCardNo() });
	}
	
	public void deleteBorrowerById(Integer cardNo)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] { cardNo });
	}

	public List<Borrower> readAllBorrowers()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_borrower", this);
	}
	public Borrower readBorrowerByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Borrower> borrowers= jdbcTemplate.query("SELECT * FROM tbl_borrower WHERE cardNo = ?", new Object[] { pk }, this);
		
		if(borrowers.size() == 0)
		{
			return null;
		}
		
		
		if (borrowers != null) {
			//System.out.println("borrower not null");
			return borrowers.get(0);
		} 	
		else {
			//System.out.println("borrower null");
			return null;
		}
	}
	
	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while(rs.next()){
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setName(rs.getString("name"));
			b.setAddress(rs.getString("address"));
			b.setPhone(rs.getString("phone"));
			borrowers.add(b);
		}
		return borrowers;
	}

	
}
