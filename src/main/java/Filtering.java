import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Filtering {
    private JComboBox<String> projectComboBox;
    private DefaultListModel<String> projectListModel;
    private JComboBox<String> taskComboBox;
    private JComboBox<String> processComboBox;
    private JComboBox<String> employeeComboBox;

    public Filtering(DefaultListModel<String> projectListModel) {
        this.projectListModel = projectListModel;

        JPanel filteringPanel = new JPanel(new BorderLayout());

        // Create and populate project dropdown
        projectComboBox = new JComboBox<>();
        projectComboBox.addItem("None"); // Default value
        for (int i = 0; i < projectListModel.getSize(); i++) {
            projectComboBox.addItem(projectListModel.getElementAt(i));
        }

        // Initialize task, process, and employee dropdowns (with "None" as default)
        taskComboBox = new JComboBox<>();
        taskComboBox.addItem("None"); // Default value
        taskComboBox.setEnabled(false); // Disable until project selected

        processComboBox = new JComboBox<>();
        processComboBox.addItem("None"); // Default value
        processComboBox.setEnabled(false); // Disable until project selected

        employeeComboBox = new JComboBox<>();
        employeeComboBox.addItem("None"); // Default value
        employeeComboBox.setEnabled(false); // Disable until project selected

        // Action listener for project selection
        projectComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProject = (String) projectComboBox.getSelectedItem();
                if (selectedProject != null && !selectedProject.equals("None")) {
                    taskComboBox.setEnabled(true);
                    processComboBox.setEnabled(true);
                    employeeComboBox.setEnabled(true);
                    updateTaskComboBox(selectedProject); // Update tasks when a project is selected
                    updateProcessComboBox(selectedProject); // Update processes when a project is selected
                    updateEmployeeComboBox(selectedProject); // Update employees when a project is selected
                } else {
                    // If "None" selected, disable and reset other dropdowns
                    taskComboBox.setEnabled(false);
                    processComboBox.setEnabled(false);
                    employeeComboBox.setEnabled(false);
                    taskComboBox.removeAllItems();
                    processComboBox.removeAllItems();
                    employeeComboBox.removeAllItems();
                }
            }
        });

        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProject = (String) projectComboBox.getSelectedItem();
                String selectedTask = (String) taskComboBox.getSelectedItem();
                String selectedProcess = (String) processComboBox.getSelectedItem();
                String selectedEmployee = (String) employeeComboBox.getSelectedItem();

                // Check if project is selected (mandatory)
                if (selectedProject != null && !selectedProject.equals("None")) {
                    displayFilteredData(selectedProject, selectedTask, selectedProcess, selectedEmployee);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a project.");
                }
            }
        });

        // Create clear button (optional)
        JButton clearButton = new JButton("Clear Selections");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectComboBox.setSelectedItem("None");
                taskComboBox.setSelectedItem("None");
                processComboBox.setSelectedItem("None");
                employeeComboBox.setSelectedItem("None");
            }
        });

        JPanel selectionPanel = new JPanel(new GridLayout(5, 2));
        selectionPanel.add(new JLabel("Select Project:"));
        selectionPanel.add(projectComboBox);
        selectionPanel.add(new JLabel("Select Task (Optional):"));
        selectionPanel.add(taskComboBox);
        selectionPanel.add(new JLabel("Select Process (Optional):"));
        selectionPanel.add(processComboBox);
        selectionPanel.add(new JLabel("Select Employee (Optional):"));
        selectionPanel.add(employeeComboBox);
        selectionPanel.add(filterButton);
        selectionPanel.add(clearButton);

        filteringPanel.add(selectionPanel, BorderLayout.CENTER);

        JFrame frame = new JFrame("Filtering Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.getContentPane().add(filteringPanel);
        frame.setVisible(true);
    }

    // Method to update task dropdown based on selected project
    private void updateTaskComboBox(String selectedProject) {
        taskComboBox.removeAllItems();
        taskComboBox.addItem("None");
        File projectFile = new File(DataEntry.DATA_DIRECTORY + "/" + selectedProject + ".csv");
        if (projectFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(projectFile))) {
                String line;
                boolean tasksSection = false;
                while ((line = reader.readLine()) != null) {
                    if (tasksSection && !line.startsWith("Name")) {
                        String[] taskDetails = line.split(",");
                        taskComboBox.addItem(taskDetails[0]);
                    } else if (line.equals("Tasks")) {
                        tasksSection = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to update process dropdown based on selected project
    private void updateProcessComboBox(String selectedProject) {
        processComboBox.removeAllItems();
        processComboBox.addItem("None");
        File projectFile = new File(DataEntry.DATA_DIRECTORY + "/" + selectedProject + ".csv");
        if (projectFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(projectFile))) {
                String line;
                boolean processesSection = false;
                while ((line = reader.readLine()) != null) {
                    if (processesSection && !line.startsWith("Name")) {
                        String[] processDetails = line.split(",");
                        processComboBox.addItem(processDetails[0]);
                    } else if (line.equals("Processes")) {
                        processesSection = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to update employee dropdown based on selected project
    private void updateEmployeeComboBox(String selectedProject) {
        employeeComboBox.removeAllItems();
        employeeComboBox.addItem("None");
        File projectFile = new File(DataEntry.DATA_DIRECTORY + "/" + selectedProject + ".csv");
        if (projectFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(projectFile))) {
                String line;
                boolean humanResourcesSection = false;
                while ((line = reader.readLine()) != null) {
                    if (humanResourcesSection && !line.startsWith("Name")) {
                        String[] employeeDetails = line.split(",");
                        employeeComboBox.addItem(employeeDetails[0]);
                    } else if (line.equals("Human Resources")) {
                        humanResourcesSection = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to display filtered data based on selected project
    private void displayFilteredData(String selectedProject, String selectedTask, String selectedProcess, String selectedEmployee) {
        StringBuilder message = new StringBuilder("Filtered Data:\n");
        message.append("Project: ").append(selectedProject).append("\n");

        File projectFile = new File(DataEntry.DATA_DIRECTORY + "/" + selectedProject + ".csv");
        if (projectFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(projectFile))) {
                String line;
                boolean dataSection = false;
                while ((line = reader.readLine()) != null) {
                    if (dataSection && !line.startsWith("Task")) {
                        String[] dataDetails = line.split(",");
                        if ((selectedTask == null || selectedTask.equals("None") || dataDetails[0].equals(selectedTask)) &&
                                (selectedProcess == null || selectedProcess.equals("None") || dataDetails[1].equals(selectedProcess)) &&
                                (selectedEmployee == null || selectedEmployee.equals("None") || dataDetails[2].equals(selectedEmployee))) {

                            message.append("\nDetails:\n");
                            message.append("Task: ").append(dataDetails[0]).append("\n");
                            message.append("Process: ").append(dataDetails[1]).append("\n");
                            message.append("Employee: ").append(dataDetails[2]).append("\n");
                            message.append("Cost: ").append(dataDetails[3]).append("\n");
                            message.append("Duration: ").append(dataDetails[4]).append("\n");
                            message.append("State: ").append(dataDetails[5]).append("\n");
                            message.append("Resources Needed: ").append(dataDetails[6]).append("\n");
                        }
                    } else if (line.equals("Data")) {
                        dataSection = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JOptionPane.showMessageDialog(null, message.toString());
    }

    public static void main(String[] args) {
        DefaultListModel<String> projectListModel = new DefaultListModel<>();
        File dir = new File(DataEntry.DATA_DIRECTORY);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv"));
        if (files != null) {
            for (File file : files) {
                projectListModel.addElement(file.getName().replace(".csv", ""));
            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Filtering(projectListModel);
            }
        });
    }
}
