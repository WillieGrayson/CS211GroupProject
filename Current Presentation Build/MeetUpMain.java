import javax.swing.*;
import java.awt.event.*;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.io.IOException;
public class MeetUpMain
{
  private static int pos = 0;
  private static JFrame mainScreen;
  private static void createScreen() throws IOException
  {
    mainScreen = new JFrame("Midpoint");
    mainScreen.getContentPane().setBackground(Color.GRAY);
    mainScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container content = mainScreen.getContentPane();
    SpringLayout spring = new SpringLayout();
    content.setLayout(spring);
    ImageIcon logo = new ImageIcon("logo.png");
    JLabel label = new JLabel(logo);
    ImageIcon start = new ImageIcon("startButton.png");
    JButton midPointButton = new JButton(start);
    
    midPointButton.setBorder(BorderFactory.createEmptyBorder());
    midPointButton.setContentAreaFilled(false);
    label.setFont(new Font("Serif", Font.PLAIN, 50));
    content.add(label);
    content.add(midPointButton);

    spring.putConstraint(SpringLayout.WEST,label,50,SpringLayout.WEST,content);
    spring.putConstraint(SpringLayout.NORTH,label,20,SpringLayout.NORTH,content);
    //
    spring.putConstraint(SpringLayout.NORTH,midPointButton,50,SpringLayout.SOUTH,label);
    spring.putConstraint(SpringLayout.WEST,midPointButton,275,SpringLayout.WEST,content);
     //Set Size of the window itself
    spring.putConstraint(SpringLayout.EAST,content,50,SpringLayout.EAST,label);
    spring.putConstraint(SpringLayout.SOUTH,content,200,SpringLayout.SOUTH,label);
    
    //Defines the Button action
      midPointButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) {
        System.out.println("Pressed!");
        try
        {
          gatherInput();
        }
        catch(IOException err)
        {
          
        }  
        //label.setText("MidPoint Logo Placeholder");
      }
    });
    mainScreen.setResizable(false);
    mainScreen.pack();
    mainScreen.setLocationRelativeTo(null);
    mainScreen.setVisible(true);
  }
  /* This method cleans up and gets rid of the strange formats and at times useless information that Google Maps returns
   * It also handles Capitalizing the first word of Each place type
   * @param initialList the "unclean" list that this method will clean
   * @return the final ""cleaned" list
   */
  public static ArrayList<MeetingPlace> cleanList(ArrayList<MeetingPlace> initialList)
  {
    ArrayList<MeetingPlace> list = initialList;
    for(MeetingPlace meeting: list)
    {
      if(meeting.getPlaceType().contains("_"));
      {
        String[] pieces = meeting.getPlaceType().split("_");
        String result = "";
        for(String piece: pieces)
          result+=piece+" ";
        meeting.setPlaceType(result);
      }
      if(!(meeting.getPlaceType().equals("")))
      {
        String result = meeting.getPlaceType().substring(0, 1).toUpperCase() + meeting.getPlaceType().substring(1);
        meeting.setPlaceType(result);
      }
      
    }  
    return list;
  }
  public static void displayOutput(ArrayList<MeetingPlace> list) throws IOException
  {
    if(list.size()>0)
    {
      pos=0;
      MeetingPlace temp = list.get(pos);
      JFrame frame = new JFrame("Meeting Places");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Container content = frame.getContentPane();
      SpringLayout spring = new SpringLayout();
      content.setLayout(spring);
      //
      JButton next = new JButton(">");
      JButton previous = new JButton("<");
      JButton reset = new JButton("Return");
      reset.setPreferredSize(new Dimension(80, 50));

      String n = temp.getName();
      String c = temp.getPlaceType();
      String a = temp.getAddress();
      JLabel title = new JLabel("Meeting Places");
      JLabel name = new JLabel(n);
      JLabel category = new JLabel(c);
      JLabel address = new JLabel(a);
      JLabel counter = new JLabel((pos+1)+"/"+list.size());
      title.setFont(new Font("Serif", Font.PLAIN, 50));
      name.setFont(new Font("Serif", Font.PLAIN, 25));
      category.setFont(new Font("Serif", Font.PLAIN, 25));
      address.setFont(new Font("Serif", Font.PLAIN, 25));
      counter.setFont(new Font("Serif",Font.PLAIN,25));
      content.add(title);
      content.add(name);
      content.add(category);
      content.add(address);
      content.add(previous);
      content.add(next);
      content.add(counter);
      content.add(reset);
      //Define Buttons actions
      next.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) {
       if(pos<list.size()-1)
       {
          pos++;
          MeetingPlace temp = list.get(pos);
          name.setText(temp.getName());
          category.setText(temp.getPlaceType());
          address.setText(temp.getAddress());
          counter.setText((pos+1)+"/"+list.size());
       }   
      }
    });
      previous.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) {
      if(pos>0)
      {
        pos--;
        MeetingPlace temp = list.get(pos);
        name.setText(temp.getName());
        category.setText(temp.getPlaceType());
        address.setText(temp.getAddress());
        counter.setText((pos+1)+"/"+list.size());
      }
     }
    });
       reset.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
        try
        {
          createScreen();
        }
        catch(IOException err)
        {
          System.out.println(err);
        }  
      }
    });
      int center = 120;
      //Define Constraints(Positions) for UI Objects
      spring.putConstraint(SpringLayout.EAST,reset,1,SpringLayout.EAST,content);
      spring.putConstraint(SpringLayout.SOUTH,reset,1,SpringLayout.SOUTH,content);
      
      spring.putConstraint(SpringLayout.NORTH,title,5,SpringLayout.NORTH,content);
      spring.putConstraint(SpringLayout.WEST,title,130,SpringLayout.WEST,content);
      
      spring.putConstraint(SpringLayout.NORTH,name,5,SpringLayout.SOUTH,title);
      spring.putConstraint(SpringLayout.WEST,name,center,SpringLayout.WEST,content);
      //
      spring.putConstraint(SpringLayout.NORTH,category,10,SpringLayout.SOUTH,name);
      spring.putConstraint(SpringLayout.WEST,category,center,SpringLayout.WEST,content);
      //
      spring.putConstraint(SpringLayout.NORTH,address,10,SpringLayout.SOUTH,category);
      spring.putConstraint(SpringLayout.WEST,address,center,SpringLayout.WEST,content);
      // was 65
      spring.putConstraint(SpringLayout.EAST,content,200,SpringLayout.EAST,title);
      spring.putConstraint(SpringLayout.SOUTH,content,200,SpringLayout.SOUTH,title);
      //Buttons
      spring.putConstraint(SpringLayout.WEST,previous,1,SpringLayout.WEST,content);
      spring.putConstraint(SpringLayout.NORTH,previous,10,SpringLayout.SOUTH,name);
      
      spring.putConstraint(SpringLayout.NORTH,next,10,SpringLayout.SOUTH,name);
      spring.putConstraint(SpringLayout.EAST,next,0,SpringLayout.EAST,content);
      
      frame.setResizable(false);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    }  
    
  }
  public static void gatherInput() throws IOException
  {
    JFrame frame = new JFrame("Input Addresses");
    frame.getContentPane().setBackground(Color.GRAY);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container content = frame.getContentPane();
    SpringLayout spring = new SpringLayout();
    content.setLayout(spring);
    //
    
    JLabel firstLabel = new JLabel("Address 1");
    JLabel secondLabel = new JLabel("Address 2");
    JTextField address1 = new JTextField("",20);
    JTextField address2 = new JTextField("",20);
    JButton enter = new JButton("Enter");
    JButton cancel = new JButton("Cancel");
    content.add(firstLabel);
    content.add(address1);
    content.add(secondLabel);
    content.add(address2);
    content.add(enter);
    content.add(cancel);
    //Button action descriptions
     enter.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) {
        Controller controller = new Controller(address1.getText(),address2.getText());
        //System.out.println(address1.getText()+"AND"+address2.getText());
        frame.dispose();
        try
        {
          ArrayList<MeetingPlace> dirtyList = controller.run().getList();
          ArrayList<MeetingPlace> ans = cleanList(dirtyList);
          mainScreen.dispose();
          displayOutput(ans);
        }
        catch(IOException err)
        {
          
        }
        // AddressIncorrectException
        catch(Exception failure)
        {
          
        }  
      }
    });
      cancel.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) {
       
        frame.dispose();
        //label.setText("MidPoint Logo Placeholder");
      }
    });
    // Defines Constraints(Positions) for all UI objects
    spring.putConstraint(SpringLayout.WEST,firstLabel,5,SpringLayout.WEST,content);
    spring.putConstraint(SpringLayout.NORTH,firstLabel,5,SpringLayout.NORTH,content);
    
    spring.putConstraint(SpringLayout.WEST,secondLabel,5,SpringLayout.WEST,content);
    spring.putConstraint(SpringLayout.NORTH,secondLabel,25,SpringLayout.NORTH,firstLabel);
    //
    spring.putConstraint(SpringLayout.WEST, address1,5,SpringLayout.EAST, firstLabel);
    spring.putConstraint(SpringLayout.NORTH, address1,5,SpringLayout.NORTH, content);
    //
    spring.putConstraint(SpringLayout.WEST, address2,5,SpringLayout.EAST, secondLabel);
    spring.putConstraint(SpringLayout.NORTH, address2,25,SpringLayout.NORTH, firstLabel);
    //
    spring.putConstraint(SpringLayout.NORTH,enter,30,SpringLayout.SOUTH,secondLabel);
    spring.putConstraint(SpringLayout.WEST,enter,75,SpringLayout.WEST,content);
    spring.putConstraint(SpringLayout.WEST,cancel,25,SpringLayout.EAST,enter);
    spring.putConstraint(SpringLayout.NORTH,cancel,30,SpringLayout.SOUTH,secondLabel);
    
    spring.putConstraint(SpringLayout.EAST,content,5,SpringLayout.EAST,address1);
    spring.putConstraint(SpringLayout.SOUTH,content,100,SpringLayout.SOUTH,firstLabel);
    //
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);   
  }
  public static void main(String[] args)
  {
    try
    {
    createScreen();
    }
    catch(IOException err)
    {
      System.out.println(err);
    }  
  }
}