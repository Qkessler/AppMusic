package umu.tds.apps.models;

public class ElderDiscount implements Discount{
	private double discount;
	private String message; 
	
	public ElderDiscount() {
		discount = 0.4;
		message = "Elder Discount - 40%";
	}
			
	@Override
	public double applyDiscount(double price) {
		return price * (1 - discount);
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public boolean isApplicable(User user) {
		return user.isElder();
	}
}
