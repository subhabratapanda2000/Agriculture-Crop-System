package microService.jwtManagement.models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class CropDetails {
	
	private int id;
	
	private String cropName;
	
	private double quantity;
	
	private double price;
	
	private int farmerId;
	private String date;


}
