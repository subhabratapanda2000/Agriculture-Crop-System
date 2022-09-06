package microService.paytmManagement.model;

public class PaymentException extends Exception  
{  
    public PaymentException (String str)  
    {  
        // calling the constructor of parent Exception  
        super(str);  
    }  
}  