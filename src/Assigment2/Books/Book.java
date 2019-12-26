package Assigment2.Books;


/**
* The Book class implements ...
*
* @author  Ekaterina Grinberg
*/
class Book {

    String       bookName;    // the book name
    int          valueTag;    // an integer between -100 and 100
    Library      library;     // the library having this book in its inventory
    RentSettings rs;          // rent settings
    
    /**
     * Constructor accepts a string for book name and an integer for value tag
     * @param bookName is a string that indicates the name of the book
     * @param valueTag is an integer indicated the value tag of the book
     * @throws UnexpectedUserInputException
     */

    public Book(String bookName, int valueTag) throws UnexpectedUserInputException
    {
    	if(valueTag < -100 || valueTag > 100)
    	{
    		throw new UnexpectedUserInputException("Book{book name= "+ bookName +" value tag= "+ valueTag +" } - value tag should be between -100 and 100.");
    	}
    	else
    	{
    		this.bookName = bookName;
        	this.valueTag=valueTag;
        	library = null;
        	rs=null;
    		
    	}
    	
    }
    /**
     * Getting the name of the book
     * @return string indicating the name of the book
     */
    public String getBookName()
	{
		return bookName;
	}
	/**
	 * Getting the Library object of the book
	 * @return Library object indicating the library that is associated with the book
	 */
	public Library getLibrary()
	{
		return library;
	}
	/**
	 * Setting a library object that associated with the book
	 * @param library of type Library object
	 */

	public void setLibrary(Library library)
	{
		if(library != null)
			this.library = library;
	}
	
	/**
	 * Get the value tag of the book
	 * @return valueTag of type integer indicates the value tag of the book
	 */

	public int getValueTag()
	{
		return valueTag;
	}
	
	/**
	 * Rents a book accepts 2 string for rent date and return date and the library from where the book is borrowed
	 * @param rentDate is a string indicating the date when the book is rented
	 * @param dueDate is a string indicated the date when the book must be returned to the library
	 * @param library is a Library object indicating the library from where the book is borrowed
	 * @return boolean indicated that no exception is thrown and the rent request is succeed
	 * @throws RentPeriodException if rentDate > dueDate
	 * @throws DateFormatException if dates are not valid
	 */
    public boolean rentBook(String rentDate, String dueDate, Library library) throws RentPeriodException, DateFormatException 
    {
    	
    	try
    	{
    		setRs(new RentSettings(rentDate,dueDate,library));
    		return true;    		    		
    		   		
    	}
    	catch(DateFormatException e)
    	{
    		e.getMessage();
    		return false;
    	}
    	catch(RentPeriodException e)
    	{
    		e.getMessage();
    		return false;
    	}
        
    }

    
    /**
     * Return the book to the library and destroy the RentSettings object for this book
     * @param library is a Library object indicates the library to which the book is returned
     */
    public void returnBook(Library library) 
    {    
        if(this.library.equals(library))
        {
        	rs = null;
        }
    }

    
    /**
     * Check the date when the book is available and returns the date
     * @param library
     * @return string indicating the date this book is available
     */
    public String availableDate(Library library) 
    {
    	if(this.library.equals(library))
    	{
    		return (rs==null)? Helper.getCurrentDate():rs.dueDate;
    	}
       return "";
    }

   
    /**
     * Checks if the if the current date is greater than the due date
     * @return boolean indicates if the book is overdue
     * @throws DateFormatException if dates are not valid
     */
    public boolean isBookOverdue() throws DateFormatException
    {
    	
       String currentDate = Helper.getCurrentDate();
       if(rs != null)
       {
    	   if(Helper.timeDifference(currentDate, rs.dueDate) < 0)
    	   {
    		   return true;
    	   }
    	   
       }
       return false;
       
    }
 
    /**
     * Checks if the book is rented
     * @param l is a Library object indicates the library that the book is rented from
     * @return boolean indicated if the book is rented
     */

    public boolean isRented(Library l)
    {
       if(this.library.equals(l))
       {
    	   if(rs != null)
    	   {
    		   return true;
    	   }
    		   
       }
    	return false;
    }
    
    /**
     * Getter for Rent Setting
     * @return RentSetting object indicates the rent setting of the book
     */

    public RentSettings getRs()
    {
       return rs;
    }
    
    /**
     * Setter for RentSettings object
     * @param rs is a RentSetting object indicates the rent setting of the book
     */

    public void setRs(RentSettings rs)
    {
    	if(rs!=null)
    	{
    		this.rs = rs;
    	}  	
       
    }
    /**
     * Equals function
     * @return a boolean indicating whether the books are equal
     * @param an object to compare with
     * */
    @Override
    public boolean equals(Object o) {
       
    	if(this == o)
    		return true;
    	if(!(o instanceof Book))
    		return false;
		
    	Book b = (Book)o;
    	if(!this.bookName.equals(b.bookName))
    		return false;
    	if(this.valueTag != b.valueTag)
    		return false;
    	
    	return true;
    	      
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
    	result = prime*result + valueTag;
    	result = prime*result + ((bookName == null) ? 0 : bookName.hashCode());
        return result;
    }
    
    /**
     * Returning a string of the book name and value tag and  Renting setting if exist
     * @return a String indicating the book name and value tag and  Renting setting if exist
     * */

    @Override
    public String toString() {

        String s = "";
        s+= bookName();
        
        if(library != null)
        {
        	s+= " => " +library.libraryName;       	
        	
        }
        s+= ')';        
        if (rs!=null)
        	
        	s+= rs.toString();
        
        return s;        
    }
    
	/**
	 * Return a string of the book name and value tag
	 * @return a String indicating the book name and value tag
	 */
    public String bookName()
    {
    	
        return "(" + bookName + ", " + valueTag;
        
    }

    private class RentSettings {

        private String rentDate;          // date when the item is requested
        private String dueDate;           // date when the item must be returned
        private boolean borrowed = false; // true if the item is rented

       
		/**
		 * Default Constructor set the dates to empty string
		 * @throws DateFormatException
		 */
        private RentSettings() throws DateFormatException 
        {
        	rentDate = "";
        	dueDate = "";
        	           
        }
        

       /**
        * Constructor accepts 2 strings for rent date and return date and the Library object for the library where the book is borrowed
        * @param rentDate is a string indicating the date when the book is rented
		* @param dueDate is a string indicated the date when the book must be returned to the library
		* @param library is a Library object indicating the library from where the book is borrowed
        * @throws DateFormatException if dates are not valid
        * @throws RentPeriodException if rent date > due date or if rent date < current date
        */
        private RentSettings(String rentDate, String dueDate, Library library)
                throws DateFormatException, RentPeriodException
        {
        	if(Helper.isValidDate(rentDate) && Helper.isValidDate(dueDate))
        	{
        		if(Helper.timeDifference(rentDate, dueDate)<0)
        		{
        			throw new RentPeriodException("The due date " + dueDate
        					+ " should not be before the rent date " + rentDate);
        		}
        		else if (Helper.timeDifference(Helper.getCurrentDate(), rentDate) < 0)
    			{
    				throw new RentPeriodException("The rent date " + rentDate
    						+ " should not be before the current date " + Helper.getCurrentDate());

    			}
        		else
        		{
        			this.rentDate=rentDate;
                	this.dueDate=dueDate;
                	setLibrary(library);
                	borrowed = true;
        		}
        	}
        	else
        		throw new DateFormatException("Invalid date format , it should be MM/dd/yyyy");
        	  
        }
        /**
         * Returning a string of rent date,return date, the library from where the book is borrowed,and the borrow condition
         * @return a String indicating rent date,return date, the library from where the book is borrowed,and the borrow condition
         * */

        @Override
        public String toString() 
        {
            return " RentSettings (" + rentDate+", " + dueDate+ ", " + library.libraryName + ", " + borrowed +")";
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
			result = prime * result + getOuterType().hashCode();
			result = prime * result + (borrowed ? 1231 : 1237);
			result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
			result = prime * result + ((rentDate == null) ? 0 : rentDate.hashCode());
			return result;
		}

		/**
	     * Equals function
	     * @return a boolean indicating whether the  rent setting are equal
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
			RentSettings other = (RentSettings) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (borrowed != other.borrowed)
				return false;
			if (dueDate == null)
			{
				if (other.dueDate != null)
					return false;
			} else if (!dueDate.equals(other.dueDate))
				return false;
			if (rentDate == null)
			{
				if (other.rentDate != null)
					return false;
			} else if (!rentDate.equals(other.rentDate))
				return false;
			return true;
		}


		private Book getOuterType()
		{
			return Book.this;
		}


        
    }
    
}
