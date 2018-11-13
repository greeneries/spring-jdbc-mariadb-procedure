package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Emp;

@Repository
public class EmpDaoWithProc {
	@Autowired
	public JdbcTemplate jdbcTemplate;

	private SimpleJdbcCall procFindEmpAll;
	private SimpleJdbcCall procFindEmpByEmpno;
	private SimpleJdbcCall procEmpCount;
	private SimpleJdbcCall fnEmpCount;

	private RowMapper<Emp> rowMapper = new RowMapper<Emp>() {
		@Override
		public Emp mapRow(ResultSet rs, int rowNum) throws SQLException {
			Emp e = new Emp();
			e.setEmpno(rs.getInt("empno"));
			e.setEname(rs.getString("ename"));
			e.setJob(rs.getString("job"));
			e.setSal(rs.getDouble("sal"));

			return e;
		}
	};

	@PostConstruct
	public void init() {
		this.procFindEmpAll = new SimpleJdbcCall(jdbcTemplate)
                  .withProcedureName("pro_find_all")
                  .returningResultSet("returnedEmps", rowMapper);
		this.procFindEmpByEmpno = new SimpleJdbcCall(jdbcTemplate)
                  .withProcedureName("pro_find_one");
		this.procEmpCount = new SimpleJdbcCall(jdbcTemplate)
                  .withProcedureName("pro_emp_count");
		this.fnEmpCount = new SimpleJdbcCall(jdbcTemplate)
                  .withFunctionName("fn_emp_count");
	}

	@SuppressWarnings("unchecked")
	public List<Emp> findAll() {
		Map<String, Object> out = procFindEmpAll.execute();
		return (List<Emp>) out.get("returnedEmps");
	}

	public Emp findEmpByEmpno(int empno) {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
                  .addValue("in_empno", empno);
		Map<String, Object> out = procFindEmpByEmpno.execute(parameterSource);

		Emp emp = new Emp();
		emp.setEmpno(empno);
		emp.setEname((String) out.get("out_ename"));
		emp.setJob((String) out.get("out_job"));
		emp.setSal((Double) out.get("out_sal"));

		return emp;
	}

	public int countUsingProc() {
		Map<String, Object> out = procEmpCount.execute();
		return (int) out.get("out_count");
	}

	public int countUsingFn() {
		return fnEmpCount.executeFunction(Integer.class);
	}

}