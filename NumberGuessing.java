import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NumberGuessing extends JFrame implements ActionListener{
    private int numberToGuess;
    private int gameScore = 0;
    private int guessesLeft;

    private JLabel hiddenNumberLabel;
    private JLabel guessesLeftLabel;
    private JTextField guessTextField;
    private JButton guessButton;
    private JLabel gameScoreLabel;
    private JLabel messageLabel;

    public NumberGuessing() {

        // Setting up the main JFrame for the game
        setTitle("Number Guessing");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Creating and configuring GUI components
        hiddenNumberLabel = new JLabel();
        guessesLeftLabel = new JLabel("Guesses Left: " + guessesLeft);
        guessTextField = new JTextField(3);
        guessButton = new JButton("Guess");
        messageLabel = new JLabel("");
        gameScoreLabel = new JLabel("Score: " + gameScore);

        guessButton.addActionListener(this);
        guessTextField.addActionListener(this);

        // Creating the main panel with GridBagLayout for organizing the components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Adding components to the main panel with specified constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(hiddenNumberLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(guessesLeftLabel, gbc);

        gbc.gridx = 2;
        mainPanel.add(guessTextField, gbc);

        gbc.gridx = 3;
        mainPanel.add(guessButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        mainPanel.add(messageLabel, gbc);

        gbc.gridy = 2;
        mainPanel.add(gameScoreLabel, gbc);

        // Adding the main panel to the content pane of the JFrame
        getContentPane().add(mainPanel);
        // Initializing the game with a random number to guess
        initializeGame();
        // Centering the JFrame on the screen and making it visible
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initializeGame(){
        // Initialize the game with a new random number to guess
        numberToGuess = (int) (Math.random() * 100) + 1;
        // Prepare a string with underscores representing the hidden number
        StringBuilder hiddenNumberString = new StringBuilder();
        for (int i = 0; i < Integer.toString(numberToGuess).length(); i++) {
            hiddenNumberString.append("_ ");
        }
        // Reset the guesses left, update labels, and show an initial message
        guessesLeft = 10;
        hiddenNumberLabel.setText(hiddenNumberString.toString());
        guessesLeftLabel.setText("Guesses Left: " + guessesLeft);
        messageLabel.setText("Enter number between 1 to 100 ");
        gameScoreLabel.setText("Score:"+gameScore);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle user's actions (Guess button or Enter key pressed in the text field)
        if(e.getSource() == guessButton || e.getSource() == guessTextField){
            String guessText = guessTextField.getText();
            int guessNum = Integer.parseInt(guessText);
            // Update the hidden number display and check if the game is over
            updateHiddenNumber(guessNum);
            guessTextField.setText("");
        }
    }

    private void updateHiddenNumber(int guessNum) {
        // Check if the user guessed the correct number
        if(guessNum == numberToGuess){
            // Display the correct number, update the score, and end the game
            hiddenNumberLabel.setText(Integer.toString(guessNum));
            gameScore+=(10*guessesLeft);
            gameScoreLabel.setText("Score:"+gameScore);
            endGame("Congratulations! you won!");
        }
        else{
            // Reduce the guesses left and display appropriate messages
            guessesLeft--;
            guessesLeftLabel.setText("Guesses Left:"+guessesLeft);

            // Check if the guess is out of the valid range or higher/lower than the hidden number
            if(guessNum > 100 || guessNum < 0){
                messageLabel.setText("Enter number between 1 to 100");
            }
            else if(guessNum > numberToGuess){
                messageLabel.setText("Number you guesssed is larger");
            }
            else if(guessNum < numberToGuess){
                messageLabel.setText("Number you guessed is smaller");
            }

            // Check if the user has run out of guesses, and if so, end the game
            if(guessesLeft == 0){
                endGame("You lose! the number is"+numberToGuess);
                gameScore = 0;
                gameScoreLabel.setText("Score:"+gameScore);
            }
        }
    }
    
    public void endGame(String displayMessage){
        // Display a message dialog indicating the game result
        guessTextField.setEnabled(false);
        guessButton.setEnabled(false);
        JOptionPane.showMessageDialog(this, displayMessage, "Game over", JOptionPane.INFORMATION_MESSAGE);

        // Reset the game, enable text field and button, and request focus on the text field
        initializeGame();
        guessTextField.setEnabled(true);
        guessButton.setEnabled(true);
        guessTextField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessing::new);
    }
}
