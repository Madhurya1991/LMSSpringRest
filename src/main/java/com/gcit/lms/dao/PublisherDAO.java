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

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>>{

	
	public void addPublisher(Publisher publisher)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) "
				+ "VALUES (?,?,?)", new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(),publisher.getPublisherPhone() });
	}

	public Integer addPublisherWithID(final Publisher publisher) throws SQLException{
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES (?,?,?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, publisher.getPublisherName());
				ps.setString(2, publisher.getPublisherAddress());
				ps.setString(3, publisher.getPublisherPhone());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}

	public void updatePublisher(Publisher publisher)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_publisher SET publisherName = ?, publisherAddress = ?, publisherPhone = ? WHERE publisherId = ?",
				new Object[] { publisher.getPublisherName(),publisher.getPublisherAddress(),
						publisher.getPublisherPhone(), publisher.getPublisherId()});
	}

	public void deletePublisher(Publisher publisher)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] { publisher.getPublisherId() });
	}
	
	public void deletePublisherById(Integer publisherId)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[] { publisherId });
	}

	public List<Publisher> readAllPublishers()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		return jdbcTemplate.query("SELECT * FROM tbl_publisher", this);
	}
	public Publisher readPublisherByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Publisher> publishers= jdbcTemplate.query("SELECT * FROM tbl_publisher WHERE publisherId  = ?", new Object[] { pk }, this);
		if (publishers != null) {
			return publishers.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()){
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));
			//add to populate books
			publishers.add(p);
		}
		return publishers;
	}

}
