package ASProjekt;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.JButton;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import com.github.lgooddatepicker.optionalusertools.PickerUtilities;
import com.github.lgooddatepicker.optionalusertools.TimeVetoPolicy;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.SpringLayout;
import javax.swing.Timer;

import java.awt.Rectangle;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.border.LineBorder;
import java.awt.Color;
import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;
import com.sun.org.apache.bcel.internal.classfile.Method;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import ASProjekt.read_write;
import ASProjekt.methods;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;



@SuppressWarnings("unused")
class timeveto implements TimeVetoPolicy {

    /**
     * isTimeAllowed, Return true if a time should be allowed, or false if a time should be
     * vetoed.
     */
    @Override
    public boolean isTimeAllowed(LocalTime time) {
        // Only allow times from 9a to 5p, inclusive.
        return PickerUtilities.isLocalTimeInRange(
                time, LocalTime.of(8, 00), LocalTime.of(20, 00), true);
    }
}


//Haupt-Deklarationsteil

@SuppressWarnings({ "serial", "unused" })
public class gui extends JFrame implements ActionListener {
	private static JPanel contentNew;
	private JTextField txt_einnahmen;
	private JTextField txt_planeadd;
	private JTextField txt_rename;
	private JTextField txt_name;
	private Object TimerTask;
	static JLabel  lbl_starterror;
	static JLabel lbl_newerror;
	static JButton btn_addleihe;
	JPanel start;
	SpringLayout sl_start;
	static Integer pixel=0;
	static Boolean animpos=false;
	static String g_imagename;
	
	Image error1 = new ImageIcon(this.getClass().getResource("/error_unknown.png")).getImage();
	Image beginn_gleich_ende = new ImageIcon(this.getClass().getResource("/beginn_gleich_ende.png")).getImage();
	Image datum_vergangenheit = new ImageIcon(this.getClass().getResource("/datum_vergangenheit.png")).getImage();
	Image ende_vor_beginn = new ImageIcon(this.getClass().getResource("/ende_vor_beginn.png")).getImage();
	Image no_plane = new ImageIcon(this.getClass().getResource("/no_plane.png")).getImage();
	Image no_name = new ImageIcon(this.getClass().getResource("/no_name.png")).getImage();
	
	static Timer t;
	
	/**
	 * Launch the application. 
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	
	//Hauptteil
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		//CODE INIT
		
		//GUI.INIT
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui frame = new gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		

	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public gui() {
		
		setResizable(false);
		
	
		String datas[] = {};
		String planes[] = {};
		String returnplanes[] = {};
		//Arrays Einlesen
		try {
		
		datas=read_write.lesen("data.txt");
		planes=read_write.lesen("planes.txt");
		
	}catch(ArrayIndexOutOfBoundsException exception){
		
		//Hier soll Unexcepted Error erscheinen.
		lbl_starterror.setIcon(new ImageIcon(error1));
		t.start();
		

	}
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(gui.class.getResource("/com/jgoodies/looks/windows/icons/Computer.gif")));
		setFont(new Font("Times New Roman", Font.PLAIN, 10));
		setTitle("Buchungs Verwaltung");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 815, 709);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		

		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, 0, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, 0, SpringLayout.EAST, getContentPane());
		tabbedPane.setFont(tabbedPane.getFont().deriveFont(tabbedPane.getFont().getSize() + 3f));
		getContentPane().add(tabbedPane);
		
		//LIST MODELL
		
	    DefaultListSelectionModel m = new DefaultListSelectionModel();
	    m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    m.setLeadAnchorNotificationEnabled(false);

	    DefaultListModel listenModell = new DefaultListModel();
		for(int i=0; i<planes.length; i++){
	    	listenModell.addElement(planes[i]);
	    }
	    DefaultListModel availableplanes = new DefaultListModel();
	    DefaultListModel lst_occupiedplanes = new DefaultListModel();
		String occupiedplanes[] = methods.occupiedtoday(read_write.lesen("data.txt"));
		for (int i = 0; i < occupiedplanes.length; i++) {
			lst_occupiedplanes.addElement(occupiedplanes[i]);
		}
		for (int i = 0; i < lst_occupiedplanes.getSize(); i++) {
			lst_occupiedplanes.removeElement(null);
		}
		


		
		
		TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.generatePotentialMenuTimes(TimeIncrement.OneHour, null, null);
		
		JPanel start_1 = new JPanel();
		tabbedPane.addTab("�bersicht", new ImageIcon(gui.class.getResource("/com/jgoodies/looks/plastic/icons/HomeFolder.gif")), start_1, null);
		SpringLayout sl_start_1 = new SpringLayout();
		start_1.setLayout(sl_start_1);
		

		
		
		//ENDE LIST MODELL
		

		
		JList list = new JList(lst_occupiedplanes);
		sl_start_1.putConstraint(SpringLayout.WEST, list, 10, SpringLayout.WEST, start_1);
		list.setFont(new Font("Tahoma", Font.PLAIN, 15));
		list.setSelectionModel(m);
		start_1.add(list);
		
		JLabel lblHeuteVerlieheneFlugzeuge = new JLabel("\u00DCbersicht f\u00FCr Heute");
		sl_start_1.putConstraint(SpringLayout.WEST, lblHeuteVerlieheneFlugzeuge, 10, SpringLayout.WEST, start_1);
		sl_start_1.putConstraint(SpringLayout.EAST, lblHeuteVerlieheneFlugzeuge, 0, SpringLayout.EAST, start_1);
		sl_start_1.putConstraint(SpringLayout.NORTH, list, 32, SpringLayout.SOUTH, lblHeuteVerlieheneFlugzeuge);
		lblHeuteVerlieheneFlugzeuge.setHorizontalAlignment(SwingConstants.CENTER);
		sl_start_1.putConstraint(SpringLayout.NORTH, lblHeuteVerlieheneFlugzeuge, 11, SpringLayout.NORTH, start_1);
		lblHeuteVerlieheneFlugzeuge.setFont(new Font("Tahoma", Font.PLAIN, 19));
		start_1.add(lblHeuteVerlieheneFlugzeuge);
		
		JLabel lblErwarteteEinnahmen = new JLabel("Erwartete Einnahmen:");
		sl_start_1.putConstraint(SpringLayout.NORTH, lblErwarteteEinnahmen, 431, SpringLayout.NORTH, start_1);
		sl_start_1.putConstraint(SpringLayout.WEST, lblErwarteteEinnahmen, 10, SpringLayout.WEST, start_1);
		sl_start_1.putConstraint(SpringLayout.EAST, lblErwarteteEinnahmen, 0, SpringLayout.EAST, start_1);
		sl_start_1.putConstraint(SpringLayout.SOUTH, list, -6, SpringLayout.NORTH, lblErwarteteEinnahmen);
		lblErwarteteEinnahmen.setHorizontalAlignment(SwingConstants.CENTER);
		lblErwarteteEinnahmen.setFont(new Font("Tahoma", Font.PLAIN, 19));
		start_1.add(lblErwarteteEinnahmen);
		
		txt_einnahmen = new JTextField();
		sl_start_1.putConstraint(SpringLayout.SOUTH, lblErwarteteEinnahmen, -6, SpringLayout.NORTH, txt_einnahmen);
		sl_start_1.putConstraint(SpringLayout.WEST, txt_einnahmen, 10, SpringLayout.WEST, start_1);
		sl_start_1.putConstraint(SpringLayout.EAST, txt_einnahmen, -10, SpringLayout.EAST, start_1);
		sl_start_1.putConstraint(SpringLayout.NORTH, txt_einnahmen, 489, SpringLayout.NORTH, start_1);
		start_1.add(txt_einnahmen);
		txt_einnahmen.setColumns(10);
		
		
		lbl_starterror = new JLabel("");
		sl_start_1.putConstraint(SpringLayout.EAST, list, -10, SpringLayout.EAST, lbl_starterror);
		sl_start_1.putConstraint(SpringLayout.SOUTH, txt_einnahmen, -85, SpringLayout.NORTH, lbl_starterror);
		sl_start_1.putConstraint(SpringLayout.NORTH, lbl_starterror, 5, SpringLayout.SOUTH, start_1);
		sl_start_1.putConstraint(SpringLayout.EAST, lbl_starterror, 0, SpringLayout.EAST, start_1);
		start_1.add(lbl_starterror);
		sl_start_1.putConstraint(SpringLayout.WEST, lbl_starterror, 0, SpringLayout.WEST, start_1);
		lbl_starterror.setIcon(new ImageIcon(error1));
		

		//Methode zeigt heutige Einnahmen.
		txt_einnahmen.setText(String.valueOf(methods.earningstoday(datas))+ "�");
		
        
		JPanel create = new JPanel();
		create.setAutoscrolls(true);
		tabbedPane.addTab("Neue Leihe", new ImageIcon(gui.class.getResource("/com/jgoodies/looks/plastic/icons/NewFolder.gif")), create, null);
		SpringLayout sl_create = new SpringLayout();
		create.setLayout(sl_create);
		
		
		//Elemente in Neue Leihe
		
		
		DatePicker datepick = new DatePicker();
		sl_create.putConstraint(SpringLayout.WEST, datepick, 61, SpringLayout.WEST, create);
		datepick.setAlignmentX(Component.LEFT_ALIGNMENT);
		datepick.getComponentDateTextField().setMinimumSize(new Dimension(80, 25));
		create.add(datepick);
		
		
		TimePicker timepick_von = new TimePicker(timeSettings);
		sl_create.putConstraint(SpringLayout.WEST, timepick_von, 309, SpringLayout.WEST, create);
		sl_create.putConstraint(SpringLayout.EAST, datepick, -41, SpringLayout.WEST, timepick_von);
		sl_create.putConstraint(SpringLayout.NORTH, datepick, 4, SpringLayout.NORTH, timepick_von);
		sl_create.putConstraint(SpringLayout.SOUTH, datepick, 0, SpringLayout.SOUTH, timepick_von);
		sl_create.putConstraint(SpringLayout.SOUTH, timepick_von, 113, SpringLayout.NORTH, create);
		timepick_von.setBounds(new Rectangle(0, 0, 20, 20));
		sl_create.putConstraint(SpringLayout.NORTH, timepick_von, 74, SpringLayout.NORTH, create);
		create.add(timepick_von);
		timeSettings.setVetoPolicy(new timeveto());
		
				
				
		JLabel lblDatum = new JLabel("Von:");
		sl_create.putConstraint(SpringLayout.WEST, lblDatum, 0, SpringLayout.WEST, timepick_von);
		sl_create.putConstraint(SpringLayout.SOUTH, lblDatum, -10, SpringLayout.NORTH, timepick_von);
		sl_create.putConstraint(SpringLayout.EAST, lblDatum, 0, SpringLayout.EAST, timepick_von);
		create.add(lblDatum);
				
		JLabel lblDatum_1 = new JLabel("Datum:");
		sl_create.putConstraint(SpringLayout.NORTH, lblDatum_1, 0, SpringLayout.NORTH, lblDatum);
		sl_create.putConstraint(SpringLayout.WEST, lblDatum_1, 0, SpringLayout.WEST, datepick);
		create.add(lblDatum_1);
				
		JLabel lblBis = new JLabel("Bis");
		sl_create.putConstraint(SpringLayout.NORTH, lblBis, 0, SpringLayout.NORTH, lblDatum);
		create.add(lblBis);
			
		TimePicker timepick_bis = new TimePicker(timeSettings);
		timepick_bis.addTimeChangeListener(new TimeChangeListener() {
		public void timeChanged(TimeChangeEvent event) {
			//methods.checkvalid;
			}
		});
		sl_create.putConstraint(SpringLayout.EAST, timepick_von, -46, SpringLayout.WEST, timepick_bis);
		sl_create.putConstraint(SpringLayout.WEST, lblBis, 0, SpringLayout.WEST, timepick_bis);
		sl_create.putConstraint(SpringLayout.NORTH, timepick_bis, 74, SpringLayout.NORTH, create);
		sl_create.putConstraint(SpringLayout.WEST, timepick_bis, 557, SpringLayout.WEST, create);
		sl_create.putConstraint(SpringLayout.SOUTH, timepick_bis, 113, SpringLayout.NORTH, create);
		sl_create.putConstraint(SpringLayout.EAST, timepick_bis, 774, SpringLayout.WEST, create);
		create.add(timepick_bis);
				
		JList lst_leihemoeglich = new JList(availableplanes);
		lst_leihemoeglich.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_create.putConstraint(SpringLayout.NORTH, lst_leihemoeglich, 35, SpringLayout.SOUTH, datepick);
		sl_create.putConstraint(SpringLayout.WEST, lst_leihemoeglich, 0, SpringLayout.WEST, datepick);
		sl_create.putConstraint(SpringLayout.SOUTH, lst_leihemoeglich, 352, SpringLayout.SOUTH, datepick);
		sl_create.putConstraint(SpringLayout.EAST, lst_leihemoeglich, 0, SpringLayout.EAST, datepick);
		create.add(lst_leihemoeglich);
				
		btn_addleihe = new JButton("Leihe Aufnehmen");
		sl_create.putConstraint(SpringLayout.WEST, btn_addleihe, 0, SpringLayout.WEST, lblBis);
		sl_create.putConstraint(SpringLayout.SOUTH, btn_addleihe, -147, SpringLayout.SOUTH, create);
		sl_create.putConstraint(SpringLayout.EAST, btn_addleihe, 248, SpringLayout.EAST, timepick_von);
		btn_addleihe.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
					
				String plane = String.valueOf(lst_leihemoeglich.getSelectedValue());
				String von = timepick_von.getTimeStringOrEmptyString();
				String bis = timepick_bis.getTimeStringOrEmptyString();
				String datum = datepick.getDateStringOrEmptyString();
				String name = txt_name.getText();	
				

				txt_name.setText("");
				btn_addleihe.setEnabled(true);
				/*
				//Debug-M�glichkeit (Console Output der Ben�tigten Variablen und ihrem Inhalt (On-Click))
				
				System.out.print("Plane: "+plane+"\n");
				System.out.print("Von: "+von+"\n");
				System.out.print("Bis: "+bis+"\n");
				System.out.print("Datum: "+datum+"\n");
				System.out.print("Name: "+name+"\n");
				*/

				
				methods.addleihe(plane, von, bis, datum, name);
				
				//aktuallisieren der "Heute verliehenen Flugzeuge" bei neuer Leihe
				
				String[] returnplanes = methods.checkavailable(datepick.getDateStringOrEmptyString(), timepick_von.getTimeStringOrEmptyString(), timepick_bis.getTimeStringOrEmptyString() , read_write.lesen("data.txt"), read_write.lesen("planes.txt"));
				availableplanes.removeAllElements();
									
				for (int i=0; i < returnplanes.length; i++) {
											
					availableplanes.addElement(returnplanes[i]);
				}
				
				btn_addleihe.setEnabled(false);
				timepick_bis.setTime(null);
				timepick_von.setTime(null);
				datepick.setDate(null);
				
				//aktuallisieren der "Heutigen Einnahmen" bei neuer Leihe
				
				txt_einnahmen.setText(String.valueOf(methods.earningstoday(read_write.lesen("data.txt")))+ "�");
			}
		});
		create.add(btn_addleihe);
		btn_addleihe.setEnabled(false);
				
		JLabel lblBuchungAufName = new JLabel("Buchung auf Name :");
		sl_create.putConstraint(SpringLayout.NORTH, lblBuchungAufName, 2, SpringLayout.NORTH, lst_leihemoeglich);
		sl_create.putConstraint(SpringLayout.WEST, lblBuchungAufName, 0, SpringLayout.WEST, lblBis);
		create.add(lblBuchungAufName);
				
		txt_name = new JTextField();
		sl_create.putConstraint(SpringLayout.NORTH, btn_addleihe, 189, SpringLayout.SOUTH, txt_name);
		sl_create.putConstraint(SpringLayout.NORTH, txt_name, 6, SpringLayout.SOUTH, lblBuchungAufName);
		sl_create.putConstraint(SpringLayout.WEST, txt_name, 0, SpringLayout.WEST, lblBis);
		sl_create.putConstraint(SpringLayout.SOUTH, txt_name, -395, SpringLayout.SOUTH, create);
		sl_create.putConstraint(SpringLayout.EAST, txt_name, 248, SpringLayout.EAST, timepick_von);
		create.add(txt_name);
		txt_name.setColumns(10);
		
		lbl_newerror = new JLabel("");
		sl_create.putConstraint(SpringLayout.NORTH, lbl_newerror, 5, SpringLayout.SOUTH, create);
		sl_create.putConstraint(SpringLayout.EAST, lbl_newerror, 0, SpringLayout.EAST, create);
		create.add(lbl_newerror);
		sl_create.putConstraint(SpringLayout.WEST, lbl_newerror, 0, SpringLayout.WEST, create);
		lbl_newerror.setIcon(new ImageIcon(error1));
				
		
		

				
		//EventListener DATE&TIME
				
		timepick_von.addTimeChangeListener(new TimeChangeListener() {
		public void timeChanged(TimeChangeEvent event) {
				LocalTime time = timepick_von.getTime();
				if (datepick.getDateStringOrEmptyString()!=("") & timepick_von.getTimeStringOrEmptyString()!=("") & timepick_bis.getTimeStringOrEmptyString()!=("")) {
					String[] returnplanes = methods.checkavailable(datepick.getDateStringOrEmptyString(), timepick_von.getTimeStringOrEmptyString(), timepick_bis.getTimeStringOrEmptyString() , read_write.lesen("data.txt"), read_write.lesen("planes.txt"));	
					btn_addleihe.setEnabled(true);
						availableplanes.removeAllElements();
						
					for (int i=0; i < returnplanes.length; i++) {
								
						availableplanes.addElement(returnplanes[i]);
								
								
					}
							
						}	
				}
			});
						
		timepick_bis.addTimeChangeListener(new TimeChangeListener() {
		public void timeChanged(TimeChangeEvent event) {
			
				if (datepick.getDateStringOrEmptyString()!=("") && timepick_von.getTimeStringOrEmptyString()!=("") && timepick_bis.getTimeStringOrEmptyString()!=("")) {
					String[] returnplanes = methods.checkavailable(datepick.getDateStringOrEmptyString(), timepick_von.getTimeStringOrEmptyString(), timepick_bis.getTimeStringOrEmptyString() , read_write.lesen("data.txt"), read_write.lesen("planes.txt"));	
					btn_addleihe.setEnabled(true);	
						availableplanes.removeAllElements();
							
					for (int i=0; i < returnplanes.length; i++) {
								
						availableplanes.addElement(returnplanes[i]);
								
								
					}
							
							
					//planelist ist eine ArrayList die alles enth�lt was angezeigt werden soll.
							
					for (int i=0; i < returnplanes.length; i++) {
								
					//	listenModell.addElement(planelist);
								
					}

				}	
			}
		});
				
		datepick.addDateChangeListener(new DateChangeListener() {
		public void dateChanged(DateChangeEvent event) {
			
				if (datepick.getDateStringOrEmptyString()!=("") & timepick_von.getTimeStringOrEmptyString()!=("") & timepick_bis.getTimeStringOrEmptyString()!=("") ) {
					String[] returnplanes = methods.checkavailable(datepick.getDateStringOrEmptyString(), timepick_von.getTimeStringOrEmptyString(), timepick_bis.getTimeStringOrEmptyString() , read_write.lesen("data.txt"), read_write.lesen("planes.txt"));	
					btn_addleihe.setEnabled(true);		
						availableplanes.removeAllElements();
					
					for (int i=0; i < returnplanes.length; i++) {
								
						availableplanes.addElement(returnplanes[i]);
								
								
					}
							
							
					}
				}
		});	
		
		
		//Objekte im Elemente tab
		
		JPanel manage = new JPanel();
		tabbedPane.addTab("Flugzeug Management", new ImageIcon(gui.class.getResource("/Airplane-Right-Red-icon.png")), manage, null);
		SpringLayout sl_manage = new SpringLayout();
		manage.setLayout(sl_manage);
	    
		/*
		JLabel lbl_logo = new JLabel("");
		sl_manage.putConstraint(SpringLayout.NORTH, lbl_logo, 0, SpringLayout.NORTH, start);
		sl_manage.putConstraint(SpringLayout.EAST, lbl_logo, 0, SpringLayout.EAST, start);
		Image logo = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
		lbl_logo.setIcon(new ImageIcon(logo));
		manage.add(lbl_logo);
		*/
		
		JList saved_planes = new JList(listenModell);
		sl_manage.putConstraint(SpringLayout.NORTH, saved_planes, 78, SpringLayout.NORTH, manage);
		sl_manage.putConstraint(SpringLayout.WEST, saved_planes, 23, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.SOUTH, saved_planes, -107, SpringLayout.SOUTH, manage);
		sl_manage.putConstraint(SpringLayout.EAST, saved_planes, 241, SpringLayout.WEST, manage);
		manage.add(saved_planes);
		saved_planes.setSelectionModel(m);
		
		txt_planeadd = new JTextField();
		sl_manage.putConstraint(SpringLayout.NORTH, txt_planeadd, 142, SpringLayout.NORTH, manage);
		sl_manage.putConstraint(SpringLayout.WEST, txt_planeadd, 50, SpringLayout.EAST, saved_planes);
		sl_manage.putConstraint(SpringLayout.EAST, txt_planeadd, -358, SpringLayout.EAST, manage);
		manage.add(txt_planeadd);
		txt_planeadd.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Flugzeug hinzuf\u00FCgen");
		sl_manage.putConstraint(SpringLayout.WEST, lblNewLabel, 50, SpringLayout.EAST, saved_planes);
		sl_manage.putConstraint(SpringLayout.EAST, lblNewLabel, -346, SpringLayout.EAST, manage);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		sl_manage.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, saved_planes);
		sl_manage.putConstraint(SpringLayout.SOUTH, lblNewLabel, 31, SpringLayout.NORTH, saved_planes);
		manage.add(lblNewLabel);
		
		JLabel lblFlugzeugeInDer = new JLabel("Flugzeuge in der Datenbank");
		sl_manage.putConstraint(SpringLayout.NORTH, lblFlugzeugeInDer, -37, SpringLayout.NORTH, saved_planes);
		sl_manage.putConstraint(SpringLayout.WEST, lblFlugzeugeInDer, 0, SpringLayout.WEST, saved_planes);
		sl_manage.putConstraint(SpringLayout.SOUTH, lblFlugzeugeInDer, -6, SpringLayout.NORTH, saved_planes);
		sl_manage.putConstraint(SpringLayout.EAST, lblFlugzeugeInDer, 0, SpringLayout.EAST, saved_planes);
		lblFlugzeugeInDer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		manage.add(lblFlugzeugeInDer);
		
		JButton btn_add = new JButton("Hinzuf\u00FCgen");
		sl_manage.putConstraint(SpringLayout.NORTH, btn_add, -1, SpringLayout.NORTH, txt_planeadd);
		sl_manage.putConstraint(SpringLayout.WEST, btn_add, 6, SpringLayout.EAST, txt_planeadd);
		sl_manage.putConstraint(SpringLayout.SOUTH, btn_add, -431, SpringLayout.SOUTH, manage);
		sl_manage.putConstraint(SpringLayout.EAST, btn_add, -214, SpringLayout.EAST, manage);
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				methods.addplane(txt_planeadd.getText());				
				listenModell.addElement(txt_planeadd.getText());
			}
		});
		btn_add.setFont(new Font("Tahoma", Font.PLAIN, 14));
		manage.add(btn_add);
		
		JButton btnNewButton_1 = new JButton("Ausgew\u00E4hltes Flugzeug l\u00F6schen");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				methods.removeplane(saved_planes.getSelectedIndex());
				listenModell.removeElement(saved_planes.getSelectedValue());
			}
		});
		sl_manage.putConstraint(SpringLayout.NORTH, btnNewButton_1, 18, SpringLayout.SOUTH, saved_planes);
		sl_manage.putConstraint(SpringLayout.WEST, btnNewButton_1, 0, SpringLayout.WEST, saved_planes);
		sl_manage.putConstraint(SpringLayout.SOUTH, btnNewButton_1, 58, SpringLayout.SOUTH, saved_planes);
		sl_manage.putConstraint(SpringLayout.EAST, btnNewButton_1, 0, SpringLayout.EAST, saved_planes);
		manage.add(btnNewButton_1);
		
		txt_rename = new JTextField();
		sl_manage.putConstraint(SpringLayout.WEST, txt_rename, 50, SpringLayout.EAST, saved_planes);
		sl_manage.putConstraint(SpringLayout.SOUTH, txt_rename, -309, SpringLayout.SOUTH, manage);
		manage.add(txt_rename);
		txt_rename.setColumns(10);
		
		JButton btn_rename = new JButton("Umbenennen");
		sl_manage.putConstraint(SpringLayout.NORTH, btn_rename, 82, SpringLayout.SOUTH, btn_add);
		sl_manage.putConstraint(SpringLayout.WEST, btn_rename, 476, SpringLayout.WEST, manage);
		sl_manage.putConstraint(SpringLayout.SOUTH, btn_rename, -309, SpringLayout.SOUTH, manage);
		sl_manage.putConstraint(SpringLayout.EAST, btn_rename, -214, SpringLayout.EAST, manage);
		btn_rename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=saved_planes.getSelectedIndex();
				methods.renameplane(index, txt_rename.getText());
				listenModell.removeElementAt(index);
				listenModell.add(index, txt_rename.getText());
			
			}
		});
		sl_manage.putConstraint(SpringLayout.EAST, txt_rename, -6, SpringLayout.WEST, btn_rename);
		btn_rename.setFont(new Font("Tahoma", Font.PLAIN, 14));
		manage.add(btn_rename);
		
		JLabel lblFlugzeugUmbennenen = new JLabel("Flugzeug umbennenen");
		sl_manage.putConstraint(SpringLayout.SOUTH, txt_planeadd, -42, SpringLayout.NORTH, lblFlugzeugUmbennenen);
		sl_manage.putConstraint(SpringLayout.SOUTH, lblFlugzeugUmbennenen, -367, SpringLayout.SOUTH, manage);
		sl_manage.putConstraint(SpringLayout.NORTH, txt_rename, 18, SpringLayout.SOUTH, lblFlugzeugUmbennenen);
		lblFlugzeugUmbennenen.setFont(new Font("Tahoma", Font.PLAIN, 17));
		sl_manage.putConstraint(SpringLayout.WEST, lblFlugzeugUmbennenen, 54, SpringLayout.EAST, saved_planes);
		manage.add(lblFlugzeugUmbennenen);
		
		JLabel lbl_logonew = new JLabel("");
		sl_manage.putConstraint(SpringLayout.SOUTH, lbl_logonew, -10, SpringLayout.SOUTH, manage);
		sl_manage.putConstraint(SpringLayout.EAST, lbl_logonew, -10, SpringLayout.EAST, manage);
		Image logo = new ImageIcon(this.getClass().getResource("/Logo.png")).getImage();
		lbl_logonew.setIcon(new ImageIcon(logo));
		manage.add(lbl_logonew);
		
		
		t=new Timer(10,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				if (g_imagename.equals("beginn_gleich_ende")) {
					lbl_starterror.setIcon(new ImageIcon (beginn_gleich_ende));
					lbl_newerror.setIcon(new ImageIcon (beginn_gleich_ende));
				}
				
				if (g_imagename.equals("error1")) {
					lbl_starterror.setIcon(new ImageIcon (error1));
					lbl_newerror.setIcon(new ImageIcon (error1));
				}
				
				if (g_imagename.equals("datum_vergangenheit")) {
					lbl_starterror.setIcon(new ImageIcon (datum_vergangenheit));
					lbl_newerror.setIcon(new ImageIcon (datum_vergangenheit));
				}
				
				if (g_imagename.equals("ende_vor_beginn")) {
					lbl_starterror.setIcon(new ImageIcon (ende_vor_beginn));
					lbl_newerror.setIcon(new ImageIcon (ende_vor_beginn));
				}
				if (g_imagename.equals("no_plane")) {
					lbl_starterror.setIcon(new ImageIcon (no_plane));
					lbl_newerror.setIcon(new ImageIcon (no_plane));
				}
				if (g_imagename.equals("no_name")) {
					lbl_starterror.setIcon(new ImageIcon (no_name));
					lbl_newerror.setIcon(new ImageIcon (no_name));
				}
					
					
				if(animpos == false) { 
					
					lbl_starterror.setLocation(0,pixel+613); //Verschiebt Label ,keine Ahnung warum da 613 steht. Vermutung: Das Fenster ist so gro�
					lbl_newerror.setLocation(0,pixel+613);
					pixel=pixel-1; //Wenn ich mit 613 = Fenstergr��e richtig liege bedeutet das ebend das das Label immer 1 Pixel nach oben verschoben wird
				//	System.out.println("X:"+ pixel +" Delay:" + t.getDelay()+ " Bereich: false"); //Debug
					if(pixel == -60) { //Das Label ist 60 pixel hoch. Wenn es also 60 Pixel nach oben verschoben wurde ist es komplett sichtbar.
						t.setDelay(2500); // Die Animation stoppt f�r 2,5 Sekunden (Nutzer soll lesen)
				//		System.out.println("X:"+ pixel +" Delay:" + t.getDelay()+ " Bereich: �bergang"); //Debug
						animpos=true;//Variable symbolosiert : Label oben und sichtbar.
					}
				}else {
					t.setDelay(1);//2ms Timing sollten das Label schnell verschwinden lassen.
				//	System.out.println("X:"+ pixel +" Delay:" + t.getDelay()+ " Bereich: true");
					lbl_starterror.setLocation(0,pixel+613);
					lbl_newerror.setLocation(0,pixel+613);
					pixel++; //hier eventuell pixel = pixel +2 f�r schnelleres verschwinden. 
					if(pixel > 5) { //Wenn 0 dann komplett verschwunden (Eigentlich, da das irgendwie nicht stimmt zur sicherheit 5)
					t.stop(); //timer wird gestoppt Preozess abgeschlossen.
					//animpos=false;
					}
				}
	
			}
		});
		
	}//END of GUI Class
	
	public void actionPerformed(ActionEvent e) {
	}
	
	public static void setlabelimage (String imagename) {
		g_imagename = imagename;
	//	lbl_starterror.setIcon(new ImageIcon(imagename));
	//	lbl_newerror.setIcon(new ImageIcon(imagename));
	//	System.out.println("Timer startet");
		t.setDelay(5);	//alle 5ms l�uft der Timer einmal ab 
		animpos=false; //animpos gibt an ob das label sichtbar ist oder nicht ( false= versteckt)
		pixel=0;		//Anfangswert
		t.start();//GOES TO t=new Timer
		btn_addleihe.setEnabled(false);
	}
	
	
}