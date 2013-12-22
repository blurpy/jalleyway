
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
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Viser "Om jAlleyway" boksen
 *
 * @author Christian Ihle
 */
public class OmGUI
{
	private JFrame omVindu;
	private Container omKont;
	private JButton lukkeKnapp;
	private JPanel knappPanel;
	private JTextArea textFelt;
	private KnappeLytter lytter;

	public void visGUI()
	{
		lytter = new KnappeLytter();
		omVindu = new JFrame();
		omKont = omVindu.getContentPane();

		knappPanel = new JPanel();
		knappPanel.setLayout( new FlowLayout() );

		textFelt = new JTextArea();
		textFelt.setLineWrap( true );
		textFelt.setWrapStyleWord( true );
		textFelt.setEditable( false );
		omKont.add( textFelt, BorderLayout.CENTER );

		lukkeKnapp = new JButton( "Lukk" );
		lukkeKnapp.addActionListener( lytter );
		knappPanel.add( lukkeKnapp );

		omKont.add( knappPanel, BorderLayout.SOUTH );

		skrivText();

		omVindu.setTitle( "Om jAlleyway" );
		omVindu.setSize( 300, 245 );
		omVindu.setResizable( false );
		omVindu.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		omVindu.setVisible( true );
	}

	private void skrivText()
	{
		String tekst = "\njAlleyway er skrevet av:\n" +
						"Christian Ihle\n" +
						"kontakt@usikkert.net\n\n" +
						"Dette programmet er gitt ut under GNU GPL versjon 2 lisensen, uten noen som helst garantier.\n" +
						"Se LICENSE eller http://www.gnu.org/ for mere informasjon.";

		textFelt.append( tekst );
	}

	private class KnappeLytter implements ActionListener
	{
		public void actionPerformed( ActionEvent hendelse )
		{
			// Visstnok en lur måte å lukke det på
			omVindu.dispose();
		}
	}
}
