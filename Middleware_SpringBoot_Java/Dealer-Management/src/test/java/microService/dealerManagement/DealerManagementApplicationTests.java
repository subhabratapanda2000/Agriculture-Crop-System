package microService.dealerManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import microService.dealerManagement.models.DealerDetails;
import microService.dealerManagement.repository.DealerRepository;
import microService.dealerManagement.service.DealerService;

@SpringBootTest
class DealerManagementApplicationTests {

	@Autowired
	DealerService service;
	
	@MockBean
	DealerRepository repo;
	
	
	@Test
	public void testFindAll() {
		when(repo.findAllDealer("ROLE_DEALER")).thenReturn(Stream
				.of(new DealerDetails(201, "abcd","us","pw", "kalyani", "99868758", true,"ROLE_DEALER", "", true), new DealerDetails(202, "gdfgfg","","", "kolkata", "99868758", true,"ROLE_DEALER", "", true)).collect(Collectors.toList()));
		System.out.println("hii "+service.findAll());
		
		assertEquals(2, service.findAll().size());
	}
	
	@Test
	public void getUserbyIdTest() {
		int id=201;
		DealerDetails fm=new DealerDetails(201, "abcd","","", "kalyani", "99868758", true,"", "", true);
		System.out.println("Hello sam");
		when(repo.findById(id)).thenReturn(java.util.Optional.of(fm));
		System.out.println(service.findById(id)+"and"+fm);
		assertEquals(fm, service.findById(id).get());
	}
	
	@Test
	public void saveDealerTest() {
		DealerDetails fm=new DealerDetails(201, "abcd","","", "kalyani", "99868758", true, "", "", true);
		when(repo.save(fm)).thenReturn(fm);
		assertEquals(fm, service.save(fm));
	}
	
	@Test
	public void deleteDealer() {
		DealerDetails fm=new DealerDetails(201, "abcd","","", "kalyani", "99868758", true, "", "", true);
		service.deleteById(201);
		verify(repo).deleteById(any());
	}

}
