/**
 * Settings.java
 *
 * Copyright (C) 2005 Tommi Laukkanen
 * http://www.substanceofcode.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package substanceofcode;

import java.io.*;
import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.rms.*;

/**
 * A class for storing and retrieving application settings and properties.
 * Class stores all settings into one Hashtable variable. Hashtable is loaded
 * from RecordStore at initialization and it is stored back to the RecordStore
 * with save method.
 *
 * @author  Tommi Laukkanen
 * @version 1.0
 */
public class Settings {
    
    private static Settings m_store;
    private MIDlet          m_midlet;
    private boolean         m_valuesChanged = false;
    private Hashtable       m_properties = new Hashtable();
    
    /**
     * Singleton pattern is used to return 
     * only one instance of record store
     */
    public static synchronized Settings getInstance( MIDlet midlet )
    throws IOException, RecordStoreException {
        if( m_store == null ) {
            m_store = new Settings( midlet );
        }
        return m_store;
    }

    /** Constructor */
    private Settings( MIDlet midlet )
    throws IOException, RecordStoreException {
        m_midlet = midlet;
        load();
    }
    
    /** Return true if value exists in record store */
    private boolean exists( String name ) {
        return getProperty( name ) != null;
    }
    
    /** Get property from Hashtable*/
    private synchronized String getProperty( String name ) {
        String value = (String) m_properties.get( name );
        if( value == null && m_midlet != null ) {
            value = m_midlet.getAppProperty( name );
            if( value != null ) {
                m_properties.put( name, value );
            }
        }
        return value;
    }
    
    /** Get boolean property */
    public boolean getBooleanProperty( String name, boolean defaultValue) {
        String value = getProperty( name );
        if( value != null ) {
            return value.equals( "true" ) || value.equals( "1" );
        }
        return defaultValue;
    }
    
    /** Get integer property */
    public int getIntProperty( String name, int defaultValue ) {
        String value = getProperty( name );
        if( value != null ) {
            try {
                return Integer.parseInt( value );
            } catch( NumberFormatException e ) {
            }
        }
        return defaultValue;
    }
    
    /** Get string property */
    public String getStringProperty( String name, String defaultValue ) {
        Object value = getProperty( name );
        return ( value != null ) ? value.toString() : defaultValue;
    }
    
    /** Load properties from record store */
    private synchronized void load()
    throws IOException, RecordStoreException {
        RecordStore rs = null;
        ByteArrayInputStream bin = null;
        DataInputStream din = null;
        
        m_valuesChanged = false;
        m_properties.clear();
        
        try {
            rs = RecordStore.openRecordStore("Store", true );
            if( rs.getNumRecords() == 0 ) {
                rs.addRecord( null, 0, 0 );
            } else {
                byte[] data = rs.getRecord( 1 );
                if( data != null ) {
                    bin = new ByteArrayInputStream( data );
                    din = new DataInputStream( bin );
                    int num = din.readInt();
                    while( num-- > 0 ) {
                        String name = din.readUTF();
                        String value = din.readUTF();
                        m_properties.put( name, value );
                    }
                }
            }
        } finally {
            if( din != null ) {
                try { din.close(); } catch( Exception e ){}
            }
            
            if( rs != null ) {
                try { rs.closeRecordStore(); } catch( Exception e ){}
            }
        }
    }
    
    /** Save property Hashtable to record store */
    public synchronized void save( boolean force )
            throws IOException, RecordStoreException {
        if( !m_valuesChanged && !force ) return;
        
        RecordStore rs = null;
        ByteArrayOutputStream bout = new
                ByteArrayOutputStream();
        DataOutputStream dout = new
                DataOutputStream( bout );
        
        try {
            dout.writeInt( m_properties.size() );
            Enumeration e = m_properties.keys();
            while( e.hasMoreElements() ) {
                String name = (String) e.nextElement();
                String value = m_properties.get( name ).toString();
                dout.writeUTF( name );
                dout.writeUTF( value );
            }
            
            byte[] data = bout.toByteArray();
            
            rs = RecordStore.openRecordStore( "Store", false );
            rs.setRecord( 1, data, 0, data.length );
        } finally {
            try { dout.close(); } catch( Exception e ){}
            
            if( rs != null ) {
                try { rs.closeRecordStore(); } catch( Exception e ){}
            }
        }
    }
    
    /** Set a boolean property */
    public void setBooleanProperty( String name, boolean value ) {
        setStringProperty( name, value ? "true" : "false" );
    }
    
    /** Set an integer property */
    public void setIntProperty( String name, int value ) {
        setStringProperty( name, Integer.toString( value ) );
    }
    
    /** Set a string property */
    public synchronized boolean setStringProperty( String name, String value ) {
        if( name == null && value == null ) return false;
        m_properties.put( name, value );
        m_valuesChanged = true;
        return true;
    }
}
