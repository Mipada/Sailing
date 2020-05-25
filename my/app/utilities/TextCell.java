/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.app.utilities;

import javax.swing.JTextField;

/**
 *
 * @author Peter
 */
public class TextCell {
    
    int x, y, x2, y2, width = 50;
    JTextField labelField = new JTextField();
    JTextField dataField = new JTextField();
    
    
    
    
    
    public TextCell(int x, int y, String label, int x2, int y2, String data, int width){
        this.x = x;
        this.y = y;
        this.labelField.setText(label);
        
        this.x2 = x2;
        this.y2 = y2;
        this.dataField.setText(data);
        
        this.width = width;
    }
    
    
    public void update(String data){
        dataField.setText(data);
    }
    
    
    
    
    
    
}
