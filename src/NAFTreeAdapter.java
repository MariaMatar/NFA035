
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

public class NAFTreeAdapter extends MouseAdapter{

    private final NAFEnterprise enterprise_;

    public NAFTreeAdapter(NAFEnterprise enterprise) {

        enterprise_ = enterprise;
    }

    // action on tree only
    public void mouseClicked(MouseEvent evt) {

        if(!SwingUtilities.isRightMouseButton(evt))
            return;

        System.out.println("mouse right clicked");
        buildPopupMenu(evt);
    }

    private void buildPopupMenu(MouseEvent evt) {

        TreePath tp = enterprise_.getTree().getPathForLocation(evt.getX(), evt.getY());
        if (tp != null){

            if (tp.toString().equals("[Omega]")){

                System.out.println("Right click on root element");

                JPopupMenu menu = new JPopupMenu();
                JMenuItem  item = new JMenuItem("Add Project");
                menu.add(item);
                menu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }
}
