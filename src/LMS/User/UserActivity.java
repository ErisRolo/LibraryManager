package LMS.User;

import LMS.Main.MainActivity;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by guohouxiao on 2017/6/9.
 * 用户界面
 */
public class UserActivity extends javax.swing.JFrame {

    private javax.swing.JButton lendBtn;
    private javax.swing.JButton returnBtn;
    private javax.swing.JButton exitBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable recordTable;
    private javax.swing.JTextArea overTimeArea;
    private javax.swing.JTextArea recordInfoArea;

    private Statement statement;
    private String loginAccount;

    public static final int ROWNUM = 100000;

    public UserActivity(Statement statement, String loginAccount) {
        this.statement = statement;
        this.loginAccount = loginAccount;
        initComponents();
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        recordTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        overTimeArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        recordInfoArea = new javax.swing.JTextArea();
        lendBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        refreshRecordTable();
        recordTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectRow = recordTable.getSelectedRow();//获取用户所选的行
                String[] recordInfo = new String[7];
                String isInfo = null;
                for (int i = 0; i < 7 ;i++) {
                    if (recordTable.getValueAt(selectRow,i)==null){
                        recordInfo[i] = "";
                    }else {
                        recordInfo[i] = recordTable.getValueAt(selectRow,i).toString();
                    }
                }
                isInfo = "记录编号：" + recordInfo[0] + "\n"
                        + "图书编号：" + recordInfo[1] + "\n"
                        + "图书名称：" + recordInfo[2] + "\n"
                        + "借出日期：" + recordInfo[3] + "\n"
                        + "约定借阅时长：" + recordInfo[4] + "天" +"\n"
                        + "归还日期：" + recordInfo[5] + "\n"
                        + "是否归还：" + recordInfo[6];
                recordInfoArea.setText(isInfo);
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
        recordInfoArea.setEditable(false);
        jScrollPane1.setViewportView(recordTable);

        refreshOverTime();
        overTimeArea.setColumns(20);
        overTimeArea.setRows(5);
        overTimeArea.setEditable(false);
        jScrollPane2.setViewportView(overTimeArea);

        recordInfoArea.setColumns(20);
        recordInfoArea.setRows(5);
        jScrollPane3.setViewportView(recordInfoArea);

        lendBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        lendBtn.setText("借阅图书");
        lendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LendBookUtil lendBookUtil = new LendBookUtil(statement);
                lendBookUtil.setVisible(true);
                lendBookUtil.okBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String recordNum = lendBookUtil.recordField.getText();//获得记录编号
                        String userNum = loginAccount;
                        String bookNum = lendBookUtil.bookField.getText();
                        String lendDateString = lendBookUtil.dateField.getText();
                        Date lendDate = stringToDate(lendDateString);
                        String lendTime= lendBookUtil.timeField.getText();
                        Boolean isreturn = false;

                        ResultSet resultSet = null;
                        try {
                            resultSet = statement.executeQuery("SELECT * FROM record WHERE Rno=" + " '" + recordNum + "'");
                            if (resultSet.next()) {
                                JOptionPane.showMessageDialog(lendBookUtil,"该编号已存在！");
                            } else {
                                ResultSet stockSet = statement.executeQuery("SELECT Bstocknum FROM book WHERE Bno=" + "'" + bookNum + "'");
                                if (stockSet.next()) {
                                    String oldStockString = stockSet.getString(1);
                                    int oldStockNum = Integer.parseInt(oldStockString);
                                    if (oldStockNum > 0) {
                                        int newStockNum = oldStockNum - 1;
                                        String newStockString = newStockNum + "";
                                        statement.execute("UPDATE book SET Bstocknum=" + "'" + newStockString + "'" + "WHERE Bno=" + "'" + bookNum + "'");
                                        ResultSet lendSet = statement.executeQuery("SELECT Blendnum FROM book WHERE Bno=" + "'" + bookNum + "'");
                                        if (lendSet.next()) {
                                            String oldLendString = lendSet.getString(1);
                                            int oldLendNum = Integer.parseInt(oldLendString);
                                            int newLendNum = oldLendNum + 1;
                                            String newLendString = newLendNum + "";
                                            statement.execute("UPDATE book SET Blendnum=" + "'" + newLendString + "'");
                                            statement.execute("INSERT INTO record(Rno,Uno,Bno,Rlenddate,Rlendtime,Risreturn) VALUES ("
                                                    + "'" + recordNum + "'" + ","
                                                    + "'" + userNum + "'" + ","
                                                    + "'" + bookNum + "'" + ","
                                                    + "'" + lendDate + "'" + ","
                                                    + "'" + lendTime + "'" + ","
                                                    + isreturn + ")");
                                            JOptionPane.showMessageDialog(lendBookUtil,"登记成功！");
                                            refreshRecordTable();
                                            refreshOverTime();
                                            lendBookUtil.setVisible(false);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(lendBookUtil,"该图书已没有库存！");
                                    }
                                }
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
                lendBookUtil.cancelBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lendBookUtil.setVisible(false);
                    }
                });
            }
        });

        returnBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        returnBtn.setText("归还图书");
        returnBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReturnBookUtil returnBookUtil = new ReturnBookUtil(statement);

                int selectRow = recordTable.getSelectedRow();
                String[] recordTabInfo = new String[2];
                for (int i = 0; i < 2; i++) {
                    recordTabInfo[i] = (String) recordTable.getValueAt(selectRow,i);
                }
                returnBookUtil.recordField.setText(recordTabInfo[0]);
                returnBookUtil.bookField.setText(recordTabInfo[1]);
                returnBookUtil.setVisible(true);

                returnBookUtil.okBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String recordNum = returnBookUtil.recordField.getText();
                        String bookNum = returnBookUtil.bookField.getText();
                        String returnString = returnBookUtil.returnDateField.getText();
                        Date returnDate = stringToDate(returnString);
                        boolean isreturn = true;
                        try {
                            System.out.println("UPDATE record SET Rreturndate=" + "'" + returnDate + "'" + ","
                                    + "Risreturn=" + isreturn + "WHERE Rno=" + "'" + recordNum + "'");
                            statement.execute("UPDATE record SET Rreturndate=" + "'" + returnDate + "'" + ","
                                    + "Risreturn=" + isreturn + " WHERE Rno=" + "'" + recordNum + "'");
                            ResultSet oldStockSet = statement.executeQuery("SELECT Bstocknum FROM book WHERE Bno=" + "'" + bookNum + "'");
                            if (oldStockSet.next()) {
                                String oldStockString = oldStockSet.getString(1);
                                int oldStockNum = Integer.parseInt(oldStockString);
                                int newStockNum = oldStockNum + 1;
                                String newStockString = newStockNum + "";
                                statement.execute("UPDATE book SET Bstocknum=" + "'" + newStockString + "'" + "WHERE Bno=" + "'" + bookNum + "'");
                                ResultSet oldLendSet = statement.executeQuery("SELECT Blendnum FROM book WHERE Bno=" + "'" + bookNum + "'");
                                if (oldLendSet.next()) {
                                    String oldLendString = oldLendSet.getString(1);
                                    int oldLendNum = Integer.parseInt(oldLendString);
                                    int newLendNum = oldLendNum - 1;
                                    String newLendString = newLendNum + "";
                                    statement.execute("UPDATE book SET Blendnum=" + "'" + newLendString + "'" + "WHERE Bno=" + "'" + bookNum + "'");
                                    JOptionPane.showMessageDialog(returnBookUtil,"登记成功！");
                                    refreshRecordTable();
                                    refreshOverTime();
                                    returnBookUtil.setVisible(false);
                                }
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                returnBookUtil.cancelBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        returnBookUtil.setVisible(false);
                    }
                });
            }
        });

        exitBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        exitBtn.setText("退出系统");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new MainActivity(statement).setVisible(true);
                UserActivity.this.setVisible(false);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(75, 75, 75)
                                                .addComponent(lendBtn)
                                                .addGap(81, 81, 81)
                                                .addComponent(returnBtn))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(60, 60, 60)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane2))
                                                .addGap(41, 41, 41))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(exitBtn)
                                                .addGap(103, 103, 103))))
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
                                        .addComponent(lendBtn)
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
        Boolean isreturned = false;

        try {
            resultSet = statement.executeQuery("SELECT Rno,record.Bno,Bname,Rlenddate,Rlendtime,Rreturndate,Risreturn FROM record,book WHERE record.Bno=book.Bno AND Uno=" + "'" + loginAccount + "'");
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
            if (data[i][columnNum-1].toString().equals("0")) {
                isreturned = false;
            } else {
                isreturned = true;
            }
            if (isreturned) {
                data[i][columnNum - 1] = "已归还";
            } else {
                data[i][columnNum - 1] = "未归还";
            }
        }

        return data;
    }

    private void refreshRecordTable() {
        int columnNum = 7;
        Object[][] data = getTableData(columnNum);
        String[] columnName = new String[] {"记录编号", "图书编号", "图书名称", "借出日期", "约定借阅时长", "归还日期", "是否归还"};
        recordTable.setModel(new javax.swing.table.DefaultTableModel(data, columnName));
    }

    private Date stringToDate(String string) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(string);//util的date类型
            date2 = new Date(date1.getTime());//sql中的date类型
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return date2;
    }

    //刷新超时提醒
    private void refreshOverTime() {
        //获得当前用户姓名
        String userName = null;
        try {
            ResultSet name = statement.executeQuery("SELECT Uname FROM user WHERE Uno=" + "'" + loginAccount + "'");
            if (name.next()) {
                userName = name.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //获得超期记录
        String overTimeRecord = "";
        int tableRows = recordTable.getRowCount();
        for (int i = 0; i < tableRows; i++) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String isreturned = recordTable.getValueAt(i,6).toString();
                if (isreturned.equals("已归还")) {
                    java.util.Date returndate = format.parse(recordTable.getValueAt(i,5).toString());
                    java.util.Date lenddate = format.parse(recordTable.getValueAt(i,3).toString());
                    long day = (returndate.getTime() - lenddate.getTime())/(24*60*60*1000);//实际借阅时间
                    long lendDay = Long.parseLong(recordTable.getValueAt(i,4).toString());//约定借阅时间
                    if (day > lendDay) {
                        overTimeRecord = overTimeRecord + recordTable.getValueAt(i,0).toString() + "  ";
                    }
                }
                if (isreturned.equals("未归还")){
                    java.util.Date nowdate = new java.util.Date();//当前时间
                    java.util.Date lenddate = format.parse(recordTable.getValueAt(i,3).toString());
                    long day = (nowdate.getTime() - lenddate.getTime())/(24*60*60*1000);
                    long lendDay = Long.parseLong(recordTable.getValueAt(i,4).toString());
                    if (day > lendDay) {
                        overTimeRecord = overTimeRecord + recordTable.getValueAt(i,0).toString() + "  ";
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        String overRecord = "当前的用户为：" + userName + "\n" +"超时的记录编号有：" + overTimeRecord + "\n" +"请尽快归还图书！";
        overTimeArea.setText(overRecord);
    }

}
