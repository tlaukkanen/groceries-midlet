/*
 * EditableList.java
 *
 * Copyright (C) 2005 Tommi Laukkanen
 * http://www.substanceofcode.com
 *
 * Created on August 28th 2005, 14:00
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */


package substanceofcode.groceries.ui;

import substanceofcode.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author Tommi Laukkanen
 */
public class EditableList 
        extends Canvas
        implements CommandListener {
    
    private GroceriesMidlet m_midlet = null;
    private substanceofcode.GroceryList  m_list = null;
    
    private int     m_position = 0;         // Currently selected item
    private boolean m_moving = false;       // Are we moving an item up'n'down
    private int     m_firstIndex = 0;       // List's first index
    private int     m_itemHeight = 0;       // List item's height
    private int     m_listStart = 0;        // First item's y-position
    private int     m_maxItemsInScreen = 0; // Maximum item count on screen
    
    /** Creates a new instance of EditableList */
    public EditableList(GroceriesMidlet midlet,
            GroceryList list) {
        super();
        m_midlet     = midlet;
        m_list       = list;
        m_position   = 0;
        m_itemHeight = 0;
        m_moving     = false;
        m_maxItemsInScreen = 0;
        setCommandListener(this);
    }
    
    /** Get current grocery list */
    public GroceryList getList() {
        return m_list;
    }
    
    /** Paint the list */
    public void paint(Graphics g) {
        g.setColor(255,255,255);
        g.fillRect(0,0,getWidth(), getHeight());
        g.setColor(0,0,0);
        
  //      g.setFont( Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE) );
        int titleHeight = 0;//g.getFont().getHeight();
//        g.drawString("Arrange list", 1, titleHeight+2, g.LEFT | g.BASELINE);

        g.setFont( Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM) );
        int subTitleHeight = g.getFont().getHeight();
        String mode = "Select";
        if(m_moving)
            mode = "Move";
        g.drawString("Mode: " + mode, 1, titleHeight + subTitleHeight + 4, g.LEFT | g.BOTTOM);
        
        if(m_listStart==0) {
            m_listStart = titleHeight + subTitleHeight + 6;
        }
        if( m_itemHeight==0 ) {
            m_itemHeight = g.getFont().getHeight() + 2;
        }        
        if( m_maxItemsInScreen == 0 ) {
            while( m_listStart+(m_maxItemsInScreen+
                        1-m_firstIndex)*m_itemHeight 
                    < getHeight() ) {
                m_maxItemsInScreen++;
            } 
        }        
        
        g.drawLine(0, m_listStart, getWidth(), m_listStart);
        
        g.setFont( Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL) );
        
    
        
        System.out.println("Max: " + m_maxItemsInScreen);
        
        // Draw list
        for(int index=m_firstIndex ; 
                index<m_list.size() && (index-m_firstIndex)<m_maxItemsInScreen ; 
                index++) {
            
            System.out.println("Index: " + index);
            
            int yPos = m_listStart+(index+1-m_firstIndex)*m_itemHeight;
            
            if(index==m_position) {
                // Draw selection bar
                if(m_moving) {
                    g.setColor(170,170,170);
                } else {
                    g.setColor(100,100,220);
                }
                g.fillRect(0, yPos-m_itemHeight+1, getWidth(), m_itemHeight+2);
                g.setColor(0,0,0);
            }
            
            g.drawString(m_list.getItemAt(index).getDescription(), 0, 
                    yPos, 
                    g.LEFT | g.BOTTOM);
        }
    }
    
    /** Command handler */
    public void commandAction(Command cmd, Displayable disp) {
    }

    /** Move along after the keypress */
    protected void keyPressed(int keyCode) {
        
        int key = getGameAction(keyCode);
        System.out.println("KeyCode:" + keyCode);
        System.out.println("DOWN:" + this.DOWN);
        System.out.println("Key:" + key);
        System.out.println("Position:" + m_position);
        
        if(key == this.FIRE) {
            m_moving = !m_moving;
        }
        if(key == this.UP) {
            if(m_position>0) {
                m_position--;
                if(m_moving) {
                    // We are moving item upwards
                    GroceryItem item = m_list.getItemAt(m_position);
                    m_list.setItemAt(m_list.getItemAt(m_position+1), m_position);
                    m_list.setItemAt(item, m_position+1);
                }
                if(m_position<m_firstIndex) {
                    m_firstIndex--;
                }
            }            
        }
        if(key == this.DOWN) {
            if(m_position<m_list.size()-1) {
                m_position++;
                if(m_moving) {
                    // We are moving item upwards
                    GroceryItem item = m_list.getItemAt(m_position);
                    m_list.setItemAt(m_list.getItemAt(m_position-1), m_position);
                    m_list.setItemAt(item, m_position-1);
                }
                
                if( m_position>=m_maxItemsInScreen ) {
                    m_firstIndex++;
                }
            }
        }
        
        repaint();
    }    
    
}
