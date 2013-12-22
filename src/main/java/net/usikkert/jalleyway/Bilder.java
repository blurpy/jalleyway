
/***************************************************************************
 *   Copyright 2005 by Christian Ihle                                      *
 *   kontakt@usikkert.net                                                  *
 *                                                                         *
 *   This file is part of jAlleyway.                                       *
 *                                                                         *
 *   jAlleyway is free software; you can redistribute it and/or modify     *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   jAlleyway is distributed in the hope that it will be useful,          *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with jAlleyway.                                                 *
 *   If not, see <http://www.gnu.org/licenses/>.                           *
 ***************************************************************************/

package net.usikkert.jalleyway;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Laster alle bildene og gjør de tilgjengelig for tegneklassen.
 *
 * @author Christian Ihle
 */
public class Bilder
{
	private Image bgBilde, klossBilde, skipBilde;
	private Image[] ballBilder;

	public Bilder()
	{
		ballBilder = new Image[4];
		ballBilder[0] = lastBilde( "bilder/ball1.png" );
		ballBilder[1] = lastBilde( "bilder/ball2.png" );
		ballBilder[2] = lastBilde( "bilder/ball3.png" );
		ballBilder[3] = lastBilde( "bilder/ball4.png" );

		bgBilde = lastBilde( "bilder/himmel.png" );
		klossBilde = lastBilde( "bilder/kloss.png" );
		skipBilde = lastBilde( "bilder/skip.png" );
	}

	public Image getBGBilde()
	{
		return bgBilde;
	}

	public Image[] getBallBilder()
	{
		return ballBilder;
	}

	public Image getKlossBilde()
	{
		return klossBilde;
	}

	public Image getSkipBilde()
	{
		return skipBilde;
	}

	private Image lastBilde( String filNavn )
	{
		// Trengte visst den lange regla her for at det skulle fungere inni en jar fil
		return new ImageIcon( getClass().getClassLoader().getResource( filNavn ) ).getImage();
	}
}
