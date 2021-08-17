// This program will let the user use a fully functioning calculator to perform simple math
//
// Variables Used:
// answer - used as the display screen for inputting and outputting numbers
// curnum - stores the current number on the screen
// operations - tells which operation is going to be used | 1 = plus, 2 = minus, 3 = multiply, 4 = divide
// decimalcounter - makes sure there is only 1 decimal per number
// ans - stores the answer of the current operation after clicking =
// prevnum - stores the first number involved in any operation
// isempty - checks if display is empty
// ifnewnum - checks if the display needs to be reset (after finding a calculation but continuing with running total)

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener
{

    JTextField answer;
    String curnum = "";
    int operations = 0, decimalcounter = 0;
    double ans, prevnum = 1;
    boolean isempty, ifnewnum = false;
    JButton buttons[] = new JButton [18];

    // Main Method - creates object
    public static void main (String args[])
    {
	new Calculator ();
    }


    // Calculator Method
    // Creates all graphics and buttons
    public Calculator ()
    {
	//Frame set-up
	this.setSize (350, 450);
	this.setTitle ("Calculator");
	this.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
	this.setLocationRelativeTo (null);

	//Set up content pane
	JPanel contentPane = (JPanel) this.getContentPane ();
	contentPane.setLayout (new BorderLayout ());

	//Sets up all buttons with names and action commands
	Font buttonfont = new Font ("Tahoma", Font.BOLD, 24);
	Color darkpink = new Color (0.87F, 0.6F, 0.6F);
	String labels[] = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0", ".", "+/-", "+", "-", "÷", "x", "CLEAR", "="};
	for (int count = 0 ; count < labels.length ; count++)
	{
	    buttons [count] = new JButton (labels [count]);
	    buttons [count].setFont (buttonfont);
	    buttons [count].setActionCommand (labels [count]);
	    buttons [count].addActionListener (this);
	}

	buttons [11].setToolTipText ("changes sign of number");
	buttons [10].setToolTipText ("adds decimal (max 1 decimal per number)");
	buttons [16].setToolTipText ("clears screen and resets calculator");
	buttons [17].setToolTipText ("calculates result");

	//Sets up main numbers panel with nums 0-9, a decimal place and positive/negative switch
	JPanel numbers = new JPanel ();
	numbers.setLayout (new GridLayout (4, 3, 10, 10));
	for (int count = 0 ; count < 12 ; count++)
	{
	    numbers.add (buttons [count]);
	    buttons [count].setBackground (Color.pink);
	}
	numbers.setBackground (Color.pink);
	contentPane.add (numbers, BorderLayout.CENTER);

	//Sets up panel with operation keys (plus, minus, divide and multiply signs)
	JPanel operations = new JPanel ();
	operations.setLayout (new GridLayout (4, 1, 10, 10));
	for (int count = 12 ; count < 16 ; count++)
	{
	    operations.add (buttons [count]);
	    buttons [count].setBackground (darkpink);
	}
	operations.setBackground (darkpink);
	contentPane.add (operations, BorderLayout.EAST);

	//Sets up bottom panel with Clear and Equal sign
	JPanel displaybuttons = new JPanel ();
	displaybuttons.setLayout (new GridLayout (1, 2, 10, 10));
	for (int count = 16 ; count < 18 ; count++)
	{
	    displaybuttons.add (buttons [count]);
	    buttons [count].setBackground (Color.darkGray);
	    buttons [count].setForeground (Color.white);
	}
	displaybuttons.setBackground (Color.darkGray);
	contentPane.add (displaybuttons, BorderLayout.SOUTH);

	//Creates display panel holding display screen and current numbers
	JPanel display = new JPanel ();
	answer = new JTextField (16);
	answer.setEditable (false);
	answer.setFont (answer.getFont ().deriveFont (24.0f));
	display.add (answer);
	contentPane.add (display, BorderLayout.NORTH);

	this.setVisible (true);
    }


    // Methods for each operation
    public void add ()
    {
	ans = Double.parseDouble (curnum);
	ans += prevnum;
    }


    public void minus ()
    {
	ans = Double.parseDouble (curnum);
	ans = prevnum - ans;
    }


    public void multiply ()
    {
	ans = Double.parseDouble (curnum);
	ans *= prevnum;
    }


    public void divide ()
    {
	ans = Double.parseDouble (curnum);
	ans = prevnum / ans;
    }


    //Rounding method - used to keep answers to reasonable length
    double Round (double num, int decimal)
    {
	int changer = 1;

	for (int count = 1 ; count <= decimal ; count++)
	{
	    changer *= 10;
	}

	if (num >= 0)
	{
	    num = (int) (num * changer + 0.5);
	}
	else
	{
	    num = (int) (num * changer - 0.5);
	}
	num /= changer;
	return num;
    }


    // Event Listener Method
    public void actionPerformed (ActionEvent evt)
    {

	int num;
	try
	{
	    // Tries to take in action command as an integer
	    // If it does, this means the user entered a number and will add it to the screen
	    num = Integer.parseInt (evt.getActionCommand ());
	    // ifnewnum determines whether the previous answer needs to be wiped off the screen so the user can enter the next required number
	    if (ifnewnum == true)
	    {
		// if so clear screen and store the result in prevnum
		prevnum = Double.parseDouble (curnum);
		curnum = "";
	    }
	    // there is not an answer to be wiped off anymore
	    ifnewnum = false;
	    //screen is not empty
	    isempty = false;
	    curnum += "" + num;
	    answer.setText (curnum);
	}
	catch (NumberFormatException numformaterr)
	{
	    // If not a number deal with situation as appropriate
	    if (evt.getActionCommand ().equals ("."))
	    {

		if (ifnewnum == true)
		{
		    //same check as above
		    prevnum = Double.parseDouble (curnum);
		    curnum = "";
		    ifnewnum = false;
		}
		//Adds a decimal to the screen unless there is already a decimal on the screen
		//Prevents invalid numbers with multiple decimal places
		if (decimalcounter == 0)
		{
		    curnum += ".";
		    isempty = false;
		    decimalcounter += 1;
		    buttons [10].setVisible (false);
		    answer.setText (curnum);
		}

	    }
	    else if (evt.getActionCommand ().equals ("+/-"))
	    {
		//Key used to change the sign of the number
		//If negative remove negative sign
		//If positive add negative sign
		try
		{
		    if (curnum.charAt (0) == '-')
		    {
			curnum = curnum.substring (1, curnum.length ());
		    }
		    else
		    {
			curnum = "-" + curnum;
		    }
		    answer.setText (curnum);
		}
		catch (IndexOutOfBoundsException indexbounderr)
		{
		    curnum = "";
		}
	    }
	    else if (evt.getActionCommand ().equals ("CLEAR"))
	    {
		//If Clear is pressed reset the display and all other variables
		curnum = "";
		prevnum = 0;
		isempty = true;
		answer.setText ("");
		decimalcounter = 0;
		buttons [10].setVisible (true);
		ifnewnum = false;
	    }
	    else
	    {
		//If none of the above either = or a operation is pressed
		if (evt.getActionCommand ().equals ("="))
		{
		    //If equal sign make sure display isn't empty
		    if (!curnum.equals ("") && isempty == false)
		    {
			try
			{
			    //Apply operation based on operations variable
			    //(declared below)
			    if (operations == 1)
			    {
				add ();
				curnum = "" + Round (ans, 5);
			    }
			    else if (operations == 2)
			    {
				minus ();
				curnum = "" + Round (ans, 5);
			    }
			    else if (operations == 3)
			    {
				divide ();
				curnum = "" + + Round (ans, 5);
			    }
			    else if (operations == 4)
			    {
				multiply ();
				curnum = "" + Round (ans, 5);
			    }
			    //Reset all numbers as the current operation is complete
			    prevnum = 0;
			    ans = 0;
			    operations = 0;
			    //If an integer result is created -> remove the extra '.0' on the end of integer
			    if (curnum.substring (curnum.length () - 2, curnum.length ()).equals (".0"))
			    {
				curnum = curnum.substring (0, curnum.length () - 2);
				//Reset decimal counter if no decimal
				decimalcounter = 0;
				buttons [10].setVisible (true);
			    }
			    else if (curnum.equals (""))
			    {
				decimalcounter = 0;
				buttons [10].setVisible (true);
			    }
			    else
			    {
				//If a decimal is not removed - maintain decimal count as 1
				decimalcounter = 1;
				buttons [10].setVisible (false);
			    }
			    answer.setText (curnum);
			}
			catch (NumberFormatException numformerr)
			{
			    curnum = "";
			}
			catch (StringIndexOutOfBoundsException stringindexerr)
			{
			    curnum = curnum;
			    //If no operation is clicked don't change the screen
			}
			ifnewnum = true;
			decimalcounter = 0;
			buttons [10].setVisible (true);
		    }
		}
		else
		{
		    //If an operation is clicked
		    try
		    {
			//To ensure running total perform same operation as = sign to calculate result
			if (operations != 0)
			{
			    if (operations == 1)
			    {
				add ();
				curnum = "" + Round (ans, 5);
			    }
			    else if (operations == 2)
			    {
				minus ();
				curnum = "" + Round (ans, 5);
			    }
			    else if (operations == 3)
			    {
				divide ();
				curnum = "" + + Round (ans, 5);
			    }
			    else if (operations == 4)
			    {
				multiply ();
				curnum = "" + Round (ans, 5);
			    }
			    //Reset all numbers as the current operation is complete
			    prevnum = 0;
			    ans = 0;
			    operations = 0;
			    //If an integer result is created -> remove the extra '.0' on the end of integer
			    if (curnum.substring (curnum.length () - 2, curnum.length ()).equals (".0"))
			    {
				curnum = curnum.substring (0, curnum.length () - 2);
				//Reset decimal counter if no decimal
				decimalcounter = 0;
				buttons [10].setVisible (true);
			    }



			}
			//Set prevnum to the current number as user is now inputting a separate number
			prevnum = Double.parseDouble (curnum);
			//Set operations variable to matching index for next operation
			if (evt.getActionCommand ().equals ("+"))
			{
			    operations = 1;
			}
			else if (evt.getActionCommand ().equals ("-"))
			{
			    operations = 2;
			}
			else if (evt.getActionCommand ().equals ("÷"))
			{
			    operations = 3;
			}
			else if (evt.getActionCommand ().equals ("x"))
			{
			    operations = 4;
			}
			// an answer has been displayed and a new number will need to be entered (reset everything)
			ifnewnum = true;
			answer.setText (curnum);
			decimalcounter = 0;
			buttons [10].setVisible (true);
		    }
		    catch (NumberFormatException numforerr)
		    {
			// If no number has been entered - do nothing
			curnum = "";
			answer.setText ("");
		    }
		}
	    }
	}
    }
}
