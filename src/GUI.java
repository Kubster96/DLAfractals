import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class containing GUI: board + buttons
 */
public class GUI extends JPanel implements ActionListener, ChangeListener {
    private static final long serialVersionUID = 1L;
    private Timer timer;
    private Board board;
    private JButton start;
    private JButton clear;
    private JFrame frame;
    private int iterNum = 0;

    private final int initDelay = 0;
    private boolean running = false;

    public GUI(Program jf) {
        frame = jf;
        timer = new Timer(initDelay, this);
        timer.stop();
    }

    /**
     * @param container to which GUI and board is added
     */
    public void initialize(Container container) {
        container.setLayout(new BorderLayout());
        container.setSize(new Dimension(1024, 768));

        JPanel buttonPanel = new JPanel();

        start = new JButton("Start");
        start.setActionCommand("Start");
        start.addActionListener(this);

        clear = new JButton("Clear");
        clear.setActionCommand("reinitialize");
        clear.addActionListener(this);

        buttonPanel.add(start);
        buttonPanel.add(clear);

        board = new Board(1024, 1024 - buttonPanel.getHeight());
        container.add(board, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(timer)) {
            iterNum++;
            board.iteration();
        } else {
            String command = e.getActionCommand();
            if (command.equals("Start")) {
                if (!running) {
                    timer.start();
                    start.setText("Pause");
                } else {
                    timer.stop();
                    start.setText("Start");
                }
                running = !running;
                clear.setEnabled(true);

            } else if (command.equals("reinitialize")) {
                iterNum = 0;
                timer.stop();
                start.setEnabled(true);
                board.reinitialize();
            }
        }
    }

    public void stateChanged(ChangeEvent e) {

    }
}
