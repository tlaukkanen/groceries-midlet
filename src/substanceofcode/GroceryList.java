/*
 * GroceryList.java
 *
 * Copyright (C) 2005 Tommi Laukkanen
 * http://www.substanceofcode.com
 *
 * Created on May 15th 2005, 17:27
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

package substanceofcode;

import java.util.*;

/**
 *
 * @author Tommi Laukkanen
 */
public class GroceryList {
    
    private String m_name = "";
    private Vector m_items = new Vector();
    
    /** Creates a new instance of GroceryList */
    public GroceryList(String name, Vector items) {
        m_name = name;
        if( items != null) {
            m_items = items;
        }
    }
    
    public String getName(){
        return m_name;
    }
    
    public Vector getItems(){
        return m_items;
    }
    
    /** Get an item from given index */
    public GroceryItem getItemAt(int index) {
        if( index>=0 && index<size()) {
            return (GroceryItem)m_items.elementAt(index);
        }
        // Invalid index -> Return null
        return null;
    }
    
    /** Set an item at given index */
    public void setItemAt(GroceryItem item, int index) {
        m_items.setElementAt(item, index);
    }
    
    public void setName(String name){
        m_name = name;
    }
    
    /** Get item count in the current list */
    public int size() {
        return m_items.size();
    }
    
    /** Add grocery item to list */
    public void addItem(GroceryItem item) {
        m_items.addElement( item );
    }
    
    /** Remove item from selected index */
    public void removeItem(int index) {
        m_items.removeElementAt( index );
    }
    
    /** Set item selected value */
    public void setItemCollected(int index, boolean collected) {
        GroceryItem item = (GroceryItem)m_items.elementAt( index ); 
        item.setCollected(collected);
    }
    
    public void setItems(Vector items){
        m_items = items;
    }
    
    public String getItemsString() {
        String result = "";
        for(int i=0; i<m_items.size(); i++) {
            GroceryItem item = (GroceryItem)m_items.elementAt( i ); 
            if( item.isCollected() ) {
                result += "+";
            } else {
                result += "-";
            }
            result += item.getDescription();
            result += "|";
        }
        return result;
    }
    
}
