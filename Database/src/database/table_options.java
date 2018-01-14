/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.GroupLayout.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Math.*;
/**
 *
 * @author dinos
 */
public class table_options extends javax.swing.JFrame  {

    /**
     * Creates new form table_options
     */
    public table_options(Connection connDB,String table_name) throws SQLException {
        //initComponents();
        this.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent me) {
                jTable1.clearSelection();  
                
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mousePressed(MouseEvent me) {
             //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
           //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
           //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
         

            
                
        });
        cdb=connDB;
        table_n=table_name;
        
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        
        Update_btn = new javax.swing.JButton();
        Delete_btn = new javax.swing.JButton();
        Insert_btn = new javax.swing.JButton();
        
      
         if(connDB!=null){
              ResultSetMetaData rsmd1=null;
           try{
           Statement stmt =connDB.createStatement();
           
           
         
           
            ResultSet rset= stmt.executeQuery("select * from "+table_name);
            rsmd1=rset.getMetaData();
            jTable1 = new JTable(buildTableModel(rset,rsmd1));
            
            
            
            final ListSelectionModel cellSelectionModel = jTable1.getSelectionModel();
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
      public void valueChanged(ListSelectionEvent e) {
        String selectedData = null;
         if(!cellSelectionModel.isSelectionEmpty()){
            
         selectedRow =cellSelectionModel.getMinSelectionIndex();
        if(pk_count==1) pk_value=jTable1.getValueAt(selectedRow,0);
        for(int i=c_count-txt_count;i<c_count;i++) {
            text_field[i-c_count+txt_count].setText(jTable1.getValueAt(selectedRow,i).toString());
            if(jTable1.getValueAt(selectedRow,i)!=null)
            table_sel_values[i-c_count+txt_count]=jTable1.getValueAt(selectedRow,i);
        }
         
         }
        
      }

    });
        
        
        
        
            DatabaseMetaData rsmd=connDB.getMetaData();
           ResultSet pkset=rsmd.getPrimaryKeys(null,null,table_n);
           pk_count=0;
           while(pkset.next()){
               pk_count++;
                       
           }
          // JOptionPane.showMessageDialog(null,""+pk_count);
           }
             catch(SQLException sqle)
           {
               JOptionPane.showMessageDialog(null,""+sqle);
            }
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jScrollPane1.setViewportView(jTable1);
        
        
        Update_btn.setText("Update");
        Update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update_btnActionPerformed(evt);
            }
        });

        Delete_btn.setText("Delete");
        Delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete_btnActionPerformed(evt);
            }
        });

        Insert_btn.setText("Insert");
        Insert_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Insert_btnActionPerformed(evt);
            }
        });

        
        if(table_n.equals("Loans") || table_n.equals("LoanRequests") 
        || table_n.equals("Commitments") || table_n.equals("Repayments") || table_n.equals("Deadlines"))
        {
            Update_btn.setEnabled(false);
        }
        if(!table_n.equals("LoanRequests"))
        {
            Delete_btn.setEnabled(false);
        }
        if(table_n.equals("Loans"))
        {
           Insert_btn.setEnabled(false);
        }
        //JOptionPane.showMessageDialog(null, pk_count);
         c_count=jTable1.getColumnCount();
        //JOptionPane.showMessageDialog(null,
    //""+c_count) ;
        if(table_n.equals("Borrowers_Athens")) pk_count=1;
        
        if(pk_count==1){
             text_field=new javax.swing.JTextField[c_count-1];
             

             txt_count=c_count-1;
             if(table_n.equals("Borrowers_Stats")) c_count=0;
        for(int i=0;i<c_count-1;i++){
            text_field[i]=new javax.swing.JTextField();
            if(!table_n.equals("Borrowers_Athens") || i!=2){
            Ghost_Text ghostText = new Ghost_Text(text_field[i],rsmd1.getColumnName(i+2));}
            else{    text_field[i].setText("Athens");
                     text_field[i].setEditable(false);}
        }
        }
        else{
             text_field=new javax.swing.JTextField[c_count];
             
             txt_count=c_count;
             
             for(int i=0;i<c_count;i++){
            text_field[i]=new javax.swing.JTextField();
             Ghost_Text ghostText = new Ghost_Text(text_field[i],rsmd1.getColumnName(i+1));
              if(table_n.equals("Borrowers_Stats")){text_field[i].setEnabled(false);}
              }
        }
         if(table_n.equals("Borrowers_Stats")) {Insert_btn.setEnabled(false); Update_btn.setEnabled(false);}
        table_sel_values=new Object[txt_count];     
        
       
       this.setTitle(table_n);
       
       javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
       getContentPane().setLayout(layout);
       layout.setAutoCreateContainerGaps(true);
       layout.setAutoCreateGaps(true);
       ParallelGroup pGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
       SequentialGroup sGroup = layout.createSequentialGroup();
       
       for(JTextField txt :text_field)
       {
           pGroup.addComponent(txt) ;
           sGroup.addComponent(txt);
       }
           
       layout.setHorizontalGroup(
               
            layout.createSequentialGroup()
                    
               .addComponent(jScrollPane1)
               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                       .addGroup(pGroup)
                               
                       
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(Delete_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Update_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Insert_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
               )
               
        );
           
       layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(sGroup)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Insert_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Delete_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Update_btn)))
                .addContainerGap(43, Short.MAX_VALUE))
        );


        pack();
    
    }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Update_btn = new javax.swing.JButton();
        Delete_btn = new javax.swing.JButton();
        Insert_btn = new javax.swing.JButton();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        Update_btn.setText("Update");
        Update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Update_btnActionPerformed(evt);
            }
        });

        Delete_btn.setText("Delete");
        Delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Delete_btnActionPerformed(evt);
            }
        });

        Insert_btn.setText("Insert");
        Insert_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Insert_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Delete_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Update_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Insert_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Insert_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Delete_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Update_btn)))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Delete_btnActionPerformed
        removeSelectedFromTable(jTable1);
        
    }//GEN-LAST:event_Delete_btnActionPerformed

    private void Update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Update_btnActionPerformed
         try{
             StringBuilder sb=new StringBuilder();
             sb.append("update "+table_n+" set ");
             String[] column_name=column_finder(jTable1);
             for(int i=c_count-txt_count;i<c_count-1;i++)
             {  
                sb.append(column_name[i-c_count+txt_count]+"=?, ");
             }
             sb.append(column_name[txt_count-1]+"=? where ");
            
             for(int i=c_count-txt_count;i<c_count-1;i++)
             {
                sb.append(column_name[i-c_count+txt_count]+"=? and ");
             }sb.append(column_name[txt_count-1]+"=?");
             PreparedStatement pstmt=cdb.prepareStatement(""+sb);
             for(int i=1;i<txt_count+1;i++)
             {
               if(text_field[i-1].getText()!="")
               pstmt.setObject(i,text_field[i-1].getText());
               else
                   pstmt.setObject(i,null);
             
             }
             for(int i=txt_count+1;i<2*txt_count+1;i++)
             {
              pstmt.setObject(i,table_sel_values[i-1-txt_count]);
             }
             //JOptionPane.showMessageDialog(null,pstmt.toString());
               for(int i=c_count-txt_count;i<c_count;i++){
                   
         table_sel_values[i-c_count+txt_count]=jTable1.getValueAt(selectedRow,i); 
         } 
             int cache_selected_Row=selectedRow;
             pstmt.executeUpdate();
             
             try{
          Statement stmt =cdb.createStatement();
          ResultSet rset= stmt.executeQuery("select * from "+table_n); 
          DefaultTableModel tm = (DefaultTableModel) jTable1.getModel();
          tm=buildTableModel(rset,rset.getMetaData());
         jTable1.setModel(tm);
         jTable1.repaint();
                  for(int i=c_count-txt_count;i<c_count;i++){
                     
         table_sel_values[i-c_count+txt_count]=jTable1.getValueAt(cache_selected_Row,i); 
         } 
                   }
              catch(SQLException sqle){}
             
         }
         catch(SQLException sqle)
         {
         JOptionPane.showMessageDialog(null,sqle);
         }
        
    }//GEN-LAST:event_Update_btnActionPerformed

    private void Insert_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Insert_btnActionPerformed
        // TODO add your handling code here:
        try{
            StringBuilder sb=new StringBuilder();
            sb.append("insert into" +" "+table_n+" "+"values"+" "+ "(");
            for(int i=0;i<txt_count;i++){
                if(i==0) sb.append("?");
                  else sb.append(",?");
               
            }
            sb.append(")");
            PreparedStatement pstmt=cdb.prepareStatement(""+sb);
            
            for(int i=0;i<txt_count;i++){
                if(text_field[i].getText()!=""){
                //JOptionPane.showMessageDialog(null,text_field[i].getText());
                pstmt.setObject(i+1,text_field[i].getText());}
                else{
                    pstmt.setObject(i+1,null);
                }
                
               
            }
            System.out.println(pstmt);
            int i=pstmt.executeUpdate();
           
            
             
              try{
          Statement stmt =cdb.createStatement();
          ResultSet rset= stmt.executeQuery("select * from "+table_n); 
          DefaultTableModel tm = (DefaultTableModel) jTable1.getModel();
          tm=buildTableModel(rset,rset.getMetaData());
         jTable1.setModel(tm);
         //jTable1.repaint();
         
                   }
               
              catch(SQLException sqle){}
             
        }
         
        catch(SQLException sqle)
        {
            
            JOptionPane.showMessageDialog(null,"Could not insert tuple"+sqle);
            
        }
    }//GEN-LAST:event_Insert_btnActionPerformed

    /**
     * @param tname
     * @param coDb
     * @param args the command line arguments
     */
    public  void main(final  Connection coDb,final String tname) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(table_options.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(table_options.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(table_options.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(table_options.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
   
        /* Create and display the form */
       
    // Closes the Connection
          if(coDb!=null){
           java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try{
                 new table_options(coDb,tname).setVisible(true);
                }
                catch(SQLException sqle){
                    JOptionPane.showMessageDialog(null,sqle+"");
                 }
                     
                 // frame1.setDefaultCloseOperation(HIDE_ON_CLOSE);
            }
        });}
          else {JOptionPane.showMessageDialog(null,
    "No connection established");
               }
    }
    
        public static DefaultTableModel buildTableModel(ResultSet rs,ResultSetMetaData metaData)
        throws SQLException {

    

    // names of columns
    Vector<String> columnNames = new Vector<String>();
    
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
        columnNames.add(metaData.getColumnName(column));
        //v_types.add(metaData.getColumnTypeName(column));
    }
    
    // data of the table
    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    while (rs.next()) {
        Vector<Object> vector = new Vector<Object>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            vector.add(rs.getObject(columnIndex));
        }
        data.add(vector);
    }

    return new DefaultTableModel(data, columnNames);

}
        public void removeSelectedFromTable(JTable from)
{       Object data1,data2,data3;
        data1=new Object();
        data2=new Object();
        data3=new Object();
        String c1,c2,c3;
        int[] rows = from.getSelectedRows();
        
        DefaultTableModel tm = (DefaultTableModel) from.getModel();
       
        c1=tm.getColumnName(0);
        c2=tm.getColumnName(1);
        c3=tm.getColumnName(2);
        
        for(int row : rows){
         data1 =(Object)from.getValueAt(row, 0);
         data2=(Object)from.getValueAt(row,1);
         data3=(Object)from.getValueAt(row,2);
        
        String query_string=new String("delete from"+"  "+table_n+"  "+"where"+" "+c1+"=?"+" and "+c2+"=? and "+c3+"=?" );
        try{
       
            PreparedStatement pstmt=cdb.prepareStatement(query_string);
            pstmt.setObject(1,data1);
            pstmt.setObject(2,data2);
            pstmt.setObject(3,data3);
           
            pstmt.executeUpdate();
            tm.removeRow(from.convertRowIndexToModel(row));
        }
        catch(SQLException sqle){
            JOptionPane.showMessageDialog(null, sqle);
            
        }
        }
        
        	
        
        
         from.clearSelection();
}        
    public String[] column_finder(JTable table1)
    { 
        String[] colName=new String[txt_count];
        for(int i=c_count-txt_count;i<c_count;i++){
           colName[i-c_count+txt_count] = table1.getModel().getColumnName(i);
        
        }
      return colName;
    
    }
    private int selectedRow;
    private Object[] table_sel_values;        
    private Object pk_value;    
    private int txt_count;    
    private int pk_count;
    private javax.swing.JTextField[] text_field;   
    private  Vector<String> v_types=new Vector<String>();    
    private String table_n;
    private int c_count;
    private Connection cdb;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Delete_btn;
    private javax.swing.JButton Insert_btn;
    private javax.swing.JButton Update_btn;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}




