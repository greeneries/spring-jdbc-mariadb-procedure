# spring-jdbc-mariadb-procedure


##### Spring Boot Project 생성 환경설정
```
STS >> New >> Spring Starter Project

Project Name : spring-jdbc-mariadb-procedure
Type : Maven
Packaging : Jar
Java : 8
Package : com.example.demo
Spring Boot Version : 2.0.6.RELEASE

Project Dependencies : Web, Lombok, JDBC, MySQL
```

##### 1. database 먼저 생성하기
```
create database topcredue;
```

##### 2. DB에서 procedure 생성하기
```
use topcredue;

--==============================================
-- # 프로시저
-- 결과를 resultset으로 처리하기 위해서 함수 대신 프로시저를 사용해야 한다.
-- (https://dev.mysql.com/doc/refman/8.0/en/create-function-udf.html)
-- 프로시저에서 사용한 질의문에 기반하여 implicit objects가 리턴된다.
-- 실행방법:
-- call pro_find_all;
--==============================================

DELIMITER //

DROP PROCEDURE IF EXISTS pro_find_all//

CREATE PROCEDURE pro_find_all()
BEGIN
	SELECT * FROM emp;
END
//

DELIMITER ;

--==============================================
-- # 프로시저
-- 실행방법:
-- call pro_find_one(1, @ename, @job, @sal);
-- SELECT @ename, @job, @sal;
--==============================================

DELIMITER //

DROP PROCEDURE IF EXISTS pro_find_one//

CREATE PROCEDURE pro_find_one ( 
  IN in_empno INT, 
  OUT out_ename VARCHAR(100), 
  OUT out_job VARCHAR(100), 
  OUT out_sal DOUBLE) 
BEGIN 
  SELECT ename, job, sal
  FROM emp WHERE empno = in_empno
  INTO out_ename, out_job, out_sal;
END
//

DELIMITER ;

--==============================================
-- # 프로시저
-- 실행방법:
-- call pro_emp_count(@count);
-- select @count;
--==============================================

DELIMITER //

DROP PROCEDURE IF EXISTS pro_emp_count//

CREATE PROCEDURE pro_emp_count(OUT out_count INT)
BEGIN
	SELECT COUNT(*) FROM emp INTO out_count;
END
//

DELIMITER ;

--==============================================
-- # 함수
-- 프로시저와 비슷해 보이지만 함수는 결과값을 리턴할 수 있다.
-- 작성문법: 
-- CREATE FUNCTION 함수이름(인자 인자타입) RETURNS 반환 타입
-- BEGIN 
--	 sql문
--	 RETURN 반환값 식
-- END
--
-- 실행방법:
-- SELECT fn_emp_count();
--==============================================

DELIMITER //
CREATE FUNCTION fn_emp_count() RETURNS INT
BEGIN
	DECLARE result INT;
	SELECT count(*) FROM emp INTO result;
	RETURN result;
END
//
DELIMITER ;
```
