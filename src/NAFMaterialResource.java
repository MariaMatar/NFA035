
public class NAFMaterialResource extends NAFResource implements NAFResourceInterface {

    NAFMaterialResource(String name){

        super(name);

        quantity_ = 0;
    }

    public double getCost(){

        double cost = 0;

        return cost;
    }

    public double getQuantity(){

        return quantity_;
    }

    private double quantity_;
}
