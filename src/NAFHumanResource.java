public class NAFHumanResource extends NAFResource implements NAFResourceInterface {

    NAFHumanResource(String name){

        super(name);

        nbHours_ = 0;
    }

    public double getCost(){

        double cost = 0;

        return cost;
    }

    public double getNbHours(){

        return nbHours_;
    }

    private  double nbHours_;
}
