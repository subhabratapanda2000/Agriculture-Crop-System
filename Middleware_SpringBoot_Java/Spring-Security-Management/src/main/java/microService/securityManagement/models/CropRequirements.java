package microService.securityManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CropRequirements {
	private String id;
    private String cropName;
    private double quantity;
    private double price;
    private String message;

}
