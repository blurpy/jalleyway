
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Viser selve GUIet.
 *
 * @author Christian Ihle
 */
public class BallGUI
{
	private static JFrame vindu;
	private Container kont;
	private static JPanel ballPanel;
	private JPanel knappPanel;
	private static JButton startKnapp, stoppKnapp, pauseKnapp, omKnapp, avsluttKnapp;
	private KnappeLytter knappeLytter;
	private TasteLytter tasteLytter;
	private OmGUI omgui;

	public void visGUI()
	{
		knappeLytter = new KnappeLytter();
		tasteLytter = new TasteLytter();
		vindu = new JFrame();
		vindu.addKeyListener( tasteLytter );
		kont = vindu.getContentPane();

		ballPanel = new JPanel();
		ballPanel.setBackground( Color.BLACK );

		knappPanel = new JPanel();
		knappPanel.setLayout( new FlowLayout() );

		startKnapp = new JButton( "Start" );
		startKnapp.addActionListener( knappeLytter );
		knappPanel.add( startKnapp );

		pauseKnapp = new JButton( "Pause" );
		pauseKnapp.setEnabled( false );
		pauseKnapp.addActionListener( knappeLytter );
		knappPanel.add( pauseKnapp );

		stoppKnapp = new JButton( "Stopp" );
		stoppKnapp.setEnabled( false );
		stoppKnapp.addActionListener( knappeLytter );
		knappPanel.add( stoppKnapp );

		omKnapp = new JButton( "Om" );
		omKnapp.addActionListener( knappeLytter );
		knappPanel.add( omKnapp );

		avsluttKnapp = new JButton( "Avslutt" );
		avsluttKnapp.addActionListener( knappeLytter );
		knappPanel.add( avsluttKnapp );

		kont.add( ballPanel, BorderLayout.CENTER );
		kont.add( knappPanel, BorderLayout.SOUTH );

		vindu.setTitle( "jAlleyway" );
		vindu.setSize( 550, 500 );
		vindu.setResizable( false );
		//vindu.setIgnoreRepaint( true );
		vindu.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		vindu.setVisible( true );
	}

	public JPanel getPanel()
	{
		return ballPanel;
	}

	public JFrame getVindu()
	{
		return vindu;
	}

	public void knappStart()
	{
		startKnapp.setEnabled( true );
		pauseKnapp.setEnabled( false );
		stoppKnapp.setEnabled( false );
	}

	public void knappStopp()
	{
		startKnapp.setEnabled( false );
		pauseKnapp.setEnabled( true );
		stoppKnapp.setEnabled( true );
	}

	public void knappNesteL( String tekst )
	{
		startKnapp.setEnabled( false );
		pauseKnapp.setEnabled( true );
		stoppKnapp.setEnabled( true );
		pauseKnapp.setText( tekst );
	}

	public String getPauseTekst()
	{
		return pauseKnapp.getText();
	}

	private class KnappeLytter implements ActionListener
	{
		private Tegner tegner;

		public KnappeLytter()
		{
			omgui = new OmGUI();
		}

		public void actionPerformed( ActionEvent hendelse )
		{
			if ( hendelse.getSource() == startKnapp )
			{
				tegner = new Tegner();
				tegner.start();
				knappStopp();
			}

			else if ( hendelse.getSource() == pauseKnapp )
			{
				if ( tegner.getVent() == true )
				{
					tegner.fortsett();
					pauseKnapp.setText( "Pause" );
				}
				else if ( tegner.getVent() == false )
				{
					tegner.pause();
					pauseKnapp.setText( "Fortsett" );
				}
			}

			else if ( hendelse.getSource() == stoppKnapp )
			{
				tegner.avslutte();
				knappStart();
				pauseKnapp.setText( "Pause" );
			}

			else if ( hendelse.getSource() == omKnapp )
			{
				if ( tegner != null )
				{
					tegner.pause();
					pauseKnapp.setText( "Fortsett" );
				}

				omgui.visGUI();
			}

			else if ( hendelse.getSource() == avsluttKnapp )
				System.exit( 0 );
		}
	}

	private class TasteLytter implements KeyListener
	{
		private Skip skip;

		public TasteLytter()
		{
			skip = new Skip();
		}

		public void keyPressed( KeyEvent tast )
		{
			// Så lenge man holder knappene inne så beveger skipet seg
			if ( tast.getKeyCode() == KeyEvent.VK_LEFT )
				skip.setXHast( -0.3 );
			else if ( tast.getKeyCode() == KeyEvent.VK_RIGHT )
				skip.setXHast( 0.3 );
		}

		public void keyTyped( KeyEvent tast )
		{

		}

		public void keyReleased( KeyEvent tast )
		{
			// Når man slipper knappen så stopper skipet
			if ( tast.getKeyCode() == KeyEvent.VK_LEFT || tast.getKeyCode() == KeyEvent.VK_RIGHT )
					skip.setXHast( 0.0 );
		}
	}
}
