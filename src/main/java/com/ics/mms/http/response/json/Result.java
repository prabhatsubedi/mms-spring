/**
 * 
 */
package com.ics.mms.http.response.json;

/**
 * @author marco_000
 *
 */
public class Result {
	
    public int result;
    
    public String message;
    
    public Result()
    {
    }

    public Result(int result)
    {
        this.result = result;
    }
    
    public Result(int result, String message)
    {
        this.result = result;
        this.message = message;
    }

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	} 
}
