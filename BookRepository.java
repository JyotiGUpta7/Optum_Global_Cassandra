package com.cassandra.java.client;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.datastax.driver.core.schemabuilder.SchemaStatement;

public class BookRepository {
	private static final String TABLE_NAME = "books";
	private static final String TABLE_NAME_BY_TITLE = TABLE_NAME + "ByTitle";
	private Session session;

	public BookRepository(Session session) {
		this.session = session;
	}

	public void createTableQB() {   // using QueryBuilder class
		SchemaStatement query = SchemaBuilder.createTable(TABLE_NAME)
				.addPartitionKey("title", DataType.varchar())
				.addColumn("id", DataType.uuid())
				.addColumn("author", DataType.varchar())
				.addColumn("subject", DataType.varchar())
				.ifNotExists();
		session.execute(query);
		    		  
		//        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(")
		//        		.append("id uuid, ").append("title text,")
		//        		.append("author text,").append("subject text, ").append("PRIMARY KEY title);");
		//        final String query = sb.toString();
		//        session.execute(query);
	}

	public void createTableBooksByTitleSB() {   // using StringBuilder class
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME_BY_TITLE)
				.append("(").append("id uuid, ").append("title text,").append("PRIMARY KEY (title));");

		final String query = sb.toString();
		session.execute(query);
	}

	public void alterTablebooksQB(String columnName, String columnType) {   // using QueryBuilder class
		SchemaStatement query = SchemaBuilder.alterTable(TABLE_NAME).addColumn("publisher").type(DataType.varchar());
		session.execute(query);
		
		//		StringBuilder sb = new StringBuilder("ALTER TABLE ").append(TABLE_NAME).append(" ADD ")
		//				.append(columnName).append(" ").append(columnType).append(";");
		//		final String query = sb.toString();
		//		session.execute(query);
	}

	public void insertbooksSB(Book book) {   // using StringBuilder class
		StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append("(id, title, author, subject, publisher) ")
				.append("VALUES (").append(book.getId()).append(", '").append(book.getTitle()).append("', '")
				.append(book.getAuthor()).append("', '").append(book.getSubject()).append("', '")
				.append(book.getPublisher()).append("');");

		final String query = sb.toString();
		session.execute(query);
	}

	public void insertbooksPS(Book book) {   // using PreparedStatement class
		PreparedStatement stmt = session.prepare("Insert into " + TABLE_NAME + "(id,title,author,subject,publisher) VALUES (?, ?, ?, ?, ?)");
		BoundStatement bdstmt = stmt.bind(book.getId(), book.getTitle(), book.getAuthor(), book.getSubject(), book.getPublisher());
		session.execute(bdstmt);
	}

	public void insertbooksQB(Book book) {   // using QueryBuilder class
		Statement stmt = QueryBuilder.insertInto(TABLE_NAME).value("id", book.getId()).value("title", book.getTitle()).value("author", book.getAuthor())
				.value("subject", book.getSubject()).value("publisher", book.getPublisher());
		session.execute(stmt);
	}

	public void insertbooksByTitleSB(Book book) {   // using StringBuilder class
		StringBuilder sb = new StringBuilder("INSERT INTO ").append(TABLE_NAME_BY_TITLE).append("(id, title) ")
				.append("VALUES (").append(book.getId()).append(", '").append(book.getTitle()).append("');");

		final String query = sb.toString();
		session.execute(query);
	}

	public void insertbooksByTitlePS(Book book) {   // using PreparedStatement class
		PreparedStatement stmt = session.prepare("Insert into " + TABLE_NAME_BY_TITLE + "(id,title) VALUES (?, ?)");
		BoundStatement bdstmt = stmt.bind(book.getId(), book.getTitle());
		session.execute(bdstmt);
	}

	public void insertbooksByTitleQB(Book book) {   // using QueryBuilder class
		Statement stmt = QueryBuilder.insertInto(TABLE_NAME_BY_TITLE).value("id", book.getId()).value("title", book.getTitle());
		session.execute(stmt);
	}

	public void insertBooksBatchSB(Book book) {   // using StringBuilder class
		StringBuilder sb = new StringBuilder("BEGIN BATCH ").append("INSERT INTO ").append(TABLE_NAME)
				.append("(id, title, author, subject, publisher) ").append("VALUES (").append(book.getId()).append(", '")
				.append(book.getTitle()).append("', '").append(book.getAuthor()).append("', '").append(book.getSubject())
				.append("', '").append(book.getPublisher()).append("');")
				.append("INSERT INTO ").append(TABLE_NAME_BY_TITLE).append("(id, title) ").append("VALUES (")
				.append(book.getId()).append(", '").append(book.getTitle()).append("');")
				.append("APPLY BATCH;");

		final String query = sb.toString();
		session.execute(query);
	}

	public Book selectByTitleSB(String title) {   // using StringBuilder class
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME)
				.append(" WHERE title = '").append(title).append("';");

		final String query = sb.toString();

		ResultSet rs = session.execute(query);

		List<Book> books = new ArrayList<Book>();
		int flag = 0;
		for (Row r : rs) {
			Book s = new Book(r.getUUID("id"), r.getString("title"), r.getString("author"),
					r.getString("subject"), r.getString("publisher"));
			books.add(s);
			flag = 1;
		}
		if (flag == 0) {
			return null;
		} else {
			return books.get(0);
		}
	}
	public Book selectByTitleQB(String search_title) {   // using QueryBuilder class
		Statement query = QueryBuilder.select().all().from(null, TABLE_NAME)
                .where(QueryBuilder.eq("title", search_title));
		
		//		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME)
		//				.append(" WHERE title = '").append(title).append("';");
		//		final String query = sb.toString();

		ResultSet rs = session.execute(query);

		List<Book> books = new ArrayList<Book>();
		int flag = 0;
		for (Row r : rs) {
			Book s = new Book(r.getUUID("id"), r.getString("title"), r.getString("author"),
					r.getString("subject"), r.getString("publisher"));
			books.add(s);
			flag = 1;
		}
		if (flag == 0) {
			return null;
		} else {
			return books.get(0);
		}
	}

	public List<Book> selectAllBooksSB() {   // using StringBuilder class
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

		final String query = sb.toString();
		ResultSet rs = session.execute(query);

		List<Book> books = new ArrayList<Book>();

		for (Row r : rs) {
			Book book = new Book(r.getUUID("id"), r.getString("title"), r.getString("author")
					, r.getString("subject"), r.getString("publisher"));
			books.add(book);
		}
		return books;
	}

	public List<Book> selectAllBooksQB() {   // using QueryBuilder class
		Statement stmt = QueryBuilder.select().all().from(TABLE_NAME);
		ResultSet results = session.execute(stmt);

		List<Book> books = new ArrayList<Book>();

		for (Row r : results) {
			Book book = new Book(r.getUUID("id"), r.getString("title"), r.getString("author"),
					r.getString("subject"), r.getString("publisher"));
			books.add(book);
		}
		return books;
	}

	public List<Book> selectAllBooksByTitleSB() {   // using StringBuilder class
		StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME_BY_TITLE);

		final String query = sb.toString();
		ResultSet rs = session.execute(query);

		List<Book> books = new ArrayList<Book>();

		for (Row r : rs) {
			Book book = new Book(r.getUUID("id"), r.getString("title"), null, null, null);
			books.add(book);
		}
		return books;
	}

	public List<Book> selectAllBooksByTitleQB() {   // using QueryBuilder class
		Statement stmt = QueryBuilder.select().all().from(TABLE_NAME_BY_TITLE);
		ResultSet results = session.execute(stmt);

		List<Book> books = new ArrayList<Book>();

		for (Row r : results) {
			Book book = new Book(r.getUUID("id"), r.getString("title"), null, null, null);
			books.add(book);
		}
		return books;
	}

	public void updatebooksByTitleSB(String search_title, String new_subject) {   // using StringBuilder class
		StringBuilder sb = new StringBuilder("UPDATE ").append(TABLE_NAME).append(" SET subject = '")
				.append(new_subject).append("' WHERE title = '").append(search_title).append("';");
		
		// StringBuilder sb = new StringBuilder("UPDATE ").append(TABLE_NAME).append(" SET subject = '")
		// .append(new_subject).append("' WHERE id = ").append(UUID.fromString(search_id)).append(" and title = '")
		// .append(search_title).append("';");
		
		final String query = sb.toString();
		session.execute(query);
		System.out.println("\nUpdate operation has been completed successfully...\n");
	}
	
	public void updatebooksByTitleQB(String search_title, String new_subject) {   // using QueryBuilder class
		Statement query = QueryBuilder.update(null, TABLE_NAME)
                .with(QueryBuilder.set("subject", new_subject))
                .where((QueryBuilder.eq("title", search_title)));

		//		StringBuilder sb = new StringBuilder("UPDATE ").append(TABLE_NAME).append(" SET subject = '")
		//				.append(new_subject).append("' WHERE title = '").append(search_title).append("';");		
		//		final String query = sb.toString();
		
		session.execute(query);
		System.out.println("\nUpdate operation has been completed successfully...\n");
	}

	public void deletebooksByTitleSB(String title) {   // using StringBuilder class
		StringBuilder sb = new StringBuilder("DELETE FROM ").append(TABLE_NAME_BY_TITLE).append(" WHERE title = '")
				.append(title).append("';");

		final String query = sb.toString();
		session.execute(query);
		System.out.println("\nDelete operation has been completed successfully...\n");
	}

	public void deletebooksByTitleQB(String title) {   // using QueryBuilder class
		Statement query = QueryBuilder.delete().from(TABLE_NAME_BY_TITLE)
                .where(QueryBuilder.eq("title", title));
        
		// Statement query = QueryBuilder.select().all().from(TABLE_NAME);
		
		session.execute(query);
		System.out.println("\nDelete operation has been completed successfully...\n");
	}

	public void deleteTableSB(String tableName) {   // using StringBuilder class
		StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(tableName);

		final String query = sb.toString();
		session.execute(query);
	}
	
	public void deleteTableQB(String tableName) {   // using QueryBuilder class
		SchemaStatement dropTable = SchemaBuilder.dropTable(tableName).ifExists();
		session.execute(dropTable);
	}
}