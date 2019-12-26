package Assigment2.Books;

/**
 * Last Name: Grinberg
 * First Name: Ekaterina
 * Student ID: 067867051
 * codeboard UserName: egrinberg
 */
import java.util.Vector;

public class Main {

    
	public static void main(String[] args) throws RentPeriodException, DateFormatException, NumberFormatException, UnexpectedUserInputException {


        /* TASK 1 - build libraries from files - at least two libraries */

        System.out.println("\n\n *" + " TASK 1 " + "*");
        Libraries ls = new Libraries(2);
        Library[] libraries = {ls.buildLibraryFromFile("Newnham", "NewnhamLibrary.txt"),
        					   ls.buildLibraryFromFile("York", "YorkLibrary.txt")}; 
        ls.setLibraries(libraries);
        System.out.println(ls);
        
        /* TASK 2 - ask for a book that is not in any library inventory */

        System.out.println("\n\n *" + " TASK 2 " + "*");
        Book book = new Book("C++",20);
        System.out.println("-- Check if the " + book + " is exist in the libraries --");
        Library library = ls.isThereBookInLibraries(book);
        if (library == null)
            System.out.println(Helper.printNonexistent(book));
        else System.out.println("The book " + book + " is exist in " + library.libraryName);

         /* TASK 3 - ask for a book that is in a library inventory
         *  issue a rent request and print the bookEssentials of Database Management
         *  issue the same rent request and print the book
         *  return the book
         *  issue the rent request with new dates and print the book
         */
        System.out.println("\n\n *" + " TASK 3 " + "*");
        //ask for a book that is in a library inventory
        book = new Book("Database System Concepts",20);
        System.out.println("-- Check if the " + book + " is exist in the library Newnham --");
        for(Book b:libraries[0].books)
        {
        	if(b.equals(book))
        	{
        		System.out.println("The book " + book + " is exist in " + libraries[0].libraryName);
        	}
        }
       
        //issue a rent request and print the bookEssentials of Database Management
        String rentDate = "04/14/2017", dueDate="04/25/2017";
        System.out.println("-- Check if the book is available --");
       	
        if(libraries[0].rentRequest(book, rentDate, dueDate))
        {
        	System.out.println(Helper.printAvailable(book, rentDate, libraries[0])); 

        	for(Book b: libraries[0].books)
        	{
        		if(b.equals(book))
        		{
        			b.rentBook(rentDate, dueDate, libraries[0]);
        			System.out.println("-- Rent the book  --");
        			System.out.println(b);
        		}
        	}
        }
        else
        {
        	System.out.println(Helper.printUnavailable(book, rentDate));        	
        }
	

        //issue the same rent request and print the book
        
        System.out.println("-- Check if the book is available --");
        if(libraries[0].rentRequest(book, rentDate, dueDate))
        {
        	System.out.println(Helper.printAvailable(book, rentDate, libraries[0])); 
        	
        	for(Book b: libraries[0].books)
        	{
        		if(b.equals(book))
        		{
        			b.rentBook(rentDate, dueDate, libraries[0]);
        			System.out.println("-- Rent the book  --");
        			System.out.println(b);
        		}
        	}
        }
        else
        {
        	System.out.println(Helper.printUnavailable(book, rentDate));
        	
        }
                
        //return book
        System.out.println("-- Return the book -- ");
        for(Book b: libraries[0].books)
    	{
    		if(b.equals(book) && b.isRented(libraries[0]))
    		{
    			b.returnBook(libraries[0]);
    			System.out.println("The book "+ b + " is returned");
    			
    		}
    	
    	}
        //issue the rent request with new dates and print the book
        String newRentDate = "04/26/2017", newDueDate="04/30/2017";        
        System.out.println("-- Check if the book is available --");
        if(libraries[0].rentRequest(book, newRentDate, newDueDate))
        {
        	System.out.println(Helper.printAvailable(book, newRentDate, libraries[0])); 
        	
        	for(Book b: libraries[0].books)
        	{
        		if(b.equals(book))
        		{
        			b.rentBook(newRentDate, newDueDate, libraries[0]);
        			System.out.println("-- Rent the book  --");
        			System.out.println(b);
        		}
        	}
        }
        else
        {
        	System.out.println(Helper.printUnavailable(book, newRentDate));
        	
        }
               
         /* TASK 4 - ask for the same book in all libraries
          * look for the same book in all libraries and return a library, if the book is in the library inventory
          * look for the same book in all libraries and return a library, if the book is available to be borrowed from that library
          * (the library returned is the first library found from the array of libraries)
          */
        System.out.println("\n\n *" + " TASK 4 " + "*");
        
        //ask for the same book in all libraries
        System.out.println("Check if the " + book + " is exist in the libraries");
        Vector<Library> foundLibraries = ls.findBookInAllLibraries(book);
        if(foundLibraries != null)
        {
        	for(Library lib:foundLibraries)
        	{        		
                System.out.println("The book " + book + " is exist in " + lib.libraryName); 
        	}
        }
        else
        {
        	System.out.println(Helper.printNonexistent(book));
        }
        //look for the same book in all libraries and return a library, if the book is in the library inventory
        System.out.println("-- Check if the " + book + " is exist in the libraries,return first library found --");
        library = ls.isThereBookInLibraries(book);
        if (library == null)
        {
        	System.out.println(Helper.printNonexistent(book));
        } 
        else
        {
        	System.out.println("The book " + book + " is exist in " + library.libraryName);        	
        }
        
        //look for the same book in all libraries and return a library, if the book is available to be borrowed from that library*.
        System.out.println("-- Check if the " + book + " is exist in the libraries,return first library where the book is available to be borrowed --");
        
        for(Library lib:foundLibraries)
    	{        		
            if(lib.isBookAvailable(book))
            {
            	System.out.println("The book " + book + " is available to be borrowed from " + lib.libraryName);
            }
    	}
      
        
        /* TASK 5 - calculate maximum value tag for each library */
        System.out.println("\n\n *" + " TASK 5 " + "*");
       
        for(Library lib:libraries)
        {
        	System.out.println("The maximum value tag for " + lib.libraryName + " is " + lib.findMaximumValueTag());
        	
        }

         /* TASK 6 - inquire about a book - it is available? when does it become available, etc. */
        System.out.println("\n\n *" + " TASK 6 " + "*");
        
        book = new Book("Java: The Complete Reference Ninth Edition",45);
        System.out.println("-- Is the "+ book+" available in Newnham campus? --");
        
        if(libraries[0].isBookAvailable(book))
        {
        	System.out.println("The book " + book + " is available to be borrowed from " + libraries[0].libraryName );
        	
        }
        else
        {
        	System.out.println("The book " + book + " is not available to be borrowed from " + libraries[0].libraryName );
        	System.out.println("-- Is the "+ book+" overdue in Newnham campus? --");
            for (Book b: libraries[0].books)
            {
            	if(b.equals(book))
            	{
            		if(b.isBookOverdue())
            		{

                    	System.out.println("The book " + book + " is overdue in " + libraries[0].libraryName );
            		}
            		else
            		{
            			System.out.println("The book " + book + " is not overdue in " + libraries[0].libraryName );
            		}
            	}
            }
        }
        
        
        
       
        System.out.println("-- Could the book "+ book+" be found in more than one library? --");
        foundLibraries = ls.findBookInAllLibraries(book);
        if(foundLibraries != null)
        {
        	for(Library lib:foundLibraries)
        	{        		
                System.out.println("The book " + book + " is exist in " + lib.libraryName); 
        	}
        }
        else
        {
        	System.out.println(Helper.printNonexistent(book));
        }
        
        System.out.println("-- When can the book " + book + " be borrowed? --");
        
        for(Library lib:foundLibraries)
    	{    
        	for(Book b:lib.books)
        	{
        		if(b.equals(book))
        		{
        			System.out.println("The book " + book + " is available to be borrowed from " + lib.libraryName + " on " + b.availableDate(lib)); 
        		}
        	}
            
    	}
        /* TASK 7 - If a book is rented from all libraries, find a library that has this book available closest to the requested date. */
        //rent the same book from task 6 from both of the libraries
        
        System.out.println("\n\n *" + " TASK 7 " + "*");
        newRentDate = "04/14/2017";
        newDueDate="04/25/2017";
        
        System.out.println("-- Check if the book is available at Newnham --");
        if(libraries[0].rentRequest(book, newRentDate, newDueDate))
        {
        	System.out.println(Helper.printAvailable(book, newRentDate, libraries[0])); 
        	
        	for(Book b: libraries[0].books)
        	{
        		if(b.equals(book))
        		{
        			b.rentBook(newRentDate, newDueDate, libraries[0]);
        			System.out.println("-- Rent the book  --");
        			System.out.println(b);
        		}
        	}
        }
        else
        {
        	System.out.println(Helper.printUnavailable(book, newRentDate));
        	
        }
        
        newRentDate = "04/16/2017";
        newDueDate="04/20/2017";
        
        System.out.println("-- Check if the book is available at York --");
        if(libraries[1].rentRequest(book, newRentDate, newDueDate))
        {
        	System.out.println(Helper.printAvailable(book, newRentDate, libraries[0])); 
        	
        	for(Book b: libraries[1].books)
        	{
        		if(b.equals(book))
        		{
        			b.rentBook(newRentDate, newDueDate, libraries[1]);
        			System.out.println("-- Rent the book  --");
        			System.out.println(b);
        		}
        	}
        }
        else
        {
        	System.out.println(Helper.printUnavailable(book, newRentDate));
        	
        }
        
        //If a book is rented from all libraries, find a library that has this book available closest to the requested date.
        String newRequestDay = "04/17/2017", newReturnDay = "04/30/2017";
        System.out.println("-- Request the book "+ book+" on " + newRequestDay + " --");
        
        int count = 0;
        foundLibraries = ls.findBookInAllLibraries(book);
        if(foundLibraries != null)
        {
        	for(Library lib:foundLibraries)
        	{        		
                System.out.println("The book " + book + " is exist in " + lib.libraryName);
                if(!lib.isBookAvailable(book))
                {
                	System.out.println("The book " + book + " is not available " + lib.libraryName);
                	count++;
                }
        	}
        }
        else
        {
        	System.out.println(Helper.printNonexistent(book));
        }
        
        if(count == foundLibraries.size())
        {
        	 System.out.println("The book " + book + " is rented from all libraries ");
        	 Library closestAvailableLibrary = ls.findClosedAvailableBook(foundLibraries,book,newRequestDay);
             System.out.println("Library "+ closestAvailableLibrary.libraryName + " has this book " + book + " available (available date: " + closestAvailableLibrary.getAvailableDay(book) +" ) closest to the requested date.");
        }
        
        
        
    }
}
