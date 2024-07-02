import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    public static final String DATA_DIRECTORY = "./";
    private static JTextArea outputTextArea;
    private static JLabel projectCostLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Project Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel for project list and load button
        JPanel projectListPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> projectListModel = new DefaultListModel<>();
        JList<String> projectFileList = new JList<>(projectListModel);
        loadProjectList(projectListModel); // Load project names from DATA_DIRECTORY

        JButton loadButton = new JButton("Load Project Details");
        projectListPanel.add(new JScrollPane(projectFileList), BorderLayout.CENTER);
        projectListPanel.add(loadButton, BorderLayout.SOUTH);

        // Panel for displaying project details
        JPanel projectDetailsPanel = new JPanel(new BorderLayout());
        JScrollPane detailsScrollPane = new JScrollPane(projectDetailsPanel);
        mainPanel.add(projectListPanel, BorderLayout.WEST);
        mainPanel.add(detailsScrollPane, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        outputTextArea = new JTextArea(20, 60);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        projectCostLabel = new JLabel("Project Cost: $0.00");
        footerPanel.add(projectCostLabel);

        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setVisible(true);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProjectDetails(projectFileList, projectDetailsPanel);
            }
        });
    }

    private static void loadProjectList(DefaultListModel<String> projectListModel) {
        File dir = new File(DATA_DIRECTORY);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".csv");
            }
        });

        if (files != null) {
            for (File file : files) {
                projectListModel.addElement(file.getName());
            }
        }
    }

    private static void loadProjectDetails(JList<String> projectFileList, JPanel projectDetailsPanel) {
        String selectedFileName = projectFileList.getSelectedValue();
        if (selectedFileName == null) {
            JOptionPane.showMessageDialog(null, "Please select a project.");
            return;
        }
        String filePath = DATA_DIRECTORY + selectedFileName;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder detailsBuilder = new StringBuilder("<html>");
            String line;
            while ((line = reader.readLine()) != null) {
                detailsBuilder.append(line).append("<br>");
            }
            detailsBuilder.append("</html>");
            JLabel projectDetailsLabel = new JLabel(detailsBuilder.toString());
            projectDetailsPanel.removeAll();
            projectDetailsPanel.add(projectDetailsLabel, BorderLayout.CENTER);
            projectDetailsPanel.revalidate();
            projectDetailsPanel.repaint();

            // Calculate and display project cost
            calculateAndDisplayProjectCost(selectedFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void calculateAndDisplayProjectCost(String selectedProject) {
        double materialCost = calculateMaterialCost(selectedProject);
        double miscCost = calculateMiscellaneousCost(selectedProject);
        double laborCost = calculateLaborCost(selectedProject);

        double totalCost = materialCost + miscCost + laborCost;

        outputTextArea.append(selectedProject + "\n");
        outputTextArea.append("Material Cost: $" + materialCost + "\n");
        outputTextArea.append("Miscellaneous Cost: $" + miscCost + "\n");
        outputTextArea.append("Labor Cost: $" + laborCost + "\n");
        outputTextArea.append("Total Project Cost: $" + totalCost + "\n\n");

        // Update the project cost in the footer label
        updateProjectCost(totalCost);
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

    private static double calculateMiscellaneousCost(String projectFile) {
        double totalCost = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_DIRECTORY + projectFile))) {
            String line;
            boolean foundMisc = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Miscellaneous")) {
                    // Skip headers
                    reader.readLine(); // Skip "Description,Cost"
                    while ((line = reader.readLine()) != null && !line.startsWith("Employees")) {
                        String[] parts = line.split(",");
                        if (parts.length >= 2) {
                            double cost = Double.parseDouble(parts[1]);
                            totalCost += cost;
                        }
                    }
                    foundMisc = true;
                    break;
                }
            }
            if (!foundMisc) {
                // Handle case where no miscellaneous costs are found
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

    public static void updateProjectCost(double cost) {
        projectCostLabel.setText("Project Cost: $" + String.format("%.2f", cost));
    }
}
