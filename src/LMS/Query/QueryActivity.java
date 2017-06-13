package LMS.Query;

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
 * Created by guohouxiao on 2017/6/9.
 * 图书查询界面
 */
public class QueryActivity extends javax.swing.JFrame {

    private javax.swing.JButton queryBtn;
    private javax.swing.JButton returnBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable bookTable;
    private javax.swing.JTextArea bookInfoArea;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField authorField;
    private javax.swing.JTextField typeField;

    private Statement statement;

    public static final int ROWNUM = 100000;
    public static final String[] columnName = new String[] {"编号", "书名", "作者", "出版社", "类别", "库存量", "借出量"};

    public QueryActivity(Statement statement) {
        this.statement = statement;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        bookTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        authorField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        typeField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        bookInfoArea = new javax.swing.JTextArea();
        queryBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        createBookTable();
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

        jLabel1.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jLabel1.setText("请选填以下查询条件");

        jLabel2.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel2.setText("图书名称");

        nameField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel3.setText("图书作者");

        authorField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel4.setText("图书类别");

        typeField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        typeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            }
        });

        bookInfoArea.setColumns(20);
        bookInfoArea.setRows(5);
        jScrollPane2.setViewportView(bookInfoArea);

        queryBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        queryBtn.setText("查询图书");
        queryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String author = authorField.getText();
                String type = typeField.getText();

                Object[][] selectData = null;

                if (!name.equals("") && !author.equals("") && !type.equals("")) {
                    String sql = "SELECT * FROM book WHERE Bname=" + "'" + name + "'AND "
                            + "Bauthor=" + "'" + author + "'AND " + "Btype=" + "'" + type + "'";
                    selectData = getTableData(7,sql);
                    bookTable.setModel(new javax.swing.table.DefaultTableModel(selectData, columnName));
                    JOptionPane.showMessageDialog(QueryActivity.this,"查询成功！");
                }

                if (!name.equals("") && !author.equals("") && type.equals("")) {
                    String sql = "SELECT * FROM book WHERE Bname=" + "'" + name + "'AND "
                            + "Bauthor=" + "'" + author + "'";
                    selectData = getTableData(7,sql);
                    bookTable.setModel(new javax.swing.table.DefaultTableModel(selectData, columnName));
                    JOptionPane.showMessageDialog(QueryActivity.this,"查询成功！");
                }

                if (!name.equals("") && author.equals("") && !type.equals("")) {
                    String sql = "SELECT * FROM book WHERE Bname=" + "'" + name + "'AND "
                            + "Btype=" + "'" + type + "'";
                    selectData = getTableData(7,sql);
                    bookTable.setModel(new javax.swing.table.DefaultTableModel(selectData, columnName));
                    JOptionPane.showMessageDialog(QueryActivity.this,"查询成功！");
                }

                if (name.equals("") && !author.equals("") && !type.equals("")) {
                    String sql = "SELECT * FROM book WHERE Bauthor=" + "'" + author + "'AND "
                            + "Btype=" + "'" + type + "'";
                    selectData = getTableData(7,sql);
                    bookTable.setModel(new javax.swing.table.DefaultTableModel(selectData, columnName));
                    JOptionPane.showMessageDialog(QueryActivity.this,"查询成功！");
                }

                if (!name.equals("") && author.equals("") && type.equals("")) {
                    String sql = "SELECT * FROM book WHERE Bname=" + "'" + name + "'";
                    selectData = getTableData(7,sql);
                    bookTable.setModel(new javax.swing.table.DefaultTableModel(selectData, columnName));
                    JOptionPane.showMessageDialog(QueryActivity.this,"查询成功！");
                }

                if (name.equals("") && !author.equals("") && type.equals("")) {
                    String sql = "SELECT * FROM book WHERE Bauthor=" + "'" + author + "'";
                    selectData = getTableData(7,sql);
                    bookTable.setModel(new javax.swing.table.DefaultTableModel(selectData, columnName));
                    JOptionPane.showMessageDialog(QueryActivity.this,"查询成功！");
                }

                if (name.equals("") && author.equals("") && !type.equals("")) {
                    String sql = "SELECT * FROM book WHERE Btype=" + "'" + type + "'";
                    selectData = getTableData(7,sql);
                    bookTable.setModel(new javax.swing.table.DefaultTableModel(selectData, columnName));
                    JOptionPane.showMessageDialog(QueryActivity.this,"查询成功！");
                }

                if (name.equals("") && author.equals("") && type.equals("")) {
                    JOptionPane.showMessageDialog(QueryActivity.this,"查询条件不能为空！");
                }

            }
        });

        returnBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        returnBtn.setText("返回上层");
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainActivity(statement).setVisible(true);
                QueryActivity.this.setVisible(false);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(33, 33, 33)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel4)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(typeField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel2)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel3)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addComponent(authorField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(50, 50, 50)
                                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(37, 37, 37))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(72, 72, 72)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(jLabel1)
                                                                .addGap(60, 60, 60))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(returnBtn)
                                                                        .addComponent(queryBtn))
                                                                .addGap(109, 109, 109))))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel1)
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(authorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(typeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addComponent(queryBtn)
                                .addGap(18, 18, 18)
                                .addComponent(returnBtn)
                                .addGap(45, 45, 45))
        );

        pack();
    }

    private Object[][] getTableData(int columnNum,String sql) {

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

        return data;
    }
    private void createBookTable() {
        int columnNum = 7;
        String sql = "SELECT * FROM book";
        Object[][] data = getTableData(columnNum,sql);
        bookTable.setModel(new javax.swing.table.DefaultTableModel(data, columnName));
    }


}
