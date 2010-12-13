/*
 * StringUtility.java
 *
 * Copyright (C) 2005 Tommi Laukkanen
 * http://www.substanceofcode.com
 *
 * Created on May 15th 2005, 21:31
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
public class StringUtility {
    
    /** Creates a new instance of StringUtility */
    private StringUtility() {
    }
    
    /** Split string into multiple strings divided by delimeter string */
    public static Vector splitString(String originalString, String delimeter){
        int i=0;
        Vector result = new Vector();
        i = originalString.indexOf( delimeter );
        
        if(i<0) {
            if(originalString.length()>0)
                result.addElement(originalString);
            return result;
        }
            
        while(i>=0){
            result.addElement( (String) originalString.substring(0, i));
            originalString = originalString.substring(i+delimeter.length());
            i = originalString.indexOf( delimeter );
        }
        result.addElement(originalString);
        
        return result;
    }    
}
