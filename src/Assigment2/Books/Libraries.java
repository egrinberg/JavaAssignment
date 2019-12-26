package Assigment2.Books;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;


public class Libraries {

    public Library[] libraries;        // a collection of libraries of type array
    public int numberOfLibraries;      // number of libraries in collection


    /**
	 * Constructor accepts number of libraries	 
	 * @param numberOfLibraries is of type integer represent the number of libraries
	 */
    public Libraries(int numberOfLibraries)
	{
		this.numberOfLibraries = numberOfLibraries;
		this.libraries = new Library[numberOfLibraries];
	}
    /**
     * Getter returns the libraries
     * @return array of Library objects 
     */

	public Library[] getLibraries()
	{
		return libraries;
	}
	/**
	 * Setter set the array of libraries to passed libraries
	 * @param libraries is an array of Library objects 
	 */
	public void setLibraries(Library[] libraries)
	{
		if(libraries.length == numberOfLibraries)
		{
			this.libraries = libraries;
		}
		
	}
	
	/** 
	 * Reads the file with fileName and creates an object of type Library
	 * @param library name and file name are strings
	 * @return the created Library object    
	 * @throws UnexpectedUserInputException if the book (with the same tag value) already exist in the library
	 * 										if the line does not have the book and the value tag
	 * @throws NumberFormatException 
	 **/
	public Library buildLibraryFromFile(String libraryName, String fileName) throws NumberFormatException, UnexpectedUserInputException {

        Library library = new Library(libraryName);

        String path = System.getProperty("user.dir");
        Book book = null;
        String s;
        boolean flag = true;
        boolean duplicate = false;
        
         try (BufferedReader br = new BufferedReader(new FileReader(path + "/src/" + fileName))) {
        // if you run locally on your environment use: new FileReader(path + "/src/" + fileName)

            while ((s = br.readLine()) != null)
            {
            	try
            	{
	            	 String[] tok = s.split(",");
	               	 if(tok.length!=2)
	               	 {
	               		 break;
	               	 }
	               	 if(flag)
	               	 {
	               		 book = new Book(tok[0],Integer.parseInt(tok[1])); 
		               	 book.setLibrary(library);
		               	 library.AddBook(book);
		               	 flag = false;
	               	 }
	               	 else 
	               	 {
	               		 book = new Book(tok[0],Integer.parseInt(tok[1]));
	               		 
	               		 for(Book b:library.books)
	               		 {
	               			if(b.equals(book))
	               			 {
	               				 duplicate = true;
	               				 throw new UnexpectedUserInputException("The book " + book + " is already exist in the library " + library.libraryName);
	               					 
	               			 }	               				 
	               		 }
	               		 
	               		 if(!duplicate)
	               		 {
	               			 book.setLibrary(library);
			               	 library.AddBook(book);
	               		 }
	               		 
	               	 }
	               	 
	               		 
	               		 
	               	 
            	}
            	catch(UnexpectedUserInputException e)
            	{
            		System.out.println(e.getMessage());
            	}
            	             	 
          
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        return library;
    }
	
	/** 
	 * Check if the book is part of the library inventory
	 * @param book is object of type Book
	 * @return the first library object where the book is found
	 */
    public Library isThereBookInLibraries(Book book)
    {
    	for(Library lib:libraries)
    	{
    		if(lib.isThereBook(book))
    			return lib;    		
    	}
       return null;
    }
    
  
    /**
     * Searches all libraries for a book that can be rented
     * @param book that is an object of type Book
     * @param requestDate is a string
     * @param dueDate is a string
     * @return returns the first library where the book is available and can be rented
     * @throws RentPeriodException 
     * @throws DateFormatException 
     */

    public Library rentBookAvailable(Book book, String requestDate, String dueDate) throws RentPeriodException, DateFormatException {

        Library foundLibrary = null;
        for(Library lib:libraries)
    	{
    		if(lib.isThereBook(book))
    		{
    			if(lib.rentRequest(book, requestDate, dueDate))
    			{
    				foundLibrary = lib;
    				break;    				
    			}
    		}
    	}     
        return foundLibrary;        
    }
    /**
     * Look inside of all the libraries and finds those that have the specified book
     * @param b is an object of type Book indicates the book to look for.
     * @return Vector<Library> indicates the libraries that the book was found in them.
     */
    public Vector<Library> findBookInAllLibraries(Book b)
    {
    	Vector<Library> foundLibraries = new Vector<Library>();
    	for(Library lib:libraries)
    	{
    		if(lib.isThereBook(b))
    			foundLibraries.add(lib);
      	}  
    	
    	return foundLibraries;
    }
    
    /**
     * Compares the available dates of the book inside of the libraries and return the library
     * that has this book available closest to the requested date
     * @param foundLibraries is of type Vector<Library> indicates the collection of libraries that have this book in the inventory
     * @param book is an object of type Book indicates the wanted book
     * @param requestDay is a String indicated the request day for the book
     * @return Library indicates library that has this book available closest to the requested date
     * @throws DateFormatException
     */
    public Library findClosedAvailableBook(Vector<Library> foundLibraries,Book book,String requestDay) throws DateFormatException
    {
		boolean firstTime = true;
		long days = 0;
		try
		{
			Helper.checkDate(requestDay);
			if (Helper.timeDifference(Helper.getCurrentDate(), requestDay) < 0)
			{
				throw new RentPeriodException("The request date " + requestDay
						+ " should not be before the current date " + Helper.getCurrentDate());

			}
			
			Iterator<Library> it = foundLibraries.iterator();
			
			while(it.hasNext())
			{
				Library lib = it.next();
				if (firstTime)
				{
						days =Helper.timeDifference(requestDay, lib.getAvailableDay(book));
						firstTime = false;
				}
					
				else if(Helper.timeDifference(requestDay, lib.getAvailableDay(book)) < days)
				{
						days = Helper.timeDifference(requestDay, lib.getAvailableDay(book));
				}
					
			}
			
		Iterator<Library> iter = foundLibraries.iterator();
			
			while(iter.hasNext())
			{
				Library availLibrary = iter.next();
				
				if(Helper.timeDifference(requestDay, availLibrary.getAvailableDay(book)) == days)
				{
						return availLibrary;
											
				}
					
			}
			
		}
		catch (RentPeriodException e)
		{
			System.out.println(e.getMessage());			
		}
		catch (DateFormatException e)
		{
			System.out.println(e.getMessage());			
		}
		
				
		return null;
    }
    
    
    /**
     * Returning a string of the libraries essentials
     * @return a String indicating the libraries essentials
     * */
	@Override
	public String toString()
	{
		String s="";
		for(Library l : libraries)
		{
			s+= l + "\n";
		}
		return s;
	}
	/**
     * Unique hashCode
     * @return a unique integer hashCode
     * */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(libraries);
		result = prime * result + numberOfLibraries;
		return result;
	}
	/**
     * Equals function
     * @return a boolean indicating whether the libraries are equal
     * @param an object to compare with
     * */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libraries other = (Libraries) obj;
		if (!Arrays.equals(libraries, other.libraries))
			return false;
		if (numberOfLibraries != other.numberOfLibraries)
			return false;
		return true;
	}
	
    
}
