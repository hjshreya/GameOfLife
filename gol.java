import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameOfLife extends JFrame {
    int size = 50;
    boolean cellsMap[][];
    JButton cells[][];
    Timer timer;
    JButton startStopButton;
    boolean running = false;

    public GameOfLife() {
        Random rnd = new Random();
        cellsMap = new boolean[size][size];
        cells = new JButton[size][size];

        setSize(600, 600);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(size, size));
        gridPanel.setBackground(Color.BLACK);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cellsMap[i][j] = rnd.nextInt(100) < 30;
                JButton temp = new JButton();
                temp.setOpaque(true);
                temp.setBorderPainted(false);

                temp.setBackground(cellsMap[i][j] ? getRandomColor() : Color.BLACK);

                gridPanel.add(temp);
                cells[i][j] = temp;
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        startStopButton = new JButton("Start");
        startStopButton.addActionListener(e -> toggleSimulation());
        add(startStopButton, BorderLayout.SOUTH);

        timer = new Timer(100, e -> updateGrid());

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void toggleSimulation() {
        if (running) {
            timer.stop();
            startStopButton.setText("Start");
        } else {
            timer.start();
            startStopButton.setText("Stop");
        }
        running = !running;
    }

    void updateGrid() {
        boolean[][] temp = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int count = countNeighbours(i, j);

                if (cellsMap[i][j]) {
                    temp[i][j] = count == 2 || count == 3;
                } else {
                    temp[i][j] = count == 3;
                }
            }
        }

        cellsMap = temp;
        refreshGrid();
    }

    void refreshGrid() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (cellsMap[i][j]) {
                    cells[i][j].setBackground(getRandomColor());
                } else {
                    cells[i][j].setBackground(Color.BLACK);
                }
            }
        }
    }

    int countNeighbours(int x, int y) {
        int count = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < size && j >= 0 && j < size) {
                    if (cellsMap[i][j]) {
                        count++;
                    }
                }
            }
        }

        if (cellsMap[x][y]) {
            count--;
        }

        return count;
    }

    Color getRandomColor() {
        Random rnd = new Random();
        return new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public static void main(String[] args) {
        new GameOfLife();
    }
}
