package tool;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JavaTool extends JFrame {
    private JTextArea textArea;
    private JButton runButton;
    private JButton saveButton;
    private File inputFile = new File("input.txt");

    public JavaTool() {
        setTitle("Java Runner Tool");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        runButton = new JButton("Run");
        saveButton = new JButton("Save");
        panel.add(saveButton);
        panel.add(runButton);
        add(panel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> saveToFile());
        runButton.addActionListener(e -> runJavaProgram());

        setVisible(true);
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write(textArea.getText());
            JOptionPane.showMessageDialog(this, "Saved to input.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void runJavaProgram() {
        try {
            saveToFile();
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "out", "lox.Lox", "input.txt");
            pb.redirectInput(inputFile); // Input from the saved file
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT); // Show output in console
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JavaTool();
    }
}
