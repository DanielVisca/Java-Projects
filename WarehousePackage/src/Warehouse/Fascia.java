package warehouse;

/**
 * A bumper on a vehicle.
 *
 */
public class Fascia extends WarehouseItem {
  /**
   * The colour of the fascia.
   */
  private String colour;

  /**
   * Whether the bumper is the front bumper or the rear bumper.
   */
  private String type;

  /**
   * @return The colour of this fascia.
   */
  public String getColour() {
    return colour;
  }

  /**
   * @return The type of this fascia.
   */
  public String getType() {
    return type;
  }

  /**
   * Construct a fascia item for the warehouse with the given colour and type.
   * 
   * @param sku This item's SKU number
   * @param colour The colour of this fascia
   * @param type Whether this fascia is a front or rear fascia
   */
  public Fascia(int sku, String colour, String type) {
    super(sku);
    this.colour = colour;
    this.type = type;
  }

}
