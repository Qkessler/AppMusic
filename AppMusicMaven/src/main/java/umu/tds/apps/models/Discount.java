package umu.tds.apps.models;

public interface Discount {
	public double applyDiscount(double price);
	public boolean isApplicable(User user);
	public double getDiscount();
	public String getMessage();
}
