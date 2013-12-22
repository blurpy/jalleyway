
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
 * Skipet i bunnen av spillet som man styrer.
 *
 * @author Christian Ihle
 */
public class Skip
{
	private static double xPos, yPos, xHast, hoyde, lengde;
	private int panelVidde;
	private BallGUI gui;

	public Skip()
	{
		gui = new BallGUI();
	}

	public void setStorrelse( double a, double b, double c, double d, double e )
	{
		xPos = a;
		yPos = b;
		xHast = c;
		hoyde = d;
		lengde = e;

		panelVidde = gui.getPanel().getSize().width;
	}

	// Oppdaterer posisjonen utifra pixler per ms * tid siden forrige gang i ms, så lenge skipet ikke er i ytterkanten av panelet.
	public void oppdaterPos( long tid )
	{
		if ( xHast < 0 && Math.round( xPos ) > Math.round( lengde ) / 2 )
			xPos += xHast * tid;
		else if ( xHast > 0 && Math.round( xPos ) < panelVidde - Math.round( lengde ) / 2 )
			xPos += xHast * tid;
	}

	public void setX( double a )
	{
		xPos = a;
	}

	public double getX()
	{
		return xPos;
	}

	public void setY( double a )
	{
		yPos = a;
	}

	public double getY()
	{
		return yPos;
	}

	public void setXHast( double a )
	{
		xHast = a;
	}

	public double getXHast()
	{
		return xHast;
	}

	public void setHoyde( double a )
	{
		hoyde = a;
	}

	public double getHoyde()
	{
		return hoyde;
	}

	public void setLengde( double a )
	{
		lengde = a;
	}

	public double getLengde()
	{
		return lengde;
	}

	// For å finne ut om skipet har truffet ballen
	public Rectangle getRekt()
	{
		return new Rectangle( (int) Math.round( xPos ) - (int) Math.round( lengde ) / 2, (int) Math.round( yPos ) - (int) Math.round( hoyde ) / 2, (int) Math.round( lengde ), (int) Math.round( hoyde ) );
	}
}
