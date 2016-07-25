package com.hendris.japi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import com.hendris.winapi.*;
import com.hendris.winapi.Window;
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
		if (xField != null) {
			xField.setText("" + e.getX());
			xField = null;
		}
		if (yField != null) {
			yField.setText("" + e.getY());
			yField = null;
		}
	}

	public void nativeMouseReleased(NativeMouseEvent arg0) {
	}

	private WinAPI winAPI = WinAPI.INSTANCE;

	public MouseGetClickPositionDemo() {
		try {
			start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Window window;

		if (!winAPI.getLoadedWindows().isEmpty()) {
			window = winAPI.getLoadedWindows().iterator().next();
		}

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 200);

		addComponents(frame);

		frame.pack();
		frame.setVisible(true);
	}

	private JTextField xField, yField;

	public void addComponents(JFrame frame) {
		Container container = frame.getContentPane();
		GridLayout gridLayout = new GridLayout(0, 3);
		container.setLayout(gridLayout);

		JTextField field1x = new JTextField();
		JTextField field1y = new JTextField();

		JTextField field2x = new JTextField();
		JTextField field2y = new JTextField();

		JTextField field3x = new JTextField();
		JTextField field3y = new JTextField();

		JLabel profileLabel = new JLabel("Profile:");
		JTextField profileField = new JTextField();

		JLabel tableLabel = new JLabel("Table:");
		JTextField size1Field = new JTextField();
		JTextField size2Field = new JTextField();
		JTextField size3Field = new JTextField();
		JTextField size4Field = new JTextField();

		JButton botao1 = new JButton("Botão 1");
		botao1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				xField = field1x;
				yField = field1y;
			}
		});

		JButton botao2 = new JButton("Botão 2");
		botao2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				xField = field2x;
				yField = field2y;
			}
		});

		JButton botao3 = new JButton("Botão 3");
		botao3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				xField = field3x;
				yField = field3y;
			}
		});

		container.add(profileLabel);
		container.add(profileField);
		container.add(new JLabel());

		container.add(tableLabel);
		container.add(size1Field);
		container.add(size2Field);

		container.add(new JLabel());
		container.add(size3Field);
		container.add(size4Field);

		container.add(botao1);
		container.add(field1x);
		container.add(field1y);


		container.add(botao2);
		container.add(field2x);
		container.add(field2y);

		container.add(botao3);
		container.add(field3x);
		container.add(field3y);

		JButton btnGerar = new JButton("Gerar");
		btnGerar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		container.add(btnGerar);
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
