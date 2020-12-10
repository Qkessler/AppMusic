package umu.tds.apps.models;

public class YouthDiscount implements Discount{
	private static double DISCOUNT = 0.25;
	private static String MESSAGE = "Youth Discount - 25%";
	
	@Override
	public double applyDiscount(double price) {
		return price * (1 - DISCOUNT);
	}
	
	public double getDiscount() {
		return DISCOUNT;
	}
	
	public String getMessage() {
		return MESSAGE;
	}

	@Override
	public boolean isApplicable(User user) {
		return user.isYouth();
	}
}
