package com.example.demo.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class Emp {
	private int empno;
	private String ename;
	private String job;
	private double sal;
}