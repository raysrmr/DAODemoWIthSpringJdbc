package com.rays.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rays.model.Trainees;

public class TrainnesDAOImpl implements TraineesDAO {

private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insertTrainees(Trainees T) {
		String query = "insert into Trainees values(?,?,?,?)";
		Object[] args = new Object[] { T.getAid(), T.gettName(), T.gettAge(), T.gettCity() };

		int out = jdbcTemplate.update(query, args);

		if (out != 0) {
			System.out.println("Employee saved with id=" + T.getAid());
		} else
			System.out.println("Employee save failed with id=" + T.getAid());
		return out;

	}

	@Override
	public List<Trainees> getAllTrainees() {
		List<Trainees> tList = new ArrayList<Trainees>();
		String query = "select * from trainees";
		List<Map<String, Object>> traineeRows = jdbcTemplate.queryForList(query);

		for (Map<String, Object> traineeRow : traineeRows) {
			Trainees trainee = new Trainees();
			trainee.setAid(Integer.parseInt(String.valueOf(traineeRow.get("aid"))));
			trainee.settName((String) traineeRow.get("t_Name"));
			trainee.settAge(Integer.parseInt(String.valueOf(traineeRow.get("t_Age"))));
			trainee.settName((String) traineeRow.get("t_City"));
			tList.add(trainee);
		}
		return tList;
	}

	@Override
	public Trainees getTraineesById(int aid) {

		String query = "select * from Trainees where aid = ?";

		// using RowMapper anonymous class, we can create a separate RowMapper for reuse
		Trainees trainee = jdbcTemplate.queryForObject(query, new Object[] { aid }, new RowMapper<Trainees>() {

			public Trainees mapRow(ResultSet rs, int rowNum) throws SQLException {
				Trainees trainee = new Trainees();
				trainee.setAid(rs.getInt(1));
				trainee.settName(rs.getString(2));
				trainee.settAge(rs.getInt(3));
				trainee.settCity(rs.getString(4));
				return trainee;
			}
		});
		return trainee;
	}

	@Override
	public int updateTrainees(Trainees trainee) {		
		String query = "update trainees set t_name=?,t_age=?,t_city=? where aid=?";
		Object[] args = new Object[] {trainee.gettName(), trainee.gettAge(), trainee.gettCity(), trainee.getAid()  };

		int out = jdbcTemplate.update(query, args);
		
		
		return out;
	}

	@Override
	public int deleteTrainees(int aid) {	
		String query = "delete from trainees where aid=?";
		int out = jdbcTemplate.update(query, aid);
		return out;
	}

}
