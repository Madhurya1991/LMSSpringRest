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
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Branch;


public class BranchDAO extends BaseDAO<Branch> implements ResultSetExtractor<List<Branch>>{
		
		public void addBranch(Branch branch)
				throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
			jdbcTemplate.update("INSERT INTO tbl_library_branch (branchName, branchAddress) "
					+ "VALUES (?,?)", new Object[] { branch.getBranchName(),
							branch.getBranchAddress() });
		}
		public List<Branch> readBranchByName(String branchName)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			branchName = "%" + branchName + "%";
			return jdbcTemplate.query("SELECT * FROM tbl_library_branch WHERE branchName LIKE ?", new Object[] { branchName }, this);
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

		
		public Integer addBranchWithID(final Branch branch) throws SQLException{
			KeyHolder holder = new GeneratedKeyHolder();
			final String sql = "INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES (?,?)";
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, branch.getBranchName());
					ps.setString(2, branch.getBranchAddress());
					return ps;
				}
			}, holder);
			return holder.getKey().intValue();
		}

		public void updateBranch(Branch branch)
				throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
			jdbcTemplate.update("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ? WHERE branchId = ?",
					new Object[] { branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()});
		}

		public void deleteBranch(Branch branch)
				throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
			jdbcTemplate.update("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] { branch.getBranchId()});
		}
		
		public void deleteBranchById(Integer branchId)
				throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
			jdbcTemplate.update("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] { branchId});
		}

		public List<Branch> readAllBranches()
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return jdbcTemplate.query("SELECT * FROM tbl_library_branch", this);
		}
		public Branch readBranchByPK(Integer pk)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			List<Branch> branches = jdbcTemplate.query("SELECT * FROM tbl_library_branch WHERE branchId  = ?", new Object[] { pk }, this);
			if (branches != null) {
				return branches.get(0);
			} else {
				return null;
			}
		}
		
		@Override
		public List<Branch> extractData(ResultSet rs) throws SQLException {
			List<Branch> branches = new ArrayList<>();
			while(rs.next()){
				Branch b = new Branch();
				b.setBranchId(rs.getInt("branchId"));
				b.setBranchName(rs.getString("branchName"));
				b.setBranchAddress(rs.getString("branchAddress"));
				branches.add(b);
			}
			return branches;
		}

	}

	
	

