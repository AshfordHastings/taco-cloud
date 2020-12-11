package tacos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name="Taco_Order")
public class Order implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date placedAt;
	
	 @ManyToMany(targetEntity=Taco.class)
	 private List<Taco> tacos = new ArrayList<>();
	 
	 @ManyToOne
	 private User user;
	 
	 public void addDesign(Taco design) {
	 	this.tacos.add(design);
	 }
	 
	 
	 @PrePersist
	 void placedAt() {
	 this.placedAt = new Date();
	 }
	
	
	
	@NotBlank(message="Name is required")
	public String name;
	@NotBlank(message="Street is required")
	public String street;
	@NotBlank(message="City is required")
	public String city;
	@NotBlank(message="State is required")
	public String state;
	@NotBlank(message="Zip Code is required")
	public String zip;
	@NotBlank(message="Credit Card Number is required")
	public String ccNumber;
	@NotBlank(message="Credit Card Expiration Date is required")
	public String ccExpiration;
	@Digits(integer=3, fraction=0, message="Invalid CVV")
	public String ccCVV;
}
