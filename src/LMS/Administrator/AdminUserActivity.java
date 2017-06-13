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
 * 用户信息管理界面
 */
public class AdminUserActivity extends javax.swing.JFrame {

    private javax.swing.JButton passBtn;
    private javax.swing.JButton refuseBtn;
    private javax.swing.JButton returnBtn1;
    private javax.swing.JButton exitBtn1;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton returnBtn2;
    private javax.swing.JButton exitBtn2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable notexamTab;
    private javax.swing.JTable isexamTab;
    private javax.swing.JTextArea notexamNumArea;
    private javax.swing.JTextArea notexamInfoArea;
    private javax.swing.JTextArea isexamNumArea;
    private javax.swing.JTextArea isexamInfoArea;

    private Statement statement;

    private int notrownum;//标记未审核数量
    private int isrownum;//标记已审核数量

    public static final int ROWNUM = 100000;

    public AdminUserActivity(Statement statement) {
        this.statement = statement;
        initComponents();
    }


    @SuppressWarnings("unchecked")
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        notexamTab = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        notexamNumArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        notexamInfoArea = new javax.swing.JTextArea();
        passBtn = new javax.swing.JButton();
        refuseBtn = new javax.swing.JButton();
        returnBtn1 = new javax.swing.JButton();
        exitBtn1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        isexamTab = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        isexamNumArea = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        isexamInfoArea = new javax.swing.JTextArea();
        deleteBtn = new javax.swing.JButton();
        returnBtn2 = new javax.swing.JButton();
        exitBtn2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N

        /**
         * 未审核表的建立
         */
        refreshNotTable();
        notexamTab.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectRow = notexamTab.getSelectedRow();//获取用户所选的行
                String[] notexamInfo = new String[8];
                String notInfo = null;
                for (int i = 0; i < 8 ;i++) {
                    notexamInfo[i] = (String) notexamTab.getValueAt(selectRow,i);
                }
                notInfo = "账号：" + notexamInfo[0] + "\n"
                        + "姓名：" + notexamInfo[1] + "\n"
                        + "性别：" + notexamInfo[2] + "\n"
                        + "班级：" + notexamInfo[3] + "\n"
                        + "院系：" + notexamInfo[4] + "\n"
                        + "密码：" + notexamInfo[5] + "\n"
                        + "电话：" + notexamInfo[6] + "\n"
                        + "审核情况：" + notexamInfo[7];
                notexamInfoArea.setText(notInfo);
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
        jScrollPane1.setViewportView(notexamTab);

        /**
         * 未审核用户数量显示区域
         */
        try {
            ResultSet notRowNum = statement.executeQuery("SELECT count(*) FROM user WHERE Uisexamed = 0");
            if (notRowNum.next()) {
                notrownum = notRowNum.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String notRowNums = "当前未审核用户的数量为：" + notrownum;
        notexamNumArea.setText(notRowNums);
        notexamNumArea.setColumns(20);
        notexamNumArea.setRows(5);
        notexamNumArea.setEditable(false);
        jScrollPane2.setViewportView(notexamNumArea);

        notexamInfoArea.setColumns(20);
        notexamInfoArea.setRows(5);
        notexamInfoArea.setEditable(false);
        jScrollPane3.setViewportView(notexamInfoArea);

        passBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        passBtn.setText("审核通过");
        passBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectRow = notexamTab.getSelectedRow();
                String selectNo = (String) notexamTab.getValueAt(selectRow,0);
                try {
                    statement.execute("UPDATE user SET Uisexamed = 1 WHERE Uno = " + "'" + selectNo + "'");

                    notrownum--;
                    String notRowNums = "当前未审核用户的数量为：" + notrownum;
                    notexamNumArea.setText(notRowNums);

                    isrownum++;
                    String isRowNums = "当前已审核用户的数量为：" + isrownum;
                    isexamNumArea.setText(isRowNums);

                    refreshNotTable();
                    refreshIsTable();

                    JOptionPane.showMessageDialog(AdminUserActivity.this,"审核成功！");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        refuseBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        refuseBtn.setText("拒绝注册");
        refuseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectRow = notexamTab.getSelectedRow();
                String selectNo = (String) notexamTab.getValueAt(selectRow,0);

                try {
                    statement.execute("DELETE FROM user WHERE Uno =" + "'" + selectNo + "'");

                    notrownum--;
                    String notRowNums = "当前未审核用户的数量为：" + notrownum;
                    notexamNumArea.setText(notRowNums);

                    String isRowNums = "当前已审核用户的数量为：" + isrownum;
                    isexamNumArea.setText(isRowNums);

                    refreshNotTable();
                    refreshIsTable();

                    JOptionPane.showMessageDialog(AdminUserActivity.this,"已拒绝该用户的申请！");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        returnBtn1.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        returnBtn1.setText("返回上层");
        returnBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminItemActivity(statement).setVisible(true);
                AdminUserActivity.this.setVisible(false);
            }
        });

        exitBtn1.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        exitBtn1.setText("退出系统");
        exitBtn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainActivity(statement).setVisible(true);
                AdminUserActivity.this.setVisible(false);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(81, 81, 81)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane3)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(80, 80, 80)
                                                .addComponent(passBtn)
                                                .addGap(66, 66, 66)
                                                .addComponent(refuseBtn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(returnBtn1)
                                                .addGap(14, 14, 14)
                                                .addComponent(exitBtn1)
                                                .addGap(10, 10, 10)))
                                .addGap(56, 56, 56))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane3)))
                                .addGap(54, 54, 54)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(passBtn)
                                        .addComponent(returnBtn1)
                                        .addComponent(exitBtn1)
                                        .addComponent(refuseBtn))
                                .addGap(0, 41, Short.MAX_VALUE))
        );
        jTabbedPane1.addTab("待审核用户", jPanel1);


        /**
         * 审核表的建立
         */
        refreshIsTable();
        isexamTab.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectRow = isexamTab.getSelectedRow();//获取用户所选的行
                String[] isexamInfo = new String[8];
                String isInfo = null;
                for (int i = 0; i < 8 ;i++) {
                    isexamInfo[i] = (String) isexamTab.getValueAt(selectRow,i);
                }
                isInfo = "账号：" + isexamInfo[0] + "\n"
                        + "姓名：" + isexamInfo[1] + "\n"
                        + "性别：" + isexamInfo[2] + "\n"
                        + "班级：" + isexamInfo[3] + "\n"
                        + "院系：" + isexamInfo[4] + "\n"
                        + "密码：" + isexamInfo[5] + "\n"
                        + "电话：" + isexamInfo[6] + "\n"
                        + "审核情况：" + isexamInfo[7];
                isexamInfoArea.setText(isInfo);
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
        jScrollPane4.setViewportView(isexamTab);

        /**
         * 已审核用户数量显示区域
         */
        try {
            ResultSet isRowNum = statement.executeQuery("SELECT count(*) FROM user WHERE Uisexamed = 1");
            if (isRowNum.next()) {
                isrownum = isRowNum.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String isRowNums = "当前已审核用户的数量为：" + isrownum;
        isexamNumArea.setText(isRowNums);
        isexamNumArea.setColumns(20);
        isexamNumArea.setRows(5);
        jScrollPane5.setViewportView(isexamNumArea);

        isexamInfoArea.setColumns(20);
        isexamInfoArea.setRows(5);
        jScrollPane6.setViewportView(isexamInfoArea);

        deleteBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        deleteBtn.setText("删除用户");
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectRow = isexamTab.getSelectedRow();
                String selectNo = (String) isexamTab.getValueAt(selectRow,0);

                try {
                    statement.execute("DELETE FROM user WHERE Uno =" + "'" + selectNo + "'");

                    String notRowNums = "当前未审核用户的数量为：" + notrownum;
                    notexamNumArea.setText(notRowNums);

                    isrownum--;
                    String isRowNums = "当前已审核用户的数量为：" + isrownum;
                    isexamNumArea.setText(isRowNums);

                    refreshNotTable();
                    refreshIsTable();

                    JOptionPane.showMessageDialog(AdminUserActivity.this,"已取消该用户的权限！");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        returnBtn2.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        returnBtn2.setText("返回上层");
        returnBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminItemActivity(statement).setVisible(true);
                AdminUserActivity.this.setVisible(false);
            }
        });

        exitBtn2.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        exitBtn2.setText("退出系统");
        exitBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainActivity(statement).setVisible(true);
                AdminUserActivity.this.setVisible(false);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(162, 162, 162)
                                                .addComponent(deleteBtn)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(returnBtn2)
                                                .addGap(14, 14, 14)
                                                .addComponent(exitBtn2)
                                                .addGap(66, 66, 66))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(81, 81, 81)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane5))
                                                .addGap(56, 56, 56))))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane6)))
                                .addGap(54, 54, 54)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(deleteBtn)
                                        .addComponent(returnBtn2)
                                        .addComponent(exitBtn2))
                                .addGap(41, 41, 41))
        );

        jTabbedPane1.addTab("已审核用户", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );

        pack();
    }

    //审核表与未审核表的封装方法
    private Object[][] getTableData(String sql, int columnNum, boolean isexamed){

        Object[][] objects = new Object[ROWNUM][columnNum];
        int rowNum = 0;
        ResultSet resultSet = null;

        try {
            resultSet = statement.executeQuery(sql);
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

        for (int i = 0; i < rowNum; i++) {
            if (isexamed) {
                data[i][columnNum - 1] = "已审核";
            } else {
                data[i][columnNum - 1] = "未审核";
            }
        }

        return data;
    }

    //刷新未审核表格
    private void refreshNotTable() {
        String sql = "SELECT * FROM user WHERE Uisexamed = 0";
        int columnNum = 8;
        boolean isexamed = false;
        Object[][] data = getTableData(sql, columnNum, isexamed);
        String[] columnName = new String[]{"账号", "姓名", "性别", "班级", "院系", "密码", "电话", "审核"};
        notexamTab.setModel(new javax.swing.table.DefaultTableModel(data,columnName));
    }

    //刷新已审核表格
    private void refreshIsTable() {
        String sql = "SELECT * FROM user WHERE Uisexamed = 1";
        int columnNum = 8;
        boolean isexamed = true;
        Object[][] data = getTableData(sql, columnNum, isexamed);
        String[] columnName = new String[]{"账号", "姓名", "性别", "班级", "院系", "密码", "电话", "审核"};
        isexamTab.setModel(new javax.swing.table.DefaultTableModel(data, columnName));
    }

}