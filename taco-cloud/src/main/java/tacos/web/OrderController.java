package tacos.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;
import tacos.User;
import tacos.configs.OrderConfigs;
import tacos.data.OrderRepository;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	OrderConfigs orderConfigs;
	
	private int pageSize = 20;
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	@GetMapping
	public String ordersForUser(
			@AuthenticationPrincipal User user, Model model) {
		
		Pageable pagable = PageRequest.of(0, orderConfigs.getPageSize());
		model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pagable));
		return "orderList";
	}
	
	
	@GetMapping("/current")
	public String orderForm(Model model) {
		model.addAttribute("order", new Order());
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors,
			SessionStatus sessionStatus,
			@AuthenticationPrincipal User user) {
		
		if(errors.hasErrors()) {
			return "orderForm";
		}
		
		order.setUser(user);
		
		orderRepo.save(order);
		sessionStatus.setComplete();
		
		log.info("Order submitted: " + order);
		return "redirect:/";
	}
	
}
