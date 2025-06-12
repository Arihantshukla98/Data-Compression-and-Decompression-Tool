package GUI;

import comp_decomp.compressor;
import comp_decomp.decompressor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

public class AppFrame extends JFrame implements ActionListener {
    RoundGradientButton selectFileButton;
    ModeToggleButton darkModeToggle;
    boolean isDarkMode = true; // Start in dark mode
    JPanel backgroundPanel;
    JPanel cardPanel;

    // 3D/multi-gradient colors
    Color[] lightColors = {
            new Color(245, 248, 255), // fresh blueish-white
            new Color(220, 230, 250), // light blue
            new Color(200, 215, 245)  // little deeper blue
    };
    Color[] darkColors = {
            new Color(30, 30, 45),
            new Color(40, 45, 60),
            new Color(50, 55, 80)
    };

    public AppFrame() {
        setTitle("DataSankuchak - File Compressor & Decompressor");
        setSize(800, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 3D multi-gradient background
        backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth(), h = getHeight();

                // First gradient (vertical)
                GradientPaint gp1 = new GradientPaint(0, 0,
                        isDarkMode ? darkColors[0] : lightColors[0],
                        0, h,
                        isDarkMode ? darkColors[1] : lightColors[1]);
                g2d.setPaint(gp1);
                g2d.fillRect(0, 0, w, h);

                // Second gradient (diagonal overlay for 3D effect)
                GradientPaint gp2 = new GradientPaint(0, 0,
                        new Color(255,255,255, isDarkMode ? 10 : 70),
                        w, h,
                        isDarkMode ? darkColors[2] : lightColors[2]);
                g2d.setPaint(gp2);
                g2d.fillRect(0, 0, w, h);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());


        cardPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Shadow
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.fillRoundRect(8, 8, getWidth() - 16, getHeight() - 16, 38, 38);
                // Card
                g2d.setColor(isDarkMode ? new Color(38, 38, 54, 230) : Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth() - 8, getHeight() - 8, 32, 32);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("DataSankuchak");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(isDarkMode ? new Color(220, 220, 240) : new Color(30, 30, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tagline = new JLabel("Compress & Decompress Files Elegantly");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tagline.setForeground(isDarkMode ? new Color(180, 180, 200) : new Color(80, 80, 80));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel description = new JLabel("<html><div style='text-align: center;'>"
                + "Easily reduce your file size or extract data with just a few clicks.<br>"
                + "Built with simplicity, performance, and elegance in mind."
                + "</div></html>");
        description.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        description.setForeground(isDarkMode ? new Color(150, 150, 170) : new Color(60, 60, 60));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setBorder(BorderFactory.createEmptyBorder(15, 0, 20, 0));

        // Modern rounded gradient button (no icon, no square)
        selectFileButton = new RoundGradientButton("Select File");
        selectFileButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        selectFileButton.setPreferredSize(new Dimension(200, 50));
        selectFileButton.setMaximumSize(new Dimension(220, 50));
        selectFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectFileButton.setFocusPainted(false);
        selectFileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        selectFileButton.setBackground(new Color(70, 130, 180));
        selectFileButton.addActionListener(this);

        // Modern round toggle button (sun/moon emoji, no square)
        darkModeToggle = new ModeToggleButton();
        darkModeToggle.setPreferredSize(new Dimension(48, 48));
        darkModeToggle.setFocusPainted(false);
        darkModeToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        darkModeToggle.setToolTipText("Toggle Dark/Light Mode");
        darkModeToggle.setSelected(true); // Start in dark mode

        darkModeToggle.addActionListener(e -> {
            isDarkMode = darkModeToggle.isSelected();
            title.setForeground(isDarkMode ? new Color(220, 220, 240) : new Color(30, 30, 30));
            tagline.setForeground(isDarkMode ? new Color(180, 180, 200) : new Color(80, 80, 80));
            description.setForeground(isDarkMode ? new Color(150, 150, 170) : new Color(60, 60, 60));
            repaint();
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(darkModeToggle);

        add(topPanel, BorderLayout.NORTH);

        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(title);
        cardPanel.add(Box.createVerticalStrut(8));
        cardPanel.add(tagline);
        cardPanel.add(description);
        cardPanel.add(Box.createVerticalStrut(16));
        cardPanel.add(selectFileButton);

        backgroundPanel.add(cardPanel);
        add(backgroundPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a file to compress or decompress");

        int response = fileChooser.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String[] options = {"Compress", "Decompress"};

            int choice = JOptionPane.showOptionDialog(this,
                    "What do you want to do with the selected file?",
                    "Choose Operation",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            try {
                if (choice == 0) {
                    long originalSize = file.length();
                    File compressedFile = compressor.compressIt(file);
                    long compressedSize = compressedFile.length();
                    double percent = 100.0 * (1.0 - ((double) compressedSize / originalSize));
                    showAnimatedPopup("✅ File compressed successfully!", compressedFile, originalSize, compressedSize, percent);
                } else if (choice == 1) {
                    File output = decompressor.decompressIt(file); // Make sure your decompressIt returns File
                    showDecompressPopup("✅ File decompressed successfully!", output);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showSimplePopup(String message) {
        JDialog dialog = new JDialog(this, "Done", true);
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(30, 150, 30));
        dialog.add(label);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(this);

        Timer timer = new Timer(1500, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }

    private void showAnimatedPopup(String message, File compressedFile, long originalSize, long compressedSize, double percent) {
        JDialog dialog = new JDialog(this, "Compression Result", true);
        dialog.setLayout(new BorderLayout());

        String filePath = compressedFile.getAbsolutePath();
        String sizeInfo = String.format("<html><div style='padding:10px;'>%s<br><br>"
                        + "<b>Original Size:</b> %.2f KB<br>"
                        + "<b>Compressed Size:</b> %.2f KB<br>"
                        + "<b>Compression:</b> %.2f%%<br><br>"
                        + "<a href='#'>Click to open saved file</a>"
                        + "</div></html>",
                message,
                originalSize / 1024.0,
                compressedSize / 1024.0,
                percent);

        JLabel label = new JLabel(sizeInfo);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(20, 120, 20));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().open(compressedFile);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(dialog, "❌ Unable to open file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.add(label, BorderLayout.CENTER);
        dialog.setSize(420, 220);
        dialog.setLocationRelativeTo(this);

        Timer timer = new Timer(5000, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }


    private void showDecompressPopup(String message, File output) {
        JDialog dialog = new JDialog(this, "Decompression Result", true);
        String info = "<html><div style='padding:10px;'>"
                + message + "<br><br>"
                + "<b>Output:</b> " + output.getAbsolutePath()
                + "<br><br><a href='#'>Click to open</a></div></html>";
        JLabel label = new JLabel(info);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(20, 120, 20));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().open(output);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(dialog, "❌ Unable to open file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        dialog.add(label);
        dialog.setSize(420, 180);
        dialog.setLocationRelativeTo(this);
        Timer timer = new Timer(5000, e -> dialog.dispose());
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }

    // --- Modern Rounded Gradient Button ---
    static class RoundGradientButton extends JButton {
        private Color gradientStart = new Color(70, 130, 180);
        private Color gradientEnd = new Color(100, 180, 255);

        public RoundGradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 16));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    gradientStart = new Color(100, 180, 255);
                    gradientEnd = new Color(70, 130, 180);
                    repaint();
                }
                public void mouseExited(MouseEvent e) {
                    gradientStart = new Color(70, 130, 180);
                    gradientEnd = new Color(100, 180, 255);
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, gradientStart, getWidth(), getHeight(), gradientEnd);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);
            g2.setColor(new Color(255, 255, 255, 50));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 32, 32);

            // Centered text
            FontMetrics fm = g2.getFontMetrics();
            int stringWidth = fm.stringWidth(getText());
            int stringHeight = fm.getAscent();
            g2.setColor(getForeground());
            g2.drawString(getText(),
                    (getWidth() - stringWidth) / 2,
                    (getHeight() + stringHeight) / 2 - 4);

            g2.dispose();
        }

        @Override
        public void paintBorder(Graphics g) {
            // No border
        }

        @Override
        public boolean contains(int x, int y) {
            return new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 32, 32).contains(x, y);
        }
    }

    // --- Modern Mode Toggle Button (Sun/Moon, golden bg in light mode, blue bg in dark mode) ---
    static class ModeToggleButton extends JToggleButton {
        public ModeToggleButton() {
            super("☀️");
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
            setForeground(new Color(255, 180, 0));
            setFont(getEmojiFont());
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addChangeListener(e -> {
                if (isSelected()) {
                    setText("🌙");
                    setForeground(new Color(220, 220, 255));
                } else {
                    setText("☀️");
                    setForeground(new Color(255, 180, 0));
                }
                repaint();
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Light/dark mode background for button (based on emoji)
            if (isSelected()) {
                // Dark mode: blueish shadow
                if (getModel().isRollover()) {
                    g2.setColor(new Color(80, 100, 180, 200));
                } else {
                    g2.setColor(new Color(60, 60, 80, 180));
                }
            } else {
                // Light mode: golden/yellow shadow
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255, 220, 100, 200));
                } else {
                    g2.setColor(new Color(255, 240, 170, 180));
                }
            }
            g2.fillOval(0, 0, getWidth(), getHeight());

            // Centered emoji
            FontMetrics fm = g2.getFontMetrics();
            int stringWidth = fm.stringWidth(getText());
            int stringHeight = fm.getAscent();
            g2.setColor(getForeground());
            g2.drawString(getText(),
                    (getWidth() - stringWidth) / 2,
                    (getHeight() + stringHeight) / 2 - 4);

            g2.dispose();
        }

        @Override
        public void paintBorder(Graphics g) {
            // No border
        }

        @Override
        public boolean contains(int x, int y) {
            Ellipse2D shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
            return shape.contains(x, y);
        }

        // Emoji font fallback (Windows, Mac, Linux)
        private static Font getEmojiFont() {
            String[] emojiFonts = {
                    "Segoe UI Emoji", // Windows
                    "Apple Color Emoji", // Mac
                    "Noto Color Emoji", // Linux
                    "Segoe UI Symbol", // Windows fallback
                    "Arial Unicode MS" // Generic fallback
            };
            for (String fontName : emojiFonts) {
                Font font = new Font(fontName, Font.PLAIN, 22);
                if (font.canDisplay("🌙".codePointAt(0)) && font.canDisplay("☀".codePointAt(0))) {
                    return font.deriveFont(Font.PLAIN, 22f);
                }
            }
            return new Font("SansSerif", Font.PLAIN, 22);
        }
    }
}
