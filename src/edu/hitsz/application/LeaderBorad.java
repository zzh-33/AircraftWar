package edu.hitsz.application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderBorad {
    private JPanel MainPanel;
    private JPanel topPanel;
    private JPanel buttonPanel;
    private JButton deleteButton;
    private JButton returnButton;
    private JLabel leaderBoradLabel;
    private JScrollPane leaderBoradPane;
    private JTable leaderBoradTable;

    private Game game;

    public LeaderBorad(Game game) {
        this.game = game;

        DefaultTableModel model = new DefaultTableModel(
                game.gameDao.getAllGameInfoLogs(game.gameInfo.getGameMode()),
                new String[]{"排名", "用户名", "游戏模式", "分数", "游戏时间"}
        );

        leaderBoradTable.setModel(model);
        leaderBoradPane.setViewportView(leaderBoradTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = leaderBoradTable.getSelectedRow();
                System.out.println(row);
                if (row != -1) {
                    int result = JOptionPane.showConfirmDialog(null, "确认删除第" + (row + 1) + "条记录吗？", "确认", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        model.removeRow(row);
                        game.gameDao.deleteGameInfoLog(row);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请选择要删除的记录", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }
}
