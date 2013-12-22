
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
import java.util.ArrayList;

/**
 * Brukes til å lagre animasjoner
 *
 * @author Christian Ihle
 */
public class Animasjon
{
	private ArrayList bilder;
	private int aktivFrameNr;
	private long animTid;
	private long totalVarighet;

	public Animasjon()
	{
		bilder = new ArrayList();
		totalVarighet = 0;
		animTid = 0;
		aktivFrameNr = 0;
	}

	// Når man legger til en frame i animasjonen så må vi vite hvor mange millisekunder den skal synes
	public void addFrame( Image bilde, long varighet )
	{
		totalVarighet += varighet;
		bilder.add( new Frame( bilde, totalVarighet ) );
	}

	// Sjekker om neste frame skal vises, ut i fra hvor lang tid det har gått siden animasjonen starta.
	public void oppdater( long bruktTid )
	{
		if ( bilder.size() > 1 )
		{
			animTid += bruktTid;

			// Restarter animasjonen
			if ( animTid >= totalVarighet )
			{
				animTid = animTid % totalVarighet;
				aktivFrameNr = 0;
			}

			// Bruker en while og ikke en if her så ikke animasjonen skal bli usynkronisert i tilfelle det f.eks går 2 frames mellom hver oppdatering.
			while ( animTid > getFrame( aktivFrameNr ).sluttTid )
			{
				aktivFrameNr++;
			}
		}
	}

	// Henter ut kun bildet fra framen som kommer fra metoden under
	public Image getBilde()
	{
		if ( bilder.size() == 0 )
			return null;
		else
			return getFrame( aktivFrameNr ).bilde;
	}

	// Henter ut valgt frame fra ArrayLista
	private Frame getFrame( int i )
	{
		return (Frame) bilder.get( i );
	}

	// Hver frame blir lagra i denne klassen med et bilde og et tidspunkt i ms, som brukes til å finne ut når neste frame skal vises.
	private class Frame
	{
		private Image bilde;
		private long sluttTid;

		public Frame( Image tempBilde, long tempSluttTid )
		{
			bilde = tempBilde;
			sluttTid = tempSluttTid;
		}
	}
}
