package zutilities.jdbcbeanmodel.db.beans;

public class RestList
    {
	private int restID ;
	private String restName ;
	private Double restRating ;
	private int catID ;
	
	public int getRestID()
	    {
		return restID;
	    }
	public void setRestID(int restID)
	    {
		this.restID = restID;
	    }
	public String getRestName()
	    {
		return restName;
	    }
	public void setRestName(String restName)
	    {
		this.restName = restName;
	    }
	public Double getRestRating()
	    {
		return restRating;
	    }
	public void setRestRating(Double restRating)
	    {
		this.restRating = restRating;
	    }
	public int getCatID()
	    {
		return catID;
	    }
	public void setCatID(int catID)
	    {
		this.catID = catID;
	    }	
    }
