package umu.tds.apps.models;

public class EducationDiscount implements Discount{
	// We could say that the education discount is applied if the email
	// contains "um.es", or that the last 5 chars must be those.
	private static final double DISCOUNT= 0.2;
	private static final String MESSAGE = "Education Discount - 20%";
	
	@Override
	public double applyDiscount(double price) {
		return price * (1 - DISCOUNT);
	}
	
	public double getDiscount() {
		return DISCOUNT;
	}

	@Override
	public boolean isApplicable(User user) {
		return user.isEducation();
	}

	@Override
	public String getMessage() {
		return MESSAGE;
	}
}
