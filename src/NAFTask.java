
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class NAFTask extends DefaultMutableTreeNode {

    /**
     * Get Cost In Progress
     * @return double
     */
    public double getCostIP(){

        double cost = 0;
        for (int i = 0; i < processes_.size(); i++) {

            cost = cost + processes_.get(i).getCost();
        }

        return cost;
    }

    /**
     * Get ElapsedTime. It is the duration in progress.
     * @return double
     */
    public double getElapsedTime(){

        double elapsedtime = 0;
        for (int i = 0; i < processes_.size(); i++) {

            elapsedtime = elapsedtime + processes_.get(i).getDuration();
        }

        return elapsedtime;
    }

    /** Get Progressive State
     *
     * @return NAFState
     */
    public NAFState getState(){

        NAFState state         = NAFState.NSTAR;
        NAFState processState  = NAFState.NSTAR;

        for (int i = 0; i < processes_.size(); i++) {

            processState = processes_.get(i).getState();

            // Ignore is process state is not started
            if(processState != NAFState.NSTAR){

                state = processState;
            }
        }

        return state;
    }

    private String  name_;
    private NAFState state_;

    private List<NAFProcess> processes_; // List of tasks associated with the project
}
