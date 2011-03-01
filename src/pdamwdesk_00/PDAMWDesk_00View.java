/*
 * PDAMWDesk_00View.java
 */

package pdamwdesk_00;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import org.json.me.JSONArray;
import org.json.me.JSONObject;

/**
 * The application's main frame.
 */
public class PDAMWDesk_00View extends FrameView {

    public PDAMWDesk_00View(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        //jPanel2.add(new FileTree(new File("c:\\")));
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = PDAMWDesk_00App.getApplication().getMainFrame();
            aboutBox = new PDAMWDesk_00AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        PDAMWDesk_00App.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButtonGenerar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabelGenerarMessages = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setMaximumSize(new java.awt.Dimension(400, 300));
        mainPanel.setMinimumSize(new java.awt.Dimension(400, 300));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(400, 300));

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(pdamwdesk_00.PDAMWDesk_00App.class).getContext().getResourceMap(PDAMWDesk_00View.class);
        jButtonGenerar.setText(resourceMap.getString("jButtonGenerar.text")); // NOI18N
        jButtonGenerar.setName("jButtonGenerar"); // NOI18N
        jButtonGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerarActionPerformed(evt);
            }
        });

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabelGenerarMessages.setFont(resourceMap.getFont("jLabelGenerarMessages.font")); // NOI18N
        jLabelGenerarMessages.setText(resourceMap.getString("jLabelGenerarMessages.text")); // NOI18N
        jLabelGenerarMessages.setName("jLabelGenerarMessages"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButtonGenerar)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabelGenerarMessages))
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jButtonGenerar)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabelGenerarMessages)
                .addContainerGap(165, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("jPanel1.TabConstraints.tabTitle"), jPanel1); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(250, 100));

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .add(jButton1)
                    .add(jLabel1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jButton1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("jPanel2.TabConstraints.tabTitle"), jPanel2); // NOI18N

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(pdamwdesk_00.PDAMWDesk_00App.class).getContext().getActionMap(PDAMWDesk_00View.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusMessageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 230, Short.MAX_VALUE)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel)
                    .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerarActionPerformed
        // TODO add your handling code here:
        //TestCSV();
        //CSVToJSON();
        //ParseCSV();
        //TreeModel treemodel = new TreeModel();
        StringBuffer sb = new StringBuffer();
        String sReaded = "";
        int iCounter = 0;

        JFileChooser jfilechooser = new JFileChooser();
        jfilechooser.setCurrentDirectory(new File("c:\\"));
        int iRetVal = jfilechooser.showDialog(jPanel1, null);

        if (iRetVal == JFileChooser.APPROVE_OPTION) {
            File fSelected = jfilechooser.getSelectedFile();
            String sFileAP = fSelected.getAbsolutePath();
            Rutas rutasParsed = ParseCSV(sFileAP);
            System.out.println("fSelected.getAbsolutePath(): "+fSelected.getAbsolutePath());
            System.out.println("fSelected.getParent(): "+fSelected.getParent());
            boolean bWriteResult = WriteJSONFiles(rutasParsed, fSelected.getParent());
            if (bWriteResult) {
                jLabelGenerarMessages.setForeground(Color.black);
                jLabelGenerarMessages.setText("<html>OK: "+GetMessages()+"</html>");
            }
            else {
                jLabelGenerarMessages.setForeground(Color.red);
                jLabelGenerarMessages.setText("<html>ERROR: "+GetMessages()+"</html>");
            }
            /*try {
                BufferedReader br = new BufferedReader(new FileReader(fSelected));

                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                    iCounter++;
                }

                sReaded = sb.toString();
                String[] sSplittedLine = sReaded.split(System.getProperty("line.separator"));

                
                JSON json = JSON.newMap();
                //JSON main = json.get("data").add();
                for (int i = 0; i < sSplittedLine.length; i++) {
                    JSON main = json.get("data").add();

                    String[] sSplittedComma = sSplittedLine[i].split(",");

                    main.get("ID").setString(sSplittedComma[0]);
                    main.get("NOMBRE").setString(sSplittedComma[1]);
                    main.get("FECHA").setString(sSplittedComma[2]);
                    main.get("ENTR DEV").setString(sSplittedComma[3]);
                    main.get("VALOR UNITARIO").setString(sSplittedComma[4]);
                    main.get("VALOR TOTAL").setString(sSplittedComma[5]);
                }

                String jsonString = json.toJSON();

                //jTextArea1.setText(jsonString + sSplittedLine.length);
                //JOptionPane.showMessageDialog(mainPanel, jsonString, "GENERADO", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ioex) {
                SetMessages(ioex.toString());
                ioex.printStackTrace();
            } catch (Exception ex) {
                SetMessages(ex.toString());
                ex.printStackTrace();
            }*/
        }
    }//GEN-LAST:event_jButtonGenerarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        JFileChooser jfilechooser = new JFileChooser();
        jfilechooser.setCurrentDirectory(new File("c:\\"));
        int iRetVal = jfilechooser.showDialog(jPanel1, null);

        if (iRetVal == JFileChooser.APPROVE_OPTION) {
            File fSelected = jfilechooser.getSelectedFile();
            String sFileAP = fSelected.getAbsolutePath();

            try {
                String sFileFull = "";
                BufferedReader in = new BufferedReader(new FileReader(sFileAP));
                String str;
                while ((str = in.readLine()) != null) {
                    sFileFull = sFileFull + str;
                }
                in.close();

                Rutas RUTAS = new Rutas(sFileFull);

                String sCSVFull = "EMPLEADO;ZONARUTA;RAZONSOC;DIRECCION;CODIGOCLI;FECHAMOV;ENTREGAS;DEVOLUCION;FALTANTES;SOBRANTES;VALORUNITA;COBRADO;VALORFEC;VALORCOM;NOMPU;FECHAARC"+System.getProperty("line.separator");
                Enumeration enumRutas = RUTAS.GetVectorRutas().elements();
                while (enumRutas.hasMoreElements()) {
                    Ruta ruta = (Ruta) enumRutas.nextElement();
                    Enumeration enumExpendios = ruta.GetVectorExpendios().elements();
                    while (enumExpendios.hasMoreElements()) {
                        Expendio expendio = (Expendio) enumExpendios.nextElement();
                        Enumeration enumFechas = expendio.GetVectorFechas().elements();
                        while (enumFechas.hasMoreElements()) {
                            Fecha fecha = (Fecha) enumFechas.nextElement();

                            sCSVFull = sCSVFull + ruta.GetCobrador() + ";" + ruta.GetZona() + ";" + expendio.GetNombre() + ";" + expendio.GetDireccion() + ";" + expendio.GetCodigoCliente() + ";" + fecha.GetFecha() + ";" + fecha.GetEntregados() + ";" + fecha.GetDevueltos() + ";" + fecha.GetFaltantes() + ";" + fecha.GetSobrantes() + ";" + fecha.GetValorUnitario() + ";" + fecha.GetCobrado() + ";" + fecha.GetValor() + ";" + expendio.GetCompendio() + ";" + ruta.GetProducto() + ";" + ruta.GetFecha() + System.getProperty("line.separator");
                        }
                    }
                }

                // (1) get today's date
                Date today = Calendar.getInstance().getTime();

                // (2) create our "formatter" (our custom format)
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");

                // (3) create a new String in the format we want
                String folderName = formatter.format(today);

                // (4) this prints "Folder Name = 2009-09-06-08.23.23"
                //System.out.println("Folder Name = " + folderName);

                //String sFileOutput = fSelected.getParent() + System.getProperty("file.separator") + "expeout_" + folderName + ".csv";
                String sFullName = fSelected.getAbsolutePath();
                int idx = sFullName.indexOf(".json");
                String sFileNew = sFullName.substring(0, idx)+".csv";
                //String sFileOutput = fSelected.getParent() + System.getProperty("file.separator") + "expeout_" + folderName + ".csv";
                String sFileOutput = sFileNew;
                BufferedWriter out = new BufferedWriter(new FileWriter(sFileOutput));
                out.write(sCSVFull);
                out.close();


                jLabel3.setForeground(Color.black);
                jLabel3.setText("<html>OK: Generado archivo "+System.getProperty("line.separator")+"'"+sFileOutput+"'.</html>");

            } catch (IOException ioex) {
                jLabel3.setForeground(Color.red);
                jLabel3.setText("<html>ERROR: "+ioex.toString()+"</html>");
                ioex.printStackTrace();
            } catch (Exception ex) {
                jLabel3.setForeground(Color.red);
                jLabel3.setText("<html>ERROR: "+ex.toString()+"</html>");
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonGenerar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelGenerarMessages;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;

    private String sMessages;
    private boolean bSuccess;


    private void SetMessages(String sMsg) {
        this.sMessages = sMsg;
    }

    private String GetMessages() {
        return this.sMessages;
    }


    public boolean GetSuccess() {
        return this.bSuccess;
    }

    public void SetSuccess(boolean suc) {
        this.bSuccess = suc;
    }


    private void CSVToJSON() {
        System.out.println("ENTER");
        Vector vFechas = new Vector();
        Vector vExpendios = new Vector();
        Vector vRutas = new Vector();
        Vector vCobradores = new Vector();
        Vector vLineas = new Vector();

        Fecha fecha = new Fecha();
        Expendio expendio = new Expendio();
        Ruta ruta = new Ruta();
        Rutas rutas = new Rutas();
        
        try {
            CSVReader reader = new CSVReader(new FileReader("c:\\TEMPO4_00.csv"), ';');
            //CSVReader reader = new CSVReader(new StringReader("hello;my;little;world\nhello;my;little;world\nhello;my;little;world\nhello;my;little;world"), ';');
            //String[] nextLine;
            String[] nextLine = reader.readNext();
            String sCobradorAnt = "";
            String sExpendioAnt = "";

            StringBuilder sb = new StringBuilder();
            String a_line = "";
            String sTotal = "";

            JSONObject jomain = new JSONObject();
            JSONArray jamain = new JSONArray();
            while ((nextLine = reader.readNext()) != null) {
                a_line = "";
                for (int i=0; i<nextLine.length; i++) {
                    a_line += nextLine[i]+";";
                }
                a_line = a_line.substring(0, a_line.length()-1);
                
                String sCobrador = nextLine[0];
                if (!sCobrador.equalsIgnoreCase(sCobradorAnt) && sCobradorAnt!=null && !sCobradorAnt.equalsIgnoreCase("")) {
                    sCobradorAnt = nextLine[0];
                    //sb.append(a_line);
                    //sb.append("||--||");
                    sTotal = sTotal+a_line+"||--||";
                }
                else if (!sCobrador.equalsIgnoreCase(sCobradorAnt) && (sCobradorAnt==null || sCobradorAnt.equalsIgnoreCase(""))) {
                    sCobradorAnt = nextLine[0];
                    sTotal = sTotal+a_line;
                }
                else {
                    //sb.append(a_line);
                    sTotal = sTotal+a_line;
                }
                // nextLine[] is an array of values from the line
                /*String sCobrador = nextLine[0];
                if (! sCobradorAnt.equalsIgnoreCase(sCobrador)) {
                    sCobradorAnt = nextLine[0];
                    //vRutas.add(sCobradorAnt);

                    ruta.SetCobrador(sCobrador);
                    ruta.SetVectorExpendios(vExpendios);
                    vRutas.add(ruta);
                    vExpendios = new Vector();
                    vExpendios.add(nextLine[4]);
                }
                else {
                    String sExpendio = nextLine[4];
                    if (! sExpendioAnt.equalsIgnoreCase(sExpendio)) {
                        sExpendioAnt = nextLine[4];
                        vExpendios.add(sExpendioAnt);

                        expendio.SetNombre(sExpendio);
                        expendio.SetVectorFechas(vFechas);
                        vFechas = new Vector();
                        vFechas.add(nextLine[9]);
                    }
                    else {
                        vFechas.add(nextLine[9]);
                    }


                }
                for (int i = 0; i < nextLine.length; i++) {
                    System.out.print(nextLine[i] + " - ");
                }
                System.out.println("");
                 */
                /*String sCobrador = nextLine[0];
                if (sCobrador.equalsIgnoreCase(sCobradorAnt)) {
                    String sExpendio = nextLine[4];
                    if (sExpendio.equalsIgnoreCase(sExpendioAnt)) {
                        fecha.SetFecha(nextLine[9]);
                        vFechas.add(fecha);
                    }
                    else {
                        expendio.SetNombre(sExpendio);
                        expendio.SetVectorFechas(vFechas);
                        vExpendios.add(vFechas);
                    }
                }
                else {

                }*/
                /*String sCobrador = nextLine[0];
                if (! sCobrador.equalsIgnoreCase(sCobradorAnt)) {
                    sCobradorAnt = nextLine[0];

                    vCobradores.add(vLineas);
                    vLineas = new Vector();
                }
                else {
                    vLineas.add(sCobrador);
                }*/

                /*String sCobrador = nextLine[0];
                String sExpendio = nextLine[4];
                if (sCobrador.equalsIgnoreCase(sCobradorAnt)) {
                    if (sExpendio.equalsIgnoreCase(sExpendioAnt)) {
                        fecha.SetFecha(nextLine[9]);
                        vFechas.add(fecha);
                    }
                    else {
                        sExpendioAnt = nextLine[4];

                        expendio.SetNombre(sExpendio);
                        expendio.SetVectorFechas(vFechas);
                        vExpendios.add(expendio);

                        vFechas = new Vector();
                        fecha.SetFecha(nextLine[9]);
                        vFechas.add(fecha);
                    }
                }
                else {
                    sCobradorAnt = nextLine[0];

                    ruta.SetCobrador(sCobrador);
                    ruta.SetVectorExpendios(vExpendios);
                    vRutas.add(ruta);

                    vExpendios = new Vector();
                    expendio.SetNombre(sExpendio);
                    vExpendios.add(expendio);
                }*/

                /*JSONObject jo = new JSONObject();
                jo.put("EMPLEADO", nextLine[0]);
                jo.put("RAZONSOC", nextLine[4]);
                jo.put("FECHAMOV", nextLine[9]);
                jamain.put(jo);*/
            }

            //vCobradores.add(vLineas);
            JSONArray jarutas = new JSONArray();
            int c=0;
            /*for (int i=0; i<jamain.length(); i++) {
                JSONObject joplain = (JSONObject) jamain.get(i);
                System.out.println("COBRADOR: "+joplain.getString("EMPLEADO"));
                c++;

                for (int j=0; j<jarutas.length(); j++) {
                    JSONObject joorder = jarutas.getJSONObject(j);
                    String sCobradorOrder = joorder.getString("COBRADOR");
                    if (sCobradorOrder!=null && !sCobradorOrder.equalsIgnoreCase("")) {
                        JSONArray jaexpendios = joorder.getJSONArray("expendios");
                        for (int k=0; k<jaexpendios.length(); k++) {
                            JSONObject joexpendio = jaexpendios.getJSONObject(k);
                            String sExpendio = joexpendio.getString("NOMBRE");
                            if (sExpendio!=null && !sExpendio.equalsIgnoreCase("")) {
                                JSONArray jafechas = joexpendio.getJSONArray("fechas");
                                for (int m=0; m<jafechas.length(); m++) {
                                    JSONObject jofecha = jafechas.getJSONObject(m);
                                    String sFecha = jofecha.getString("FECHA");
                                    if (sFecha!=null && sFecha.equalsIgnoreCase("")) {
                                        break;
                                    }
                                }
                            }
                            else {

                            }
                        }
                    }
                    else {
                        joorder.put("COBRADOR", joplain.getString("EMPLEADO"));
                    }
                }
            }*/

            //String testString = "Real-How-To";
            /*String testString = "hola|este|es|mi|mundo";
    System.out.println(
        java.util.Arrays.toString(
        testString.split("|")
    ));*/
    
            //String sTotal = sb.toString();
            String[] splitted = sTotal.split("\\|\\|--\\|\\|");
            //String[] splitted1 = "hola||--||este||--||es||--||mi||--||mundo".split("||--||");
            //System.out.println("splitted1.length: "+splitted1.length);
            //int idx = sTotal.indexOf("||--||");
            //System.out.println("idx: "+idx);
            System.out.println("SPLIT COUNT: "+splitted.length);
            /*System.out.println(splitted[0]);
            System.out.println(splitted[1]);
            System.out.println(splitted[2]);
            System.out.println(splitted[3]);*/
            //System.out.println(splitted[4]);


            String[] sigLinea;
            int cnt=0;
            for (int i = 0; i < splitted.length; i++) {
                CSVReader innerreader = new CSVReader(new StringReader(splitted[i]), ';');
                while ((sigLinea = reader.readNext()) != null) {
                    System.out.println(sigLinea.toString());
                    System.out.println("cnt: "+cnt++);
                    /*a_line = "";
                    for (int j = 0; j < nextLine.length; j++) {
                        a_line += nextLine[j] + ";";
                    }
                    a_line = a_line.substring(0, a_line.length() - 1);

                    String sCobrador = nextLine[0];
                    if (!sCobrador.equalsIgnoreCase(sCobradorAnt) && sCobradorAnt != null && !sCobradorAnt.equalsIgnoreCase("")) {
                        sCobradorAnt = nextLine[0];
                        //sb.append(a_line);
                        //sb.append("||--||");
                        sTotal = sTotal + a_line + "||--||";
                    } else if (!sCobrador.equalsIgnoreCase(sCobradorAnt) && (sCobradorAnt == null || sCobradorAnt.equalsIgnoreCase(""))) {
                        sCobradorAnt = nextLine[0];
                        sTotal = sTotal + a_line;
                    } else {
                        //sb.append(a_line);
                        sTotal = sTotal + a_line;
                    }*/
                }
            }

            System.out.println("c="+c);
            //rutas.SetVectorRutas(vRutas);
            //jomain.put("ARRAY", jamain);
            System.out.println(jamain.toString());
            System.out.println("EXIT");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void TestCSV() {
        try {
            /*CSVReader reader = new CSVReader(new FileReader("c:\\TEMPO4.csv"), ';');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            for (int i=0; i<nextLine.length; i++) {
            System.out.print(nextLine[i]+" - ");
            }
            System.out.println("");
            }*/

            /*CsvReader csvreader = new CsvReader("c:\\TEMPO4.csv", ';');
            System.out.println("START "+csvreader.get("EMPLEADO,C,46")+" END");*/

            /*CsvReader csvreader = CsvReader.parse("ALEXANDER DURANGO GARCIA;ZC002;ZONA COBRO 2;1500;DROGUERIA PERALONSO;CR 11C #46 18;DROPER01;C;;20/01/11;2;;;960;;PAT;LA PATRIA");
            System.out.println("STARTED "+csvreader.get(0)+" ENDED");*/

            /*CsvReader products = new CsvReader("products.csv");

            products.readHeaders();

            while (products.readRecord()) {
            String productID = products.get("ProductID");
            String productName = products.get("ProductName");
            String supplierID = products.get("SupplierID");
            String categoryID = products.get("CategoryID");
            String quantityPerUnit = products.get("QuantityPerUnit");
            String unitPrice = products.get("UnitPrice");
            String unitsInStock = products.get("UnitsInStock");
            String unitsOnOrder = products.get("UnitsOnOrder");
            String reorderLevel = products.get("ReorderLevel");
            String discontinued = products.get("Discontinued");

            // perform program logic here
            System.out.println(productID + ":" + productName);
            }

            products.close();*/
            /*
            // Parse the data
            String[][] values = CSVParser.parse(
            new StringReader(
            "hello,world\n"
            + "how,are,you"));

            // Display the parsed data
            for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
            System.out.println(values[i][j]);
            }
            System.out.println("-----");
            }
             *
             */

            /*
            // Create the printer
            CSVPrinter csvp = new CSVPrinter(
            System.out);

            // Write to the printer
            csvp.writeln(
            new String[]{
            "hello", "world"
            });
             *
             */
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /** Add nodes from under "dir" into curTop. Highly recursive. */
    DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        //String curPath = dir.getPath(); // setea la cadena current path
        //String curPath = dir.getName();
        String curPath= "";
        try {
            curPath = dir.getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(PDAMWDesk_00View.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);    // setea el objeto current dir como un objeto DefaultMutableTreeNode en la cadena current path
        if (curTop != null) { // should only be null at root    // si el parámetro objeto current top es diferente de nulo
            curTop.add(curDir);   // entonces añadir al objeto current top el objeto current dir
        }
        Vector ol = new Vector();   // inicializar un vector ol object list
        String[] tmp = dir.list();  // inicializar un arreglo temporal con los contenidos del parametro archivo dir
        for (int i = 0; i < tmp.length; i++) // iniciar un ciclo desde 0 hasta la longitud del arreglo temporal, es decir la cantidad de objetos del FS contenidos en el objeto archivo dir
        {
            ol.addElement(tmp[i]);    // añadir el elemento actual al vector ol object list
        }
        Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);    // ordenar el vector ol con la regla ordenamiento alfabetico insensitivo
        File f; // declarar el objeto archivo f
        Vector files = new Vector();    // inicializar un objecto vector files
        // Make two passes, one for Dirs and one for Files. This is #1.
        for (int i = 0; i < ol.size(); i++) {   // iniciar un ciclo desde 0 hasta el tamaño del vector ol
            String thisObject = (String) ol.elementAt(i); // inicializar la cadena thisObject como el elemento actual del vector ol
            String newPath;   // declarar cadena nueva ruta
            if (curPath.equals(".")) // si la cadena current path es igual "." (directorio actual)
            {
                newPath = thisObject;   // entonces la cadena nueva ruta será igual a la cadena thisObject (será igual al elemento del vector ol actual)
            } else // si no
            {
                newPath = curPath + File.separator + thisObject;    // entonces la cadena nueva ruta será igual a la concatenación de la cadena current path + separador de archivo + thisObject (el objeto actual del vector ol)
                //newPath = thisObject;
            }
            if ((f = new File(newPath)).isDirectory()) // inicializa el objeto archivo f como la nueva ruta, si es un directorio
            {
                addNodes(curDir, f);    // invocar a añadir nodos con los parámetros el objeto DefaultMutableTreeNode current dir y el objeto archivo directorio f
            } else // si no es un directorio
            {
                files.addElement(thisObject);   // añadir al vector files la cadena que representa thisObject
            }
        }
        // Pass two: for files.
        for (int fnum = 0; fnum < files.size(); fnum++) {
            curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        }
        return curDir;
    }

    private Rutas ParseCSV(String sCSVFile) {
        int contador = 0;
        Rutas rutas = new Rutas();
        Ruta ruta = new Ruta();
        Expendio expendio = new Expendio();
        Fecha fecha = new Fecha();

        Vector vFechas = new Vector();
        Vector vExpendios = new Vector();
        Vector vRutas = new Vector();
        Vector vRUTAS = new Vector();

        try {
            //CSVReader reader = new CSVReader(new FileReader("c:\\TEMPO4_00.csv"), ';');
            CSVReader reader = new CSVReader(new FileReader(sCSVFile), ';');
            String[] nextLine = reader.readNext();
            String sCobradorAnt = "";
            String sCobrador = "";
            String a_line = "";
            String sFull = "";

            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                //System.out.println(nextLine[0] + nextLine[1] + "etc...");
                a_line = "";
                for (int i = 0; i < nextLine.length; i++) {
                    a_line = a_line + nextLine[i] + ";";
                }
                a_line = a_line.substring(0, a_line.length() - 1);

                sCobrador = nextLine[0];
                if (!sCobrador.equalsIgnoreCase(sCobradorAnt) && (sCobradorAnt == null || sCobradorAnt.equalsIgnoreCase(""))) {
                    sCobradorAnt = nextLine[0];
                    sFull = sFull + a_line + System.getProperty("line.separator");
                } else if (!sCobrador.equalsIgnoreCase(sCobradorAnt) && sCobradorAnt != null && !sCobrador.equalsIgnoreCase("")) {
                    sCobradorAnt = nextLine[0];
                    sFull = sFull + "||-empl-||" + a_line + System.getProperty("line.separator");
                } else {
                    sFull = sFull + a_line + System.getProperty("line.separator");
                }
            }

            String[] splitEmpleado = sFull.split("\\|\\|-empl-\\|\\|");
            System.out.println("splitted.length: " + splitEmpleado.length);
            //System.out.println(sFull);
            CSVReader readerEmpleado;// = new CSVReader(new FileReader("c:\\TEMPO4_00.csv"), ';');
            String[] nextLineEmpleado;
            String a_lineEmpleado = "";
            String sExpendioAnt = "";
            String sExpendio = "";
            String sFullEmpleado = "";

            vRUTAS = new Vector();
            for (int i = 0; i < splitEmpleado.length; i++) {
                //System.out.println(splitEmpleado[i]);
                if (i==0) {
                    sFullEmpleado = "||-expe-||";
                }
                String sEmpleadoNombre = "";
                String sRutaProducto = "";
                String sRutaZona = "";
                readerEmpleado = new CSVReader(new StringReader(splitEmpleado[i]), ';');
                while ((nextLineEmpleado = readerEmpleado.readNext()) != null) {

                    a_lineEmpleado = "";
                    for (int j = 0; j < nextLineEmpleado.length; j++) {
                        a_lineEmpleado = a_lineEmpleado + nextLineEmpleado[j] + ";";
                        //System.out.println(Arrays.toString(nextLineEmpleado));
                    }
                    a_lineEmpleado = a_lineEmpleado.substring(0, a_lineEmpleado.length() - 1);

                    sExpendio = nextLineEmpleado[4];
                    if (!sExpendio.equalsIgnoreCase(sExpendioAnt) && (sExpendioAnt == null || sExpendioAnt.equalsIgnoreCase(""))) {
                        sExpendioAnt = nextLineEmpleado[4];
                        sFullEmpleado = sFullEmpleado + a_lineEmpleado + System.getProperty("line.separator");
                    } else if (!sExpendio.equalsIgnoreCase(sExpendioAnt) && sExpendioAnt != null && !sExpendioAnt.equalsIgnoreCase("")) {
                        sExpendioAnt = nextLineEmpleado[4];
                        sFullEmpleado = sFullEmpleado + "||-expe-||" + a_lineEmpleado + System.getProperty("line.separator");
                    } else {
                        sFullEmpleado = sFullEmpleado + a_lineEmpleado + System.getProperty("line.separator");
                    }

                    sEmpleadoNombre = nextLineEmpleado[0];
                    sRutaProducto = nextLineEmpleado[16];
                    sRutaZona = nextLineEmpleado[1];
                }

                String[] splitExpendios = sFullEmpleado.split("\\|\\|-expe-\\|\\|");
                System.out.println("splitExpendios.length: "+splitExpendios.length);
                //System.out.println(sFullEmpleado);
                //System.out.println(System.getProperty("line.separator"));
                //System.out.println(System.getProperty("line.separator"));
                sFullEmpleado = "";


                CSVReader readerExpendio;
                String[] nextLineExpendio;
                String a_lineExpendio = "";

                vExpendios = new Vector();
                for (int m = 1; m < splitExpendios.length; m++) {
                    //System.out.println(splitEmpleado[i]);
                    vFechas = new Vector();
                    String sExpendioNombre = "";
                    String sExpendioDireccion = "";
                    String sExpendioCodigoCliente = "";
                    readerExpendio = new CSVReader(new StringReader(splitExpendios[m]), ';');
                    while ((nextLineExpendio = readerExpendio.readNext()) != null) {

                        //System.out.println("contador: "+contador);
                        contador++;
                        /*a_lineExpendio = "";
                        for (int n = 0; n < nextLineExpendio.length; n++) {
                            a_lineExpendio = a_lineExpendio + nextLineExpendio[n] + ";";
                            //System.out.println(Arrays.toString(nextLineEmpleado));
                        }
                        a_lineExpendio = a_lineExpendio.substring(0, a_lineExpendio.length() - 1);*/

                        fecha = new Fecha();

                        String sFechaFecha = nextLineExpendio[9];
                        if (sFechaFecha==null || sFechaFecha.equalsIgnoreCase("")) {
                            fecha.SetFecha("");
                        }
                        else {
                            fecha.SetFecha(sFechaFecha);
                        }

                        String sFechaEntregados = nextLineExpendio[10];
                        if (sFechaEntregados==null || sFechaEntregados.equalsIgnoreCase("")) {
                            fecha.SetEntregados(0);
                        }
                        else {
                            int iFechaEntregados = Integer.parseInt(sFechaEntregados);
                            fecha.SetEntregados(iFechaEntregados);
                        }

                        String sFechaDevueltos = nextLineExpendio[11];
                        if (sFechaDevueltos==null || sFechaDevueltos.equalsIgnoreCase("")) {
                            fecha.SetDevueltos(0);
                        }
                        else {
                            int iFechaDevueltos = Integer.parseInt(sFechaDevueltos);
                            fecha.SetDevueltos(iFechaDevueltos);
                        }

                        String sFechaFaltantes = nextLineExpendio[12];
                        if (sFechaFaltantes==null || sFechaFaltantes.equalsIgnoreCase("")) {
                            fecha.SetFaltantes(0);
                        }
                        else {
                            int iFechaFaltantes = Integer.parseInt(sFechaFaltantes);
                            fecha.SetFaltantes(iFechaFaltantes);
                        }

                        String sFechaValorUnitario = nextLineExpendio[13];
                        if (sFechaValorUnitario==null || sFechaValorUnitario.equalsIgnoreCase("")) {
                            fecha.SetValorUnitario(0);
                        }
                        else {
                            int iFechaValorUnitario = Integer.parseInt(sFechaValorUnitario);
                            fecha.SetValorUnitario(iFechaValorUnitario);
                        }
                        
                        fecha.SetVisitado(0);
                        
                        vFechas.add(fecha);

                        sExpendioNombre = nextLineExpendio[4];
                        sExpendioDireccion = nextLineExpendio[5];
                        sExpendioCodigoCliente = nextLineExpendio[6];
                    }

                    expendio = new Expendio();
                    expendio.SetNombre(sExpendioNombre);
                    expendio.SetDireccion(sExpendioDireccion);
                    expendio.SetCodigoCliente(sExpendioCodigoCliente);
                    expendio.SetCompendio(0);
                    expendio.SetVectorFechas(vFechas);
                    expendio.SetVisitado(0);
                    vExpendios.add(expendio);
                }

                ruta = new Ruta();
                ruta.SetCobrador(sEmpleadoNombre);
                ruta.SetProducto(sRutaProducto);
                ruta.SetZona(sRutaZona);

                // (1) get today's date
                Date today = Calendar.getInstance().getTime();

                // (2) create our "formatter" (our custom format)
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");

                // (3) create a new String in the format we want
                String folderName = formatter.format(today);

                // (4) this prints "Folder Name = 2009-09-06-08.23.23"
                //System.out.println("Folder Name = " + folderName);

                //ruta.SetFecha((new Date()).toString());
                ruta.SetFecha(folderName);
                ruta.SetVectorExpendios(vExpendios);
                vRutas.add(ruta);
            }

            rutas.SetVectorRutas(vRutas);
            System.out.println("--OUTPUT RUTAS START--");
            System.out.println(rutas.JSONObjectToString());
            System.out.println("--OUTPUT RUTAS END--");

            System.out.println("END");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rutas;
    }

    private boolean WriteJSONFiles(Rutas rutas, String sAbsoluteDestination) {
        try {
            int c = 0;
            int iCntRutas = 0;

            JSONObject joOriginal = new JSONObject(rutas.JSONObjectToString());
            JSONArray jaRutas = joOriginal.getJSONArray("rutas");
            for (int i = 0; i < jaRutas.length(); i++) {
                JSONObject joElement = jaRutas.getJSONObject(i);
                JSONArray jaNew = new JSONArray();
                jaNew.put(joElement);
                JSONObject joMain = new JSONObject();
                joMain.put("rutas", jaNew);

                String sRutaCobrador = joElement.getString("COBRADOR");
                String sRutaCobradorEncoded = new String(sRutaCobrador.getBytes("ISO-8859-1"), "ISO-8859-1");
                String sRutaCobradorLC = sRutaCobradorEncoded.toLowerCase();
                String sRutaCobradorLCRA = sRutaCobradorLC.replaceAll(" ", "_");
                String sRutaCobradorFinal = sRutaCobradorLCRA.replaceAll("\\?", "");
                String sFileName = "COBRAD"+iCntRutas+".json";
                System.out.println(sFileName);
                //BufferedWriter out = new BufferedWriter(new FileWriter(sAbsoluteDestination+System.getProperty("file.separator")+sRutaCobradorFinal+".json"));
                BufferedWriter out = new BufferedWriter(new FileWriter(sAbsoluteDestination+System.getProperty("file.separator")+sFileName));
                out.write(joMain.toString());
                out.close();

                c++;
                iCntRutas++;
            }

            SetMessages("Escritos '"+c+"' archivos en '"+sAbsoluteDestination+"'.");
            SetSuccess(true);
        } catch (Exception ex) {
            SetMessages(ex.toString());
            SetSuccess(false);
            ex.printStackTrace();
        }

        return GetSuccess();
    }
}





/**
 * @author Kirill Grouchnikov
 */
class FileTreePanel1 extends JPanel {

    /**
     * File system view.
     */
    protected static FileSystemView fsv = FileSystemView.getFileSystemView();

    /**
     * Renderer for the file tree.
     *
     * @author Kirill Grouchnikov
     */
    private static class FileTreeCellRenderer extends DefaultTreeCellRenderer {

        /**
         * Icon cache to speed the rendering.
         */
        private Map<String, Icon> iconCache = new HashMap<String, Icon>();
        /**
         * Root name cache to speed the rendering.
         */
        private Map<File, String> rootNameCache = new HashMap<File, String>();

        /*
         * (non-Javadoc)
         *
         * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree,
         *      java.lang.Object, boolean, boolean, boolean, int, boolean)
         */
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {
            FileTreeNode ftn = (FileTreeNode) value;
            File file = ftn.file;
            String filename = "";
            if (file != null) {
                if (ftn.isFileSystemRoot) {
                    // long start = System.currentTimeMillis();
                    filename = this.rootNameCache.get(file);
                    if (filename == null) {
                        filename = fsv.getSystemDisplayName(file);
                        this.rootNameCache.put(file, filename);
                    }
                    // long end = System.currentTimeMillis();
                    // System.out.println(filename + ":" + (end - start));
                } else {
                    filename = file.getName();
                }
            }
            JLabel result = (JLabel) super.getTreeCellRendererComponent(tree,
                    filename, sel, expanded, leaf, row, hasFocus);
            if (file != null) {
                Icon icon = this.iconCache.get(filename);
                if (icon == null) {
                    // System.out.println("Getting icon of " + filename);
                    icon = fsv.getSystemIcon(file);
                    this.iconCache.put(filename, icon);
                }
                result.setIcon(icon);
            }
            return result;
        }
    }

    /**
     * A node in the file tree.
     *
     * @author Kirill Grouchnikov
     */
    private static class FileTreeNode implements TreeNode {

        /**
         * Node file.
         */
        private File file;
        /**
         * Children of the node file.
         */
        private File[] children;
        /**
         * Parent node.
         */
        private TreeNode parent;
        /**
         * Indication whether this node corresponds to a file system root.
         */
        private boolean isFileSystemRoot;

        /**
         * Creates a new file tree node.
         *
         * @param file
         *            Node file
         * @param isFileSystemRoot
         *            Indicates whether the file is a file system root.
         * @param parent
         *            Parent node.
         */
        public FileTreeNode(File file, boolean isFileSystemRoot, TreeNode parent) {
            this.file = file;
            this.isFileSystemRoot = isFileSystemRoot;
            this.parent = parent;
            this.children = this.file.listFiles();
            if (this.children == null) {
                this.children = new File[0];
            }
        }

        /**
         * Creates a new file tree node.
         *
         * @param children
         *            Children files.
         */
        public FileTreeNode(File[] children) {
            this.file = null;
            this.parent = null;
            this.children = children;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.swing.tree.TreeNode#children()
         */
        public Enumeration<?> children() {
            final int elementCount = this.children.length;
            return new Enumeration<File>() {

                int count = 0;

                /*
                 * (non-Javadoc)
                 *
                 * @see java.util.Enumeration#hasMoreElements()
                 */
                public boolean hasMoreElements() {
                    return this.count < elementCount;
                }

                /*
                 * (non-Javadoc)
                 *
                 * @see java.util.Enumeration#nextElement()
                 */
                public File nextElement() {
                    if (this.count < elementCount) {
                        return FileTreeNode.this.children[this.count++];
                    }
                    throw new NoSuchElementException("Vector Enumeration");
                }
            };

        }

        /*
         * (non-Javadoc)
         *
         * @see javax.swing.tree.TreeNode#getAllowsChildren()
         */
        public boolean getAllowsChildren() {
            return true;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.swing.tree.TreeNode#getChildAt(int)
         */
        public TreeNode getChildAt(int childIndex) {
            return new FileTreeNode(this.children[childIndex],
                    this.parent == null, this);
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.swing.tree.TreeNode#getChildCount()
         */
        public int getChildCount() {
            return this.children.length;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
         */
        public int getIndex(TreeNode node) {
            FileTreeNode ftn = (FileTreeNode) node;
            for (int i = 0; i < this.children.length; i++) {
                if (ftn.file.equals(this.children[i])) {
                    return i;
                }
            }
            return -1;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.swing.tree.TreeNode#getParent()
         */
        public TreeNode getParent() {
            return this.parent;
        }

        /*
         * (non-Javadoc)
         *
         * @see javax.swing.tree.TreeNode#isLeaf()
         */
        public boolean isLeaf() {
            return (this.getChildCount() == 0);
        }
    }
    /**
     * The file tree.
     */
    //private JTree tree;
    public JTree tree;

    /**
     * Creates the file tree panel.
     */
    public FileTreePanel1() {
        this.setLayout(new BorderLayout());

        File[] roots = File.listRoots();
        //File[] roots = {(new File("c:\\"))};
        FileTreeNode rootTreeNode = new FileTreeNode(roots);
        this.tree = new JTree(rootTreeNode);
        this.tree.setCellRenderer(new FileTreeCellRenderer());
        this.tree.setRootVisible(false);
        final JScrollPane jsp = new JScrollPane(this.tree);
        jsp.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.add(jsp, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JFrame frame = new JFrame("File tree");
                frame.setSize(500, 400);
                frame.setLocationRelativeTo(null);
                frame.add(new FileTreePanel1());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
