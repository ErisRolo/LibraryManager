package LMS.AccountManage;

import LMS.Administrator.AdminItemActivity;
import LMS.Main.MainActivity;
import LMS.User.UserActivity;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by guohouxiao on 2017/6/9.
 * 登录界面
 */
public class LoginActivity extends javax.swing.JFrame {

    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton okBtn;
    private javax.swing.JButton returnBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JRadioButton adminRBtn;
    private javax.swing.JRadioButton userRBtn;
    private javax.swing.JTextField loginAccountField;

    private Statement statement;

    private String loginAccount;
    private String password;

    public LoginActivity(Statement statement) {
        this.statement = statement;
        initComponents();
    }



    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        okBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();
        loginAccountField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        adminRBtn = new javax.swing.JRadioButton();
        userRBtn = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("宋体", 0, 36)); // NOI18N
        jLabel1.setText("登录图书管理系统");

        jLabel2.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel2.setText("登录账号");

        jLabel3.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel3.setText("登录密码");

        jLabel4.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel4.setText("用户类别");

        okBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        okBtn.setText("确认登录");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loginAccount = loginAccountField.getText();//获取登录账号
                password = passwordField.getText();//获取登录密码

                ResultSet existAccount = null;//账号
                ResultSet rightPassword = null;//密码
                ResultSet isExamed = null;//权限

                if (loginAccount.equals("")) {
                    JOptionPane.showMessageDialog(LoginActivity.this,"登录账号不能为空！");
                }

                if (adminRBtn.isSelected()) {
                    try {
                        existAccount = statement.executeQuery("SELECT * FROM administrator WHERE Ano=" + "'" + loginAccount + "'");
                        if (existAccount.next()) {
                            rightPassword = (statement.executeQuery("SELECT Apassword FROM administrator WHERE Ano="+ "'" + loginAccount + "'"));
                            if (rightPassword.next()) {
                                String rightpassword = rightPassword.getString(1);
                                if (password.equals(rightpassword)) {
                                    JOptionPane.showMessageDialog(LoginActivity.this,"登陆成功！");
                                    new AdminItemActivity(statement).setVisible(true);
                                    LoginActivity.this.setVisible(false);
                                } else {
                                    JOptionPane.showMessageDialog(LoginActivity.this,"密码错误！");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(LoginActivity.this,"该账号不存在！");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                if (userRBtn.isSelected()) {
                    try {
                        existAccount = statement.executeQuery("SELECT * FROM user WHERE Uno=" + "'" + loginAccount + "'");
                        if (existAccount.next()) {
                            rightPassword = (statement.executeQuery("SELECT Upassword FROM user WHERE Uno=" + "'" + loginAccount + "'"));
                            if (rightPassword.next()) {
                                String rightpassword = rightPassword.getString(1);
                                if (password.equals(rightpassword)) {
                                    isExamed = statement.executeQuery("SELECT Uisexamed FROM user WHERE Uno=" + "'" + loginAccount + "'");
                                    if (isExamed.next()) {
                                        Boolean isexamed = isExamed.getBoolean(1);
                                        if (isexamed) {
                                            JOptionPane.showMessageDialog(LoginActivity.this,"登陆成功！");
                                            new UserActivity(statement,loginAccount).setVisible(true);
                                            LoginActivity.this.setVisible(false);
                                        } else {
                                            JOptionPane.showMessageDialog(LoginActivity.this,"暂时没有权限！");
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(LoginActivity.this,"密码错误！");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(LoginActivity.this,"该账号不存在！");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        returnBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        returnBtn.setText("返回上层");
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainActivity(statement).setVisible(true);
                LoginActivity.this.setVisible(false);
            }
        });

        loginAccountField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        passwordField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        buttonGroup1.add(adminRBtn);
        adminRBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        adminRBtn.setText("管理员");

        buttonGroup1.add(userRBtn);
        userRBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        userRBtn.setText("用户");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(78, 78, 78)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jLabel4))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(loginAccountField, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                                                        .addComponent(passwordField)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(22, 22, 22)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(okBtn)
                                                                        .addComponent(adminRBtn))
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(88, 88, 88)
                                                                                .addComponent(userRBtn))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(71, 71, 71)
                                                                                .addComponent(returnBtn))))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(147, 147, 147)
                                                .addComponent(jLabel1)))
                                .addContainerGap(106, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel1)
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(loginAccountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(adminRBtn)
                                        .addComponent(userRBtn)
                                        .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(okBtn)
                                        .addComponent(returnBtn))
                                .addGap(42, 42, 42))
        );

        pack();
    }

}
