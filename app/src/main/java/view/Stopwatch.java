package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Stopwatch implements ActionListener {

        JPanel panel = new JPanel();
        JButton startButton = new JButton("START");
        JButton resetButton = new JButton("RESET");
        JLabel timeLabel = new JLabel();
        int elapsedTime = 0;
        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        boolean started = false;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        String seconds_string = String.format("%02d", seconds); // using string.format to make sec/min/hour appear with 00 before the actual time
        String minutes_string = String.format("%02d", minutes);
        String hours_string = String.format("%02d", hours);

        Timer timer = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                elapsedTime = elapsedTime + 1000;
                hours = (elapsedTime / 3600000);
                minutes = (elapsedTime / 60000) % 60;
                seconds = (elapsedTime / 1000) % 60;
                seconds_string = String.format("%02d", seconds);
                minutes_string = String.format("%02d", minutes);
                hours_string = String.format("%02d", hours);
                timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);

            }

        });

        Stopwatch() {

            timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
            timeLabel.setBounds((screenSize.height/2)/2-100, 50, 200, 100);
            timeLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
            timeLabel.setBorder(BorderFactory.createBevelBorder(1));
            timeLabel.setOpaque(true);
            timeLabel.setHorizontalAlignment(JTextField.CENTER);

            startButton.setBounds((screenSize.height/2)/2-100, 150, 100, 50);
            startButton.setFont(new Font("Marvel", Font.PLAIN, 10));
            startButton.setFocusable(false);
            startButton.addActionListener(this);

            resetButton.setBounds((screenSize.height/2)/2, 150, 100, 50);
            resetButton.setFont(new Font("Marvel Normal", Font.PLAIN, 10));
            resetButton.setFocusable(false);
            resetButton.addActionListener(this);

            panel.add(startButton);
            panel.add(resetButton);
            panel.add(timeLabel);

            panel.setBackground(Color.LIGHT_GRAY);
            panel.setLayout(null);
            panel.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == startButton) {

                if (started == false) {
                    started = true;
                    startButton.setText("STOP");
                    start();
                } else {
                    started = false;
                    startButton.setText("START");
                    stop();
                }

            }
            if (e.getSource() == resetButton) {
                started = false;
                startButton.setText("START");
                reset();
            }

        }

        void start() {
            timer.start();
        }

        void stop() {
            timer.stop();
        }

        void reset() {
            timer.stop();
            elapsedTime = 0;
            seconds = 0;
            minutes = 0;
            hours = 0;
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        }

        public JPanel getStopWatch() {
            return panel;
        }
}
