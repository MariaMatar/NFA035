
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class NAFProcess extends DefaultMutableTreeNode {

    public double getCost(){

        double cost = 0;
        return cost;
    }

    public double getDuration(){

        return duration_;
    }

    /**
     *  Set state
     * @param state
     */
    public  void setState(NAFState state){

        state_ = state;
    }

    /** Get State
     *
     * @return NAFState
     */
    public NAFState getState(){

        return state_;
    }

    public String getName(){

        return name_;
    }

    public List<NAFRessource> getRessources(){

        return ressources_;
    }

    private String              name_;
    private double              duration_;
    private NAFState            state_;
    private List<NAFRessource>  ressources_;
}
