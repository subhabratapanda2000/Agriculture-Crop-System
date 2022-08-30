package microService.dealerManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import microService.dealerManagement.models.DealerDetails;


@Repository
public interface DealerRepository extends MongoRepository<DealerDetails, Integer> {
	@Query("{'active':?0}")
	List<DealerDetails> findActiveDealer(boolean b);
	
	@Query("{'role':?0}")
	List<DealerDetails> findAllDealer(String role);
	
	@Query("{'userName':?0}")
	Optional<DealerDetails> findByUserName(String userName);
	
	@Query("{'mobileNo':?0}")
	Optional<DealerDetails> findByMobile(String mobile);
	
	@Query("{'name':?0}")
	Optional<DealerDetails> findByName(String name);

}
