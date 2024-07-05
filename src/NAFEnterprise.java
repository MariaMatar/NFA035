//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.UIManager; // for look and feel

public class NAFEnterprise extends JFrame{

    NAFEnterprise(){

        super("OMEGA - Fabrication de prototypes");

        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initGui();
    }

    public JTree getTree(){

        return tree_;
    }

    private void initRightPane(){

        // Toolbar: save, Start, stop, clear,
    }
    private void initGui(){

        // init tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Omega");
        tree_                       = new JTree(root);
        tree_.addMouseListener(new NAFTreeAdapter(this));
        tree_.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // add tree in scroll pane
        JScrollPane jsPane          = new JScrollPane();
        jsPane.setViewportView(tree_);
        jsPane.setPreferredSize(new Dimension(200, this.getHeight()));

        // build the right pane
        rightPane_                  = new JPanel();

        // build the splitpane
        splitPane_                  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jsPane, rightPane_);
        splitPane_.setDividerSize(3);

        // add the splitpane to the frame
        this.getContentPane().add(splitPane_);
    }


    private JTree       tree_;
    private JPanel      rightPane_;
    private JSplitPane  splitPane_;

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            NAFEnterprise enterprise = new NAFEnterprise();

            // resize the frame if needed due to inner components size

            enterprise.setVisible(true);
        }
        catch (Exception e) {

            System.out.println("Look and Feel not set");
        }
    }
}