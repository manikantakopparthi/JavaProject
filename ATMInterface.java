import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ATMInterface extends JFrame {
    private JTextField userIdField;
    private JPasswordField pinField;

    ArrayList <Integer> Money = new ArrayList<>();
    HashMap <String,String> personDetails = new HashMap<>();
    ArrayList <String> transactionHistory = new ArrayList<>();

    private int index;

    public ATMInterface() {

        setTitle("ATM Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        // Sample user details and account balances
        personDetails.put("admin", "1234");
        personDetails.put("user1", "0723");

        Money.add(1000);
        Money.add(50000);
        
        // Username Panel Label and TextField for entering user ID
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        usernamePanel.add(new JLabel("USER ID:"));
        userIdField = new JTextField(8);
        userIdField.setPreferredSize(new Dimension(200, 22));
        usernamePanel.add(userIdField);

        // Pin Panel Label and TextField for entering Pin
        JPanel pinPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        pinPanel.add(new JLabel("      PIN:"));
        pinField = new JPasswordField(8);
        pinField.setPreferredSize(new Dimension(200, 22));
        pinPanel.add(pinField);

        //Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(140, 45));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                char[] pinChars = pinField.getPassword();
                String pin = new String(pinChars);
                // Checking the login credentials
                if (validateLogin(userId, pin)) {
                    showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(ATMInterface.this, "Invalid credentials. Please try again.");
                }
            }
        });

        // Adding components to JFrame
        add(usernamePanel, BorderLayout.BEFORE_FIRST_LINE);
        add(pinPanel,BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
    }

    // Function to Check if the provided user ID and PIN match the stored values
    private boolean validateLogin(String userId, String pin) {
        ArrayList<String> keyList = new ArrayList<>(personDetails.keySet());
        index = keyList.indexOf(userId);
        if(index == -1)
            return false;
        else{
            String pinCheck = personDetails.get(userId);
            if(!pin.equals(pinCheck))
                return false;
        }
        return true;
    }

    // Function contains buttons and their functionalities
    private void showMainMenu() {
        getContentPane().removeAll();
        repaint();

        setTitle("ATM Main Menu");

        // Button Panel for main menu options
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10,10, 10);  // Set spacing between buttons

        // Transaction History Button
        JButton transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.setPreferredSize(new Dimension(200, 40));
        transactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // transaction history functionality
                // JOptionPane.showMessageDialog(ATMInterface.this, "Transaction History");
                transactionHistoryMenu();
            }
        }); 
        constraints.gridx = 0;
        constraints.gridy = 0;
        buttonPanel.add(transactionHistoryButton, constraints);

        // Withdraw Button
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setPreferredSize(new Dimension(200, 40));
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // withdraw functionality
                updateMoney("withdraw");
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 0;
        buttonPanel.add(withdrawButton, constraints);

        // Deposit Button
        JButton depositButton = new JButton("Deposit");
        depositButton.setPreferredSize(new Dimension(200, 40));
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // deposit functionality
                updateMoney("deposit");
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 1;
        buttonPanel.add(depositButton, constraints);

        // Transfer Button
        JButton transferButton = new JButton("Transfer");
        transferButton.setPreferredSize(new Dimension(200, 40));
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // transfer functionality
                updateMoney("transfer");
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 1;
        buttonPanel.add(transferButton, constraints);

        // Balance Button
        JButton balanceButton = new JButton("Balance");
        balanceButton.setPreferredSize(new Dimension(200, 40));
        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                repaint();
                JOptionPane.showMessageDialog(ATMInterface.this, "Your Account Balance is : " + Money.get(index));
                showMainMenu();
            }
        });
        constraints.gridx = 0;
        constraints.gridy = 2;
        buttonPanel.add(balanceButton, constraints);

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(200, 40));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // quit functionality
                JOptionPane.showMessageDialog(ATMInterface.this, "Quit");
                System.exit(0);
            }
        });
        constraints.gridx = 1;
        constraints.gridy = 2;
        buttonPanel.add(quitButton, constraints);

        // Adding button panel to the JFrame
        add(buttonPanel, BorderLayout.CENTER);
        revalidate();
    }

    //Function to display transactionHistory
    private void transactionHistoryMenu(){
        getContentPane().removeAll();
        repaint();

        setTitle("TransactionHistory");

        // Create and configure the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a JEditorPane with HTML content
        JEditorPane editorPane = new JEditorPane();
        editorPane.setContentType("text/html"); // Set content type to HTML
        editorPane.setEditable(false); // Make it read-only

        // Create an HTML string with bold text for each transaction
        StringBuilder htmlContent = new StringBuilder("<html><body><div align='center'>");
        for (String transaction : transactionHistory) {
            htmlContent.append("<b>").append(transaction).append("</b><br>");
        }
        htmlContent.append("</div></body></html>");
        
        // Set the HTML content to the JEditorPane
        editorPane.setText(htmlContent.toString());

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(140, 45));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(ATMInterface.this,"Exit Transaction History");
                showMainMenu();
            }
        });

        add(new JScrollPane(editorPane), BorderLayout.CENTER);
        add(exitButton, BorderLayout.SOUTH);
        revalidate();
    }

    // Function to performe required operation
    private void updateMoney(String s){
        getContentPane().removeAll();
        repaint();

        JLabel DisplayLabel = new JLabel();
        JTextField amountTextField = new JTextField(10);
        JButton conformButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");

        if(s.equals("withdraw")){
            setTitle("Withdraw Amount");
            DisplayLabel.setText("Enter Amount to Withdraw");
        }
        else if(s.equals("deposit")){
            setTitle("Deposit Amount");
            DisplayLabel.setText("Enter Amount to Deposit");
        }
        else if(s.equals("transfer")){
            setTitle("Transfer Amount");
            DisplayLabel.setText("Enter Amount to Transfer");
        }

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(ATMInterface.this,"Cancel Transaction");
                showMainMenu();
            }
        });
        
        conformButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String amountString = amountTextField.getText();
                int amountToUpdate = Integer.parseInt(amountString);
                int amount = Money.get(index);
                if(s.equals("withdraw")){
                    if(amountToUpdate>amount){
                        JOptionPane.showMessageDialog(ATMInterface.this,"Insufficent Balance");
                        transactionHistory.add("Withdraw Failed");
                        showMainMenu();
                    }
                    else{
                        amount = amount-amountToUpdate;
                        Money.set(index, amount);
                        JOptionPane.showMessageDialog(ATMInterface.this,"Withdraw Successfull");
                        transactionHistory.add("Amount Withdraw : "+amountToUpdate);
                        showMainMenu();
                    }
                    amountTextField.setText("");
                }
                else if(s.equals("deposit")){
                    amount = amount+amountToUpdate;
                    Money.set(index, amount);
                    JOptionPane.showMessageDialog(ATMInterface.this,"Deposited Successfull");
                    transactionHistory.add("Amout Deposite : "+amountToUpdate);
                    amountTextField.setText("");
                    showMainMenu();
                }
                else if(s.equals("transfer")){
                    if(amountToUpdate>amount){
                        JOptionPane.showMessageDialog(ATMInterface.this,"Insufficent Balance");
                        transactionHistory.add("Transfer Failed");
                        showMainMenu();
                    }
                    else{
                        amount = amount-amountToUpdate;
                        Money.set(index, amount);
                        JOptionPane.showMessageDialog(ATMInterface.this,amountToUpdate+"Transfered Successfull");
                        transactionHistory.add("Amount Trasfer : "+amountToUpdate);
                        amountTextField.setText("");
                        showMainMenu();
                    }
                }
            }
        });

        JPanel withdrawPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10); // Set spacing between components

        constraints.gridx = 0;
        constraints.gridy = 0;
        withdrawPanel.add(DisplayLabel, constraints);

        constraints.gridy = 1;
        amountTextField.setPreferredSize(new Dimension(200, 25));
        withdrawPanel.add(amountTextField, constraints);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.insets = new Insets(10, 10, 10, 10); // Set spacing between components

        buttonConstraints.gridx = 0;
        buttonPanel.add(cancelButton, buttonConstraints);

        buttonConstraints.gridx = 1;
        buttonPanel.add(conformButton, buttonConstraints);

        add(withdrawPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
        revalidate();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ATMInterface atmInterface = new ATMInterface();
                atmInterface.setVisible(true);
            }
        });
    }
}
