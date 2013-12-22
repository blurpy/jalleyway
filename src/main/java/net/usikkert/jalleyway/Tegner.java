
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.text.DecimalFormat;

/**
 * Dette er klassen som tar seg av all tegninga på skjermen.
 *
 * <p>Bugs:</p>
 * <ul>
 *   <li>Ballen går litt videre når man pauser spillet.</li>
 *   <li>Tiden går når man trykker på pause, så spilletid blir feil.</li>
 *   <li>Innimellom forsvinner en kloss.</li>
 *   <li>Innimellom har ikke panelet fokus.</li>
 * </ul>
 *
 * @author Christian Ihle
 */
public class Tegner extends Thread
{
	private BallGUI gui;
	private Ball ball;
	private Skip skip;
	private Animasjon anim;
	private Level level;
	private Kloss[] kloss;
	private Bilder bilder;

	private Graphics g, dbg;
	private Graphics2D g2d;
	private Image dbBilde, skipBilde, bgBilde, klossBilde;
	private Image[] ballBilder;
	private Rectangle rBall, rSkip, rKloss;
	private DecimalFormat toDes;

	private boolean kjore, treff, vente;
	private double ballXPos, ballYPos, ballXHast, ballYHast, ballRadius, skipXPos, skipYPos, skipXHast, skipHoyde, skipLengde, tempTall;
	private int xVegg, yVegg, forsvunne, treffnr, levelNr, liv, fpsTeller, fpsTid, fps;
	private long tid, bruktTid, startTid;

	public Tegner()
	{
		toDes = new DecimalFormat( "0.00" );

		kjore = true;
		ballXPos = 180;
		ballYPos = 300;
		ballXHast = 0.25;
		ballYHast = 0.25;
		ballRadius = 6;
		skipXPos = 275;
		skipYPos = 410;
		skipXHast = 0.0;
		skipHoyde = 22;
		skipLengde = 76;
		levelNr = 1;
		liv = 4;

		skip = new Skip();
		level = new Level();
		gui = new BallGUI();
		ball = new Ball();
		anim = new Animasjon();
		bilder = new Bilder();

		skip.setStorrelse( skipXPos, skipYPos, skipXHast, skipHoyde, skipLengde );
		ball.setStorrelse(  ballXPos, ballYPos, ballXHast, ballYHast, ballRadius );

		levelVelger();

		ballBilder = bilder.getBallBilder();
		bgBilde = bilder.getBGBilde();
		klossBilde = bilder.getKlossBilde();
		skipBilde = bilder.getSkipBilde();

		// Setter opp en animasjon av alle bildene til ballen, med 100ms mellom hvert bytte
		for ( int i = 0; i < ballBilder.length; i++ )
			anim.addFrame( ballBilder[i], 100 );

		startTid = System.currentTimeMillis();
		tid = startTid;
	}

	public void run()
	{
		// Finner størrelsen på panelet
		xVegg = gui.getPanel().getSize().width; //542
		yVegg = gui.getPanel().getSize().height; //436

		// Lager et bilde på størrelse med panelet som vi kan bruke som double buffer
		dbBilde = gui.getPanel().createImage( xVegg, yVegg );

		gui.getVindu().requestFocus();

		while ( kjore )
		{
			// Regner ut hvor lang tid det har gått siden forrige gang
			bruktTid = System.currentTimeMillis() - tid;
			tid += bruktTid;

			// For å finne antall frames per sekund
			fpsTid += bruktTid;
			fpsTeller++;

			// Dette blir sånn ca 1 gang i sekundet
			if ( fpsTid >= 1000 )
			{
				fps = fpsTeller;
				fpsTeller = 0;
				fpsTid = 0;
			}

			// Hindrer at input lagger
			try { sleep( 0 ); }
			catch ( InterruptedException IFeil ) {}

			// Pause
			if ( vente )
			{
				try { sleep( 100 ); }
				catch ( InterruptedException IFeil ) {}
			}

			else
				oppdater();
		}
	}

	// Sender hvilken level vi er på, og får riktig kloss tilbake fra Level klassen
	private void levelVelger()
	{
		kloss = level.getKloss( levelNr );

		if ( kloss.length == 0 )
		{
			levelNr = 1;
			levelVelger();
		}
	}

	// Resetter nødvendige variabler for å starte ny level eller på nytt liv
	private void resetVar()
	{
		skipXPos = 275;
		skipYPos = 410;
		skip.setX( skipXPos );
		skip.setY( skipYPos );

		ballXPos = 180;
		ballYPos = 300;
		ball.setX( ballXPos );
		ball.setY( ballYPos );
		ball.setXHast( ballXHast );
		ball.setYHast( ballYHast );

		startTid = System.currentTimeMillis();
		tid = startTid;
	}

	private void oppdater()
	{
		// Sender antall ms siden forrige gang, så klassene kan regne ut hvor langt de skal ha beveget seg, eller hvilken frame som skal synes
		anim.oppdater( bruktTid );
		ball.oppdaterPos( bruktTid );
		skip.oppdaterPos( bruktTid );

		rBall = ball.getRekt();
		rSkip = skip.getRekt();

		// Setter opp double buffer bildet
		dbg = dbBilde.getGraphics();

		// Aktiverer AntiAlias for penere tekst
		g2d = (Graphics2D) dbg;
		g2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );

		// Male de forskjellige elementene i double buffer bildet
		malBG( dbg );
		malFPS( dbg );
		malKlosser( dbg );
		malSkip( dbg );
		malBall( dbg );

		// Sjekke kollisjoner og endre retninger
		sjekkBall( dbg );

		// Hindre litt minnelekasjer
		dbg.dispose();

		// Male bildet på skjermen
		g = gui.getPanel().getGraphics();
		g.drawImage( dbBilde, 0, 0, gui.getPanel() );
		g.dispose();

		// Var visst kjekt å ha i Linux for å fikse synkronisering mellom grafikk og input
		//Toolkit.getDefaultToolkit().sync();
	}

	private void malFPS( Graphics g2 )
	{
		g2.setColor( Color.white );
		g2.drawString( "FPS: " + fps, 10, 20 );
	}

	private void malBG( Graphics g2 )
	{
		g2.drawImage( bgBilde, 0, 0, null );
	}

	private void malKlosser( Graphics g2 )
	{
		for ( int i = 0; i < kloss.length; i++ )
		{
			if ( kloss[i].getSynlig() == true )
				g2.drawImage( klossBilde, kloss[i].getX() - kloss[i].getLengde()/2, kloss[i].getY() - kloss[i].getHoyde()/2, kloss[i].getLengde(), kloss[i].getHoyde(), null );
		}
	}

	private void malSkip( Graphics g2 )
	{
		skipXPos = skip.getX();
		skipYPos = skip.getY();

		g2.drawImage( skipBilde, (int) Math.round( skipXPos ) - (int) Math.round( skipLengde ) / 2, (int) Math.round( skipYPos ) - (int) Math.round( skipHoyde ) / 2, (int) Math.round( skipLengde ), (int) Math.round( skipHoyde ), null );
	}

	private void malBall( Graphics g2 )
	{
		ballXPos = ball.getX();
		ballYPos = ball.getY();
		ballRadius = ball.getRadius();

		g2.drawImage( anim.getBilde(), (int) Math.round( ballXPos ) - (int) Math.round( ballRadius ), (int) Math.round( ballYPos ) - (int) Math.round( ballRadius ), (int) Math.round( ballRadius ) * 2, (int) Math.round( ballRadius ) * 2, null );
	}

	private void sjekkBall( Graphics g2 )
	{
		// Sjekker om man har tatt alle klossene
		if ( forsvunne == kloss.length )
		{
			g2.setColor( Color.white );
			g2.drawString( "Du vant, og slo ut " + kloss.length + " klosser!", 190, 330 );
			g2.drawString( "Du brukte " + toDes.format( (double) ( System.currentTimeMillis() - startTid ) / 1000 ) + " sekunder.", 195, 350 );
			gui.knappNesteL( "Neste level" );

			levelNr++;
			vente = true;
			forsvunne = 0;
			levelVelger();
		}

		// Hvis man har truffet en ball så skjules klossen
		// Tar det sånn for at ikke ballen skal treffe så mange klosser av gangen.
		if ( treff == true )
		{
			forsvunne++;
			kloss[treffnr].setSynlig( false );
			treff = false;
		}

		// Sjekker alle klossene om de har møtt ballen
		for ( int i = 0; i < kloss.length; i++ )
		{
			rKloss = kloss[i].getRekt();

			if ( rBall.intersects( rKloss ) && kloss[i].getSynlig() == true )
			{
				// Før ballen til å sprette oppå klossene
				if ( ( ballYPos + ballRadius ) > ( kloss[i].getY() - kloss[i].getHoyde()/2 ) && ( ballYPos + ballRadius ) < ( kloss[i].getY() - kloss[i].getHoyde()/2 + 3 ) )
				{
					treff = true;
					treffnr = i;
					ball.setYHast( -Math.abs( ball.getYHast() ) );
					break;
				}

				// Før ballen til å sprette under klossene
				else if ( ( ballYPos - ballRadius ) < ( kloss[i].getY() + kloss[i].getHoyde()/2 ) && ( ballYPos - ballRadius ) > ( kloss[i].getY() + kloss[i].getHoyde()/2 - 3 ) )
				{
					treff = true;
					treffnr = i;
					ball.setYHast( +Math.abs( ball.getYHast() ) );
					break;
				}

				// Fø ballen til å sprette på venstre side av klossene
				else if ( ( ballXPos + ballRadius ) > ( kloss[i].getX() - kloss[i].getLengde()/2 ) && ( ballXPos + ballRadius ) < ( kloss[i].getX() - kloss[i].getLengde()/2 + 3 ) )
				{
					treff = true;
					treffnr = i;
					ball.setXHast( -Math.abs( ball.getXHast() ) );
					break;
				}

				// Før ballen til å sprette på høyre side av klossene
				else if ( ( ballXPos - ballRadius ) < ( kloss[i].getX() + kloss[i].getLengde()/2 ) && ( ballXPos - ballRadius ) > ( kloss[i].getX() + kloss[i].getLengde()/2 - 3 ) )
				{
					treff = true;
					treffnr = i;
					ball.setXHast( +Math.abs( ball.getXHast() ) );
					break;
				}
			}
		}

		// Venstre vegg
		if ( ballXPos > xVegg - ballRadius )
			ball.setXHast( -Math.abs( ball.getXHast() ) );

		// Høyre vegg
		else if ( ballXPos < ballRadius )
			ball.setXHast( +Math.abs( ball.getXHast() ) );

		// Skipet. Når ballen treffer skipet så blir den sendt i en litt tilfeldig vinkel i samme retning.
		else if ( rBall.intersects( rSkip ) )
		{
			tempTall = ( Math.random() * -0.06 );

			ball.setYHast( -Math.abs( ballYHast ) + tempTall );

			if ( ball.getXHast() < 0 )
				ball.setXHast( -Math.abs( ballXHast ) - tempTall );
			else
				ball.setXHast( Math.abs( ballXHast ) + tempTall );
		}

		// Taket
		else if ( ballYPos < ballRadius )
			ball.setYHast( +Math.abs( ball.getYHast() ) );

		// Bakken
		else if ( ballYPos > yVegg - ballRadius )
		{
			liv--;

			if ( liv == 0 )
			{
				kjore = false;
				g2.setColor( Color.white );
				g2.drawString( "Du tapte! Av " + kloss.length + " klosser, slo du ut " + forsvunne + ".", 170, 330 );
				g2.drawString( "Du brukte " + toDes.format( (double) ( System.currentTimeMillis() - startTid ) / 1000 ) + " sekunder.", 195, 350 );
				gui.knappStart();
				forsvunne = 0;
			}
			else
			{
				g2.setColor( Color.white );
				g2.drawString( "Av " + kloss.length + " klosser, slo du ut " + forsvunne + ". Du har " + liv + " liv igjen.", 145, 330 );
				g2.drawString( "Du brukte " + toDes.format( (double) ( System.currentTimeMillis() - startTid ) / 1000 ) + " sekunder.", 195, 350 );
				gui.knappNesteL( "Prøv igjen" );
				vente = true;
			}
		}
	}

	// Dette stopper tråden
	public void avslutte()
	{
		kjore = false;
	}

	// Pause alt
	public void pause()
	{
		vente = true;
	}

	// Starter kjøring igjen, og eventuelt gjør klar til ny level
	public void fortsett()
	{
		vente = false;
		gui.getVindu().requestFocus();

		if ( gui.getPauseTekst().equals( "Neste level" ) || gui.getPauseTekst().equals( "Prøv igjen" ) )
			resetVar();
	}

	public boolean getVent()
	{
		return vente;
	}
}
