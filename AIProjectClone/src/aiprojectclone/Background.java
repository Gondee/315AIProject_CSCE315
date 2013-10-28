/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

/**
 *
 * @author joshkruger
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


/**
 *
 * @author Josh Kruger
 */
public class Background extends JFrame implements ActionListener {

    JButton Load = new JButton("Find Picture");
    JButton Can = new JButton("Cancel");
    
    String black;
    String white;

    public void setBackground(String s)
    {
        
        if(s == "WHITE"){
            white = "WHITE";
            black ="";
        }
        if(s == "BLACK"){
            black = "BLACK";
            white = "";
        }
       
       setTitle("Select URL for peice");
       setSize(300,150);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setLocationRelativeTo(null);

       JPanel pane = new JPanel();

       JLabel Directions = new JLabel("Note* Images must be .png (PNG)");

       pane.setLayout(null);

       Directions.setBounds(new Rectangle(29,10,200,30));
       pane.add(Directions);
       Load.setBounds(new Rectangle(25,50,125,25));         //Placing the Icons
       Can.setBounds(new Rectangle(155,50,100,25));

       Load.addActionListener(this);
       Can.addActionListener(this);

       pane.add(Load);
       pane.add(Can);








        
        
        add(pane);


        setVisible(true);


    }


    //Listens for the Actions in the background program
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == Load )
        {
        JFrame newFrame = new JFrame();
        FileDialog Choosefile = new FileDialog(newFrame,"Choose a image", FileDialog.LOAD);
        Choosefile.setFile(".png");
        Choosefile.show();
        



        String path = Choosefile.getDirectory();        //Not in use.
        String FileName = Choosefile.getFile();
            
        String FullNewBackground = path + FileName;     //combines the path and the file name  outdated

        setNewBackground(FullNewBackground);        //preps the file to be witten to...

        JOptionPane.showMessageDialog(null, "New Tiles have been set, Select update to put changes into action");
        dispose();


        }//End of Load If

        if(source == Can)
        {
            dispose();

        }


    }//End actionListener

    public void setNewBackground(String Path)           //
    {
        
        
        
        
        //Begin writing files
        if(black == "BLACK"){
            
        String FilePath = Path;
        File f = new File(black+".txt");   //Delete the existing one
        f.delete(); //File deleted
        try {

      BufferedWriter Store = new BufferedWriter(new FileWriter(black+".txt", true));

      Store.flush();
      Store.write(FilePath);

      Store.close();
      } catch (IOException e) { System.out.print("Background didnt apply"); }
        
        
      }
        else if(white =="WHITE"){
            
            
        String FilePath = Path;
        File f = new File(white+".txt");   //Delete the existing one
        f.delete(); //File deleted
        try {

      BufferedWriter Store = new BufferedWriter(new FileWriter(white+".txt", true));

      Store.flush();
      Store.write(FilePath);

      Store.close();
      } catch (IOException e) { System.out.print("Background didnt apply"); }
       
            
            
            
            
        }
        
        
        
    
    
    
    
    


    }//End of setNewBackground






    public String getBackgroundURL(String g)                //Returns the URL, so the main window, or any window can use it
    {
        
        if(g == "WHITE"){
            white = "WHITE";
            black ="";
        }
        if(g == "BLACK"){
            black = "BLACK";
            white = "";
        }
        
        
        
        if(black == "BLACK"){
        
                String returnURL = new String();

                try{
                    int x =0;
                BufferedReader readout = new BufferedReader(new FileReader(black+".txt"));
            String URLS = new String();


                while ((URLS = readout.readLine()) != null)
                {

                returnURL = URLS;

                }
            readout.close();
            } catch (IOException e) {System.out.println("Could Not Read out background file"); }


                return returnURL;
    }
        
        
        
        else if(white == "WHITE") {
            
                String returnURL = new String();

                try{
                    int x =0;
                BufferedReader readout = new BufferedReader(new FileReader(white+".txt"));
            String URLS = new String();


                while ((URLS = readout.readLine()) != null)
                {

                returnURL = URLS;

                }
            readout.close();
            } catch (IOException e) {System.out.println("Could Not Read out background file"); }


                return returnURL;    


            
            
            
        }
        else
            return "NO COLOR";

}
    
    
}
    
