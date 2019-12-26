package Assigment2.Books;

import java.util.Iterator;
import java.util.Vector;

public class Library implements MaxTagValue
{

	String libraryName;
	Vector<Book> books;

	/**
	 * Constructor accepts a string for libraryName
	 * 
	 * @param libraryName of type string
	 */
	public Library(String libraryName)
	{
		this.libraryName = libraryName;
		books = new Vector<Book>();

	}

	public void AddBook(Book book)
	{
		books.addElement(book);
	}

	/**
	 * Search all the books from a library and returns the maximum value tag
	 * 
	 * @return an integer indicating the maximum value tag
	 */
	public int findMaximumValueTag()
	{

		int maxElement = -100;

		for (Book b : books)
		{
			if (b.getValueTag() > maxElement)
			{
				maxElement = b.getValueTag();
			}
		}

		return maxElement;
	}

	public boolean isThereBook(Book book)
	{
		for (Book b : books)
		{
			if (b.equals(book))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the book is available in this library for the specified period
	 * of time
	 * 
	 * @param wanted
	 *            is an object of type Book that indicates the book that wants
	 *            to be borrowed
	 * @param requestDate
	 *            is a string indicated the date when the book can be rented
	 * @param dueDate
	 *            is a string indicated the date when the book must be returned
	 *            to the library
	 * @return a boolean indicated if book can be borrowed from this library
	 * @throws DateFormatException 
	 **/

	public boolean rentRequest(Book wanted, String requestDate, String dueDate)
			throws RentPeriodException, DateFormatException
	{

		try
		{
			Helper.checkDate(requestDate);
			Helper.checkDate(dueDate);
			if (Helper.timeDifference(Helper.getCurrentDate(), requestDate) < 0)
			{
				throw new RentPeriodException("The request date " + requestDate
						+ " should not be before the current date " + Helper.getCurrentDate());

			}
			else if(Helper.timeDifference(requestDate, dueDate) < 0)
			{
				throw new RentPeriodException("The due date " + dueDate
						+ " should not be before the request date " + requestDate);
			}
			else
			{
				for (Book b : books)
				{
					if (b.equals(wanted) && b.isRented(this))
					{
						if (Helper.timeDifference(b.availableDate(this), requestDate) < 0)
						{
							return false;
						}						
					}
				}

			}

		} catch (RentPeriodException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		catch (DateFormatException e)
		{
			System.out.println(e.getMessage());
			return false;
		}


		return true;
	}

	public boolean isBookAvailable(Book book) throws DateFormatException
	{
		for (Book b : books)
		{
			if (b.equals(book))
			{
				if (Helper.timeDifference(b.availableDate(this), Helper.getCurrentDate()) >= 0)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returning a string of the available date of the book
	 * @param book is an Object of type Book indicates the book that wanted to get the available date
	 * @return String indicates the available date of the book
	 */
	public String getAvailableDay(Book book)
	{
		for(Book b: books)
		{
			if(b.equals(book))
			{
				return b.availableDate(this);
			}

		}
		return "";
	}

	/**
	 * Returning a string of the library name and the book essentials
	 * 
	 * @return a String indicating the library name and the book essentials
	 */
	@Override
	public String toString()
	{
		String s = "";
		s = "Library= " + libraryName + "\n[\n";
		for (Book b : books)
		{
			s += b + "\n";
		}
		s += "]\n";
		return s;
	}

	/**
	 * Unique hashCode
	 * 
	 * @return a unique integer hashCode
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((books == null) ? 0 : books.hashCode());
		result = prime * result + ((libraryName == null) ? 0 : libraryName.hashCode());
		return result;
	}

	/**
	 * Equals function
	 * 
	 * @return a boolean indicating whether the library are equals
	 * @param an object to compare with
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof Library))
			return false;

		Library other = (Library) obj;

		if (!libraryName.equals(other.libraryName))
			return false;
		if (!books.equals(other.books))
			return false;

		return true;
	}

}
