package microService.securityManagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendOfferDetails {
	private String date;
	private String time;
	private CropRequirements cropRequirements;
	private DealerBasicInfo dealerInfo;

}
