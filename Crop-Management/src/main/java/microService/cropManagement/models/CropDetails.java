package microService.cropManagement.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection="CropDetails")
public class CropDetails {
	@Id
	private int id;
	@Field
	private String cropName;
	@Field
	private double quantity;
	@Field
	private double price;
	@Field
	private int farmerId;

}
