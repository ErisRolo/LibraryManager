package LMS.Main;

import LMS.AccountManage.AccountItemActivity;
import LMS.AccountManage.LoginActivity;
import LMS.Database.Database;
import LMS.Query.QueryActivity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by guohouxiao on 2017/6/9.
 * 主界面
 */
public class MainActivity extends javax.swing.JFrame {

    private javax.swing.JButton queryBtn;
    private javax.swing.JButton loginBtn;
    private javax.swing.JButton accountBtn;
    private javax.swing.JLabel jLabel1;

    private Statement statement;

    public MainActivity(Statement statement) {
        this.statement = statement;
        initComponents();
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        queryBtn = new javax.swing.JButton();
        loginBtn = new javax.swing.JButton();
        accountBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("楷体", 0, 36));
        jLabel1.setText("      欢迎使用图书管理系统");

        queryBtn.setFont(new java.awt.Font("宋体", 0, 24));
        queryBtn.setText("图书查询");
        queryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new QueryActivity(statement).setVisible(true);
                MainActivity.this.setVisible(false);
            }
        });

        loginBtn.setFont(new java.awt.Font("宋体", 0, 24));
        loginBtn.setText("登录系统");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginActivity(statement).setVisible(true);
                MainActivity.this.setVisible(false);
            }
        });

        accountBtn.setFont(new java.awt.Font("宋体", 0, 24));
        accountBtn.setText("个人中心");
        accountBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new AccountItemActivity(statement).setVisible(true);
                MainActivity.this.setVisible(false);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(186, 186, 186)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(accountBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(queryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(queryBtn)
                                .addGap(44, 44, 44)
                                .addComponent(loginBtn)
                                .addGap(43, 43, 43)
                                .addComponent(accountBtn)
                                .addContainerGap(69, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    public static void main(String args[]) throws SQLException, IllegalAccessException {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LMS.Main.MainActivity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        Database database = new Database();
        MainActivity context = new MainActivity(database.statement);
        context.setVisible(true);
    }

}
