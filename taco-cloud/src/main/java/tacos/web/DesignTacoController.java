package tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.data.IngredientRepository;


@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
	private final IngredientRepository ingredientRepo;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	  @GetMapping
	  public String showDesignForm(Model model) {
		  List<Ingredient> ingredients = new ArrayList<Ingredient>();
		  ingredientRepo.findAll().forEach(
				  ingredient -> ingredients.add(ingredient));
		  
		  // Makes array with all types contained in ingredients, uses values() to get the enum values
		  Type[] types = Ingredient.Type.values();
		  
		  // Maps each type to a List holding ingredients that contain that type, ingredients returned by the function filterByType()
		  for (Type type : types) {
			  model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		  }
		  // Sets object that will be sent in POST request back to the server
		  model.addAttribute("design", new Taco());
		  //returns design /design is  the view that will be shown to the user
	    return "design";
	  }
	  
	  @PostMapping
	  public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, Model model) {
		  if (errors.hasErrors()) {
			  return "design";
		  }
		  
		  
		  log.info("Processing design: " + design);
		  
		  // redirects the to template /orders/current
		  return "redirect:/orders/current";
	  }
	  
	  
	  private List<Ingredient> filterByType(
		      List<Ingredient> ingredients, Type type) {
		    return ingredients
		              .stream()
		              .filter(x -> x.type().equals(type))
		              .collect(Collectors.toList());
		  }
}
