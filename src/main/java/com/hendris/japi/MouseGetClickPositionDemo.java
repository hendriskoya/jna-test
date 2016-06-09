package com.hendris.japi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

public class MouseGetClickPositionDemo implements NativeMouseListener {

	/**
	 * Esse código será utilizado para mapear as posições dos botões
	 * quando o usuário for criar o profile de jogo
	 * @param args
	 * @throws InterruptedException
     */
	public static void main(String[] args) throws InterruptedException {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MouseGetClickPositionDemo();
			}
		});
	}

	public void nativeMouseClicked(NativeMouseEvent arg0) {

	}

	public void nativeMousePressed(NativeMouseEvent e) {
		System.out.println(e.getX());
		System.out.println(e.getY());
		if (selectedField != null) {
			selectedField.setText("" + e.getX() + ", " + e.getY());
			selectedField = null;
		}
	}

	public void nativeMouseReleased(NativeMouseEvent arg0) {
	}

	public MouseGetClickPositionDemo() {
		try {
			start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 200);

		addComponents(frame);

		frame.pack();
		frame.setVisible(true);
	}

	private JTextField selectedField;

	public void addComponents(JFrame frame) {
		Container container = frame.getContentPane();
		GridLayout gridLayout = new GridLayout(0, 2);
		container.setLayout(gridLayout);

		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();

		JButton botao1 = new JButton("Botão 1");
		botao1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedField = field1;
			}
		});

		JButton botao2 = new JButton("Botão 2");
		botao2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedField = field2;
			}
		});

		JButton botao3 = new JButton("Botão 3");
		botao3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedField = field3;
			}
		});


		container.add(botao1);
		container.add(field1);


		container.add(botao2);
		container.add(field2);

		container.add(botao3);
		container.add(field3);

	}

	public void start() throws InterruptedException {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		//GlobalScreen.addNativeMouseListener(new MouseGetClickPositionDemo());
		GlobalScreen.addNativeMouseListener(this);

		Thread.sleep(1000);
		NativeMouseEvent me = new  NativeMouseEvent(
				NativeMouseEvent.NATIVE_MOUSE_CLICKED,
				System.currentTimeMillis(),
				0,
				75, 10,
				1, NativeMouseEvent.BUTTON1);
		GlobalScreen.postNativeEvent(me);

		//Thread.sleep(500);
		/*
		me = new  NativeMouseEvent(
				NativeMouseEvent.NATIVE_MOUSE_RELEASED,
				System.currentTimeMillis(),
				0,
				50, 10,
				1, NativeMouseEvent.BUTTON1);

		GlobalScreen.postNativeEvent(me);
		*/
	}
}
