package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DBException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	private Map<Integer, Department> departments;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
		departments = new HashMap<>();
	}

	@Override
	public void insert(Seller obj) {
		
		try {
			PreparedStatement st = null;
			ResultSet rs = null;
			st = conn.prepareStatement("INSERT INTO seller\r\n"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) \r\n"
					+ "VALUES \r\n"
					+ "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DBException("Unexpected error! No rows affected!");
			}
			
			DB.closeResultSet(rs);
			DB.closeStatement(st);
				
		} catch(SQLException e) {
			throw new DBException(e.getMessage());
		}
		

	}

	@Override
	public void update(Seller obj) {
		try {
			PreparedStatement st = conn.prepareStatement("UPDATE seller \r\n"
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? \r\n"
					+ "WHERE Id = ?", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
			
		}catch(SQLException e) {
			throw new DBException(e.getMessage());
		}

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Seller seller = new Seller();
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName \r\n" + "FROM seller INNER JOIN department \r\n"
							+ "ON seller.DepartmentId = department.Id \r\n" + "WHERE seller.Id = ?");
			st.setInt(1, id);

			rs = st.executeQuery();

			if(rs.next()) {
				Department dep = instantiateDepartment(rs);
				
				seller = instantiateSeller(rs, dep);
				return seller;
			}
			
			return null;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		Statement st = null;
		ResultSet rs = null;
		List<Seller> list = new ArrayList<Seller>();
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT seller.*,department.Name as DepName \r\n"
					+ "FROM seller INNER JOIN department \r\n"
					+ "ON seller.DepartmentId = department.Id\r\n"
					+ "ORDER BY Name");
			
			while(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, dep);
				list.add(seller);
			}
			return list;
			
		}catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDepartment(dep);
		
		return seller;
	}
	
	private Department instantiateDepartment(ResultSet sr) throws SQLException{
		Department dep = departments.get(sr.getInt("DepartmentId"));
		
		if(dep == null) {
			Department dep1 = new Department();
			dep1.setId(sr.getInt("DepartmentId"));
			dep1.setName(sr.getString("DepName"));
			
			dep = dep1;
		}
		
		return dep;
		
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Seller seller = new Seller();
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName \r\n"
					+ "FROM seller INNER JOIN department \r\n"
					+ "ON seller.DepartmentId = department.Id\r\n"
					+ "WHERE DepartmentId = ?\r\n"
					+ "ORDER BY Name");
			st.setInt(1, department.getId());

			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			
			while(rs.next()) {
				Department dep = instantiateDepartment(rs);
				seller = instantiateSeller(rs, dep);
				list.add(seller);
			}
			
			return list;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
		
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
