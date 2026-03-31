package main;

/**

 * Project: Lab 4
 * Purpose Details: Pizza Shop
 * Course: IST 242
 * Author: Matthew Sulpizio
 * Date Developed: 3/20/2026
 * Last Date Changed: 3/31/2026
 * Rev:

 */

public class Pizza {

    /**
     * The pizza crust.
     */
    private String crust;

    /**
     * The pizza size.
     */
    private String size;

    /**
     * The topping on the pizza.
     */
    private String topping;

    /**
     * The price of the pizza.
     */
    private double price;

    /**
     * Default constructor.
     */
    public Pizza() {
    }

    /**
     * Creates a Pizza object with all field values.
     *
     * @param crust The pizza crust.
     * @param size The pizza size.
     * @param topping The topping on the pizza.
     * @param price The pizza price.
     */
    public Pizza(String crust, String size, String topping, double price) {
        this.crust = crust;
        this.size = size;
        this.topping = topping;
        this.price = price;
    }

    /**
     * Gets the pizza crust.
     *
     * @return The pizza crust.
     */
    public String getCrust() {
        return crust;
    }

    /**
     * Sets the pizza crust.
     *
     * @param crust The pizza crust to set.
     */
    public void setCrust(String crust) {
        this.crust = crust;
    }

    /**
     * Gets the pizza size.
     *
     * @return The pizza size.
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the pizza size.
     *
     * @param size The pizza size to set.
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Gets the topping.
     *
     * @return The topping.
     */
    public String getTopping() {
        return topping;
    }

    /**
     * Sets the topping.
     *
     * @param topping The topping to set.
     */
    public void setTopping(String topping) {
        this.topping = topping;
    }

    /**
     * Gets the price.
     *
     * @return The price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price The price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns a string representation of the Pizza object.
     *
     * @return A string showing pizza field values.
     */
    @Override
    public String toString() {
        return "Pizza {\n" +
                "  Crust: " + crust + "\n" +
                "  Size: " + size + "\n" +
                "  Topping: " + topping + "\n" +
                "  Price: " + price + "\n" +
                "}";
    }
}