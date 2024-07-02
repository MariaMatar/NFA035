package com.Nfa035;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataEntry {

    public static final String DATA_DIRECTORY = "./";
    private static List<Object[]> humanResources = new ArrayList<>();
    private static List<Object[]> materialResources = new ArrayList<>();
    private static List<Object[]> tasks = new ArrayList<>();
    private static List<Object[]> processes = new ArrayList<>();
    private static DefaultComboBoxModel<String> taskComboBoxModel = new DefaultComboBoxModel<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Data Entry Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel1 = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel(new BorderLayout());
        JPanel panel3 = new JPanel();

        JPanel projectInputPanel = new JPanel(new GridLayout(3, 2));
        projectInputPanel.add(new JLabel("Project Name:"));
        JTextField projectNameField = new JTextField(5);
        projectInputPanel.add(projectNameField);

        projectInputPanel.add(new JLabel("Project Cost:"));
        JSpinner projectCostSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.01));
        projectInputPanel.add(projectCostSpinner);

        projectInputPanel.add(new JLabel("Project State:"));
        JComboBox<String> projectStateComboBox = new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});
        projectInputPanel.add(projectStateComboBox);

        projectInputPanel.add(new JLabel("Project Duration:"));
        JSpinner projectDurationSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.01));
        projectInputPanel.add(projectDurationSpinner);

        JButton button1 = new JButton("Add New Project");
        JButton button2 = new JButton("Load Project Details");
        JButton button3 = new JButton("Button 3");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectName = projectNameField.getText();
                double projectCost = (double) projectCostSpinner.getValue();
                String projectState = (String) projectStateComboBox.getSelectedItem();
                double projectDuration = (double) projectDurationSpinner.getValue();

                if (projectName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Project Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String dataFile = DATA_DIRECTORY + projectName + ".csv";
                appendToFile(dataFile, "Project Details");
                appendToFile(dataFile, "Name,Cost,State,Duration");
                appendToFile(dataFile, String.join(",", projectName, String.valueOf(projectCost), projectState, String.valueOf(projectDuration)));

                JDialog dialog = new JDialog(frame, "Data Entry Dialog", true);
                dialog.setSize(600, 400);
                dialog.setLayout(new BorderLayout());

                JTabbedPane dialogTabbedPane = new JTabbedPane();

                JPanel taskPanel = createTaskPanel();
                JPanel humanResourcePanel = createHumanResourcePanel(dataFile);
                JPanel materialResourcePanel = createMaterialResourcePanel();
                JPanel processPanel = createProcessPanel();

                dialogTabbedPane.addTab("Add Task", taskPanel);
                dialogTabbedPane.addTab("Add Employee", humanResourcePanel);
                dialogTabbedPane.addTab("Add Material", materialResourcePanel);
                dialogTabbedPane.addTab("Add Process", processPanel);

                dialog.add(dialogTabbedPane, BorderLayout.CENTER);

                JButton submitButton = new JButton("Save Project");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        saveProjectData(dataFile, dialog);
                    }
                });

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.add(submitButton);
                dialog.add(buttonPanel, BorderLayout.SOUTH);

                dialog.setVisible(true);
            }
        });

        JPanel projectListPanel = new JPanel(new BorderLayout());

        JList<String> projectFileList = new JList<>();
        DefaultListModel<String> projectFileListModel = new DefaultListModel<>();
        projectFileList.setModel(projectFileListModel);

        File dir = new File(DATA_DIRECTORY);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv"));
        if (files != null) {
            for (File file : files) {
                projectFileListModel.addElement(file.getName());
            }
        }

        projectListPanel.add(new JScrollPane(projectFileList), BorderLayout.WEST);

        JPanel projectDetailsPanel = new JPanel(new BorderLayout());
        projectListPanel.add(projectDetailsPanel, BorderLayout.CENTER);

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProjectDetails(projectFileList, projectDetailsPanel);
            }
        });

        panel2.add(projectListPanel, BorderLayout.CENTER);
        panel2.add(button2, BorderLayout.SOUTH);

        panel1.add(projectInputPanel, BorderLayout.NORTH);
        panel1.add(button1, BorderLayout.SOUTH);
        panel3.add(button3);

        tabbedPane.addTab("Data Entry", panel1);
        tabbedPane.addTab("Simulation", panel2);
        tabbedPane.addTab("Filtering", panel3);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JPanel createMaterialResourcePanel() {
        JPanel materialResourcePanel = new JPanel(new BorderLayout());
        JPanel materialResourceInputPanel = new JPanel(new GridLayout(4, 2));
        materialResourceInputPanel.add(new JLabel("Material Type:"));
        JComboBox<String> materialTypeComboBox = new JComboBox<>(new String[]{"Material 1", "Material 2", "Material 3"});
        materialResourceInputPanel.add(materialTypeComboBox);

        materialResourceInputPanel.add(new JLabel("Material Cost:"));
        JSpinner materialCostSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.01));
        materialResourceInputPanel.add(materialCostSpinner);

        materialResourceInputPanel.add(new JLabel("Material Quantity:"));
        JSpinner materialQuantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        materialResourceInputPanel.add(materialQuantitySpinner);

        materialResourceInputPanel.add(new JLabel("Material Availability:"));
        JCheckBox materialAvailabilityCheckBox = new JCheckBox("Available");
        materialResourceInputPanel.add(materialAvailabilityCheckBox);

        materialResourcePanel.add(materialResourceInputPanel, BorderLayout.NORTH);

        DefaultTableModel materialResourceTableModel = new DefaultTableModel(new Object[]{"Type", "Cost", "Quantity", "Availability"}, 0);
        JTable materialResourceTable = new JTable(materialResourceTableModel);
        materialResourcePanel.add(new JScrollPane(materialResourceTable), BorderLayout.CENTER);

        JButton addMaterialResourceButton = new JButton("Add Material");
        addMaterialResourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String materialType = (String) materialTypeComboBox.getSelectedItem();
                double materialCost = (double) materialCostSpinner.getValue();
                int materialQuantity = (int) materialQuantitySpinner.getValue();
                boolean materialAvailable = materialAvailabilityCheckBox.isSelected();
                materialResources.add(new Object[]{materialType, materialCost, materialQuantity, materialAvailable ? "Available" : "Not Available"});
                materialResourceTableModel.addRow(new Object[]{materialType, materialCost, materialQuantity, materialAvailable ? "Available" : "Not Available"});
            }
        });

        materialResourcePanel.add(addMaterialResourceButton, BorderLayout.SOUTH);
        return materialResourcePanel;
    }

    private static JPanel createTaskPanel() {
        JPanel taskPanel = new JPanel(new BorderLayout());
        JPanel taskInputPanel = new JPanel(new GridLayout(4, 2));
        taskInputPanel.add(new JLabel("Task Name:"));
        JTextField taskNameField = new JTextField(20);
        taskInputPanel.add(taskNameField);

        taskInputPanel.add(new JLabel("Task Duration (hours):"));
        JSpinner taskDurationSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        taskInputPanel.add(taskDurationSpinner);

        taskInputPanel.add(new JLabel("Task Priority:"));
        JSpinner taskPrioritySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        taskInputPanel.add(taskPrioritySpinner);

        taskPanel.add(taskInputPanel, BorderLayout.NORTH);

        DefaultTableModel taskTableModel = new DefaultTableModel(new Object[]{"Name", "Type", "Duration", "Priority"}, 0);
        JTable taskTable = new JTable(taskTableModel);
        taskPanel.add(new JScrollPane(taskTable), BorderLayout.CENTER);

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = taskNameField.getText();
                int taskDuration = (int) taskDurationSpinner.getValue();
                int taskPriority = (int) taskPrioritySpinner.getValue();
                tasks.add(new Object[]{taskName, taskDuration, taskPriority});
                taskTableModel.addRow(new Object[]{taskName, taskDuration, taskPriority});
            }
        });

        JButton addProcessButton = new JButton("Add Process");
        addProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog processDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(taskPanel), "Add Process", true);
                processDialog.setLayout(new BorderLayout());
                processDialog.setSize(400, 300);

                JPanel processInputPanel = new JPanel(new GridLayout(4, 2));
                processInputPanel.add(new JLabel("Process Name:"));
                JTextField processNameField = new JTextField(20);
                processInputPanel.add(processNameField);

                processInputPanel.add(new JLabel("Process Type:"));
                JComboBox<String> processTypeComboBox = new JComboBox<>(new String[]{"Type 1", "Type 2", "Type 3"});
                processInputPanel.add(processTypeComboBox);

                processInputPanel.add(new JLabel("Process Duration (hours):"));
                JSpinner processDurationSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
                processInputPanel.add(processDurationSpinner);

                processInputPanel.add(new JLabel("Process Cost:"));
                JSpinner processCostSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.01));
                processInputPanel.add(processCostSpinner);

                JButton saveProcessButton = new JButton("Save Process");
                saveProcessButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String processName = processNameField.getText();
                        String processType = (String) processTypeComboBox.getSelectedItem();
                        int processDuration = (int) processDurationSpinner.getValue();
                        double processCost = (double) processCostSpinner.getValue();
                        processes.add(new Object[]{processName, processType, processDuration, processCost});
                        JOptionPane.showMessageDialog(processDialog, "Process added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        processDialog.dispose();
                    }
                });


                processDialog.add(processInputPanel, BorderLayout.CENTER);
                processDialog.add(saveProcessButton, BorderLayout.SOUTH);
                processDialog.setVisible(true);
            }
        });

        taskPanel.add(addTaskButton, BorderLayout.SOUTH);
        taskPanel.add(addProcessButton, BorderLayout.EAST);

        return taskPanel;
    }

    private static JPanel createHumanResourcePanel(String dataFile) {
        JPanel humanResourcePanel = new JPanel(new BorderLayout());
        JPanel humanResourceInputPanel = new JPanel(new GridLayout(6, 2));

        humanResourceInputPanel.add(new JLabel("Employee Name:"));
        JTextField employeeNameField = new JTextField(20);
        humanResourceInputPanel.add(employeeNameField);

        humanResourceInputPanel.add(new JLabel("Employee Role:"));
        JComboBox<String> employeeRoleComboBox = new JComboBox<>(new String[]{"Role 1", "Role 2", "Role 3"});
        humanResourceInputPanel.add(employeeRoleComboBox);

        humanResourceInputPanel.add(new JLabel("Hourly Rate:"));
        JSpinner hourlyRateSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.01));
        humanResourceInputPanel.add(hourlyRateSpinner);

        humanResourceInputPanel.add(new JLabel("Employee Availability:"));
        JCheckBox employeeAvailabilityCheckBox = new JCheckBox("Available");
        humanResourceInputPanel.add(employeeAvailabilityCheckBox);

        humanResourceInputPanel.add(new JLabel("Assigned Task:"));
        JComboBox<String> assignedTaskComboBox = new JComboBox<>(taskComboBoxModel);
        humanResourceInputPanel.add(assignedTaskComboBox);

        humanResourcePanel.add(humanResourceInputPanel, BorderLayout.NORTH);

        DefaultTableModel humanResourceTableModel = new DefaultTableModel(new Object[]{"Name", "Role", "Rate", "Availability", "Task"}, 0);
        JTable humanResourceTable = new JTable(humanResourceTableModel);
        humanResourcePanel.add(new JScrollPane(humanResourceTable), BorderLayout.CENTER);

        JButton addHumanResourceButton = new JButton("Add Employee");
        addHumanResourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeName = employeeNameField.getText();
                String employeeRole = (String) employeeRoleComboBox.getSelectedItem();
                double hourlyRate = (double) hourlyRateSpinner.getValue();
                boolean employeeAvailable = employeeAvailabilityCheckBox.isSelected();
                String assignedTask = (String) assignedTaskComboBox.getSelectedItem();
                humanResources.add(new Object[]{employeeName, employeeRole, hourlyRate, employeeAvailable ? "Available" : "Not Available", assignedTask});
                humanResourceTableModel.addRow(new Object[]{employeeName, employeeRole, hourlyRate, employeeAvailable ? "Available" : "Not Available", assignedTask});
                appendToFile(dataFile, "Employee," + String.join(",", employeeName, employeeRole, String.valueOf(hourlyRate), employeeAvailable ? "Available" : "Not Available", assignedTask));
            }
        });

        humanResourcePanel.add(addHumanResourceButton, BorderLayout.SOUTH);
        return humanResourcePanel;
    }

    private static JPanel createProcessPanel() {
        JPanel processPanel = new JPanel(new BorderLayout());
        JPanel processInputPanel = new JPanel(new GridLayout(4, 2));

        processInputPanel.add(new JLabel("Process Name:"));
        JTextField processNameField = new JTextField(20);
        processInputPanel.add(processNameField);

        processInputPanel.add(new JLabel("Process Type:"));
        JComboBox<String> processTypeComboBox = new JComboBox<>(new String[]{"Type 1", "Type 2", "Type 3"});
        processInputPanel.add(processTypeComboBox);

        processInputPanel.add(new JLabel("Process Duration (hours):"));
        JSpinner processDurationSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        processInputPanel.add(processDurationSpinner);

        processInputPanel.add(new JLabel("Process Cost:"));
        JSpinner processCostSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.01));
        processInputPanel.add(processCostSpinner);

        processPanel.add(processInputPanel, BorderLayout.NORTH);

        DefaultTableModel processTableModel = new DefaultTableModel(new Object[]{"Name", "Type", "Duration", "Cost"}, 0);
        JTable processTable = new JTable(processTableModel);
        processPanel.add(new JScrollPane(processTable), BorderLayout.CENTER);

        JButton addProcessButton = new JButton("Add Process");
        addProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String processName = processNameField.getText();
                String processType = (String) processTypeComboBox.getSelectedItem();
                int processDuration = (int) processDurationSpinner.getValue();
                double processCost = (double) processCostSpinner.getValue();
                processes.add(new Object[]{processName, processType, processDuration, processCost});
                processTableModel.addRow(new Object[]{processName, processType, processDuration, processCost});
            }
        });

        processPanel.add(addProcessButton, BorderLayout.SOUTH);
        return processPanel;
    }

    private static void appendToFile(String fileName, String data) {
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveProjectData(String dataFile, JDialog dialog) {
        try {
            FileWriter writer = new FileWriter(dataFile, true);
            BufferedWriter buffer = new BufferedWriter(writer);

            buffer.write("Tasks\n");
            buffer.write("Name,Duration,Priority\n");
            for (Object[] task : tasks) {
                buffer.write(String.join(",", task[0].toString(), task[1].toString(), task[2].toString(), task[3].toString()));
                buffer.write("\n");
            }

            buffer.write("Human Resources\n");
            buffer.write("Name,Role,Rate,Availability,Task\n");
            for (Object[] hr : humanResources) {
                buffer.write(String.join(",", hr[0].toString(), hr[1].toString(), hr[2].toString(), hr[3].toString(), hr[4].toString()));
                buffer.write("\n");
            }

            buffer.write("Material Resources\n");
            buffer.write("Type,Cost,Quantity,Availability\n");
            for (Object[] mr : materialResources) {
                buffer.write(String.join(",", mr[0].toString(), mr[1].toString(), mr[2].toString(), mr[3].toString()));
                buffer.write("\n");
            }

            buffer.write("Processes\n");
            buffer.write("Name,Type,Duration,Cost\n");
            for (Object[] process : processes) {
                buffer.write(String.join(",", process[0].toString(), process[1].toString(), process[2].toString(), process[3].toString()));
                buffer.write("\n");
            }

            buffer.close();
            writer.close();

            JOptionPane.showMessageDialog(dialog, "Project data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(dialog, "Failed to save project data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void loadProjectDetails(JList<String> projectFileList, JPanel projectDetailsPanel) {
        String selectedProject = projectFileList.getSelectedValue();
        if (selectedProject == null) {
            JOptionPane.showMessageDialog(null, "Please select a project.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        projectDetailsPanel.removeAll();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(DATA_DIRECTORY + selectedProject));
            String line;
            JTextArea projectDetailsArea = new JTextArea();
            projectDetailsArea.setEditable(false);
            projectDetailsPanel.add(new JScrollPane(projectDetailsArea), BorderLayout.CENTER);

            while ((line = reader.readLine()) != null) {
                projectDetailsArea.append(line + "\n");
            }

            reader.close();
            projectDetailsPanel.revalidate();
            projectDetailsPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load project details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
