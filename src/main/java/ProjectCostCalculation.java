import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectCostCalculation {

    public static final String DATA_DIRECTORY = "./";
    private static JList<String> projectList;
    private static DefaultListModel<String> projectListModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Project Cost Calculation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create project list panel
        JPanel projectListPanel = new JPanel(new BorderLayout());
        projectListModel = new DefaultListModel<>();
        projectList = new JList<>(projectListModel);
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectListPanel.add(new JScrollPane(projectList), BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton calculateButton = new JButton("Calculate Costs");
        buttonPanel.add(calculateButton);

        // Create output panel
        JTextArea outputTextArea = new JTextArea(20, 60);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        // Add components to main panel
        mainPanel.add(projectListPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setVisible(true);

        // Load project list
        loadProjectList();

        // Action listener for Calculate Costs button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateProjectCosts(outputTextArea);
            }
        });

        // Action listener for project list selection
        projectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedProject = projectList.getSelectedValue();
                if (selectedProject != null) {
                    outputTextArea.setText("");
                    outputTextArea.append("Selected Project: " + selectedProject + "\n\n");
                }
            }
        });
    }

    private static void loadProjectList() {
        projectListModel.clear();
        List<String> projectFiles = getProjectFiles();
        for (String projectFile : projectFiles) {
            projectListModel.addElement(projectFile);
        }
    }

    private static void calculateProjectCosts(JTextArea outputTextArea) {
        String selectedProject = projectList.getSelectedValue();
        if (selectedProject == null) {
            JOptionPane.showMessageDialog(null, "Please select a project.");
            return;
        }

        // Reset output
        outputTextArea.setText("");

        // Calculate costs for the selected project
        outputTextArea.append("Selected Project: " + selectedProject + "\n");

        // Calculate material cost
        double materialCost = calculateMaterialCost(selectedProject);
        outputTextArea.append("Material Cost: $" + materialCost + "\n");

        // Calculate labor cost
        double laborCost = calculateLaborCost(selectedProject);
        outputTextArea.append("Labor Cost: $" + laborCost + "\n");

        // Total project cost
        double totalCost = materialCost  + laborCost;
        outputTextArea.append("Total Project Cost: $" + totalCost + "\n\n");
    }

    private static List<String> getProjectFiles() {
        List<String> projectFiles = new ArrayList<>();
        File dir = new File(DATA_DIRECTORY);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv"));
        if (files != null) {
            for (File file : files) {
                projectFiles.add(file.getName());
            }
        }
        return projectFiles;
    }

    private static double calculateMaterialCost(String projectFile) {
        double totalCost = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_DIRECTORY + projectFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Material Resources")) {
                    // Skip headers
                    reader.readLine(); // Skip "Type,Cost,Quantity,Availability"
                    while ((line = reader.readLine()) != null && !line.startsWith("Processes")) {
                        String[] parts = line.split(",");
                        if (parts.length >= 3) {
                            double cost = Double.parseDouble(parts[1]);
                            int quantity = Integer.parseInt(parts[2]);
                            totalCost += cost * quantity;
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalCost;
    }

    private static double calculateLaborCost(String projectFile) {
        double totalCost = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_DIRECTORY + projectFile))) {
            String line;
            boolean foundLabor = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Employees")) {
                    // Skip headers
                    reader.readLine(); // Skip "Name,Rate,ID,Task"
                    while ((line = reader.readLine()) != null && !line.startsWith("Tasks")) {
                        String[] parts = line.split(",");
                        if (parts.length >= 2) {
                            double rate = Double.parseDouble(parts[1]);
                            // Assuming you have hourly rate and hours worked per process/task in the data
                            // Example: hourlyRate * hoursWorked
                            totalCost += rate * 8; // Example: 8 hours worked
                        }
                    }
                    foundLabor = true;
                    break;
                }
            }
            if (!foundLabor) {
                // Handle case where no labor costs are found
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalCost;
    }
}
