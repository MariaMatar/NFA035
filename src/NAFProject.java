import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class NAFProject extends DefaultMutableTreeNode {

    /**
     * Get Cost In Progress
     * @return double
     */
    public double getCostIP(){

        double cost = 0;
        for (int i = 0; i < tasks_.size(); i++) {

            cost = cost + tasks_.get(i).getCostIP();
        }

        return cost;
    }

    /**
     * Get ElapsedTime. It is the duration in progress.
     * @return double
     */
    public double getElapsedTime(){

        return 0;
    }

    /** Get Progressive State
     *
     * @return NAFState
     */
    public NAFState getState(){

        return NAFState.NSTAR;
    }

    private String          name_;
    private List<NAFTask>   tasks_; // List of tasks associated with the project
}
