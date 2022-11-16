package com.cassandra.java.client;

import java.util.List;
import java.util.Scanner;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;

public class CassandraClient {
    // private static final Logger LOG = LoggerFactory.getLogger(CassandraClient.class);
	Scanner scan = new Scanner(System.in);
	CassandraConnector connector;
	Session session;
	BookRepository br;
	KeyspaceRepository ksr;

    public static void main(String args[]) {
    	CassandraClient cc = new CassandraClient();
    	int choice = 0;
    	System.out.println("\nAlways at first issue command 'drop keyspace library' at CQLsh command prompt...");
    	System.out.println("At first execute options 1 & 2 in sequence always before selecting other options...");
    	System.out.println("Before quit always execute options 7 & 8 in sequence...\n");
    	while (choice != 8) {
    		choice = cc.menu();
    		switch (choice) {
    			case 1:  // CREATE
    				System.out.println("Create Keyspace and Database Table option has been selected...");
    				cc.cassandra_connect();
    				System.out.println("Keyspace and Database Tables have been created successfully...\n");
    				
    				break;
    			
    			case 2:  // INSERT
    				System.out.println("Insert Record option has been selected...");
    				cc.insertRecords();
    				System.out.println("All records got inserted successfully...\n");
    				
    				break;
    			
    			case 3:  // SEARCH
    				System.out.println("Search Record option has been selected...");
    				Book searched_book;
    				
    				String book_title = "Title-2";
    				System.out.println("\nSearching against the book title: " + book_title + " using StringBuilder class...");
    				searched_book = cc.br.selectByTitleSB(book_title);   // using StringBuilder
    				if (searched_book == null) {
    					System.out.println("\nUnsuccessful searching against the book title: " + book_title + "...\n");
    				} else {
    					System.out.println("\nSuccessful searching against the book title: " + book_title + "...\n");
    					System.out.println("Booke details shown below -");
    					System.out.println("Book ID: " + searched_book.getId().toString());
    					System.out.println("Book Title: " + searched_book.getTitle());
    					System.out.println("Book Author: " + searched_book.getAuthor());
    					System.out.println("Book Subject: " + searched_book.getSubject());
    					System.out.println("Book Publisher: " + searched_book.getPublisher());
    				}
    				
    				System.out.println("\nSearching against the book title: " + book_title + " using QueryBuilder class...");
    				searched_book = cc.br.selectByTitleQB(book_title);   // using QueryBuilder class
    				if (searched_book == null) {
    					System.out.println("\nUnsuccessful searching against the book title: " + book_title + "...");
    				} else {
    					System.out.println("\nSuccessful searching against the book title: " + book_title + "...\n");
    					System.out.println("Booke details shown below -");
    					System.out.println("Book ID: " + searched_book.getId().toString());
    					System.out.println("Book Title: " + searched_book.getTitle());
    					System.out.println("Book Author: " + searched_book.getAuthor());
    					System.out.println("Book Subject: " + searched_book.getSubject());
    					System.out.println("Book Publisher: " + searched_book.getPublisher());
    				}
    				
    				book_title = "Title-200";
    				System.out.println("Searching against the book title: " + book_title + " using StringBuilder class...\n");
    				searched_book = cc.br.selectByTitleSB(book_title);   // using StringBuilder
    				if (searched_book == null) {
    					System.out.println("Unsuccessful searching against the book title: " + book_title + "...\n");
    				} else {
    					System.out.println("Successful searching against the book title: " + book_title + "...");
    					System.out.println("Book ID: " + searched_book.getId().toString());
    					System.out.println("Book Title: " + searched_book.getTitle());
    					System.out.println("Book Author: " + searched_book.getAuthor());
    					System.out.println("Book Subject: " + searched_book.getSubject());
    					System.out.println("Book Publisher: " + searched_book.getPublisher());
    				}
    				
    				break;
    			
    			case 4:  // DISPLAY
    				System.out.println("Display Record option has been selected...");
    				
    				System.out.println("\nDisplaying all records of Books table using StringBuilder class...");
    				List<Book> results = cc.br.selectAllBooksSB();   // using StringBuilder class
    				for (Book row: results) {
    					System.out.print("ID: " + row.getId() + ", Title: " + row.getTitle());
    					System.out.print(", Author: " + row.getAuthor() + ", Subject: " + row.getSubject());
    					System.out.println(", Publisher: " + row.getPublisher());
    				}
    				
    				System.out.println("\nDisplaying all records of Books table using QueryBuilder class...");
    				results = cc.br.selectAllBooksQB();   // using QueryBuilder class
    				for (Book row: results) {
    					System.out.print("ID: " + row.getId() + ", Title: " + row.getTitle());
    					System.out.print(", Author: " + row.getAuthor() + ", Subject: " + row.getSubject());
    					System.out.println(", Publisher: " + row.getPublisher());
    				}
    				// cc.br.selectAllBookSB().forEach(o -> System.out.println("ID: " + o.getId() + ", Title: " 
    				// + o.getTitle() + ", Author: " + o.getAuthor() + ", Subject: " + o.getSubject() + ", Title: " 
    				// + o.getPublisher()));
 
    				System.out.println("\nDisplaying all records of BooksByTitle table using StringBuilder class...");
    				cc.br.selectAllBooksByTitleSB().forEach(row -> System.out.println("ID: " + row.getId() + ", Title: " + row.getTitle()));   // using StringBuilder class
    				
    				System.out.println("\nDisplaying all records of BooksByTitle table using QueryBuilder class...");
    				cc.br.selectAllBooksByTitleQB().forEach(row -> System.out.println("ID: " + row.getId() + ", Title: " + row.getTitle()));   // using QueryBuilder class
    				
    				System.out.println("\nDisplay operation has been completed successfully...\n");
    				
    				break;
    			
    			case 5:  // UPDATE
    				System.out.println("Update Record option has been selected...");
    				
    				String search_title = new String("Title-2");
    				String new_subject = new String("Subject-222");
    				System.out.println("\nWe are updating subject of old book title " + search_title + " by new subject " + new_subject + " in Books table using StringBuilder class...");
    				cc.br.updatebooksByTitleSB(search_title, new_subject);   // using StringBuilder class
    				
    				search_title = new String("Title-3");    				
    				new_subject = new String("Subject-333");
    				System.out.println("We are updating subject of old book title " + search_title + " by new subject " + new_subject + " in Books table using QueryBuilder class...");
    				cc.br.updatebooksByTitleQB(search_title, new_subject);   // using QueryBuilder class
    				
    				break;
    			
    			case 6:  // DELETE
    				System.out.println("Delete Record option has been selected...");
    				
    				String deleting_title = "Title-5";
    				System.out.println("\nDeleting record with title " + deleting_title + " from BooksByTitle table using StringBuilder class...");
    				cc.br.deletebooksByTitleSB(deleting_title);   // using StringBuilder class
    				
    				deleting_title = "Title-6";
    				System.out.println("\nDeleting record with title " + deleting_title + " from BooksByTitle table using QueryBuilder class...");
    				cc.br.deletebooksByTitleQB(deleting_title);   // using QueryBuilder class
    				
    				break;
    			
    			case 7:  // DROP
    				System.out.println("Delete Database Table and Keyspace option has been selected...");
    				
    				System.out.println("\nDeleting Books table using StringBuilder class...");
    				cc.br.deleteTableSB("books");   // using StringBuilder class
    				
    				System.out.println("\nDeleting BooksByTitle table using QueryBuilder class...");
    		        cc.br.deleteTableQB("booksByTitle");   // using QueryBuilder class
    		        
    		        System.out.println("\nDeleting Library keyspace using StringBuilder class...\n");
  		            cc.ksr.deleteKeyspace("library");
    				
  		            break;
    			
    			case 8:  // QUIT
    				System.out.println("Quit option has been selected...");
    				
    				cc.connector.close();
    				
    				System.out.println("Database connection has been terminated successfully...");
    				System.out.println("\nThis Java application has completed its execution successfully...!!!\n");
    				
    				break;
    		}
    	}
    }
    
    void cassandra_connect() {
    	this.connector = new CassandraConnector();
    	this.connector.connect("127.0.0.1", null);
    	this.session = this.connector.getSession();

    	this.ksr = new KeyspaceRepository(this.session);
    	this.ksr.createKeyspace("library", "SimpleStrategy", 1);
    	this.ksr.useKeyspace("library");

    	this.br = new BookRepository(this.session);
    	this.br.createTableQB();   // using QueryBuilder class
    	this.br.alterTablebooksQB("publisher", "text");   // using QueryBuilder class

    	this.br.createTableBooksByTitleSB();   // using StringBuilder class
    }
    
    void insertRecords() {
    	Book book;
    	
    	book = new Book(UUIDs.timeBased(), "Title-1", "Author-1", "Subject-1", "Publisher-1");
		this.br.insertbooksSB(book);   // using StringBuilder class
		book = new Book(UUIDs.timeBased(), "Title-2", "Author-2", "Subject-2", "Publisher-2");
		this.br.insertbooksPS(book);   // using PreparedStatement class
		book = new Book(UUIDs.timeBased(), "Title-3", "Author-3", "Subject-3", "Publisher-3");
		this.br.insertbooksQB(book);   // using QueryBuilder class
		book = new Book(UUIDs.timeBased(), "Title-4", null, null, null);
		
		this.br.insertbooksByTitleSB(book);   // using StringBuilder class
		book = new Book(UUIDs.timeBased(), "Title-5", null, null, null);
		this.br.insertbooksByTitlePS(book);   // using PreparedStatement class
		book = new Book(UUIDs.timeBased(), "Title-6", null, null, null);
		this.br.insertbooksByTitleQB(book);   // using QueryBuilder class
		
		book = new Book(UUIDs.timeBased(), "Title-7", "Author-7", "Subject-7", "Publisher-7");
		this.br.insertBooksBatchSB(book);   // using StringBuilder class
        book = new Book(UUIDs.timeBased(), "Title-8", "Author-8", "Subject-8", "Publisher-8");
        this.br.insertBooksBatchSB(book);   // using StringBuilder class
        book = new Book(UUIDs.timeBased(), "Title-9", "Author-9", "Subject-9", "Publisher-9");
        this.br.insertBooksBatchSB(book);   // using StringBuilder class
    }
    
    int menu() {
    	int ch = 0;
    	
    	while (ch < 1 || ch > 8) {
    		System.out.println("M A I N  M E N U ...");
    		System.out.println("~~~~~~~~~~~~~~~~~~~~");
    		System.out.println("1 >  C R E A T E  K E Y S P A C E  &  D A T A B A S E  T A B L E S ...");
    		System.out.println("2 >  I N S E R T  R E C O R D  O P E R A T I O N ...");
    		System.out.println("3 >  S E A R C H  R E C O R D  O P E R A T I O N ...");
    		System.out.println("4 >  D I S P L A Y  R E C O R D  O P E R A T I O N ...");    		
    		System.out.println("5 >  U P D A T E  R E C O R D  O P E R A T I O N ...");
    		System.out.println("6 >  D E L E T E  R E C O R D  O P E R A T I O N ...");
    		System.out.println("7 >  D E L E T E  D A T A B A S E  T A B L E S  &  K E Y S P A C E ...");
    		System.out.println("8 >  Q U I T  O P E R A T I O N ...");
    		System.out.print("\nPlease enter your choice number (1 to 8): ");
    		ch = scan.nextInt();
    	}
    	
    	return ch;
    }
}