package br.ufrgs.dunjeonsdragons.ui;

import br.ufrgs.dunjeonsdragons.GameEntityFactory;
import br.ufrgs.dunjeonsdragons.model.GamePlayer;
import br.ufrgs.dunjeonsdragons.template.PlayerClassTemplate;
import br.ufrgs.dunjeonsdragons.template.PlayerRaceTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;

public class CreatePlayerDialog extends JDialog {

    /**
     * The game entity factory
     */
    private final GameEntityFactory factory;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;

    public CreatePlayerDialog(final GameEntityFactory factory) {
        this.factory = factory;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        comboBox1.setModel(new DefaultComboBoxModel<PlayerRaceTemplate>(new PlayerRaceTemplate[]{
                (PlayerRaceTemplate) factory.loader.load("HUMAN"),
                (PlayerRaceTemplate) factory.loader.load("ORC"),
                (PlayerRaceTemplate) factory.loader.load("ELF")
        }));
        comboBox1.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                final PlayerRaceTemplate classTemplate = (PlayerRaceTemplate) value;
                JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);
                if (value != null) {
                    renderer.setText(classTemplate.getName());
                }
                return renderer;
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final PlayerRaceTemplate raceTemplate = (PlayerRaceTemplate) comboBox1.getSelectedItem();

                PlayerClassTemplate[] array = new PlayerClassTemplate[raceTemplate.getStartingClasses().size()];
                raceTemplate.getStartingClasses().toArray(array);

                comboBox2.setModel(new DefaultComboBoxModel<PlayerClassTemplate>(array));
            }
        });
        comboBox1.setSelectedIndex(0);

        comboBox2.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                final PlayerClassTemplate classTemplate = (PlayerClassTemplate) value;
                JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);
                if (value != null) {
                    renderer.setText(classTemplate.getName());
                }
                return renderer;
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public GamePlayer createPlayer() {
        final GamePlayer player = factory.createPlayer(
                ((PlayerRaceTemplate) comboBox1.getSelectedItem()).getIdentifier(),
                ((PlayerClassTemplate) comboBox2.getSelectedItem()).getIdentifier(),
                factory.loader.getExperienceTable()
        );
        player.setName(textField1.getText());
        return player;
    }
}
