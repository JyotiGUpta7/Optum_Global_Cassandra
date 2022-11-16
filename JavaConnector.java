package com.demo.javaconnector;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
// import com.datastax.driver.core.*;

public class JavaConnector {
	public static void main(String[] args) {
		Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		Session session = cluster.connect("firstkeyspace");
		// Strategy - 1 (INSERT)
		session.execute("insert into emp(emp_id,emp_name,emp_age) values (10,'Hariprasad',30)");
		session.execute("insert into emp(emp_id,emp_name,emp_age) values (20,'Bidhan',35)");
		session.execute("insert into emp(emp_id,emp_name,emp_age) values (30,'Titli',20)");
		ResultSet results = session.execute("Select * from emp;");
		for (Row row: results) {
			System.out.print("Emp ID: " + row.getInt("emp_id"));
			System.out.print(", Emp Name: " + row.getString("emp_name"));
			System.out.println(", Emp Age: " + row.getInt("emp_age"));
		}
		
		JavaConnector jc = new JavaConnector();
		jc.addEmp(session,  40, "Ashoke", 33);
		jc.addEmp(session,  50, "Smita", 22);
		
		jc.addEmpQB(session,  60, "Kakoli", 32);
		jc.addEmpQB(session,  70, "Sudhir", 52);
		
		System.out.println("\nDisplaying records using querybuilder() method...");
		Statement stmt = QueryBuilder.select().all().from("emp");
		results = session.execute(stmt);
		for (Row row: results) {
			System.out.print("Emp ID: " + row.getInt("emp_id"));
			System.out.print(", Emp Name: " + row.getString("emp_name"));
			System.out.println(", Emp Age: " + row.getInt("emp_age"));
		}
	}
	
	private void addEmp(Session session, int empid, String empname, int empage) {
		PreparedStatement stmt = session.prepare("insert into emp(emp_id,emp_name,emp_age) values (?,?,?)");
		BoundStatement bdsmt = stmt.bind(empid, empname, empage);
		session.execute(bdsmt);
	}
	
	private void addEmpQB(Session session, int empid, String empname, int empage) {
		Statement stmt = QueryBuilder.insertInto("emp").value("emp_id",empid).value("emp_name", empname).value("emp_age", empage);
		session.execute(stmt);
	}
}

/*
Cassandra Commands -
create keyspace firstkeyspace with replication = {'class':'SimpleStrategy','replication_factor':'1'};
describe keyspaces;
use firstkeyspace;
======================== CREATE TABLE
create table emp (
	emp_id int,
	emp_name text,
	emp_age int,
	primary key((emp_id))
);

*/