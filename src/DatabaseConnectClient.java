/*
 *  Name: Gregory Flynn
 *  Course: CNT 4714 Summer 2022
 *  Assignment title: Project 2 - A Two-tier Client-Server Application
 *  Date: July 4, 2022
 * 
 *  Class: DatabaseConnectClient.java
 */

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
// import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.io.File;
import java.util.Vector;
import java.awt.Color;
import java.awt.Dimension;

// classTags: [FRONT END] [DRIVER] [CLIENT-SERVER]

// Create a 'DatabaseConnectClient' object that
//  1. graphically displays all pertinent information
//  2. provides buttons for executing actions
//  3. allows login as an arbitrary user
//  4. connects to a specific database via JDBC
//  5. issues mySQL-style commands
//  6. logs all client queries and updates
public class DatabaseConnectClient extends JFrame {

  /* class variables that represent GUI components */
  private JTextArea queryArea;
  
  /* CONSTRUCTOR */
  public DatabaseConnectClient() {
    
    /* INITIALIZE 'JFrame' OBJECT */
    // evoke JFrame constructor and provide string.
    //  the constructor parameter string represents title of application.
    //  the JFrame object will be the "top-level component".
    //    that is to say, the JFrame object is the "root" in the "containment hierarchy".
    //      in english, all other components go onto the JFrame window.
    super("SQL Client App Version 1 - (GDF - CNT 4714 - Summer 2022 - Project 2)");
    
    // set dimensions of JFrame
    setSize( 2000, 1200 );

    /* INITIALIZE GUI COMPONENTS */
    // try-catch structure necessary because
    //  1. 'Connector/J' depedency could be missing:
    //    handled in the 'ClassNotFoundException' catch block
    //  2. Database connection invalid:
    //    handled in the 'SQLException' catch block
    try {

      /* INSTANTIATE 'Color' OBJECTS */
      //  These Color objects are used to paint different components
      Color verylightGrey = new Color( 238, 238, 238 );
      Color limeGreen = new Color( 1, 255, 0 );

      // change background color of JFrame with methods
      // '.getContentPane()' from the 'JFrame' class and
      //    .getContentPane(): why?
      //      "In Java Swing, the layer that is used to hold objects is called the 'Content Pane'."
      //      The window that pops up when the program is run IS the content pane.
      //        Therefore, if modifying anything on this window, the content pane needs to be changed.
      // '.setBackground(Color)' from the 'Component' class. 
      //    JFrame methods can be called statically since this class
      //    EXTENDS the JFrame class.
      //      in other words, the JVM knows JFrame methods are in reference
      //      to 'DatabaseConnectClient' since this code is inside the class's constructor
      //      and the class IS A JFrame object.
      getContentPane().setBackground( verylightGrey );

      /* INITIALIZE 'JButton' COMPONENTS */
      JButton clearSQLCommand = new JButton( "Clear SQL Command" );
      clearSQLCommand.setBackground( Color.WHITE );
      clearSQLCommand.setForeground( Color.RED );

      JButton executeSQLCommand = new JButton( "Execute SQL Command" );
      executeSQLCommand.setBackground( limeGreen );

      // Layout buttons left to right using a BoxLayout manager
      //  buttonBox organizes its components from left to right ("LINE_AXIS").
      Box buttonBox = new Box( BoxLayout.LINE_AXIS );

      // Components are organized in the order they are added:
      //  Adding 'clearSQLCommand' first ensures it is on the left.

      buttonBox.add( clearSQLCommand );
      buttonBox.add( executeSQLCommand );

      /* INSTANTIATE TOP-RIGHT OF JFRAME CONTENT PANE 
       *  The purpose of the top-right components is to input, clear, and execute
       *  SQL commands.
       *  includedComponenets
       *    1. 'clearSQLCommand'    (JButton) 
       *    2. 'executesSQLCommand' (JButton)
       *    3. 'queryAreaLabel'     (JLabel)
       *    4. 'queryArea'          (JTextArea)
       */
      JLabel queryAreaLabel = new JLabel( "Enter An SQL Command" );

      /* INSTANTIATE A 'JTextArea' OBJECT */
      //  the purpose of the JTextArea is to allow the user to pass MySQL queries to the client
      //  when connected to the database, the client then passes the query to the database (server).
      //    This is the "two-tier" behavior!
      queryArea = new JTextArea();

      // set text wrapping property.
      // passing 'true' as a parameter has text wrap at word boundary
      // passing 'false' has text wrap at character boundary
      queryArea.setWrapStyleWord( true );
      
      // set line wrapping property.
      // 'true' paramater has lines wrap if they exceed JTextArea width
      // 'false' paramter has lines never wrap
      // queryArea.setLineWrap( true );

      // align all SQL Command Area components in 'topRightBox'
      //  topRightBox componenets are aligned from top to bottom
      Box topRightBox = new Box( BoxLayout.PAGE_AXIS );
      topRightBox.add( queryAreaLabel );
      topRightBox.add( queryArea );
      topRightBox.add( buttonBox );

      /* INSTANTIATE TOP-LEFT OF JFRAME CONTENT PANE 
       *  The purpose of the top-left components is to log in to the database
       *  as any arbitrary user via a properties file.
       *  includedComponenets
       *    1. 'connectionDetailsLabel'     (JLabel) 
       *    2. 'propertiesFilePane'         (JTextPane)
       *    3. 'propertiesFileComboBox'     (JComboBox)
       *    4. 'usernamePane'               (JTextPane)
       *    5. 'usernameField'              (JTextField)
       *    6. 'passwordPane'               (JTextPane)
       *    7. 'passwordField'              (JPasswordField)
       *    8. 'connectToDatabaseButton'    (JButton)
       */

      JLabel connectionDetailsLabel = new JLabel( "Connection Details" );
      
      JTextPane propertiesFilePane = new JTextPane();

      // hard-code 'File' objects to put into a vector
      File rootUserFile = new File( "root.properties" );
      File clientUserFile = new File( "client.properties" );

      // instantiate and populate a Vector of files
      //  'propertiesFileList' is passed to a JComboBox to provide options
      Vector propertiesFileList = new Vector();
      propertiesFileList.add( 0, rootUserFile );
      propertiesFileList.add( 1, clientUserFile );

      // System.out.println( propertiesFileList.size() );

      JComboBox propertiesFileComboBox = new JComboBox( propertiesFileList );

      // use a Box to align a pane with its respective input field
      Box propertiesFileBox = new Box( BoxLayout.LINE_AXIS );
      propertiesFileBox.add( propertiesFilePane );
      propertiesFileBox.add( propertiesFileComboBox );

      JTextPane usernamePane = new JTextPane();

      JTextField usernameField = new JTextField();

      Box usernameBox = new Box( BoxLayout.LINE_AXIS );
      usernameBox.add( usernamePane );
      usernameBox.add( usernameField );

      JTextPane passwordPane = new JTextPane();

      JPasswordField passwordField = new JPasswordField();

      Box passwordBox = new Box( BoxLayout.LINE_AXIS );
      passwordBox.add( passwordPane );
      passwordBox.add( passwordField );

      JButton connectToDatabaseButton = new JButton( "Connect to Database" );

      Box topLeftBox = new Box( BoxLayout.PAGE_AXIS );
      topLeftBox.add( connectionDetailsLabel );
      topLeftBox.add( propertiesFileBox );
      topLeftBox.add( usernameBox );
      topLeftBox.add( passwordBox );
      topLeftBox.add( connectToDatabaseButton );

      /***** SPLIT JFRAME CONTENT INTO TWO CENTRAL BOXES *****/

      // 'northBox' is the top half of the JFrame content
      //  northBox organizes its content left to right
      Box northBox = new Box( BoxLayout.LINE_AXIS );

      northBox.add( topLeftBox );
      northBox.add( topRightBox );

      //  'southBox' is the bottom half of the JFrame content
      //  southBox organizes its content top to bottom
      Box southBox = new Box( BoxLayout.PAGE_AXIS );
    
      // add Box objects to JFrame.
      //  having the boxes managed by a BorderLayout manager allows them
      //  to take up as much space as the window is given.
      add( northBox, BorderLayout.NORTH );
      add( southBox, BorderLayout.SOUTH );

      // have the JFrame object create its own process
      setVisible(true);
    }
    // catch(ClassNotFoundException classNotFound) {
      
      //   // instantiate and intitalize a 'JOptionPane' object
    //   // to deliver an error pop-up.
    //   JOptionPane.showMessageDialog(null, "MySQL driver not found",
    //     "Driver not found", JOptionPane.ERROR_MESSAGE);
    
    //   // terminate program
    //   //  the parameter "1" indicates an abnormal program termination
    //   System.exit(1);
    // }
    finally {
      // DISPOSE of JFrame when user clicks exits out of process
      // this is alternative to the default behavior of HIDING on close
      setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }
    
  }
  
  public static void main(String args[]) {
    
    // create a 'DatabaseConnectClient' object which contains all graphical components.
    new DatabaseConnectClient();

    // System.out.println("Test");
  }
}
