
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
 * Ballen som spretter rundt.
 *
 * @author Christian Ihle
 */
public class Ball
{
	private double xPos, yPos, xHast, yHast, radius;

	public void setStorrelse( double a, double b, double c, double d, double e )
	{
		xPos = a;
		yPos = b;
		xHast = c;
		yHast = d;
		radius = e;
	}

	// Regner ut posisjon utifra pixler per ms * tid siden forrige gang i ms.
	public void oppdaterPos( long tid )
	{
		xPos += xHast * tid;
		yPos += yHast * tid;
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

	public void setYHast( double a )
	{
		yHast = a;
	}

	public double getYHast()
	{
		return yHast;
	}

	public void setRadius( double a )
	{
		radius = a;
	}

	public double getRadius()
	{
		return radius;
	}

	// Brukes for å sjekke om ballen har truffet noe
	public Rectangle getRekt()
	{
		return new Rectangle( (int) Math.round( xPos ) - (int) Math.round( radius ), (int) Math.round( yPos ) - (int) Math.round( radius ), (int) Math.round( radius ) * 2, (int) Math.round( radius ) * 2 );
	}
}
