package microService.farmerManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import microService.farmerManagement.models.FarmerDetails;


	@Repository
	public interface FarmerRepository extends MongoRepository<FarmerDetails, Integer> {
		@Query("{'active':?0}")
		List<FarmerDetails> findActiveFarmer(boolean b);
		
		@Query("{'role':?0}")
		List<FarmerDetails> findAllFarmer(String role);
		
		@Query("{'userName':?0}")
		Optional<FarmerDetails> findByUserName(String userName);
		
		@Query("{'mobileNo':?0}")
		Optional<FarmerDetails> findByMobile(String mobile);
		
		@Query("{'name':?0}")
		Optional<FarmerDetails> findByName(String name);

}
