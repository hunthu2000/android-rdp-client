/* LicenceStore_Localised.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 12 $
 * Author: $Author: miha_vitorovic $
 * Date: $Date: 2007-05-11 19:49:09 +0800 (五, 2007-05-11) $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.4 specific extension of LicenceStore class
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 * 
 * (See gpl.txt for details of the GNU General Public License.)
 * 
 */
/**
 * 
 * Created on 05-Aug-2003
 */

package net.propero.rdp_src1_4;

import java.util.prefs.Preferences;

import net.propero.rdp.LicenceStore;
import net.propero.rdp.Options;

public class LicenceStore_Localised extends LicenceStore {

	public byte[] load_licence() {
		Preferences prefs = Preferences.userNodeForPackage(this.getClass());
		return prefs.getByteArray("licence." + Options.hostname, null);

	}

	public void save_licence(byte[] databytes) {
		Preferences prefs = Preferences.userNodeForPackage(this.getClass());
		prefs.putByteArray("licence." + Options.hostname, databytes);
	}

}
