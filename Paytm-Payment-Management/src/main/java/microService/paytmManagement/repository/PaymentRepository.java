package microService.paytmManagement.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import microService.paytmManagement.model.PaymentDetails;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentDetails, Integer> {
	
	
	@Query("{'customerId':?0}")
	Optional<PaymentDetails> findByUserId(int customerId);
	

}
