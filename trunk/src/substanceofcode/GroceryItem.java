/*
 * GroceryItem.java
 *
 * Copyright (C) 2005 Tommi Laukkanen
 * http://www.substanceofcode.com
 *
 * Created on May 19th 2005, 21:57
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

/**
 *
 * @author Tommi Laukkanen
 */
public class GroceryItem {
    
    private boolean m_collected;
    private String  m_description;
    
    /** Creates a new instance of GroceryItem */
    public GroceryItem(String description, boolean collected) {
        m_description = description;
        m_collected   = collected;
    }
    
    /** Grocery item's description */
    public String getDescription() {
        return m_description;
    }
    public void setDescription(String desc) {
        m_description = desc;
    }
    
    /** 
     * Grocery item's collected status 
     * true = item has been collected    
     */
    public boolean isCollected() {
        return m_collected;
    }
    public void setCollected(boolean collected) {
        m_collected = collected;
    }
}
