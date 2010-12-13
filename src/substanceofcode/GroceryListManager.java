/*
 * GroceryListManager.java
 *
 * Copyright (C) 2005 Tommi Laukkanen
 * http://www.substanceofcode.com
 *
 * Created on May 15th 2005, 19:30
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
 */

package substanceofcode;

import java.util.*;

/**
 *
 * @author Tommi Laukkanen
 */
public class GroceryListManager {
    
    private Hashtable m_lists;
    private Settings m_settings;
    private static GroceryListManager m_manager;
    
    /** Creates a new instance of GroceryListManager */
    private GroceryListManager(Settings settings) {
        m_lists = new Hashtable();
        m_settings = settings;
        initializeLists();
    }
    
    public static GroceryListManager getInstance(Settings settings){
        if( m_manager==null) {
            m_manager = new GroceryListManager(settings);
        }
        return m_manager;
    }
    
/*    public void Save(){
        String titles = "";
        for(int i=0; i<m_lists.size(); i++) {
            String items = "";
            GroceryList list = (GroceryList) m_lists..elementAt( i );
            titles += list.getName() + "|";
 
            for(int j=0; j<list.getItems().size(); j++) {
                items += list.getItems().elementAt( j ) + "|";
            }
            m_settings.setStringProperty("List_" + list.getName(), items);
        }
        m_settings.setStringProperty("Lists", titles);
 
    }
 */
    
    /**
     * Add grocery list. If grocery list already exists in
     * the hashtable then the old list is overwritten.
     */
    public void add(GroceryList list){
        if( list!=null) {
            if( m_lists.contains(list.getName()))
                m_lists.remove(list.getName());
            m_lists.put(list.getName(), list);
        }
    }
    
    public void update(GroceryList list){
        if( list!=null)
            m_lists.put(list.getName(), list);
    }
    
    public GroceryList getList(String title) {
        return (GroceryList)m_lists.get(title);
    }
    
    public void delete(GroceryList list){
        m_settings.setStringProperty( "List_" + list.getName(), "");
        m_lists.remove(list.getName());
    }
    
    public Vector getAll(){
        Vector list = new Vector();
        for( Enumeration e = m_lists.elements(); e.hasMoreElements(); ) {
            list.addElement((GroceryList)e.nextElement());
        }
        return list;
    }
    
    /** Save grocery lists */
    public void save() {
        String listTitles = "";
        for( Enumeration e = m_lists.elements(); e.hasMoreElements(); ) {
            GroceryList list = (GroceryList)e.nextElement();
            listTitles += list.getName() + "|";
            m_settings.setStringProperty("List_" + list.getName(),
                    list.getItemsString());
        }
        m_settings.setStringProperty("Lists", listTitles);
    }
    
    /** Initialize grocery lists */
    private void initializeLists() {
        String lists="";
        Vector titles = new Vector();
        
        // Load grocery lists from settings record store
        lists = m_settings.getStringProperty("Lists","");
        
        // Split list into grocery list titles
        titles = StringUtility.splitString(lists, "|");
        
        // Create an instance of a grocery list and
        // add it to grocery list array
        if( titles.size()>0) {
            for(int i=0; i<titles.size(); i++) {
                String title = (String)titles.elementAt(i);
                if( title.length()>0 ) {
                    String items = m_settings.getStringProperty("List_" + title, "");
                    Vector groceryItemVector = getGroceryItems( items, "|" );
                    GroceryList list = new GroceryList(title, groceryItemVector);
                    m_lists.put(list.getName(), list);

                }
            }
        }
    }
    
    /** Create grocery item list from string */
    public static Vector getGroceryItems(String items, String separator) {
        Vector itemVector = StringUtility.splitString(items, separator);
        Vector groceryItemVector = new Vector();
        for(int j=0; j<itemVector.size(); j++) {
            String item = (String)itemVector.elementAt(j);
            boolean collected = false;
            if(item.length()>1) {
                if(item.charAt(0)=='+') {
                    // Is selected
                    collected = true;
                    item = item.substring(1);
                }
                if(item.charAt(0)=='-') {
                    // Is not selected
                    collected = false;
                    item = item.substring(1);
                }
                GroceryItem groceryItem =
                        new GroceryItem(item, collected);
                groceryItemVector.addElement(groceryItem);
            }
        }
        return groceryItemVector;
    }
    
    
    
}
