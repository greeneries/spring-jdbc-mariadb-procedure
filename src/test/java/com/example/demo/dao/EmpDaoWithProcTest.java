package com.example.demo.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.domain.Emp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmpDaoWithProcTest {
	@Resource(name="empDaoWithProc")
	private EmpDaoWithProc dao;

	@Test
	public void testFindAll() {
		List<Emp> emps = dao.findAll();
		emps.forEach(System.out::println);
	}
	
	@Test
	public void testFindEmpByEmpno() {
		Emp emp = dao.findEmpByEmpno(1);
		System.out.println(emp);
	}
	
	@Test
	public void testCountUsingProc() {
		int count = dao.countUsingProc();
		System.out.println("count = " + count);
	}

	@Test
	public void testCountUsingFn() {
		int count = dao.countUsingFn();
		System.out.println("count = " + count);
	}
}