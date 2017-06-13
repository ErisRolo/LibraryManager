package LMS.AccountManage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by guohouxiao on 2017/6/10.
 * 修改密码界面
 */
public class ChangeActivity extends javax.swing.JFrame {

    private javax.swing.JButton okBtn;
    private javax.swing.JButton returnBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField oldPassField;
    private javax.swing.JPasswordField newPassField;
    private javax.swing.JPasswordField okPassField;
    private javax.swing.JTextField loginAccountField;

    private Statement statement;

    public ChangeActivity(Statement statement) {
        this.statement = statement;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        loginAccountField = new javax.swing.JTextField();
        oldPassField = new javax.swing.JPasswordField();
        newPassField = new javax.swing.JPasswordField();
        okPassField = new javax.swing.JPasswordField();
        okBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel1.setText("用户账号");

        jLabel2.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel2.setText("原密码");

        jLabel3.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel3.setText("新密码");

        jLabel4.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        jLabel4.setText("确认密码");

        loginAccountField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        oldPassField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        newPassField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        okPassField.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        okBtn.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N
        okBtn.setText("确认修改");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String loginAccount = loginAccountField.getText();//账号
                String oldPassword = oldPassField.getText();//原密码
                String newPassword = newPassField.getText();//新密码
                String okPassword = okPassField.getText();//确认密码

                ResultSet existAccount = null;//用于判断原账号是否存在
                ResultSet existPassword = null;//原密码

                if (loginAccount.equals("")) {
                    JOptionPane.showMessageDialog(ChangeActivity.this,"用户账号不能为空！");
                }

                try {
                    existAccount = statement.executeQuery("SELECT * FROM user WHERE Uno=" + "'" + loginAccount + "'");
                    if (existAccount.next()) {
                        existPassword = statement.executeQuery("SELECT Upassword FROM user WHERE Uno=" + "'" + loginAccount + "'");
                        if (existPassword.next()) {
                            String oldpassword = existPassword.getString(1);
                            if (oldPassword.equals(oldpassword)) {
                                if (newPassword.equals(okPassword)) {
                                    statement.execute("UPDATE user SET Upassword =" + "'" + newPassword + "'" + "WHERE Uno=" + "'" + loginAccount + "'");
                                    JOptionPane.showMessageDialog(ChangeActivity.this,"修改成功！");
                                    new AccountItemActivity(statement).setVisible(true);
                                    ChangeActivity.this.setVisible(false);
                                } else {
                                    JOptionPane.showMessageDialog(ChangeActivity.this,"新密码必须与确认密码相同！");
                                }
                            } else {
                                JOptionPane.showMessageDialog(ChangeActivity.this,"原密码错误！");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(ChangeActivity.this,"该账号不存在！");
                    }
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
                new AccountItemActivity(statement).setVisible(true);
                ChangeActivity.this.setVisible(false);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(107, 107, 107)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabel4)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(jLabel1)
                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                        .addGap(8, 8, 8)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel3)
                                                                                .addComponent(jLabel2)))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(loginAccountField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(oldPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(newPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(okPassField, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(162, 162, 162)
                                                .addComponent(okBtn)
                                                .addGap(84, 84, 84)
                                                .addComponent(returnBtn)))
                                .addContainerGap(115, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(loginAccountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(oldPassField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(newPassField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(okPassField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(okBtn)
                                        .addComponent(returnBtn))
                                .addGap(53, 53, 53))
        );

        pack();
    }

}