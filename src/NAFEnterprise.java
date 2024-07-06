//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;
import java.io.IOException;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.FileReader;


public class NAFEnterprise {

    public NAFEnterprise(String name){

        name_ = name;
    }

    public void init() throws IOException{

        try {

            loadEmployeeFunctionsFomCsv();
            loadEmployeesFromCsv();
            loadRawMaterialsFromCsv();
            loadMiscellaneousFromCsv();
        }
        catch (IOException ex) {

            throw ex;
        }
    }

    private void loadEmployeeFunctionsFomCsv() throws IOException {

        try {

            FileReader      fr          = new FileReader("data/employeeFunctions.csv");
            BufferedReader  br          = new BufferedReader(fr);

            // Read the file line by line and ignore first line.
            // First line is the csv header : function,cost($/h)
            String          line        = br.readLine();
            int             lineNumber  = 0;
            while (line != null) {

                lineNumber = lineNumber +1;
                if(lineNumber != 1) {

                    //Get all tokens available in line
                    String[] tokens = line.split(",");
                    Double cost = Double.valueOf(tokens[1]);
                    employeeFunctions_.put(tokens[0], cost);
                }

                line = br.readLine();
            }
            br.close();
        }
        catch (IOException ex) {

            throw ex;
        }
    }

    private void loadEmployeesFromCsv() throws IOException {

        try {

            FileReader      fr          = new FileReader("data/employees.csv");
            BufferedReader  br          = new BufferedReader(fr);

            // Read the file line by line and ignore first line.
            // First line is the csv header : firstname,surname,function
            String          line        = br.readLine();
            int             lineNumber  = 0;
            while (line != null) {

                lineNumber = lineNumber +1;
                if(lineNumber != 1) {

                    //Get all tokens available in line
                    String[] tokens         = line.split(",");
                    String firstname        = tokens[0];
                    String surname          = tokens[1];
                    String employeeFunction = tokens[2];

                    String name         = firstname + "." + surname;
                    employees_.put(name, employeeFunction);
                }

                line = br.readLine();
            }

            br.close();
        }
        catch (IOException ex) {

            throw ex;
        }
    }

    private void loadRawMaterialsFromCsv() throws IOException {

        try {

            FileReader      fr          = new FileReader("data/rawMaterials.csv");
            BufferedReader  br          = new BufferedReader(fr);

            // Read the file line by line and ignore first line.
            // First line is the csv header : name,cost($/kg)
            String          line        = br.readLine();
            int             lineNumber  = 0;
            while (line != null) {

                lineNumber = lineNumber +1;
                if(lineNumber != 1) {

                    //Get all tokens available in line
                    String[] tokens = line.split(",");
                    Double cost = Double.valueOf(tokens[1]);
                    rawMaterials_.put(tokens[0], cost);
                }

                line = br.readLine();
            }
            br.close();
        }
        catch (IOException ex) {

            throw ex;
        }
    }

    private void loadMiscellaneousFromCsv() throws IOException {

        try {

            FileReader      fr          = new FileReader("data/miscellaneous.csv");
            BufferedReader  br          = new BufferedReader(fr);

            // Read the file line by line and ignore first line.
            // First line is the csv header : name,cost($)
            String          line        = br.readLine();
            int             lineNumber  = 0;
            while (line != null) {

                lineNumber = lineNumber +1;
                if(lineNumber != 1) {

                    //Get all tokens available in line
                    String[] tokens = line.split(",");
                    Double cost = Double.valueOf(tokens[1]);
                    miscellaneous_.put(tokens[0], cost);
                }

                line = br.readLine();
            }
            br.close();
        }
        catch (IOException ex) {

            throw ex;
        }
    }

    String                              name_;

    /**
     * Association: EmployeeFunction(String)/Cost(double)
     */
    Hashtable<String, Double>           employeeFunctions_ = new Hashtable<String, Double>();

    /**
     * Association: Firstname.Surname(String)/Function(String)
     */
    Hashtable<String,String>            employees_           = new Hashtable<String, String>();

    /**
     * Association: Name(String)/Cost(double)
     */
    Hashtable<String, Double>           rawMaterials_       = new Hashtable<String, Double>();

    /**
     * Association: Name(String)/Cost(double)
     */
    Hashtable<String, Double>           miscellaneous_     = new Hashtable<String, Double>();

    public static void main(String[] args) {

        try {

            NAFEnterprise   enterprise  = new NAFEnterprise("Omega");
            enterprise.init();

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            NAFUI           ui          = new NAFUI();
            ui.setVisible(true);
        }
        catch (Exception ex) {

            System.out.println(ex);
        }
    }
}
