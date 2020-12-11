package tacos.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tacos.Order;
import tacos.User;

@Repository
public interface OrderRepository 
	extends CrudRepository<Order, Long>{

	List<Order> findByZip(String zip);
	List<Order> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);	
	}
