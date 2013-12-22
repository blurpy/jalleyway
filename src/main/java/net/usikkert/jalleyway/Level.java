
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

/**
 * Lager og returnerer alle levlene.
 *
 * @author Christian Ihle
 */
public class Level
{
	private Kloss[] kloss;
	private int rad, kol, lengde, hoyde, farge, snu;

	public Kloss[] getKloss( int level )
	{
		switch( level )
		{
			case 1:		klossLevel1();
						break;
			case 2:		klossLevel2();
						break;
			case 3:		klossLevel3();
						break;
			case 4:		klossLevel4();
						break;
			case 5:		klossLevel5();
						break;
			default:	kloss = new Kloss[0];
						break;
		}

		return kloss;
	}

	// En stor kloss på midten
	private void klossLevel1()
	{
		kol = 275;
		rad = 100;
		hoyde = 40;
		lengde = 80;
		farge = 0;

		kloss = new Kloss[1];

		kloss[0] = new Kloss();
		kloss[0].setStorrelse( kol, rad, hoyde, lengde, farge );
	}

	// 5 mellomstore klosser på rekke
	private void klossLevel2()
	{
		kol = 105;
		rad = 100;
		hoyde = 30;
		lengde = 60;
		farge = 0;

		kloss = new Kloss[5];

		for ( int i = 0; i < kloss.length; i++ )
		{
			if ( i % 2 == 0 )
				farge = 0;
			else
				farge = 1;

			kloss[i] = new Kloss();
			kloss[i].setStorrelse( kol, rad, hoyde, lengde, farge );
			kol += 80;
		}
	}

	// 36 små klosser i en pyramide form
	private void klossLevel3()
	{
		int tall = 0;
		int ekstraTall = 1;
		int teller = 0;
		int startKol = 275;

		kol = startKol;
		rad = 35;
		hoyde = 20;
		lengde = 39;
		farge = 0;
		snu = 0;

		kloss = new Kloss[36];

		for ( int i = 0; i < kloss.length; i++ )
		{
			kloss[i] = new Kloss();

			if ( tall == 0 )
			{
				kloss[i].setStorrelse( kol, rad, hoyde, lengde, farge );
				kol = kol - 80;
				tall++;
				teller++;
			}

			else if ( i % tall == 0 )
			{
				rad += 21;
				kol = startKol - ( teller * 40 );

				if ( snu == 1 )
					snu = 0;
				else
					snu = 1;

				kloss[i].setStorrelse( kol, rad, hoyde, lengde, farge );

				teller++;
				ekstraTall += 2;
				tall += ekstraTall;
			}

			else
			{
				if ( i % 2 == snu )
					farge = 0;
				else
					farge = 1;

				kloss[i].setStorrelse( kol, rad, hoyde, lengde, farge );
			}

			kol += 40;
		}
	}

	// 60 små klosser i en rektangel
	private void klossLevel4()
	{
		kol = 85;
		rad = 35;
		hoyde = 20;
		lengde = 40;
		farge = 0;
		snu = 0;

		kloss = new Kloss[60];

		for ( int i = 0; i < kloss.length; i++ )
		{
			kloss[i] = new Kloss();

			if ( i % 10 == 0 )
			{
				rad += 21;
				kol = 85;

				if ( snu == 1 )
					snu = 0;
				else
					snu = 1;
			}

			if ( i % 2 == snu )
				farge = 0;
			else
				farge = 1;

			kloss[i].setStorrelse( kol, rad, hoyde, lengde, farge );
			kol += 41;
		}
	}

	// 80 små klosser i en litt bredere rektangel
	private void klossLevel5()
	{
		kol = 85;
		rad = 35;
		hoyde = 20;
		lengde = 40;
		farge = 0;
		snu = 0;

		kloss = new Kloss[80];

		for ( int i = 0; i < kloss.length; i++ )
		{
			kloss[i] = new Kloss();

			if ( i % 8 == 0 )
			{
				rad += 21;
				kol = 125;

				if ( snu == 1 )
					snu = 0;
				else
					snu = 1;
			}

			if ( i % 2 == snu )
				farge = 0;
			else
				farge = 1;

			kloss[i].setStorrelse( kol, rad, hoyde, lengde, farge );
			kol += 41;
		}
	}
}
