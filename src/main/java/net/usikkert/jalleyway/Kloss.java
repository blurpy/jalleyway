
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

import java.awt.Rectangle;

/**
 * Klossene som man skal banke bort.
 *
 * @author Christian Ihle
 */
public class Kloss
{
	private int xPos, yPos, hoyde, lengde, farge;
	private boolean synlig;

	public void setStorrelse( int a, int b, int c, int d, int e )
	{
		xPos = a;
		yPos = b;
		hoyde = c;
		lengde = d;
		farge = e;
		synlig = true;
	}

	public void setX( int a )
	{
		xPos = a;
	}

	public int getX()
	{
		return xPos;
	}

	public void setY( int a )
	{
		yPos = a;
	}

	public int getY()
	{
		return yPos;
	}

	public void setHoyde( int a )
	{
		hoyde = a;
	}

	public int getHoyde()
	{
		return hoyde;
	}

	public void setLengde( int a )
	{
		lengde = a;
	}

	public int getLengde()
	{
		return lengde;
	}

	public void setFarge( int a )
	{
		farge = a;
	}

	public int getFarge()
	{
		return farge;
	}

	public void setSynlig( boolean a )
	{
		synlig = a;
	}

	public boolean getSynlig()
	{
		return synlig;
	}

	// For å sjekke om de har blitt truffet av ballen
	public Rectangle getRekt()
	{
		return new Rectangle( xPos - lengde / 2, yPos - hoyde / 2, lengde, hoyde );
	}
}
