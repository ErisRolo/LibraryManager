package LMS.Administrator;

import LMS.Main.MainActivity;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by guohouxiao on 2017/6/10.
 * 图书信息管理界面
 */
public class AdminBookActivity extends javax.swing.JFrame {

    private javax.swing.JButton addBookBtn;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton deleteBookBtn;
    private javax.swing.JButton returnBtn;
    private javax.swing.JButton exitBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable bookTable;
    private javax.swing.JTextArea bookTypeNumArea;
    private javax.swing.JTextArea bookInfoArea;

    private Statement statement;

    public static final int ROWNUM = 100000;

    private int bookTypeNum;

    public AdminBookActivity(Statement statement) {
        this.statement = statement;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        bookTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        bookTypeNumArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        bookInfoArea = new javax.swing.JTextArea();
        addBookBtn = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        deleteBookBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        //图书表格的建立
        refreshBookTable();
        bookTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectRow = bookTable.getSelectedRow();//获取用户所选的行
                String[] bookTabInfo = new String[8];
                String bookInfo = null;
                for (int i = 0; i < 7 ;i++) {
                    bookTabInfo[i] = (String) bookTable.getValueAt(selectRow,i);
                }
                bookInfo = "编号：" + bookTabInfo[0] + "\n"
                        + "书名：" + bookTabInfo[1] + "\n"
                        + "作者：" + bookTabInfo[2] + "\n"
                        + "出版社：" + bookTabInfo[3] + "\n"
                        + "类别：" + bookTabInfo[4] + "\n"
                        + "库存量：" + bookTabInfo[5] + "\n"
                        + "借出量：" + bookTabInfo[6];
                bookInfoArea.setText(bookInfo);
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        jScrollPane1.setViewportView(bookTable);

        /**
         * 图书类别数量显示区域
         */
        try {
            ResultSet notRowNum = statement.executeQuery("SELECT count(*) FROM book");
            if (notRowNum.next()) {
                bookTypeNum = notRowNum.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String notRowNums = "当前入库的图书的类别有 " + bookTypeNum + " 种";
        bookTypeNumArea.setText(notRowNums);
        bookTypeNumArea.setColumns(20);
        bookTypeNumArea.setRows(5);
        jScrollPane2.setViewportView(bookTypeNumArea);

        bookInfoArea.setColumns(20);
        bookInfoArea.setRows(5);
        jScrollPane3.setViewportView(bookInfoArea);

        addBookBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        addBookBtn.setText("添加图书");
        addBookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddBookUtil addBookUtil = new AddBookUtil(statement);
                addBookUtil.setVisible(true);
                addBookUtil.okBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String num = addBookUtil.numField.getText();
                        String name = addBookUtil.nameField.getText();
                        String author = addBookUtil.authorField.getText();
                        String press = addBookUtil.pressField.getText();
                        String type = addBookUtil.typeField.getText();
                        String stock = addBookUtil.stockField.getText();
                        String lend = addBookUtil.lendField.getText();

                        ResultSet resultSet = null;

                        try {
                            resultSet = statement.executeQuery("SELECT * FROM book WHERE Bno=" + "'" + num + "'");
                            if (resultSet.next()) {
                                JOptionPane.showMessageDialog(addBookUtil,"该编号已存在！");
                            } else {
                                statement.execute("INSERT INTO book(Bno,Bname,Bauthor,Bpress,Btype,Bstocknum,Blendnum) VALUES ("
                                        + "'" + num + "'" + ","
                                        + "'" + name + "'" + ","
                                        + "'" + author + "'" + ","
                                        + "'" + press + "'" + ","
                                        + "'" + type + "'" + ","
                                        + "'" + stock + "'" + ","
                                        + "'" + lend + "'" + ")");
                                JOptionPane.showMessageDialog(addBookUtil,"添加成功！");
                                addBookUtil.setVisible(false);
                                bookTypeNum++;
                                String notRowNums = "当前入库的图书的类别有 " + bookTypeNum + " 种";
                                bookTypeNumArea.setText(notRowNums);
                                refreshBookTable();
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
            }
        });

        jButton2.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jButton2.setText("修改图书");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                ModifyBookUtil modifyBookUtil = new ModifyBookUtil(statement);

                int selectRow = bookTable.getSelectedRow();//获取用户所选的行
                String[] bookTabInfo = new String[8];
                for (int i = 0; i < 7 ;i++) {
                    bookTabInfo[i] = (String) bookTable.getValueAt(selectRow,i);
                }
                modifyBookUtil.numField.setText(bookTabInfo[0]);
                modifyBookUtil.numField.setEditable(false);
                modifyBookUtil.nameField.setText(bookTabInfo[1]);
                modifyBookUtil.authorField.setText(bookTabInfo[2]);
                modifyBookUtil.pressField.setText(bookTabInfo[3]);
                modifyBookUtil.typeField.setText(bookTabInfo[4]);
                modifyBookUtil.stockField.setText(bookTabInfo[5]);
                modifyBookUtil.lendField.setText(bookTabInfo[6]);

                modifyBookUtil.setVisible(true);

                modifyBookUtil.okBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String num = modifyBookUtil.numField.getText();
                        String name = modifyBookUtil.nameField.getText();
                        String author = modifyBookUtil.authorField.getText();
                        String press = modifyBookUtil.pressField.getText();
                        String type = modifyBookUtil.typeField.getText();
                        String stock = modifyBookUtil.stockField.getText();
                        String lend = modifyBookUtil.lendField.getText();

                        try {
                            statement.execute("UPDATE book SET Bname=" + "'" + name + "'" + ","
                                    + "Bauthor = " + "'" + author + "'" + ","
                                    + "Bpress = " + "'" + press + "'" + ","
                                    + "Btype = " + "'" + type + "'" + ","
                                    + "Bstocknum = " + "'" + stock + "'" + ","
                                    + "Blendnum = " + "'" + lend + "'"
                                    + "WHERE Bno=" + "'" + num + "'" );
                            JOptionPane.showMessageDialog(modifyBookUtil,"修改成功！");
                            modifyBookUtil.setVisible(false);
                            String notRowNums = "当前入库的图书的类别有 " + bookTypeNum + " 种";
                            bookTypeNumArea.setText(notRowNums);
                            refreshBookTable();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }

                });
            }
        });

        deleteBookBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        deleteBookBtn.setText("删除图书");
        deleteBookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectRow = bookTable.getSelectedRow();
                String selectNo = (String) bookTable.getValueAt(selectRow,0);

                try {
                    statement.execute("DELETE FROM book WHERE Bno =" + "'" + selectNo + "'");

                    bookTypeNum--;
                    String notRowNums = "当前入库的图书的类别有 " + bookTypeNum + " 种";
                    bookTypeNumArea.setText(notRowNums);
                    refreshBookTable();

                    JOptionPane.showMessageDialog(AdminBookActivity.this,"已删除该图书的记录！");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        returnBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        returnBtn.setText("返回上层");
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminItemActivity(statement).setVisible(true);
                AdminBookActivity.this.setVisible(false);
            }
        });

        exitBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        exitBtn.setText("退出系统");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new MainActivity(statement).setVisible(true);
                AdminBookActivity.this.setVisible(false);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addComponent(addBookBtn)
                                                .addGap(42, 42, 42)
                                                .addComponent(jButton2)
                                                .addGap(46, 46, 46)
                                                .addComponent(deleteBookBtn)))
                                .addGap(81, 81, 81)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                                .addComponent(jScrollPane3))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(9, 9, 9)
                                                .addComponent(returnBtn)
                                                .addGap(18, 18, 18)
                                                .addComponent(exitBtn)))
                                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addBookBtn)
                                        .addComponent(jButton2)
                                        .addComponent(deleteBookBtn)
                                        .addComponent(returnBtn)
                                        .addComponent(exitBtn))
                                .addGap(0, 30, Short.MAX_VALUE))
        );

        pack();
    }

    private Object[][] getTableData(int columnNum) {

        Object[][] objects = new Object[ROWNUM][columnNum];
        int rowNum = 0;
        ResultSet resultSet = null;

        try {
            resultSet = statement.executeQuery("SELECT * FROM book");
            while (resultSet.next()) {
                for (int i = 1; i <= columnNum; i++){
                    objects[rowNum][i-1] = resultSet.getObject(i);
                }
                rowNum++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] data = new Object[rowNum][columnNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                data[i][j] = objects[i][j];
            }
        }

        return data;
    }

    //图书表的建立与刷新
    private void refreshBookTable() {
        int columnNum = 7;
        Object[][] data = getTableData(columnNum);
        String[] columnName = new String[] {"编号", "书名", "作者", "出版社", "类别", "库存量", "借出量"};
        bookTable.setModel(new javax.swing.table.DefaultTableModel(data, columnName));
    }

}

